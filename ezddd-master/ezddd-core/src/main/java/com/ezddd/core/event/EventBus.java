package com.ezddd.core.event;

public interface EventBus {
    void publish(Event event) throws Exception;
}
