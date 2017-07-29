/**
 *
 */
package jp.co.necsoft.web_md.common.validators;



import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import play.data.validation.Constraints.Validator;
import play.libs.F.Tuple;

/**
 * @author 1134140716332
 *
 */
public class DateTimeFormatValidator extends Validator<String> implements ConstraintValidator<jp.co.necsoft.web_md.common.validators.DateTimeFormat, String> {

    /* Default error message */
    final static public String message = "error.DateTimeFormat";
    private String pattern;

	@Override
	public void initialize(jp.co.necsoft.web_md.common.validators.DateTimeFormat constraintAnnotation) {
		this.pattern = constraintAnnotation.pattern();
	}

	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		return isValid(arg0);
	}

	@Override
	public Tuple<String, Object[]> getErrorMessageKey() {
		return play.libs.F.Tuple(message, new Object[] {pattern});
	}

	public boolean isValid(String value) {
		if (value == null || value.equals("")) {
			return true;
		}
		@SuppressWarnings("unused")
		DateTime dt;
		try {
			dt = DateTimeFormat.forPattern(pattern).parseDateTime(value);
		} catch (UnsupportedOperationException e) {
			return false;
		} catch (IllegalArgumentException e) {
			return false;
		}
		dt = null;
		return true;
	}
}
