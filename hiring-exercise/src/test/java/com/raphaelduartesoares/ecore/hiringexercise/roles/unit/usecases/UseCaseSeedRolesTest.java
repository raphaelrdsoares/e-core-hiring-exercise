package com.raphaelduartesoares.ecore.hiringexercise.roles.unit.usecases;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.RequestRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IServiceRoles;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.seed.usecases.UseCaseSeedRoles;

public class UseCaseSeedRolesTest {

    @Mock
    private IServiceRoles serviceRoles;

    private UseCaseSeedRoles useCase;

    @BeforeEach
    void setup() throws Exception {
        MockitoAnnotations.openMocks(this);

        useCase = new UseCaseSeedRoles(serviceRoles);
    }

    @Test
    public void shouldSeedDevQaPoRoles() throws Exception {

        useCase.seedRoles();

        verify(serviceRoles, times(3)).createRole(Mockito.any(RequestRoleDto.class));
        verify(serviceRoles, times(1)).createRole(new RequestRoleDto("dev", "Developer", true));
        verify(serviceRoles, times(1)).createRole(new RequestRoleDto("qa", "Tester", false));
        verify(serviceRoles, times(1)).createRole(new RequestRoleDto("po", "Product Owner", false));

    }

}
