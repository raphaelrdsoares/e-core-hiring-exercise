package com.raphaelduartesoares.ecore.hiringexercise.roles.services.seed.usecases;

import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.RequestRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IServiceRoles;

public class UseCaseSeedRoles {
    private IServiceRoles serviceRoles;

    public UseCaseSeedRoles(IServiceRoles serviceRoles) {
        this.serviceRoles = serviceRoles;
    }

    public void seedRoles() {
        serviceRoles.createRole(new RequestRoleDto("dev", "Developer", true));
        serviceRoles.createRole(new RequestRoleDto("qa", "Tester"));
        serviceRoles.createRole(new RequestRoleDto("po", "Product Owner"));
    }
}
