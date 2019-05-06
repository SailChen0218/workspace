package com.ezddd.core.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EzComponent {
    int priority() default 0;
    String area() default "standard";
}