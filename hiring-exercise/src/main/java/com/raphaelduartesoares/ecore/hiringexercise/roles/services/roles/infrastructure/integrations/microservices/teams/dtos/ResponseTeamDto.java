package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.integrations.microservices.teams.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseTeamDto {

    public String id;
    public String name;
    public String teamLeadId;
    public List<String> teamMemberIds;
}
