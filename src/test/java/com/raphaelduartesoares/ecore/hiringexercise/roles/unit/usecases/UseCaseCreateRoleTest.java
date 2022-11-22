package com.raphaelduartesoares.ecore.hiringexercise.roles.unit.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.RequestRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.ResponseRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories.entities.EntityRole;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IRepositoryRoles;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.usecases.UseCaseCreateRole;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.DuplicatedEntityException;

public class UseCaseCreateRoleTest {

    @Mock
    private IRepositoryRoles repositoryRoles;

    private UseCaseCreateRole useCase;

    private String roleCode = "dev";
    private String roleName = "Developer";
    private String uuid = "dc82bf91-d3a1-5afc-8b66-3959ae896f05";
    private EntityRole.EntityRoleBuilder mockEntityBuilder;

    @BeforeEach
    void setup() throws Exception {
        MockitoAnnotations.openMocks(this);

        useCase = new UseCaseCreateRole(repositoryRoles);

        mockEntityBuilder = EntityRole.builder()
                .code(roleCode)
                .name(roleName)
                .isDefault(false)
                .id(UUID.fromString(uuid))
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()));
    }

    @Test
    public void shouldCreateRoleSuccessfullyIfDoesNotExists() throws Exception {
        when(repositoryRoles.save(Mockito.any(EntityRole.class))).thenReturn(mockEntityBuilder.build());
        
        ResponseRoleDto response = useCase.createRole(new RequestRoleDto(roleCode, roleName, false));
        
        verify(repositoryRoles, times(1)).save(new EntityRole(roleCode, roleName, false));
        
        ResponseRoleDto mockResponse = new ResponseRoleDto(roleCode, roleName, false);
        assertEquals(mockResponse, response);
    }

    @Test
    public void shouldValidateIfRoleExistsBeforeCreate() {
        when(repositoryRoles.findByCode(roleCode)).thenReturn(Optional.of(mockEntityBuilder.build()));

        DuplicatedEntityException exception = assertThrows(
                DuplicatedEntityException.class,
                new Executable() {
                    @Override
                    public void execute() throws Exception {
                        useCase.createRole(new RequestRoleDto(roleCode, roleName, false));

                    }
                });

        verify(repositoryRoles, times(1)).findByCode(roleCode);
        assertEquals("[Already exists a role with code 'dev']", exception.errors.toString());
        assertEquals("Role already exists", exception.getMessage());
    }

    @Test
    public void shouldUpdateExistingDefaultRoleToFalseWhenCreateNewDefaultRole() throws Exception {
        boolean isDefault = true;
        EntityRole mockEntityDefault = (EntityRole) mockEntityBuilder.code("deva").isDefault(true)
                .id(UUID.fromString("c95f5701-a136-55ad-867b-9748db868af8")).build();
        when(repositoryRoles.findDefault()).thenReturn(Optional.of(mockEntityDefault));
        when(repositoryRoles.findByCode(roleCode)).thenReturn(Optional.empty());
        when(repositoryRoles.save(Mockito.any(EntityRole.class))).thenReturn(mockEntityBuilder.isDefault(
                isDefault).build());

        ResponseRoleDto response = useCase.createRole(new RequestRoleDto(roleCode, roleName, isDefault));

        verify(repositoryRoles, times(1)).findDefault();
        verify(repositoryRoles, times(1)).save(new EntityRole("deva", roleName, !isDefault));
        verify(repositoryRoles, times(1)).save(new EntityRole(roleCode, roleName, isDefault));

        ResponseRoleDto mockResponse = new ResponseRoleDto(roleCode, roleName, isDefault);
        assertEquals(mockResponse, response);
    }

    @Test
    public void shouldNotUpdateExistingDefaultRoleToFalseWhenCreateNewNonDefaultRole() throws Exception {
        boolean isDefault = false;
        EntityRole mockEntityDefault = (EntityRole) mockEntityBuilder.code("default").isDefault(true)
                .id(UUID.fromString("c95f5701-a136-55ad-867b-9748db868af8")).build();
        when(repositoryRoles.findDefault()).thenReturn(Optional.of(mockEntityDefault));
        when(repositoryRoles.findByCode(roleCode)).thenReturn(Optional.empty());
        when(repositoryRoles.save(Mockito.any(EntityRole.class))).thenReturn(mockEntityBuilder.isDefault(
                isDefault).build());

        ResponseRoleDto response = useCase.createRole(new RequestRoleDto(roleCode, roleName, isDefault));

        verify(repositoryRoles, times(0)).findDefault();
        verify(repositoryRoles, times(1)).save(Mockito.any(EntityRole.class));

        ResponseRoleDto mockResponse = new ResponseRoleDto(roleCode, roleName, isDefault);
        assertEquals(mockResponse, response);
    }

    @Test
    public void shouldCreateNewNonDefaultRoleSuccessfullyEvenIfThereIsNoDefaultRoleCreated() throws Exception {
        boolean isDefault = false;
        when(repositoryRoles.findDefault()).thenReturn(Optional.empty());
        when(repositoryRoles.findByCode(roleCode)).thenReturn(Optional.empty());
        when(repositoryRoles.save(Mockito.any(EntityRole.class))).thenReturn(mockEntityBuilder.build());

        ResponseRoleDto response = useCase.createRole(new RequestRoleDto(roleCode, roleName, false));

        verify(repositoryRoles, times(0)).findDefault();
        verify(repositoryRoles, times(1)).save(new EntityRole(roleCode, roleName, isDefault));

        ResponseRoleDto mockResponse = new ResponseRoleDto(roleCode, roleName, isDefault);
        assertEquals(mockResponse, response);
    }
}
