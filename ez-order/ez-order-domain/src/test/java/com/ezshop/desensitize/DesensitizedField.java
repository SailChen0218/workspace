package com.ezshop.desensitize;

import com.ezshop.desensitize.type.SensitiveType;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DesensitizedField {
    Class<? extends SensitiveType> value();
}