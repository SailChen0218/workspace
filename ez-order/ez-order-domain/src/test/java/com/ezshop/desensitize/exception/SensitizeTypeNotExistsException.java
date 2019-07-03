package com.ezshop.desensitize.exception;

public class SensitizeTypeNotExistsException extends RuntimeException {
    public SensitizeTypeNotExistsException(String msg) {
        super(msg);
    }

    public SensitizeTypeNotExistsException(Throwable exception) {
        super(exception);
    }

    public SensitizeTypeNotExistsException(String msg, Throwable exception) {
        super(msg, exception);
    }
}
