package com.ead.authuser.services.exceptions;

public class IdentityException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public IdentityException(String message) {
        super(message);
    }
}
