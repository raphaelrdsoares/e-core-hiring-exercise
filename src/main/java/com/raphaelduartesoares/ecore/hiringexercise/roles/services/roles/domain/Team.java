package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.domain;

import java.util.List;
import java.util.Optional;

import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.integrations.microservices.teams.dtos.ResponseTeamDto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@EqualsAndHashCode
public class Team {

    private String id;
    private String name;
    private String teamLeadId;
    private List<String> teamMemberIds;

    public static Optional<Team> fromDto(ResponseTeamDto teamDto) {
        if (teamDto == null) {
            return Optional.empty();
        }
        return Optional.of(
                Team.builder()
                        .id(teamDto.id)
                        .name(teamDto.name)
                        .teamLeadId(teamDto.teamLeadId)
                        .teamMemberIds(teamDto.teamMemberIds)
                        .build());
    }

    public boolean userExistsInTeam(String userId) {
        return teamMemberIds.stream().anyMatch(id -> id.equals(userId)) || teamLeadId.equals(userId);
    }
}
