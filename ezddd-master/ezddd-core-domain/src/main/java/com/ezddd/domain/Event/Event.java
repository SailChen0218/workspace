package com.ezddd.domain.Event;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;

public interface Event<T> extends Serializable {
    Object getIdentifier();
    Instant getTimestamp();
    int getEventType();
    Event<T> withMetaData(T metaData);
    Event<T> additionalData(Map<String, Object> additionalData);
}
