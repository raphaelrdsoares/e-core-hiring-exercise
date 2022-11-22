package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        insertEntity(entity);
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
