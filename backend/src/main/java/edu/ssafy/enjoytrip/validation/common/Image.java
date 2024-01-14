package edu.ssafy.enjoytrip.validation.common;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageValidator.class)
public @interface Image {
    String message() default "입력한 파일이 존재하지 않습니다.";
    Class[] groups() default {};
    Class[] payload() default {};
}
