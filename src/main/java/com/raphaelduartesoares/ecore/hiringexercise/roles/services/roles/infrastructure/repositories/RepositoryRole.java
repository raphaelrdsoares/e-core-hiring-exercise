package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories.entities.EntityRole;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IRepositoryRoles;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.RepositoryException;

@Component
public class RepositoryRole implements IRepositoryRoles {

    List<EntityRole> roles = new ArrayList<>();

    @Override
    public EntityRole save(EntityRole role) throws RepositoryException {
        if (role.id == null) {
            insertRole(role);
        } else {
            updateRole(role);
        }
        printDatabase();
        return role;
    }

    @Override
    public EntityRole findByCode(String code) {
        printDatabase();
        Optional<EntityRole> existingRole = roles.stream().filter(role -> role.code.equals(code)).findFirst();
        if (existingRole.isPresent()) {
            return existingRole.get();
        }
        return null;
    }

    @Override
    public EntityRole findDefault() {
        printDatabase();
        Optional<EntityRole> defaultRole = roles.stream().filter(role -> role.isDefault).findFirst();
        if (defaultRole.isPresent()) {
            return defaultRole.get();
        }
        return null;
    }

    private void insertRole(EntityRole role) {
        role.id = UUID.randomUUID();
        roles.add(role);
    }

    private void updateRole(EntityRole role) throws RepositoryException {
        OptionalInt existingRole = IntStream
                .range(0, roles.size())
                .filter(i -> role.id.equals(roles.get(i).id))
                .findFirst();

        if (existingRole.isEmpty()) {
            throw new RepositoryException("Error updating entity", "entity id not found");
        }
        int index = existingRole.getAsInt();
        roles.set(index, role);
    }

    private void printDatabase() {
        System.out.println(roles);
    }

}
