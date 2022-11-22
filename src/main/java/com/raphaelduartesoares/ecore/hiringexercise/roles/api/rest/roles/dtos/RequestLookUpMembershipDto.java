package com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos;

import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class RequestLookUpMembershipDto {
    @Pattern(regexp = "^(?!\\s*$).+", message = "Property 'roleCode' must not be blank")
    public String roleCode;

    @Pattern(regexp = "^(?!\\s*$).+", message = "Property 'teamId' must not be blank")
    public String teamId;

    @Pattern(regexp = "^(?!\\s*$).+", message = "Property 'userId' must not be blank")
    public String userId;
}
