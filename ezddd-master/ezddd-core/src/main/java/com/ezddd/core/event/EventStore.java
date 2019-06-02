package com.ezddd.core.event;

import java.util.List;

public interface EventStore {
    List<Event> listEventsByIdentifier(Object identifier);
    void appendEvent(Event event);
}
