package com.smartcontrol.webapp.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EanValidator  implements  ConstraintValidator<EanConstraint, String>
{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) 
	{
		return value != null && value.matches("[0-9]+")
		          && (value.length() == 8) || (value.length() == 13);
	}

}
