package com.ezddd.common.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EzCommandExecutor {
    Class<?> commandType();
    Class<?> commandBus() default Object.class;
    int priority() default 0;
    String area() default "standard";
}
