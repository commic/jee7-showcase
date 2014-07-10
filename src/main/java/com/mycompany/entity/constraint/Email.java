package com.mycompany.entity.constraint;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;


@Pattern.List({
	  @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
	    +"[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*"
	    +"@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
	})
	@Constraint(validatedBy = {})
	@Documented
	@Target({ElementType.METHOD,
	    ElementType.FIELD,
	    ElementType.ANNOTATION_TYPE,
	    ElementType.CONSTRUCTOR,
	    ElementType.PARAMETER})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Email {

	    String message() default "{Bitte geben Sie eine gültige E-Mail-Adresse ein}";

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
	        Email[] value();
	    }
	}