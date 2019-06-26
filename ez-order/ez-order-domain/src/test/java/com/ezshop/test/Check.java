package com.ezshop.test;

public @interface Check {
    String expression() default "";
    String message() default "";
}
