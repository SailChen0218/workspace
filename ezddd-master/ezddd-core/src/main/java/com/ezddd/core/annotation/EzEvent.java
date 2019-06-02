package com.ezddd.core.annotation;

import com.ezddd.core.constants.Area;
import com.ezddd.core.event.impl.DefaultEventBus;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EzEvent {
    String domain();
    boolean eventSourcing() default true;
    Class<?> eventBusType() default DefaultEventBus.class;
    int priority() default 0;
    String area() default Area.STANDARD;
}
