package com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.exceptions;

public class DuplicatedEntityException extends AbstractException {

    public DuplicatedEntityException(String title, String error) {
        super(title, error);
    }

}
