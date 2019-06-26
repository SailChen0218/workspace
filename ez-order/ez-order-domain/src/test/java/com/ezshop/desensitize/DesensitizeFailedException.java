package com.ezshop.desensitize;

public class DesensitizeFailedException extends RuntimeException {
    public DesensitizeFailedException(String msg) {
        super(msg);
    }

    public DesensitizeFailedException(Throwable exception) {
        super(exception);
    }
}
