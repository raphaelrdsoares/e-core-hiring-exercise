package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces;

import java.util.Optional;

import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories.entities.EntityMembership;

public interface IRepositoryMembership {
    public EntityMembership save(EntityMembership role);

    public Optional<EntityMembership> findByRoleAndTeamAndUse(String roleCode, String teamId, String userId);
}
