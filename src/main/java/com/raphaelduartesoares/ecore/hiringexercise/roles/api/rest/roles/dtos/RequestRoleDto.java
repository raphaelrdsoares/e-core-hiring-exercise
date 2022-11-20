package com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class RequestRoleDto {

    @NotBlank(message = "Role.code is required")
    public String code;
    @NotBlank(message = "Role.code is required")
    public String name;
    public boolean isDefault;

    public RequestRoleDto(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
