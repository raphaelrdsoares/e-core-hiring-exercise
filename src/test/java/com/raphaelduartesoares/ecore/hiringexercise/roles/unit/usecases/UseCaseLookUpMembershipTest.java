package com.raphaelduartesoares.ecore.hiringexercise.roles.unit.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.RequestLookUpMembershipDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.ResponseMembershipDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories.entities.EntityMembership;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IRepositoryMembership;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.usecases.UseCaseLookUpMembership;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.InvalidInputDataException;

public class UseCaseLookUpMembershipTest {

    @Mock
    private IRepositoryMembership repositoryMembership;

    private UseCaseLookUpMembership useCase;

    private String roleCodeDev = "dev";
    private String teamId = UUID.randomUUID().toString();
    private String userId = UUID.randomUUID().toString();

    @BeforeEach
    void setup() throws Exception {
        MockitoAnnotations.openMocks(this);

        useCase = new UseCaseLookUpMembership(repositoryMembership);
    }

    @ParameterizedTest
    @CsvSource({ ",", "''", "'   '" })
    public void shouldThrowExceptionWhenAllPropertiesAreNullOrBlankInRequest(String propertyValue) {
        InvalidInputDataException exception = assertThrows(
                InvalidInputDataException.class,
                new Executable() {
                    @Override
                    public void execute() throws Exception {
                        useCase.lookUpMembership(
                                new RequestLookUpMembershipDto(propertyValue, propertyValue, propertyValue));
                    }
                });
        assertEquals("[Request must have at least one parameter]", exception.errors.toString());
        assertEquals("Invalid input data", exception.getMessage());
    }

    @Test
    public void shouldReturnListOfMembershipsWheOnlyRoleCodeIsPassed() throws Exception {
        EntityMembership entityMembership1 = EntityMembership.builder()
                .id(UUID.randomUUID())
                .roleCode(roleCodeDev)
                .userId(UUID.randomUUID().toString())
                .teamId(UUID.randomUUID().toString())
                .build();
        EntityMembership entityMembership2 = EntityMembership.builder()
                .id(UUID.randomUUID())
                .roleCode(roleCodeDev)
                .userId(UUID.randomUUID().toString())
                .teamId(UUID.randomUUID().toString())
                .build();
        when(repositoryMembership.findAllByRoleAndOrTeamIdAndOrUserId(roleCodeDev, null, null))
                .thenReturn(List.of(entityMembership1, entityMembership2));

        List<ResponseMembershipDto> memberships = useCase
                .lookUpMembership(new RequestLookUpMembershipDto(roleCodeDev, null, null));

        verify(repositoryMembership, times(1)).findAllByRoleAndOrTeamIdAndOrUserId(roleCodeDev, null, null);
        assertEquals(2, memberships.size());
        assertEquals(roleCodeDev, memberships.get(0).roleCode);
        assertEquals(roleCodeDev, memberships.get(1).roleCode);
    }

    @Test
    public void shouldReturnListOfMembershipsWheOnlyTeamIdIsPassed() throws Exception {
        EntityMembership entityMembership1 = EntityMembership.builder()
                .id(UUID.randomUUID())
                .roleCode(UUID.randomUUID().toString())
                .userId(UUID.randomUUID().toString())
                .teamId(teamId)
                .build();
        EntityMembership entityMembership2 = EntityMembership.builder()
                .id(UUID.randomUUID())
                .roleCode(UUID.randomUUID().toString())
                .userId(UUID.randomUUID().toString())
                .teamId(teamId)
                .build();
        when(repositoryMembership.findAllByRoleAndOrTeamIdAndOrUserId(null, teamId, null))
                .thenReturn(List.of(entityMembership1, entityMembership2));

        List<ResponseMembershipDto> memberships = useCase
                .lookUpMembership(new RequestLookUpMembershipDto(null, teamId, null));

        verify(repositoryMembership, times(1)).findAllByRoleAndOrTeamIdAndOrUserId(null, teamId, null);
        assertEquals(2, memberships.size());
        assertEquals(teamId, memberships.get(0).teamId);
        assertEquals(teamId, memberships.get(1).teamId);
    }

    @Test
    public void shouldReturnListOfMembershipsWheOnlyUserIdIsPassed() throws Exception {
        EntityMembership entityMembership1 = EntityMembership.builder()
                .id(UUID.randomUUID())
                .roleCode(UUID.randomUUID().toString())
                .userId(userId)
                .teamId(UUID.randomUUID().toString())
                .build();
        EntityMembership entityMembership2 = EntityMembership.builder()
                .id(UUID.randomUUID())
                .roleCode(UUID.randomUUID().toString())
                .userId(userId)
                .teamId(UUID.randomUUID().toString())
                .build();
        when(repositoryMembership.findAllByRoleAndOrTeamIdAndOrUserId(null, null, userId))
                .thenReturn(List.of(entityMembership1, entityMembership2));

        List<ResponseMembershipDto> memberships = useCase
                .lookUpMembership(new RequestLookUpMembershipDto(null, null, userId));

        verify(repositoryMembership, times(1)).findAllByRoleAndOrTeamIdAndOrUserId(null, null, userId);
        assertEquals(2, memberships.size());
        assertEquals(userId, memberships.get(0).userId);
        assertEquals(userId, memberships.get(1).userId);
    }

    @Test
    public void shouldReturnListOfMembershipsWheOnlyRoleCodeAndTeamIdIsPassed() throws Exception {
        EntityMembership entityMembership1 = EntityMembership.builder()
                .id(UUID.randomUUID())
                .roleCode(roleCodeDev)
                .userId(UUID.randomUUID().toString())
                .teamId(teamId)
                .build();
        EntityMembership entityMembership2 = EntityMembership.builder()
                .id(UUID.randomUUID())
                .roleCode(roleCodeDev)
                .userId(UUID.randomUUID().toString())
                .teamId(teamId)
                .build();
        when(repositoryMembership.findAllByRoleAndOrTeamIdAndOrUserId(roleCodeDev, teamId, null))
                .thenReturn(List.of(entityMembership1, entityMembership2));

        List<ResponseMembershipDto> memberships = useCase
                .lookUpMembership(new RequestLookUpMembershipDto(roleCodeDev, teamId, null));

        verify(repositoryMembership, times(1)).findAllByRoleAndOrTeamIdAndOrUserId(roleCodeDev, teamId, null);
        assertEquals(2, memberships.size());
        assertEquals(roleCodeDev, memberships.get(0).roleCode);
        assertEquals(roleCodeDev, memberships.get(1).roleCode);
        assertEquals(teamId, memberships.get(0).teamId);
        assertEquals(teamId, memberships.get(1).teamId);
    }

    @Test
    public void shouldReturnListOfMembershipsWheOnlyRoleCodeAndUserIdIsPassed() throws Exception {
        EntityMembership entityMembership1 = EntityMembership.builder()
                .id(UUID.randomUUID())
                .roleCode(roleCodeDev)
                .userId(userId)
                .teamId(UUID.randomUUID().toString())
                .build();
        EntityMembership entityMembership2 = EntityMembership.builder()
                .id(UUID.randomUUID())
                .roleCode(roleCodeDev)
                .userId(userId)
                .teamId(UUID.randomUUID().toString())
                .build();
        when(repositoryMembership.findAllByRoleAndOrTeamIdAndOrUserId(roleCodeDev, null, userId))
                .thenReturn(List.of(entityMembership1, entityMembership2));

        List<ResponseMembershipDto> memberships = useCase
                .lookUpMembership(new RequestLookUpMembershipDto(roleCodeDev, null, userId));

        verify(repositoryMembership, times(1)).findAllByRoleAndOrTeamIdAndOrUserId(roleCodeDev, null, userId);
        assertEquals(2, memberships.size());
        assertEquals(roleCodeDev, memberships.get(0).roleCode);
        assertEquals(roleCodeDev, memberships.get(1).roleCode);
        assertEquals(userId, memberships.get(0).userId);
        assertEquals(userId, memberships.get(1).userId);
    }

    @Test
    public void shouldReturnListOfMembershipsWheOnlyTeamIdAndUserIdIsPassed() throws Exception {
        EntityMembership entityMembership1 = EntityMembership.builder()
                .id(UUID.randomUUID())
                .roleCode(UUID.randomUUID().toString())
                .userId(userId)
                .teamId(teamId)
                .build();
        EntityMembership entityMembership2 = EntityMembership.builder()
                .id(UUID.randomUUID())
                .roleCode(UUID.randomUUID().toString())
                .userId(userId)
                .teamId(teamId)
                .build();
        when(repositoryMembership.findAllByRoleAndOrTeamIdAndOrUserId(null, teamId, userId))
                .thenReturn(List.of(entityMembership1, entityMembership2));

        List<ResponseMembershipDto> memberships = useCase
                .lookUpMembership(new RequestLookUpMembershipDto(null, teamId, userId));

        verify(repositoryMembership, times(1)).findAllByRoleAndOrTeamIdAndOrUserId(null, teamId, userId);
        assertEquals(2, memberships.size());
        assertEquals(teamId, memberships.get(0).teamId);
        assertEquals(teamId, memberships.get(1).teamId);
        assertEquals(userId, memberships.get(0).userId);
        assertEquals(userId, memberships.get(1).userId);
    }

    @Test
    public void shouldReturnListOfMembershipsWheRoleCodeAndTeamIdAndUserIdIsPassed() throws Exception {
        EntityMembership entityMembership1 = EntityMembership.builder()
                .id(UUID.randomUUID())
                .roleCode(roleCodeDev)
                .userId(userId)
                .teamId(teamId)
                .build();
        EntityMembership entityMembership2 = EntityMembership.builder()
                .id(UUID.randomUUID())
                .roleCode(roleCodeDev)
                .userId(userId)
                .teamId(teamId)
                .build();
        when(repositoryMembership.findAllByRoleAndOrTeamIdAndOrUserId(roleCodeDev, teamId, userId))
                .thenReturn(List.of(entityMembership1, entityMembership2));

        List<ResponseMembershipDto> memberships = useCase
                .lookUpMembership(new RequestLookUpMembershipDto(roleCodeDev, teamId, userId));

        verify(repositoryMembership, times(1)).findAllByRoleAndOrTeamIdAndOrUserId(roleCodeDev, teamId, userId);
        assertEquals(2, memberships.size());
        assertEquals(teamId, memberships.get(0).teamId);
        assertEquals(teamId, memberships.get(1).teamId);
        assertEquals(userId, memberships.get(0).userId);
        assertEquals(userId, memberships.get(1).userId);
        assertEquals(roleCodeDev, memberships.get(0).roleCode);
        assertEquals(roleCodeDev, memberships.get(1).roleCode);
    }

}
