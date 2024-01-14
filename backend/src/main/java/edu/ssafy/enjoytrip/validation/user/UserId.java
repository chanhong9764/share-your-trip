package edu.ssafy.enjoytrip.validation.user;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserIdValidator.class)
public @interface UserId {
    String message() default "아이디는 영소문자, 숫자로 구성된 4~20자리로 입력해주세요.";
    Class[] groups() default {};
    Class[] payload() default {};
}
