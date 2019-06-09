package com.ezddd.core.mq;

public class ProducerStartFailedException extends RuntimeException {
    public ProducerStartFailedException(Throwable cause) {
        super(cause);
    }

    public ProducerStartFailedException(String error) {
        super(error);
    }
}
