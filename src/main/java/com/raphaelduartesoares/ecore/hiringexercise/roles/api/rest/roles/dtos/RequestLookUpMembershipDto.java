package com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class RequestLookUpMembershipDto {
    public String roleCode;
    public String teamId;
    public String userId;
}
