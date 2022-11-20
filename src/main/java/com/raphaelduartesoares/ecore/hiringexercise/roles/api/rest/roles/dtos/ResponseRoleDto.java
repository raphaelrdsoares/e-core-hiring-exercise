package com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public class ResponseRoleDto {

    public String code;
    public String displayName;
    public boolean isDefault;
}
