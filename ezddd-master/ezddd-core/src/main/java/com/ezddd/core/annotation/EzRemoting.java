package com.ezddd.core.annotation;

import com.ezddd.core.remote.RpcType;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EzRemoting {
    RpcType rpcType() default RpcType.HESSIAN;
}
