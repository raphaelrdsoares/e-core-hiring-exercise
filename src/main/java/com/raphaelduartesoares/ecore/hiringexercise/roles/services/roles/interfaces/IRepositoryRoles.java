package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces;

import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories.entities.EntityRole;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.RepositoryException;

public interface IRepositoryRoles {
    public EntityRole save(EntityRole role) throws RepositoryException;

    public EntityRole findByCode(String code);

    public EntityRole findDefault();
}
