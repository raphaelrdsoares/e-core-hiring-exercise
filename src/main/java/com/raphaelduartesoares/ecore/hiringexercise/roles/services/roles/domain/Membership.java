package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.domain;

import java.util.Optional;
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
public class Membership {

    private String id;
    private String roleCode;
    private String userId;
    private String teamId;

    public static Membership fromDto(RequestAssignRoleDto requestDto) {
        return Membership.builder()
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
                    "It is not allowed to assign null or empty string to property 'roleCode' in Membership class.");
        }
        this.roleCode = roleCode;
    }

    public boolean isRoleCodeNullOrEmpty() {
        return roleCode == null || roleCode.isBlank();
    }

    public static Optional<Membership> fromEntity(Optional<EntityMembership> entity) {
        if (entity.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(Membership.builder()
                .id(entity.get().id.toString())
                .roleCode(entity.get().roleCode)
                .userId(entity.get().userId)
                .teamId(entity.get().teamId)
                .build());
    }

}
