package com.ezddd.core.annotation;

import com.ezddd.core.command.CommandBusType;
import com.ezddd.core.constants.Area;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EzCommand {
    String domain();
    int commandType();
    String commandBus() default CommandBusType.DEFAULT;
    int priority() default 0;
    String area() default Area.STANDARD;
}
