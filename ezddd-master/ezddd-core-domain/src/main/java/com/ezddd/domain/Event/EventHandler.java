package com.ezddd.domain.Event;

public interface EventHandler {
    EventResult handle(Event event);
}
