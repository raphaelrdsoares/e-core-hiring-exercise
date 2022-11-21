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

    List<EntityRole> entities = new ArrayList<>();

    @Override
    public EntityRole save(EntityRole entity) throws RepositoryException {
        if (entity.id == null) {
            insertEntity(entity);
        } else {
            updateEntity(entity);
        }
        printDatabase();
        return entity;
    }

    @Override
    public Optional<EntityRole> findByCode(String code) {
        Optional<EntityRole> existingRole = entities.stream().filter(role -> role.code.equals(code)).findFirst();
        if (existingRole.isPresent()) {
            return existingRole;
        }
        return Optional.empty();
    }

    @Override
    public Optional<EntityRole> findDefault() {
        Optional<EntityRole> defaultRole = entities.stream().filter(role -> role.isDefault).findFirst();
        if (defaultRole.isPresent()) {
            return defaultRole;
        }
        return Optional.empty();
    }

    private void insertEntity(EntityRole entity) {
        entity.id = UUID.randomUUID();
        entities.add(entity);
    }

    private void updateEntity(EntityRole entity) throws RepositoryException {
        OptionalInt existingRole = IntStream
                .range(0, entities.size())
                .filter(i -> entity.id.equals(entities.get(i).id))
                .findFirst();

        if (existingRole.isEmpty()) {
            throw new RepositoryException("Error updating entity", "entity id not found");
        }
        int index = existingRole.getAsInt();
        entities.set(index, entity);
    }

    private void printDatabase() {
        System.out.println("Roles Database:");
        System.out.println(entities);
    }

}
