package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces;

import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories.entities.EntityRole;

public interface IRepositoryRoles {
    public EntityRole save(EntityRole role);

    public EntityRole findByCode(String code);
}
