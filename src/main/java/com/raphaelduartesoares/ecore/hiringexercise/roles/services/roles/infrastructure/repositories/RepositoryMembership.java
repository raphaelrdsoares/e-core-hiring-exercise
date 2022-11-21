package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories.entities.EntityMembership;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IRepositoryMembership;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.RepositoryException;

@Component
public class RepositoryMembership implements IRepositoryMembership {

    List<EntityMembership> entities = new ArrayList<>();

    @Override
    public EntityMembership save(EntityMembership entity) throws RepositoryException {
        if (entity.id == null) {
            insertEntity(entity);
        } else {
            updateEntity(entity);
        }
        printDatabase();
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

    private void insertEntity(EntityMembership entity) {
        entity.id = UUID.randomUUID();
        entities.add(entity);
    }

    private void updateEntity(EntityMembership entity) throws RepositoryException {
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
        System.out.println("Membership Database:");
        System.out.println(entities);
    }

}
