package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces;

import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.integrations.microservices.teams.dtos.ResponseTeamDto;
import java.util.List;

public interface IIntegrationMicroserviceTeams {
  public List<ResponseTeamDto> getTeams();

  public ResponseTeamDto getTeamById(String id);
}
