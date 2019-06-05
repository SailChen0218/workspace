package com.ezddd.core.event;

public class EventPulishFailedException extends RuntimeException {
    public EventPulishFailedException(Throwable cause) {
        super(cause);
    }

    public EventPulishFailedException(String error) {
        super(error);
    }
}
