package com.ezddd.common.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EzAppService {
    String bizCode() default "";
    int priority() default 0;
    String area() default "standard";
}
