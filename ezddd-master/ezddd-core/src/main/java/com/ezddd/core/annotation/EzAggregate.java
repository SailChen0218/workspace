package com.ezddd.core.annotation;

import com.ezddd.core.constants.Area;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EzAggregate {
    int priority() default 0;
    String area() default Area.STANDARD;
}
