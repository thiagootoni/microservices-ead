package com.ead.authuser.exceptions.models;

public class DataBaseException extends RuntimeException	{

    private static final long serialVersionUID = 1L;

    public DataBaseException(String message) {
        super(message);
    }

}
