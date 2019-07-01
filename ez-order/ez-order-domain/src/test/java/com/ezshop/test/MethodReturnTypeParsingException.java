package com.ezshop.test;

public class MethodReturnTypeParsingException extends RuntimeException {
    public MethodReturnTypeParsingException(String msg) {
        super(msg);
    }

    public MethodReturnTypeParsingException(Throwable exception) {
        super(exception);
    }

    public MethodReturnTypeParsingException(String msg, Throwable exception) {
        super(msg, exception);
    }
}
