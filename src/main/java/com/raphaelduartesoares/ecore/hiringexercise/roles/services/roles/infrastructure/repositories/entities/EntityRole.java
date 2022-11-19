package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories.entities;

import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.infrastructure.repository.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Builder
public class EntityRole extends BaseEntity {
    public String code;
    public String name;
    public boolean isDefault;

}
