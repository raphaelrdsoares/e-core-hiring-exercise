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
public class RequestRoleDto {

    @NotBlank(message = "Property 'code' is required")
    @Schema(example = "dev")
    public String code;

    @NotBlank(message = "Property 'name' is required")
    @Schema(example = "Developer")
    public String name;

    public boolean isDefault;

    public RequestRoleDto(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
