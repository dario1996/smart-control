package com.smartcontrol.webapp.validations;

import com.smartcontrol.webapp.repository.IvaRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IvaValidator implements  ConstraintValidator<IvaConstraint, Integer>
{
	private IvaRepository ivaRepository;
	
	public IvaValidator(IvaRepository ivaRepository)
	{
		this.ivaRepository = ivaRepository;
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) 
	{
		return ivaRepository.findByIdIva(value) != null;
	}

}
