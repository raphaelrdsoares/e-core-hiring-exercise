package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories.entities;

import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.infrastructure.repository.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder()
public class EntityMembership extends BaseEntity {
    public String roleCode;
    public String userId;
    public String teamId;

}
