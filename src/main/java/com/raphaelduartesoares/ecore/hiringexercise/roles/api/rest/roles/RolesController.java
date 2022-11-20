package com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.RequestRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.ResponseRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IServiceRoles;

@RestController
@RequestMapping("/roles")
public class RolesController {

    @Autowired
    private IServiceRoles serviceRoles;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseRoleDto> createRole(@Valid @RequestBody RequestRoleDto requestRole)
            throws Exception {
        ResponseRoleDto response = serviceRoles.createRole(requestRole);
        return new ResponseEntity<ResponseRoleDto>(response, HttpStatus.CREATED);
    }
}
