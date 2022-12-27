package com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public class ResponseRoleDto {

    @Schema(example = "dev")
    public String code;

    @Schema(example = "Developer")
    public String name;

    public boolean isDefault;
}
