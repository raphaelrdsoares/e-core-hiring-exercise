package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles;

import java.util.List;

import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.RequestRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.ResponseRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.ResponseTeamDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.ResponseUserDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IServiceRoles;

public class ServiceRoles implements IServiceRoles {

    @Override
    public ResponseRoleDto createRole(RequestRoleDto roleDto) {
        // TODO - Verificar se a role existe pelo code
        return null;
    }

    @Override
    public void assignRole(String roleCode, String teamId, String userId) {
        // TODO - Código da vaga é opcional
        // TODO - Verificar se a role existe
        // TODO - Verificar se o usuário existe
        // TODO - Verificar se o time existe
        // TODO - Verificar se o usuário está dentro do time existe
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
