package com.ezshop.desensitize;

import com.ezshop.desensitize.dto.ErrorDto;
import java.util.List;

public class ValidateFailedException extends RuntimeException {
    private List<ErrorDto> errors;

    public ValidateFailedException(String msg) {
        super(msg);
    }

    public ValidateFailedException(List<ErrorDto> errors, String msg) {
        super(msg);
        this.errors = errors;
    }

    public ValidateFailedException(Throwable exception) {
        super(exception);
    }

    public List<ErrorDto> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDto> errors) {
        this.errors = errors;
    }
}
