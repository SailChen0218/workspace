package com.ezshop.desensitize.exception;

public class DesensitizeFailedException extends Exception {
    public DesensitizeFailedException(String msg) {
        super(msg);
    }

    public DesensitizeFailedException(Throwable exception) {
        super(exception);
    }

    public DesensitizeFailedException(String msg, Throwable exception) {
        super(msg, exception);
    }
}
