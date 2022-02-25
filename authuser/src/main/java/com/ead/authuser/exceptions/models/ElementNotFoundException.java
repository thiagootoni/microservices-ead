package com.ead.authuser.exceptions.models;

public class ElementNotFoundException extends RuntimeException	{

    private static final long serialVersionUID = 1L;

    public ElementNotFoundException(String message) {
        super(message);
    }

}
