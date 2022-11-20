package com.raphaelduartesoares.ecore.hiringexercise.roles.services.seed.usecases;

import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.RequestRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IServiceRoles;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.DuplicatedEntityException;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.RepositoryException;

public class UseCaseSeedRoles {
    private IServiceRoles serviceRoles;

    public UseCaseSeedRoles(IServiceRoles serviceRoles) {
        this.serviceRoles = serviceRoles;
    }

    public void seedRoles() {
        seedDeveloperRole();
        seedProductOwnerRole();
        seedTesterRole();
    }

    private void seedTesterRole() {
        try {
            serviceRoles.createRole(new RequestRoleDto("po", "Product Owner"));
        } catch (DuplicatedEntityException | RepositoryException e) {
        }
    }

    private void seedProductOwnerRole() {
        try {
            serviceRoles.createRole(new RequestRoleDto("qa", "Tester"));
        } catch (DuplicatedEntityException | RepositoryException e) {
        }
    }

    private void seedDeveloperRole() {
        try {
            serviceRoles.createRole(new RequestRoleDto("dev", "Developer", true));
        } catch (DuplicatedEntityException | RepositoryException e) {
        }
    }
}
