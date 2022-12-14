package com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractException extends Exception {

    public List<String> errors = new ArrayList<String>();

    public AbstractException(String title, String error) {
        super(title);
        this.errors = Arrays.asList(error);
    }

}