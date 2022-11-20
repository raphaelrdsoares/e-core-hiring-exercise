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
public class EntityRole extends BaseEntity {
    public String code;
    public String name;
    public boolean isDefault;
}
