package com.ezshop.desensitize;

import java.util.Map;

public class ValidateFailedException extends RuntimeException {
    private Map<String, String> errors;

    public ValidateFailedException(String msg) {
        super(msg);
    }

    public ValidateFailedException(Map<String, String> errors, String msg) {
        super(msg);
        this.errors = errors;
    }

    public ValidateFailedException(Throwable exception) {
        super(exception);
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
