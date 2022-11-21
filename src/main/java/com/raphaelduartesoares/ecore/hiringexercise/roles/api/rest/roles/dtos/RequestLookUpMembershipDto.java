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
    @Pattern(regexp = "^(?!\\s*$).+", message = "Property 'role' must not be blank")
    public String role;

    @Pattern(regexp = "^(?!\\s*$).+", message = "Property 'team' must not be blank")
    public String team;

    @Pattern(regexp = "^(?!\\s*$).+", message = "Property 'user' must not be blank")
    public String user;
}
