package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.usecases;

import java.util.Optional;

import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.RequestAssignRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.domain.Role;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.domain.Membership;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.domain.Team;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories.entities.EntityMembership;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IIntegrationMicroserviceTeams;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IRepositoryMembership;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IRepositoryRoles;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.InternalOperationNotAllowedException;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.NotFoundException;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.RepositoryException;

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
            throws NotFoundException, InternalOperationNotAllowedException, RepositoryException {
        Membership membership = Membership.fromDto(requestDto);

        if (membership.isRoleCodeNullOrEmpty()) {
            assignDefaultRoleToMembership(membership);
        } else {
            checkIfRoleExistsByCode(membership);
        }

        checkIfTeamExistsAndUserExistsInTeam(membership);

        saveMembership(membership);
    }

    private void assignDefaultRoleToMembership(Membership membership)
            throws NotFoundException, InternalOperationNotAllowedException {
        Optional<Role> defaultExistingRole = Role.fromEntity(repositoryRoles.findDefault());
        if (defaultExistingRole.isEmpty()) {
            throw new NotFoundException("Entity not found", "Default role does not exists");
        }
        membership.setRoleCode(defaultExistingRole.get().getCode());
    }

    private void checkIfRoleExistsByCode(Membership membership) throws NotFoundException {
        Optional<Role> existingRole = Role.fromEntity(repositoryRoles.findByCode(membership.getRoleCode()));
        if (existingRole.isEmpty()) {
            throw new NotFoundException("Entity not found",
                    String.format("There is no role with code '%s'", membership.getRoleCode()));
        }
    }

    private void checkIfTeamExistsAndUserExistsInTeam(Membership membership) throws NotFoundException {
        Optional<Team> team = Team.fromDto(microserviceTeams.getTeamById(membership.getTeamId()));
        checkIfTeamExists(membership, team);
        checkIfUserExistsInTeam(membership, team.get());
    }

    private void checkIfTeamExists(Membership membership, Optional<Team> team) throws NotFoundException {
        if (team.isEmpty()) {
            throw new NotFoundException("Entity not found",
                    String.format("There is no team with id '%s'", membership.getTeamId()));
        }
    }

    private void checkIfUserExistsInTeam(Membership membership, Team team) throws NotFoundException {
        boolean userExistsInTeamMembers = team.getTeamMemberIds().stream()
                .anyMatch(id -> id.equals(membership.getUserId()));
        boolean isUserTeamLead = membership.getUserId().equals(team.getTeamLeadId());

        if (!userExistsInTeamMembers && !isUserTeamLead) {
            throw new NotFoundException("Entity not found",
                    String.format("There is no user '%s' in team '%s'", membership.getUserId(),
                            membership.getTeamId()));

        }
    }

    private void saveMembership(Membership membership)
            throws RepositoryException, InternalOperationNotAllowedException {
        Optional<Membership> existingMembership = Membership.fromEntity(repositoryMembership.findByTeamAndUser(
                membership.getTeamId(), membership.getUserId()));
        if (existingMembership.isPresent()) {
            existingMembership.get().setRoleCode(membership.getRoleCode());
            membership = existingMembership.get();
        }
        EntityMembership entity = membership.toEntity();
        repositoryMembership.save(entity);
    }

}
