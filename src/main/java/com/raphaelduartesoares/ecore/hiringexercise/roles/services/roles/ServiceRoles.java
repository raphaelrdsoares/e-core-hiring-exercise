package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.RequestRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.ResponseRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.ResponseTeamDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.ResponseUserDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IRepositoryRoles;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IServiceRoles;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.usecases.UseCaseCreateRole;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.DuplicatedEntityException;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.RepositoryException;

@Component
public class ServiceRoles implements IServiceRoles {

    @Autowired
    private IRepositoryRoles repositoryRoles;

    @Override
    public ResponseRoleDto createRole(RequestRoleDto roleDto) throws DuplicatedEntityException, RepositoryException {
        return new UseCaseCreateRole(repositoryRoles).createRole(roleDto);
    }

    @Override
    public void assignRole(String roleCode, String teamId, String userId) {
        // TODO - Código da vaga é opcional
        // TODO - Verificar se a role existe
        // TODO - Verificar se o usuário existe
        // TODO - Verificar se o time existe
        // TODO - Verificar se o usuário está dentro do time
        // TODO Auto-generated method stub

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
