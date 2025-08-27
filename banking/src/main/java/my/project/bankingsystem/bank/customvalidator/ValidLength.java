package my.project.bankingsystem.bank.customvalidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValidLengthValidator.class)	// required validation dependency
public @interface ValidLength {
	
	String message() default "Invalid characters";
	
	Class<?>[] groups() default { };
	
	Class<? extends Payload>[] payload() default { }; 
}
