package com.ead.authuser.exceptions.models;

public class UnprocessableEntityException extends RuntimeException{

    private String message;

    public UnprocessableEntityException(String message){
        super(message);
    }
}
