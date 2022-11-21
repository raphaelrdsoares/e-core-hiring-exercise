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

import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.RequestAssignRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.RequestRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.ResponseRoleDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IServiceRoles;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.ApiError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/roles")
@Tag(name = "Roles")
public class RolesController {

    @Autowired
    private IServiceRoles serviceRoles;

    @Operation(summary = "Creates a role", description = "Creates a new role. If the new role is 'default=true', it will replace the existing default role.")
    @ApiResponse(responseCode = "201", description = "Role created.")
    @ApiResponse(responseCode = "400", description = "The request does not attend the specification.", content = @Content(schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "409", description = "Conflict with current server state.", content = @Content(schema = @Schema(implementation = ApiError.class)))
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseRoleDto> createRole(@Valid @RequestBody RequestRoleDto requestRole) throws Exception {
        ResponseRoleDto response = serviceRoles.createRole(requestRole);
        return new ResponseEntity<ResponseRoleDto>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Creates a membership", description = "Assigns a role to an user in a team.")
    @ApiResponse(responseCode = "201", description = "Membership created.")
    @ApiResponse(responseCode = "400", description = "The request does not attend the specification.", content = @Content(schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "404", description = "Entity not found.", content = @Content(schema = @Schema(implementation = ApiError.class)))
    @PostMapping(path = "/membership", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity assignRole(@Valid @RequestBody RequestAssignRoleDto requestAssignRole) throws Exception {
        serviceRoles.assignRole(requestAssignRole);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
