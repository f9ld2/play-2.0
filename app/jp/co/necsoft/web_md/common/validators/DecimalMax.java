package jp.co.necsoft.web_md.common.validators;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = DecimalMaxValidator.class)
@play.data.Form.Display(name="constraint.digits", attributes={"value"})
public @interface DecimalMax {
	String message() default DecimalMaxValidator.message;
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
    double value();
}
