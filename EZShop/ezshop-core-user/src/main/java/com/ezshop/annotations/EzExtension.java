package com.ezshop.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface EzExtension {
    String area() default "";
    int priority() default 0;
}