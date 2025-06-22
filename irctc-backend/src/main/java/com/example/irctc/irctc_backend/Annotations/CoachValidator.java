package com.example.irctc.irctc_backend.Annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CoachValidator implements ConstraintValidator<ValidCoach,Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
       if(value>100){
           return true;
       }
        // validation logic
        return false;
    }
}
