package com.mycompany.entity.constraint;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;


@Pattern.List({
	@Pattern(regexp="\\+?\\d{2}\\d{11}")
	})
	@Constraint(validatedBy = {})
	@Documented
	@Target({ElementType.METHOD,
	    ElementType.FIELD,
	    ElementType.ANNOTATION_TYPE,
	    ElementType.CONSTRUCTOR,
	    ElementType.PARAMETER})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Phone {

	    String message() default "{Bitte geben Sie eine gültige Telefon- bzw. Faxnummer an}";

	    Class<?>[] groups() default {};

	    Class<? extends Payload>[] payload() default {};

	    @Target({ElementType.METHOD,
	        ElementType.FIELD,
	        ElementType.ANNOTATION_TYPE,
	        ElementType.CONSTRUCTOR,
	        ElementType.PARAMETER})
	    @Retention(RetentionPolicy.RUNTIME)
	    @Documented
	    @interface List {
	        Phone[] value();
	    }
	}