package com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions;

public class InternalOperationNotAllowedException extends AbstractException {

    public InternalOperationNotAllowedException(String title, String error) {
        super(title, error);
    }
}
