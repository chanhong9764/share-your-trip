package edu.ssafy.enjoytrip.validation.user;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserPasswordValidator.class)
public @interface UserPassword {
    String message() default "비밀번호는 영문 대/소문자, 숫자, 특수문자를 조합하여 8 ~ 16자를 사용하세요.";
    Class[] groups() default {};
    Class[] payload() default {};
}
