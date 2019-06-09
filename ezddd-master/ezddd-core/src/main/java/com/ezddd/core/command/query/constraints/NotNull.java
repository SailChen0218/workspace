package com.ezddd.core.command.query.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

//@Retention(RUNTIME)
//@Target(ElementType.ANNOTATION_TYPE)
//@Documented
//@Constraint
//public @interface NotNull {
//	String message() default "{javax.validation.constraints.NotNull.message}";
//}

public class NotNull implements Validator<String> {
	@Override
	public boolean validate(String value) throws ValidatorException {
		return false;
	}
}
