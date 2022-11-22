package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces;

import java.util.List;

import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.RequestAssignRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.RequestLookUpMembershipDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.RequestRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.ResponseMembershipDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.ResponseRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.DuplicatedEntityException;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.InternalOperationNotAllowedException;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.InvalidInputDataException;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.NotFoundException;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.RepositoryException;

public interface IServiceRoles {
    public ResponseRoleDto createRole(RequestRoleDto requestRole) throws DuplicatedEntityException, RepositoryException;

    public ResponseMembershipDto assignRole(RequestAssignRoleDto requestAssignRole)
            throws NotFoundException, InternalOperationNotAllowedException, RepositoryException;

    public List<ResponseMembershipDto> lookUpMembership(RequestLookUpMembershipDto requestLookUpMembership)
            throws InvalidInputDataException;

}
