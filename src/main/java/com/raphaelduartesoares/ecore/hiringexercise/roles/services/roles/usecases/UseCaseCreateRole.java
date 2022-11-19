package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.usecases;

import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.exceptions.DuplicatedEntityException;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.RequestRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.ResponseRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.domain.Role;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories.entities.EntityRole;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IRepositoryRoles;

public class UseCaseCreateRole {

    private IRepositoryRoles repositoryRoles;

    public UseCaseCreateRole(IRepositoryRoles repositoryRoles) {
        this.repositoryRoles = repositoryRoles;
    }

    public ResponseRoleDto createRole(RequestRoleDto requestRoleDto) throws DuplicatedEntityException {

        Role role = Role.fromDto(requestRoleDto);

        checkIfRoleAlreadyExists(role);

        repositoryRoles.save(role.toEntity());

        return role.toDto();
    }

    private void checkIfRoleAlreadyExists(Role role) throws DuplicatedEntityException {
        EntityRole existentEntity = repositoryRoles.findByCode(role.getCode());
        if (existentEntity != null) {
            throw new DuplicatedEntityException("Role already exists", String.format(
                    "Already exists a role with code '%s'", role.getCode()));
        }
    }
}
