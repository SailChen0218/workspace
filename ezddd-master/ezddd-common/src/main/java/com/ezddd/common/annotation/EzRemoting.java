package com.ezddd.common.annotation;

import com.ezddd.common.remote.RpcType;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EzRemoting {
    RpcType rpcType() default RpcType.HESSIAN;
}
