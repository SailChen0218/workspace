package com.ezddd.core.annotation;

import com.ezddd.core.remote.protocol.ProtocolType;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EzRemoting {
    String protocol() default ProtocolType.HESSIAN;
}
