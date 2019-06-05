package com.ezddd.core.mq;

public class ConsumerStartFailedException extends RuntimeException {
    public ConsumerStartFailedException(Throwable cause) {
        super(cause);
    }

    public ConsumerStartFailedException(String error) {
        super(error);
    }
}
