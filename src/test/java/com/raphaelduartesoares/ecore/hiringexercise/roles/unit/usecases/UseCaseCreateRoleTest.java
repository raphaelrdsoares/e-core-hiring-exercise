package com.raphaelduartesoares.ecore.hiringexercise.roles.unit.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.exceptions.DuplicatedEntityException;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.RequestRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.ResponseRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories.entities.EntityRole;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IRepositoryRoles;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.usecases.UseCaseCreateRole;

public class UseCaseCreateRoleTest {

    @Mock
    private IRepositoryRoles repositoryRoles;

    private UseCaseCreateRole useCase;

    private String roleCode = "dev";
    private String roleDisplayName = "Developer";
    private String uuid = "dc82bf91-d3a1-5afc-8b66-3959ae896f05";
    private EntityRole mockEntity;

    @BeforeEach
    void setup() throws Exception {
        MockitoAnnotations.openMocks(this);

        useCase = new UseCaseCreateRole(repositoryRoles);

        mockEntity = new EntityRole(roleCode, roleDisplayName, false);
        mockEntity.id = UUID.fromString(uuid);
        mockEntity.createdAt = LocalDateTime.now();
        mockEntity.updatedAt = LocalDateTime.now();
    }

    @Test
    public void shouldInsertRoleIfDoesNotExists() throws DuplicatedEntityException {
        ResponseRoleDto mockResponse = new ResponseRoleDto(roleCode, roleDisplayName, false);
        RequestRoleDto request = new RequestRoleDto(roleCode, roleDisplayName);
        when(repositoryRoles.save(Mockito.any(EntityRole.class))).thenReturn(mockEntity);

        ResponseRoleDto response = useCase.createRole(request);

        verify(repositoryRoles, times(1)).save(new EntityRole(roleCode, roleDisplayName, false));
        assertEquals(mockResponse, response);
    }

    @Test
    public void shouldValidateIfRoleExistsBeforeInsert() {
        RequestRoleDto request = new RequestRoleDto(roleCode, roleDisplayName);
        when(repositoryRoles.findByCode(roleCode)).thenReturn(mockEntity);

        DuplicatedEntityException exception = assertThrows(
                DuplicatedEntityException.class,
                new Executable() {
                    @Override
                    public void execute() throws Exception {
                        useCase.createRole(request);

                    }
                });

        verify(repositoryRoles, times(1)).findByCode(roleCode);
        assertEquals("[Already exists a role with code 'dev']", exception.errors.toString());
        assertEquals("Role already exists", exception.getMessage());
    }

    // TODO - testar que, quando é enviado uma role default, as outras roles são
    // modificadas para não default
}
