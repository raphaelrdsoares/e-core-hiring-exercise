package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.domain;

import java.util.UUID;

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

    private String id;
    private String code;
    private String name;
    private boolean isDefault;

    public void setNonDefault() {
        this.isDefault = false;
    }

    public static Role fromDto(RequestRoleDto requestRoleDto) {
        return Role.builder()
                .code(requestRoleDto.code)
                .name(requestRoleDto.displayName)
                .isDefault(requestRoleDto.isDefault)
                .build();
    }

    public static Role fromEntity(EntityRole entity) {
        return Role.builder()
                .id(entity.id.toString())
                .code(entity.code)
                .name(entity.name)
                .isDefault(entity.isDefault)
                .build();
    }

    public ResponseRoleDto toDto() {
        return new ResponseRoleDto(code, name, isDefault);
    }

    public EntityRole toEntity() {
        EntityRole entity = new EntityRole(code, name, isDefault);
        if (this.id != null) {
            entity.id = UUID.fromString(this.id);
        }
        return entity;
    }

}
