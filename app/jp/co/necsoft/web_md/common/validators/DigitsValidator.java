/**
 * 
 */
package jp.co.necsoft.web_md.common.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import play.data.validation.Constraints.Validator;
import play.libs.F.Tuple;

/**
 * @author 1134140119159
 *
 */
public class DigitsValidator extends Validator<Number> implements
		ConstraintValidator<Digits, Number> {

    final static public String message = "error.digits";
    private long integer;
    private long fraction;
    
	@Override
	public void initialize(Digits constraintAnnotation) {
		this.integer = constraintAnnotation.integer();
		this.fraction = constraintAnnotation.fraction();
	}

	@Override
	public boolean isValid(Number object, ConstraintValidatorContext arg1) {
		return isValid(object);
	}

	
	@Override
	public Tuple<String, Object[]> getErrorMessageKey() {
		return play.libs.F.Tuple(message, new Object[] { integer, fraction });
	}

	@Override
	public boolean isValid(Number object) {
        if (object == null) {
            return true;
        }
        
        String str = object.toString();
        String[] strArr = str.split("\\.");
        if (strArr[0].length() > integer) {
        	return false;
        }
        if (strArr.length > 1) {
        	if (strArr[1].length() > fraction) {
        		return false;
        	}
        }
        return true;
	}

}
