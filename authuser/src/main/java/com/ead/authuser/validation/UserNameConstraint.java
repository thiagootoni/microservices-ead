package com.ead.authuser.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = UsernameConstraintImpl.class)
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
public @interface UserNameConstraint {
    String message() default "userName cannot be null, empty or contains empty spaces.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
