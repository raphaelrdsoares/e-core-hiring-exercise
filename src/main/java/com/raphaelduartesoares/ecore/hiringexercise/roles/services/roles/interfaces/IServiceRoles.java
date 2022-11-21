package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces;

import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.RequestAssignRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.RequestRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.ResponseRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.ResponseTeamDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.ResponseUserDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.DuplicatedEntityException;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.InternalOperationNotAllowedException;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.NotFoundException;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.RepositoryException;

import java.util.List;

public interface IServiceRoles {
    public ResponseRoleDto createRole(RequestRoleDto requestRole) throws DuplicatedEntityException, RepositoryException;

    public void assignRole(RequestAssignRoleDto requestAssignRole)
            throws NotFoundException, InternalOperationNotAllowedException, RepositoryException;

    public ResponseRoleDto lookUpMembership(String teamId, String userId);

    public List<ResponseUserDto> lookUpTeamMemberships(String roleCode, String teamId);

    public List<ResponseTeamDto> lookUpUserMemberships(String roleCode, String userId);
}
