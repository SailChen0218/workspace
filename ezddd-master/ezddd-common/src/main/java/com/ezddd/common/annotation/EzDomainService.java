package com.ezddd.common.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EzDomainService {
    int priority() default 0;
    String area() default "standard";
}
