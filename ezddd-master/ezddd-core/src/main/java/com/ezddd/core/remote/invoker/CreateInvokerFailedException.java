package com.ezddd.core.remote.invoker;

public class CreateInvokerFailedException extends RuntimeException {
    public CreateInvokerFailedException(String message) {
        super(message);
    }

    public CreateInvokerFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
