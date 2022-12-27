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

import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories.entities.EntityMembership;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IRepositoryMembership;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.EnvironmentVariables;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.RepositoryException;

@Component
public class RepositoryMembership extends RepositoryBase<EntityMembership> implements IRepositoryMembership {

    List<EntityMembership> entities = new ArrayList<>();

    @Autowired
    private EnvironmentVariables environmentVariables;

    @Override
    public EntityMembership save(EntityMembership entity) throws RepositoryException {
        if (entity.id == null) {
            insertEntity(entity);
        } else {
            updateEntity(entity);
        }
        persistEntitiesInFile();
        return entity;
    }

    @Override
    public Optional<EntityMembership> findByTeamAndUser(String teamId, String userId) {
        Optional<EntityMembership> entity = entities.stream()
                .filter(e -> e.teamId.equals(teamId) && e.userId.equals(userId))
                .findFirst();
        if (entity.isPresent()) {
            return entity;
        }
        return Optional.empty();
    }

    @Override
    public List<EntityMembership> findAllByRoleAndOrTeamIdAndOrUserId(String roleCode, String teamId,
            String userId) {
        List<EntityMembership> foundEntities = entities.stream().filter(e -> {
            boolean isEntityInFilter = true;
            if (roleCode != null) {
                isEntityInFilter = isEntityInFilter && e.roleCode.equals(roleCode);
            }
            if (teamId != null) {
                isEntityInFilter = isEntityInFilter && e.teamId.equals(teamId);
            }
            if (userId != null) {
                isEntityInFilter = isEntityInFilter && e.userId.equals(userId);
            }
            return isEntityInFilter;
        }).toList();

        return foundEntities;
    }

    public void deleteAll() throws RepositoryException {
        entities = new ArrayList<>();
        persistEntitiesInFile();
    }

    private void insertEntity(EntityMembership entity) {
        entity.id = UUID.randomUUID();
        entity.createdAt = Timestamp.valueOf(LocalDateTime.now());
        entity.updatedAt = Timestamp.valueOf(LocalDateTime.now());
        entities.add(entity);
    }

    private void updateEntity(EntityMembership entity) throws RepositoryException {
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
        entities = loadEntitiesFromFile(EntityMembership[].class);
    }

    @Override
    String getDatabaseFilePath() {
        return environmentVariables.getDatabaseSchemaMembershipFilePath();

    }

    @Override
    List<EntityMembership> getEntities() {
        return entities;
    }

}
