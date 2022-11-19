package com.raphaelduartesoares.ecore.hiringexercise.roles.services.seed;

import org.springframework.beans.factory.annotation.Autowired;

import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IServiceRoles;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.seed.interfaces.IServiceSeed;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.seed.usecases.UseCaseSeedRoles;

public class ServiceSeed implements IServiceSeed {

    @Autowired
    private IServiceRoles serviceRoles;

    @Override
    public void seedRoles() {
        new UseCaseSeedRoles(serviceRoles).seedRoles();
    }

}
