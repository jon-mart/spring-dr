
package com.example.demo;

//import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator{
	
	Person person;
//	@Autowired
//	@Qualifier("emailValidator")
//	EmailValidator emailValidator;

	@Override
	public boolean supports(Class<?> arg0) {
		return Person.class.equals(arg0);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "firstName", "need first name");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "lastName", "need last name");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "email", "need email");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "workingYears", "need working experences");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "desgination", "need desgination");
		
	}
	
}
