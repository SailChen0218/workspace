package com.ezddd.core.event;

import java.io.Serializable;
import java.time.Instant;

public interface Event<S> extends Serializable {
    String getEventId();
    String getEventName();
    int getEventType();
    Instant getTimestamp();
    S getSender();
    <A> A getArgs();
}
