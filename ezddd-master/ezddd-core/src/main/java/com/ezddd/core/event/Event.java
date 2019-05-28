package com.ezddd.core.event;

import java.io.Serializable;
import java.time.Instant;

public interface Event<S> extends Serializable {
    Instant getTimestamp();
    String getEventId();
    String getEventName();
    String getIdentifier();
    int getEventType();
    S getSender();
    <A> A getArgs();
}
