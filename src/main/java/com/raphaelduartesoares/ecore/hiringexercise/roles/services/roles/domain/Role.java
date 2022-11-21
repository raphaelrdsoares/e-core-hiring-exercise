package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.domain;

import java.util.Optional;
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

    public static Role fromDto(RequestRoleDto requestRoleDto) {
        return Role.builder()
                .code(requestRoleDto.code)
                .name(requestRoleDto.name)
                .isDefault(requestRoleDto.isDefault)
                .build();
    }

    public static Optional<Role> fromEntity(Optional<EntityRole> entity) {
        if (entity.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(
                Role.builder()
                        .id(entity.get().id.toString())
                        .code(entity.get().code)
                        .name(entity.get().name)
                        .isDefault(entity.get().isDefault)
                        .build());
    }

}
