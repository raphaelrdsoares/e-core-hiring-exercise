package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories.entities.EntityRole;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IRepositoryRoles;

@Component
public class RepositoryRole implements IRepositoryRoles {

    @Override
    public EntityRole save(EntityRole role) {
        role.id = UUID.fromString("9a7779c9-6adc-5254-85ba-1e39e7828962");
        // TODO Auto-generated method stub
        return role;
    }

    @Override
    public EntityRole findByCode(String code) {
        // TODO Auto-generated method stub
        return null;
    }

}
