package com.ead.course.exceptions;

public class UnprocessableEntityException extends RuntimeException{

    private String message;

    public UnprocessableEntityException(String message){
        super(message);
    }
}
