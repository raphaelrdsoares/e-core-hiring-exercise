package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.RequestAssignRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.RequestRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.ResponseRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.ResponseTeamDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.ResponseUserDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IIntegrationMicroserviceTeams;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IRepositoryMembership;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IRepositoryRoles;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IServiceRoles;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.usecases.UseCaseAssignRole;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.usecases.UseCaseCreateRole;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.DuplicatedEntityException;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.InternalOperationNotAllowedException;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.NotFoundException;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.RepositoryException;

@Component
public class ServiceRoles implements IServiceRoles {

    @Autowired
    private IRepositoryRoles repositoryRoles;
    @Autowired
    private IRepositoryMembership repositoryMembership;
    @Autowired
    private IIntegrationMicroserviceTeams microserviceTeams;

    @Override
    public ResponseRoleDto createRole(RequestRoleDto roleDto) throws DuplicatedEntityException, RepositoryException {
        return new UseCaseCreateRole(repositoryRoles).createRole(roleDto);
    }

    @Override
    public void assignRole(RequestAssignRoleDto requestAssignRole)
            throws NotFoundException, InternalOperationNotAllowedException, RepositoryException {
        new UseCaseAssignRole(repositoryRoles, repositoryMembership, microserviceTeams).assignRole(requestAssignRole);
    }

    @Override
    public ResponseRoleDto lookUpMembership(String teamId, String userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ResponseUserDto> lookUpTeamMemberships(String roleCode, String teamId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ResponseTeamDto> lookUpUserMemberships(String roleCode, String userId) {
        // TODO Auto-generated method stub
        return null;
    }
}
