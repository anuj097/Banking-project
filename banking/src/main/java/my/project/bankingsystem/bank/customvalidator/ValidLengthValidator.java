package my.project.bankingsystem.bank.customvalidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidLengthValidator implements ConstraintValidator<ValidLength, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		if(value.length()==6) {
			return true;
		}
		return false;
	}

}
