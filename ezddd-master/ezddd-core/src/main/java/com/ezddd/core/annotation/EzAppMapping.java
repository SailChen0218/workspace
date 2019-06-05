package com.ezddd.core.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EzAppMapping {
    String bizDetailCode() default "";
}