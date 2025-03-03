package com.smartcontrol.webapp.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = IvaValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IvaConstraint 
{
	  String message() default "Inserisci un'aliquota iva presente in anagrafica";

	  Class<?>[] groups() default {};

	  Class<? extends Payload>[] payload() default {};
}

