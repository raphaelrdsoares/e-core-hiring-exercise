package com.raphaelduartesoares.ecore.hiringexercise.roles.shared;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class EnvironmentVariables {
    @Value("${env.microservices.teams.baseUrl}")
    private String microservicesTeamsBaseUrl;

    @Value("${env.microservices.users.baseUrl}")
    private String microservicesUsersBaseUrl;

}
