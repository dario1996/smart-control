package com.smartcontrol.webapp.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


@Documented
@Constraint(validatedBy = EanValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EanConstraint 
{
	  String message() default "Nel campo barcode sono ammessi solo valori numerici aventi esattamente 8 o 13 caratteri";

	  Class<?>[] groups() default {};

	  Class<? extends Payload>[] payload() default {};
}
