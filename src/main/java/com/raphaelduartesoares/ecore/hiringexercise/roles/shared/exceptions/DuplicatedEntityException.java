package com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions;

public class DuplicatedEntityException extends AbstractException {

    public DuplicatedEntityException(String title, String error) {
        super(title, error);
    }

}
