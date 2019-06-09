package com.ezddd.core.command.query.constraints;

import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RUNTIME)
@Inherited
public @interface Constraint {
    ConstraintType type();
    String message();
}
