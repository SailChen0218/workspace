package com.ezddd.domain.Event;

import java.util.List;

public interface EventStore {
    List<Event> listEventsByIdentifier(Object identifier);
    void appendEvent(Event event) throws Exception;
}
