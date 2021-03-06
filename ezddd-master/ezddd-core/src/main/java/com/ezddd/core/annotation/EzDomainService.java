package com.ezddd.core.annotation;

import com.ezddd.core.constants.Area;
import com.ezddd.core.remote.protocol.ProtocolType;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EzDomainService {
    Class<?> interfaceType();
    String protocol() default ProtocolType.HESSIAN;
    int priority() default 0;
    String area() default Area.STANDARD;
}
