package com.ezddd.core.event;

public class EventPublishFailedException extends RuntimeException {
    public EventPublishFailedException(String message) {
        super(message);
    }

    public EventPublishFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
