package com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class RequestAssignRoleDto {
    @Schema(example = "dev")
    public String roleCode;

    @NotBlank(message = "Property 'userId' is required")
    @Schema(example = "83b239d5-d81e-5c7a-9ed3-7d4d0f9fc218")
    public String userId;

    @NotBlank(message = "Property 'teamID' is required")
    @Schema(example = "5693b2b3-8d10-5bdc-bc35-7f5875ad66c0")
    public String teamId;
}
