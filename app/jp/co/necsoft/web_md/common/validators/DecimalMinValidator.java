/**
 * 
 */
package jp.co.necsoft.web_md.common.validators;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import play.data.validation.Constraints.Validator;
import play.libs.F.Tuple;

/**
 * @author 1134140119159
 *
 */
public class DecimalMinValidator extends Validator<Number> implements
		ConstraintValidator<DecimalMin, Number> {

    final static public String message = "error.decimalmin";
    private BigDecimal value;
    
	@Override
	public void initialize(DecimalMin constraintAnnotation) {
		this.value = BigDecimal.valueOf(constraintAnnotation.value());
	}

	@Override
	public boolean isValid(Number object, ConstraintValidatorContext arg1) {
		return isValid(object);
	}
	
	@Override
	public Tuple<String, Object[]> getErrorMessageKey() {
		return play.libs.F.Tuple(message, new Object[] { value });
	}

	@Override
	public boolean isValid(Number object) {
        if (object == null) {
            return true;
        }
        
        BigDecimal bd = new BigDecimal(object.toString());
        if (bd.compareTo(value) < 0) {
        	return false;
        }
        return true;
	}

}
