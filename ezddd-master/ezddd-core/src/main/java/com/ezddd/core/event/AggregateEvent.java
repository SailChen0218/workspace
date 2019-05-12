package com.ezddd.core.event;

import java.time.Instant;

public interface AggregateEvent<S> extends Event<S> {
    Instant getTimestamp();
    String getEventId();
    String getEventName();
    int getEventType();
    S getSender();
    <A> A getArgs();
}
