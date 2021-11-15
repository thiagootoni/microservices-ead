package com.ead.authuser.controllers.exceptions;

import lombok.Data;

import java.time.Instant;

@Data
public class StandardError {
    private String error;
    private String message;
    private Instant timestamp;
    private String path;
    private Integer status;
}
