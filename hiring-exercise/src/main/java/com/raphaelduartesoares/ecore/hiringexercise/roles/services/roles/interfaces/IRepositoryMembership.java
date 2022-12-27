package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces;

import java.util.List;
import java.util.Optional;

import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories.entities.EntityMembership;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.RepositoryException;

public interface IRepositoryMembership {
    public EntityMembership save(EntityMembership role) throws RepositoryException;

    public Optional<EntityMembership> findByTeamAndUser(String teamId, String userId);

    public List<EntityMembership> findAllByRoleAndOrTeamIdAndOrUserId(String roleCodeDev, String teamId, String userId);

}
