package com.ezshop.test;

public @interface Verification {
    String target();
    Check[] rules() default {};
}
