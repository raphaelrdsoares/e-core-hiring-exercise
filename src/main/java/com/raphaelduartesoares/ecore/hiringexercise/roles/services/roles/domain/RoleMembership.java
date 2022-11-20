package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.domain;

import java.util.UUID;

import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.RequestAssignRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories.entities.EntityMembership;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.InternalOperationNotAllowedException;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@EqualsAndHashCode
public class RoleMembership {

    private String id;
    private String roleCode;
    private String userId;
    private String teamId;

    public static RoleMembership fromDto(RequestAssignRoleDto requestDto) {
        return RoleMembership.builder()
                .roleCode(requestDto.roleCode)
                .userId(requestDto.userId)
                .teamId(requestDto.teamId)
                .build();
    }

    public EntityMembership toEntity() {
        EntityMembership entity = new EntityMembership(roleCode, userId, teamId);
        if (this.id != null) {
            entity.id = UUID.fromString(this.id);
        }
        return entity;
    }

    public void setRoleCode(String roleCode) throws InternalOperationNotAllowedException {
        if (roleCode == null || roleCode.isBlank()) {
            throw new InternalOperationNotAllowedException("Set domain property",
                    "It is not allowed to assign null or empty string to property 'roleCode' in RoleMembership class.");
        }
        this.roleCode = roleCode;
    }

}
