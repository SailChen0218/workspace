package com.ezshop.desensitize;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DesensitizedField {
    Class<? extends SensitiveType> value();
}