package com.ezddd.core.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EzCommand {
    String domain();
    int commandType();
    String commandBus() default "DefaultCommandBus";
    int priority() default 0;
    String area() default "standard";
    int grade() default 0;
}
