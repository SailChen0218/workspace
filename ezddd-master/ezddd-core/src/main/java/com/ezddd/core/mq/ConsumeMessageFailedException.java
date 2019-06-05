package com.ezddd.core.mq;

public class ConsumeMessageFailedException extends RuntimeException {
    public ConsumeMessageFailedException(Throwable cause) {
        super(cause);
    }

    public ConsumeMessageFailedException(String error) {
        super(error);
    }
}
