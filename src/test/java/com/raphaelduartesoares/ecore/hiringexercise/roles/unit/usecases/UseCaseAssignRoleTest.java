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
    private String userId = "f6341d2e-9f9e-5c64-8467-fb092d20a36e";
    private String teamLeadId = "bc81f10f-60f2-5a2c-a838-4ff28914171b";
    private String teamId = "251b0d8a-0a27-5c35-a9c4-7287c8911ad5";
    private EntityRole mockEntityRoleDev = EntityRole
            .builder()
            .code(roleCodeDev)
            .name("Developer")
            .isDefault(true)
            .id(UUID.fromString("88017d97-a4b3-58b3-9cf5-580197413508"))
            .build();
    private EntityMembership mockEntityMembership = EntityMembership
            .builder()
            .roleCode("qa")
            .teamId(teamId)
            .userId(userId)
            .id(UUID.fromString("e4181e91-7b50-5433-b90c-d9e9aa21f3c6"))
            .build();
    private ResponseTeamDto mockTeamDto = ResponseTeamDto
            .builder()
            .id(teamId)
            .name("Ordinary Coral Lynx")
            .teamLeadId(teamLeadId)
            .teamMemberIds(
                    Arrays.asList(
                            "95750ce6-698b-59cf-8d90-4e6a09b910f4",
                            "c8282811-587e-5409-a48d-0f6e4b6e08a0",
                            userId))
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
    assertEquals(
      "[There is no team with id '251b0d8a-0a27-5c35-a9c4-7287c8911ad5']",
      exception.errors.toString()
    );
    assertEquals("Entity not found", exception.getMessage());
  }

    @Test
    public void shouldThrowExceptionWhenUserDoesNotExistsInTeam() {
        mockTeamDto.teamMemberIds.set(2, "f8778885-9c1e-5d01-a5f6-9b9f4254e3e1");
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
                "[There is no user 'f6341d2e-9f9e-5c64-8467-fb092d20a36e' in team '251b0d8a-0a27-5c35-a9c4-7287c8911ad5']",
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
