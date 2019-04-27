package com.ezddd.domain.Event;

public interface EventHandler<T> {
    EventResult handle(Event<T> event);
}
