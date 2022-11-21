package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.integrations.microservices.teams;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.integrations.microservices.teams.dtos.ResponseTeamDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IIntegrationMicroserviceTeams;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.EnvironmentVariables;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.infrastructure.integrations.WebClientFactory;

@Component
public class IntegrationMicroserviceTeams implements IIntegrationMicroserviceTeams {

    @Autowired
    private WebClientFactory webClientFactory;

    @Autowired
    private EnvironmentVariables environmentVariables;

    @Override
    public List<ResponseTeamDto> getTeams() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseTeamDto getTeamById(String id) {
        try {
            WebClient webClient = webClientFactory
                    .getClient(environmentVariables.getMicroservicesTeamsBaseUrl());
            return webClient
                    .get()
                    .uri(uriBuilder -> uriBuilder.path("/{id}").build(id))
                    .retrieve()
                    .bodyToMono(ResponseTeamDto.class)
                    .block();
        } catch (Exception e) {
            return null;
        }
    }
}
