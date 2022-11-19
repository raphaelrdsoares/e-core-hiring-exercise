package com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RequestRoleDto {

    public String code;
    public String displayName;
    public boolean isDefault;

    public RequestRoleDto(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}
