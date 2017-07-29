/**
 * 
 */
package jp.co.necsoft.web_md.common.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.joda.time.DateTime;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.format.ISODateTimeFormat;

import play.data.validation.Constraints.Validator;
import play.libs.F.Tuple;

/**
 * @author 1134140119159
 *
 */
public class BasicDateValidator extends Validator<String> implements ConstraintValidator<BasicDate, String> {

    /* Default error message */
    final static public String message = "error.basicdate";

	@Override
	public void initialize(BasicDate constraintAnnotation) {
	}

	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		return isValid(arg0);
	}

	@Override
	public Tuple<String, Object[]> getErrorMessageKey() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public boolean isValid(String value) {
		if (value == null) {
			return true;
		}
		@SuppressWarnings("unused")
		DateTime dt;
		try {
			// ISO8601 yyyyMMdd
			dt = ISODateTimeFormat.basicDate().parseDateTime(value);
		} catch (IllegalFieldValueException e) {
			return false;
		} catch (IllegalArgumentException e) {
			return false;
		}
		dt = null;
		return true;
	}
}
