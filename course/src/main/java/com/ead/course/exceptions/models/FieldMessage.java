package com.ead.course.exceptions.models;

import lombok.Data;
import org.springframework.validation.FieldError;

@Data
public class FieldMessage {

    private String field;
    private String message;


    public FieldMessage(FieldError err) {
        super();
        this.field = err.getField();
        this.message = err.getDefaultMessage();
    }
}
