package com.ezddd.core.annotation;

import com.ezddd.core.constants.Area;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EzEvent {
    String domain();
    int priority() default 0;
    String area() default Area.STANDARD;
}
