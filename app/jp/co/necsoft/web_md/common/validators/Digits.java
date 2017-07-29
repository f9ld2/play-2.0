package jp.co.necsoft.web_md.common.validators;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = DigitsValidator.class)
@play.data.Form.Display(name="constraint.digits", attributes={"integer ", "fraction "})
public @interface Digits {
	String message() default DigitsValidator.message;
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
    long integer();
    long fraction();
}
