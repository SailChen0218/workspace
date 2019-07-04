package com.ezshop.desensitize.exception;

public class MethodReturnTypeParsingException extends Exception {
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
