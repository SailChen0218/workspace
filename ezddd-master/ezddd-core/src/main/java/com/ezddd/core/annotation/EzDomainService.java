package com.ezddd.core.annotation;

import com.ezddd.core.remote.RpcType;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EzDomainService {
    Class<?> interfaceType();
    RpcType rpcType() default RpcType.HESSIAN;
    int priority() default 0;
    String area() default "standard";
}
