package com.example.irctc.irctc_backend.Annotations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CoachValidator.class)
public @interface ValidCoach {

    String message() default "Invalid coach Number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
