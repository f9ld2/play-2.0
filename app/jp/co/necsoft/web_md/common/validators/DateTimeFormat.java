package jp.co.necsoft.web_md.common.validators;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = DateTimeFormatValidator.class)
@play.data.Form.Display(name="constraint.DateTimeFormat", attributes={"pattern"})
public @interface DateTimeFormat {
	final static String BASIC_ISO_DATE = "yyyyMMdd";
	final static String ISO_LOCAL_TIME_WO_COLON = "HHmmss";

	String message() default DateTimeFormatValidator.message;
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	String pattern();
}
