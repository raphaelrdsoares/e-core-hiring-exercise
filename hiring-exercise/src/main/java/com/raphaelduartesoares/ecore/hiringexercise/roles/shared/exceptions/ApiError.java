package com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ApiError {
    @Schema(example = "BAD_REQUEST")
    private HttpStatus status;

    @Schema(example = "Malformed request")
    private String title;

    private List<String> errors;

    public ApiError(final HttpStatus status, final String title, final List<String> errors) {
        this.status = status;
        this.title = title;
        this.errors = errors;
    }
}
