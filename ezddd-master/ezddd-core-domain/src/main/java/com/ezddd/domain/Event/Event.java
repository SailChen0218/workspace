package com.ezddd.domain.Event;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;

public interface Event extends Serializable {
    String getEventName();
    Instant getTimestamp();
    int getEventType();
    <T> Event withSender(T sender);
    Event withArgs(EventArgs args);
}
