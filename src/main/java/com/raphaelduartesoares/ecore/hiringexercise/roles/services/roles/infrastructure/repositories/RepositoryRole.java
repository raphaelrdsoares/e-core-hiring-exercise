package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories.entities.EntityRole;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IRepositoryRoles;

@Component
public class RepositoryRole implements IRepositoryRoles {

    List<EntityRole> roles = new ArrayList<>();

    @Override
    public EntityRole save(EntityRole role) {
        if (role.id == null) {
            role.id = UUID.randomUUID();
        }
        roles.add(role);
        return role;
    }

    @Override
    public EntityRole findByCode(String code) {
        Optional<EntityRole> existingRole = roles.stream().filter(role -> role.code.equals(code)).findFirst();
        if (existingRole.isPresent()) {
            return existingRole.get();
        }
        return null;
    }

    @Override
    public EntityRole findDefault() {
        Optional<EntityRole> defaultRole = roles.stream().filter(role -> role.isDefault).findFirst();
        if (defaultRole.isPresent()) {
            return defaultRole.get();
        }
        return null;
    }

}
