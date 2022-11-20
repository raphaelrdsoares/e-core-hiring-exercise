package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces;

import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.RequestRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.ResponseRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.ResponseTeamDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.ResponseUserDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.DuplicatedEntityException;

import java.util.List;

public interface IServiceRoles {
    public ResponseRoleDto createRole(RequestRoleDto roleDto) throws DuplicatedEntityException;

    public void assignRole(String roleCode, String teamId, String userId);

    public ResponseRoleDto lookUpMembership(String teamId, String userId);

    public List<ResponseUserDto> lookUpTeamMemberships(String roleCode, String teamId);

    public List<ResponseTeamDto> lookUpUserMemberships(String roleCode, String userId);
}
