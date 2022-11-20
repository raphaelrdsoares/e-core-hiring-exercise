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
    public void shouldCreateRoleSuccessfullyIfDoesNotExists() throws DuplicatedEntityException {
        when(repositoryRoles.save(Mockito.any(EntityRole.class))).thenReturn(mockEntityBuilder.build());
        
        ResponseRoleDto response = useCase.createRole(new RequestRoleDto(roleCode, roleDisplayName));
        
        verify(repositoryRoles, times(1)).save(new EntityRole(roleCode, roleDisplayName, false));
        
        ResponseRoleDto mockResponse = new ResponseRoleDto(roleCode, roleDisplayName, false);
        assertEquals(mockResponse, response);
    }

    @Test
    public void shouldValidateIfRoleExistsBeforeCreate() {
        when(repositoryRoles.findByCode(roleCode)).thenReturn(mockEntityBuilder.build());

        DuplicatedEntityException exception = assertThrows(
                DuplicatedEntityException.class,
                new Executable() {
                    @Override
                    public void execute() throws Exception {
                        useCase.createRole(new RequestRoleDto(roleCode, roleDisplayName));

                    }
                });

        verify(repositoryRoles, times(1)).findByCode(roleCode);
        assertEquals("[Already exists a role with code 'dev']", exception.errors.toString());
        assertEquals("Role already exists", exception.getMessage());
    }

    @Test
    public void shouldUpdateExistingDefaultRoleToFalseWhenCreateNewDefaultRole() throws DuplicatedEntityException {
        boolean isDefault = true;
        EntityRole mockEntityDefault = (EntityRole) mockEntityBuilder.code("deva").isDefault(true)
                .id(UUID.fromString("c95f5701-a136-55ad-867b-9748db868af8")).build();
        when(repositoryRoles.findDefault()).thenReturn(mockEntityDefault);
        when(repositoryRoles.findByCode(roleCode)).thenReturn(null);
        when(repositoryRoles.save(Mockito.any(EntityRole.class))).thenReturn(mockEntityBuilder.isDefault(
                isDefault).build());

        ResponseRoleDto response = useCase.createRole(new RequestRoleDto(roleCode, roleDisplayName, isDefault));

        verify(repositoryRoles, times(1)).findDefault();
        verify(repositoryRoles, times(1)).save(new EntityRole("deva", roleDisplayName, !isDefault));
        verify(repositoryRoles, times(1)).save(new EntityRole(roleCode, roleDisplayName, isDefault));

        ResponseRoleDto mockResponse = new ResponseRoleDto(roleCode, roleDisplayName, isDefault);
        assertEquals(mockResponse, response);
    }

    @Test
    public void shouldCreateNewNonDefaultRoleSuccessfullyEvenIfThereIsNoDefaultRoleCreated()
            throws DuplicatedEntityException {
        boolean isDefault = false;
        when(repositoryRoles.findDefault()).thenReturn(null);
        when(repositoryRoles.findByCode(roleCode)).thenReturn(null);
        when(repositoryRoles.save(Mockito.any(EntityRole.class))).thenReturn(mockEntityBuilder.build());

        ResponseRoleDto response = useCase.createRole(new RequestRoleDto(roleCode, roleDisplayName));

        verify(repositoryRoles, times(1)).findDefault();
        verify(repositoryRoles, times(1)).save(new EntityRole(roleCode, roleDisplayName, isDefault));

        ResponseRoleDto mockResponse = new ResponseRoleDto(roleCode, roleDisplayName, isDefault);
        assertEquals(mockResponse, response);
    }
}
