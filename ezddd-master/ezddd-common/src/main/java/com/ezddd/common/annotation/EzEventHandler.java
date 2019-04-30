package com.ezddd.common.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EzEventHandler {
    boolean eventSourcing() default true;
}
