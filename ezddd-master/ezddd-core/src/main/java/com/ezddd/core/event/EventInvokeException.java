package com.ezddd.core.event;

public class EventInvokeException extends RuntimeException {
    public EventInvokeException(Throwable cause) {
        super(cause);
    }

    public EventInvokeException(String error) {
        super(error);
    }
}
