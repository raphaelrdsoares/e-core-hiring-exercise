package com.raphaelduartesoares.ecore.hiringexercise.roles.unit.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.RequestAssignRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.integrations.microservices.teams.dtos.ResponseTeamDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories.entities.EntityMembership;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories.entities.EntityRole;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IIntegrationMicroserviceTeams;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IRepositoryMembership;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IRepositoryRoles;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.usecases.UseCaseAssignRole;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.NotFoundException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UseCaseAssignRoleTest {

    @Mock
    private IRepositoryRoles repositoryRoles;

    @Mock
    private IRepositoryMembership repositoryMembership;

    @Mock
    private IIntegrationMicroserviceTeams microserviceTeams;

    private UseCaseAssignRole useCase;

    private String roleCodeDev = "dev";
    private String userId = UUID.randomUUID().toString();
    private String teamLeadId = UUID.randomUUID().toString();
    private String teamId = UUID.randomUUID().toString();
    private EntityRole mockEntityRoleDev = EntityRole
            .builder()
            .code(roleCodeDev)
            .name("Developer")
            .isDefault(true)
            .id(UUID.randomUUID())
            .build();
    private EntityMembership mockEntityMembership = EntityMembership
            .builder()
            .roleCode("qa")
            .teamId(teamId)
            .userId(userId)
            .id(UUID.randomUUID())
            .build();
    private ResponseTeamDto mockTeamDto = ResponseTeamDto
            .builder()
            .id(teamId)
            .name("Ordinary Coral Lynx")
            .teamLeadId(teamLeadId)
            .teamMemberIds(Arrays.asList(UUID.randomUUID().toString(), UUID.randomUUID().toString(), userId))
            .build();

    @BeforeEach
    void setup() throws Exception {
        MockitoAnnotations.openMocks(this);

        useCase = new UseCaseAssignRole(
                repositoryRoles,
                repositoryMembership,
                microserviceTeams);
    }

  @Test
  public void shouldThrowExceptionWhenRoleCodeDoesNotExists() {
    when(repositoryRoles.findByCode(roleCodeDev)).thenReturn(Optional.empty());

    NotFoundException exception = assertThrows(
      NotFoundException.class,
      new Executable() {
        @Override
        public void execute() throws Exception {
          useCase.assignRole(
            new RequestAssignRoleDto(roleCodeDev, userId, teamId)
          );
        }
      }
    );

    verify(repositoryRoles, times(1)).findByCode(roleCodeDev);
    assertEquals(
      "[There is no role with code 'dev']",
      exception.errors.toString()
    );
    assertEquals("Entity not found", exception.getMessage());
  }

  @Test
  public void shouldThrowExceptionWhenTeamDoesNotExists() {
    when(repositoryRoles.findByCode(roleCodeDev)).thenReturn(Optional.of(mockEntityRoleDev));
    when(microserviceTeams.getTeamById(teamId)).thenReturn(null);

    NotFoundException exception = assertThrows(
      NotFoundException.class,
      new Executable() {
        @Override
        public void execute() throws Exception {
          useCase.assignRole(
            new RequestAssignRoleDto(roleCodeDev, userId, teamId)
          );
        }
      }
    );

    verify(microserviceTeams, times(1)).getTeamById(teamId);
    assertEquals(String.format( "[There is no team with id '%s']", teamId),
      exception.errors.toString()
    );
    assertEquals("Entity not found", exception.getMessage());
  }

    @Test
    public void shouldThrowExceptionWhenUserDoesNotExistsInTeam() {
        mockTeamDto.teamMemberIds.set(2, UUID.randomUUID().toString());
        when(repositoryRoles.findByCode(roleCodeDev)).thenReturn(Optional.of(mockEntityRoleDev));
        when(microserviceTeams.getTeamById(teamId)).thenReturn(mockTeamDto);

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                new Executable() {
                    @Override
                    public void execute() throws Exception {
                        useCase.assignRole(
                                new RequestAssignRoleDto(roleCodeDev, userId, teamId));
                    }
                });

        verify(microserviceTeams, times(1)).getTeamById(teamId);
        assertEquals(
                String.format("[There is no user '%s' in team '%s']", userId, teamId),
                exception.errors.toString());
        assertEquals("Entity not found", exception.getMessage());
    }

  @Test
  public void shouldAssignRoleSuccessfully() throws Exception {
    when(repositoryRoles.findByCode(roleCodeDev)).thenReturn(Optional.of(mockEntityRoleDev));
    when(microserviceTeams.getTeamById(teamId)).thenReturn(mockTeamDto);

    useCase.assignRole(new RequestAssignRoleDto(roleCodeDev, userId, teamId));

    verify(repositoryMembership, times(1)).save(new EntityMembership(roleCodeDev, userId, teamId));
  }

  @Test
  public void shouldAssignDefaultRoleSuccessfullyWhenNoRoleCodeIsPassed() throws Exception {
    when(repositoryRoles.findDefault()).thenReturn(Optional.of(mockEntityRoleDev));
    when(microserviceTeams.getTeamById(teamId)).thenReturn(mockTeamDto);

    useCase.assignRole(new RequestAssignRoleDto(null, userId, teamId));

    verify(repositoryRoles, times(1)).findDefault();
    verify(repositoryMembership, times(1)).save(new EntityMembership(roleCodeDev, userId, teamId));
  }

    @Test
    public void shouldThrowExceptionWhenNoRoleCodeIsPassedAndNoDefaultRoleExists() throws Exception {
        when(repositoryRoles.findDefault()).thenReturn(Optional.empty());
        when(microserviceTeams.getTeamById(teamId)).thenReturn(mockTeamDto);

        NotFoundException exception = assertThrows(
        NotFoundException.class,
        new Executable() {
            @Override
            public void execute() throws Exception {
            useCase.assignRole(new RequestAssignRoleDto(null, userId, teamId));
            }
        }
        );

        verify(repositoryRoles, times(1)).findDefault();
        assertEquals("[Default role does not exists]", exception.errors.toString());
        assertEquals("Entity not found", exception.getMessage());
    }

    @Test
    public void shouldUpdateMembershipIfAlreadyExists() throws Exception {
        when(repositoryRoles.findByCode(roleCodeDev)).thenReturn(Optional.of(mockEntityRoleDev));
        when(microserviceTeams.getTeamById(teamId)).thenReturn(mockTeamDto);
        when(repositoryMembership.findByTeamAndUser(teamId, userId)).thenReturn(Optional.of(mockEntityMembership));

        useCase.assignRole(new RequestAssignRoleDto(roleCodeDev, userId, teamId));

        verify(repositoryMembership, times(1)).findByTeamAndUser(teamId, userId);
        mockEntityMembership.roleCode = roleCodeDev;
        verify(repositoryMembership, times(1)).save(mockEntityMembership);
    }

    @Test
  public void shouldAssignRoleToTeamLeadSuccessfully() throws Exception {
    when(repositoryRoles.findByCode(roleCodeDev)).thenReturn(Optional.of(mockEntityRoleDev));
    when(microserviceTeams.getTeamById(teamId)).thenReturn(mockTeamDto);

    useCase.assignRole(new RequestAssignRoleDto(roleCodeDev, teamLeadId, teamId));

    verify(repositoryMembership, times(1)).save(new EntityMembership(roleCodeDev, teamLeadId, teamId));
  }

}
