package com.ezddd.domain.Event;

import com.ezddd.domain.model.Aggregate;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;

public interface Event<T extends Aggregate> extends Serializable {
    String getEventName();
    Instant getTimestamp();
    int getEventType();
    Event<T> withSender(T sender);
    Event<T> withArgs(Map<String, Object> args);
}
