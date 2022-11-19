package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.domain;

import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.RequestRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.ResponseRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories.entities.EntityRole;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@EqualsAndHashCode
public class Role {

    private String code;
    private String name;
    private boolean isDefault;

    public static Role fromDto(RequestRoleDto requestRoleDto) {
        return Role.builder().code(requestRoleDto.code).name(requestRoleDto.displayName)
                .isDefault(requestRoleDto.isDefault).build();
    }

    public ResponseRoleDto toDto() {
        return new ResponseRoleDto(code, name, isDefault);
    }

    public EntityRole toEntity() {
        return new EntityRole(code, name, isDefault);
    }

}
