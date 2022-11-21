package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces;

import java.util.Optional;

import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories.entities.EntityRole;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.RepositoryException;

public interface IRepositoryRoles {
    public EntityRole save(EntityRole role) throws RepositoryException;

    public Optional<EntityRole> findByCode(String code);

    public Optional<EntityRole> findDefault();
}
