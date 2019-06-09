package com.ezddd.core.command.query;

public class GetParameterFailedException extends RuntimeException {
    public GetParameterFailedException(String message) {
        super(message);
    }

    public GetParameterFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
