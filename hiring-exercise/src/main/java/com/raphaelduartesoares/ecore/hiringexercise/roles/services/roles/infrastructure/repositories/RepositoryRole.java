package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories.entities.EntityRole;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IRepositoryRoles;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.EnvironmentVariables;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.RepositoryException;

@Component
public class RepositoryRole extends RepositoryBase<EntityRole> implements IRepositoryRoles {

    List<EntityRole> entities = new ArrayList<>();

    @Autowired
    private EnvironmentVariables environmentVariables;

    @Override
    public EntityRole save(EntityRole entity) throws RepositoryException {
        if (entity.id == null) {
            insertEntity(entity);
        } else {
            updateEntity(entity);
        }
        persistEntitiesInFile();
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

    public void deleteAll() throws RepositoryException {
        entities = new ArrayList<>();
        persistEntitiesInFile();
    }

    private void insertEntity(EntityRole entity) {
        entity.id = UUID.randomUUID();
        entity.createdAt = Timestamp.valueOf(LocalDateTime.now());
        entity.updatedAt = Timestamp.valueOf(LocalDateTime.now());
        entities.add(entity);
    }

    private void updateEntity(EntityRole entity) throws RepositoryException {
        OptionalInt existingRoleIndex = IntStream
                .range(0, entities.size())
                .filter(i -> entity.id.equals(entities.get(i).id))
                .findFirst();

        if (existingRoleIndex.isEmpty()) {
            throw new RepositoryException("Error updating entity", "entity id not found");
        }
        entity.createdAt = entities.get(existingRoleIndex.getAsInt()).createdAt;
        entity.updatedAt = Timestamp.valueOf(LocalDateTime.now());
        int index = existingRoleIndex.getAsInt();
        entities.set(index, entity);
    }

    @PostConstruct
    private void loadEntities() throws RepositoryException {
        entities = loadEntitiesFromFile(EntityRole[].class);
    }

    @Override
    String getDatabaseFilePath() {
        return environmentVariables.getDatabaseSchemaRolesFilePath();
    }

    @Override
    List<EntityRole> getEntities() {
        return entities;
    }

}
