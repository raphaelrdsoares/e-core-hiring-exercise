package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.usecases;

import java.util.Optional;

import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.RequestAssignRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.domain.Role;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.domain.RoleMembership;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.domain.Team;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories.entities.EntityMembership;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IIntegrationMicroserviceTeams;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IRepositoryMembership;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IRepositoryRoles;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.InternalOperationNotAllowedException;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.NotFoundException;

public class UseCaseAssignRole {

    private IRepositoryRoles repositoryRoles;
    private IRepositoryMembership repositoryMembership;
    private IIntegrationMicroserviceTeams microserviceTeams;

    public UseCaseAssignRole(IRepositoryRoles repositoryRoles, IRepositoryMembership repositoryMembership,
            IIntegrationMicroserviceTeams microserviceTeams) {
        this.repositoryRoles = repositoryRoles;
        this.repositoryMembership = repositoryMembership;
        this.microserviceTeams = microserviceTeams;
    }

    public void assignRole(RequestAssignRoleDto requestDto)
            throws NotFoundException, InternalOperationNotAllowedException {
        RoleMembership roleMembership = RoleMembership.fromDto(requestDto);

        if (roleMembership.getRoleCode() == null) {
            assignDefaultRoleToMembership(roleMembership);
        } else {
            checkIfRoleExistsByCode(roleMembership);
        }

        checkIfTeamExistsAndUserExistsInTeam(roleMembership);

        saveMembership(roleMembership);
    }

    private void assignDefaultRoleToMembership(RoleMembership roleMembership)
            throws NotFoundException, InternalOperationNotAllowedException {
        Optional<Role> defaultExistingRole = Role.fromEntity(repositoryRoles.findDefault());
        if (defaultExistingRole.isEmpty()) {
            throw new NotFoundException("Entity not found", "Default role does not exists");
        }
        roleMembership.setRoleCode(defaultExistingRole.get().getCode());
    }

    private void checkIfRoleExistsByCode(RoleMembership roleMembership) throws NotFoundException {
        Optional<Role> existingRole = Role.fromEntity(repositoryRoles.findByCode(roleMembership.getRoleCode()));
        if (existingRole.isEmpty()) {
            throw new NotFoundException("Entity not found",
                    String.format("There is no role with code '%s'", roleMembership.getRoleCode()));
        }
    }

    private void checkIfTeamExistsAndUserExistsInTeam(RoleMembership roleMembership) throws NotFoundException {
        Optional<Team> team = Team.fromDto(microserviceTeams.getTeamById(roleMembership.getTeamId()));
        checkIfTeamExists(roleMembership, team);
        checkIfUserExistsInTeam(roleMembership, team);
    }

    private void checkIfTeamExists(RoleMembership roleMembership, Optional<Team> team) throws NotFoundException {
        if (team.isEmpty()) {
            throw new NotFoundException("Entity not found",
                    String.format("There is no team with id '%s'", roleMembership.getTeamId()));
        }
    }

    private void checkIfUserExistsInTeam(RoleMembership roleMembership, Optional<Team> team) throws NotFoundException {
        boolean userExistsInTeam = team.get().getTeamMemberIds().stream()
                .anyMatch(id -> id.equals(roleMembership.getUserId()));
        if (!userExistsInTeam) {
            throw new NotFoundException("Entity not found",
                    String.format("There is no user '%s' in team '%s'", roleMembership.getUserId(),
                            roleMembership.getTeamId()));

        }
    }

    private void saveMembership(RoleMembership roleMembership) {
        Optional<EntityMembership> existingMembership = repositoryMembership.findByRoleAndTeamAndUse(
                roleMembership.getRoleCode(), roleMembership.getTeamId(), roleMembership.getUserId());
        if (existingMembership.isEmpty()) {
            EntityMembership entity = roleMembership.toEntity();
            repositoryMembership.save(entity);
        }
    }

}
