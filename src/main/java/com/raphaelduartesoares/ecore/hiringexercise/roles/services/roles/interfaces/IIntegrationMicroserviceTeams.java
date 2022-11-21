package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces;

import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.integrations.microservices.teams.dtos.ResponseTeamDto;

public interface IIntegrationMicroserviceTeams {
    public ResponseTeamDto getTeamById(String id);
}
