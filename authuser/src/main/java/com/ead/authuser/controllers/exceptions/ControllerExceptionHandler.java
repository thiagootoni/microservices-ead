package com.ead.authuser.controllers.exceptions;

import com.ead.authuser.services.exceptions.DataBaseException;
import com.ead.authuser.services.exceptions.ElementNotFoundException;
import com.ead.authuser.services.exceptions.IdentityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<StandardError> elementNotFoundExceptionHandler(ElementNotFoundException e, HttpServletRequest request){

        StandardError error = new StandardError();
        HttpStatus status = HttpStatus.NOT_FOUND;

        error.setError("Element not found");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        error.setStatus(status.value());
        error.setTimestamp(Instant.now());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<StandardError> dataBaseExceptionHandler(ElementNotFoundException e, HttpServletRequest request){

        StandardError error = new StandardError();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        error.setError("Database violation exception");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        error.setStatus(status.value());
        error.setTimestamp(Instant.now());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(IdentityException.class)
    public ResponseEntity<StandardError> confirmationIdentityExceptionHandler(IdentityException e, HttpServletRequest request){

        StandardError error = new StandardError();
        HttpStatus status = HttpStatus.CONFLICT;

        error.setError("Confirmation identity error exception");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        error.setStatus(status.value());
        error.setTimestamp(Instant.now());

        return ResponseEntity.status(status).body(error);
    }
}
