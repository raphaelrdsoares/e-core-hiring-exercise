package com.raphaelduartesoares.ecore.hiringexercise.roles.services.seed;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IServiceRoles;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.seed.interfaces.IServiceSeed;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.seed.usecases.UseCaseSeedRoles;

@Component
public class ServiceSeed implements IServiceSeed {

    @Autowired
    private IServiceRoles serviceRoles;

    @Override
    @PostConstruct
    public void seedRoles() {
        new UseCaseSeedRoles(serviceRoles).seedRoles();
    }

}
