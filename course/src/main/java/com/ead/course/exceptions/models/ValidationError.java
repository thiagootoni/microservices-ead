package com.ead.course.exceptions.models;
import lombok.Data;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationError extends StandardError{

    List<FieldMessage> errors = new ArrayList<>();

    public void fillErrors(List<FieldError> list) {
        for (FieldError err : list) {
            errors.add(new FieldMessage(err));
        }
    }

}
