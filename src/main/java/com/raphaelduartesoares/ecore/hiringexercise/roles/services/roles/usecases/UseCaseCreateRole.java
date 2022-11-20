package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.usecases;

import java.util.Optional;

import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.RequestRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.ResponseRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.domain.Role;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories.entities.EntityRole;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IRepositoryRoles;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.DuplicatedEntityException;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.RepositoryException;

public class UseCaseCreateRole {

    private IRepositoryRoles repositoryRoles;

    public UseCaseCreateRole(IRepositoryRoles repositoryRoles) {
        this.repositoryRoles = repositoryRoles;
    }

    public ResponseRoleDto createRole(RequestRoleDto requestRoleDto)
            throws DuplicatedEntityException, RepositoryException {

        Role role = Role.fromDto(requestRoleDto);

        checkIfRoleAlreadyExists(role);

        updatesExistentRolesToNonDefaultIfNewRoleIsDefault(role);

        saveRole(role);

        return role.toDto();
    }

    private void checkIfRoleAlreadyExists(Role role) throws DuplicatedEntityException {
        EntityRole existentEntity = repositoryRoles.findByCode(role.getCode());
        if (existentEntity != null) {
            throw new DuplicatedEntityException("Role already exists", String.format(
                    "Already exists a role with code '%s'", role.getCode()));
        }
    }

    private void updatesExistentRolesToNonDefaultIfNewRoleIsDefault(Role role) throws RepositoryException {
        if (role.isDefault()) {
            updateExistingDefaultRoleToNonDefault();
        }
    }

    private void updateExistingDefaultRoleToNonDefault() throws RepositoryException {
        Optional<Role> defaultRole = Role.fromEntity(repositoryRoles.findDefault());
        if (defaultRole.isPresent()) {
            defaultRole.get().setNonDefault();
            repositoryRoles.save(defaultRole.get().toEntity());
        }
    }

    private void saveRole(Role role) throws RepositoryException {
        EntityRole savedEntity = repositoryRoles.save(role.toEntity());
        role = Role.fromEntity(savedEntity).get();
    }

}
