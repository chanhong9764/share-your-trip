package edu.ssafy.enjoytrip.validation.user;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserIdValidator implements ConstraintValidator<UserId, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) {
            return false;
        }
        return value.matches("^[a-z0-9]{4,20}$");
    }
}
