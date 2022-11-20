package com.raphaelduartesoares.ecore.hiringexercise.roles.unit.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
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
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.infrastructure.repository.BaseEntity;

public class UseCaseCreateRoleTest {

    @Mock
    private IRepositoryRoles repositoryRoles;

    private UseCaseCreateRole useCase;

    private String roleCode = "dev";
    private String roleDisplayName = "Developer";
    private String uuid = "dc82bf91-d3a1-5afc-8b66-3959ae896f05";
    private EntityRole.EntityRoleBuilder mockEntityBuilder;

    @BeforeEach
    void setup() throws Exception {
        MockitoAnnotations.openMocks(this);

        useCase = new UseCaseCreateRole(repositoryRoles);

        mockEntityBuilder = EntityRole.builder()
                .code(roleCode)
                .name(roleDisplayName)
                .isDefault(false)
                .id(UUID.fromString(uuid))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now());
    }

    @Test
    public void shouldInsertRoleIfDoesNotExists() throws DuplicatedEntityException {
        ResponseRoleDto mockResponse = new ResponseRoleDto(roleCode, roleDisplayName, false);
        RequestRoleDto request = new RequestRoleDto(roleCode, roleDisplayName);
        when(repositoryRoles.save(Mockito.any(EntityRole.class))).thenReturn(mockEntityBuilder.build());

        ResponseRoleDto response = useCase.createRole(request);

        verify(repositoryRoles, times(1)).save(new EntityRole(roleCode, roleDisplayName, false));
        assertEquals(mockResponse, response);
    }

    @Test
    public void shouldValidateIfRoleExistsBeforeInsert() {
        RequestRoleDto request = new RequestRoleDto(roleCode, roleDisplayName);
        when(repositoryRoles.findByCode(roleCode)).thenReturn(mockEntityBuilder.build());

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

    @Test
    public void shouldUpdateExistingDefaultRoleToFalseWhenCreateNewDefaultRole() throws DuplicatedEntityException {
        EntityRole mockEntityDefault = (EntityRole) mockEntityBuilder.code("deva").isDefault(true)
                .id(UUID.fromString("c95f5701-a136-55ad-867b-9748db868af8")).build();
        when(repositoryRoles.findDefault()).thenReturn(mockEntityDefault);
        when(repositoryRoles.findByCode(roleCode)).thenReturn(null);
        when(repositoryRoles.save(Mockito.any(EntityRole.class))).thenReturn(mockEntityBuilder.isDefault(true).build());

        ResponseRoleDto response = useCase.createRole(new RequestRoleDto(roleCode, roleDisplayName, true));

        verify(repositoryRoles, times(1)).findDefault();
        verify(repositoryRoles, times(1)).save(new EntityRole("deva", roleDisplayName, false));
        verify(repositoryRoles, times(1)).save(new EntityRole(roleCode, roleDisplayName, true));

        ResponseRoleDto mockResponse = new ResponseRoleDto(roleCode, roleDisplayName, true);
        assertEquals(mockResponse, response);
    }

    // TODO - testar que, quando é enviado uma role default, as outras roles são
    // modificadas para não default

    // TODO - testar que não vai dar erro se não houver nenhum default cadastrado e
    // a nova role não for default também
}
