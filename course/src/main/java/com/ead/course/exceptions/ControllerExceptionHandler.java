package com.ead.course.exceptions;

import com.ead.course.exceptions.models.StandardError;
import com.ead.course.exceptions.models.ValidationError;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validationMethodExceptionHandler(MethodArgumentNotValidException e, HttpServletRequest request){

        ValidationError error = new ValidationError();
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Validation exception");
        error.setMessage(getDetailMessage(e));
        error.setPath(request.getRequestURI());

        error.fillErrors(e.getBindingResult().getFieldErrors());

        return ResponseEntity.status(status).body(error);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e, HttpServletRequest request){

        StandardError error = new StandardError();
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Validation exception");
        error.setMessage(getDetailMessage(e));
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<StandardError> unprocessableEntityExceptionHandler(UnprocessableEntityException e, HttpServletRequest request){

        StandardError error = new StandardError();
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Unprocessable exception");
        error.setMessage(getDetailMessage(e));
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }

    private String getDetailMessage(Exception e) {
        return StringUtils.substringBefore(e.getMessage(), "; nested exception is");
    }

}
