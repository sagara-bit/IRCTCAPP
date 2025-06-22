package com.example.irctc.irctc_backend.Annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return false;
    }
}
