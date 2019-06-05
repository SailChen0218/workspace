package com.ezddd.core.event;

import java.lang.reflect.Method;

public interface EventDefinition {
    String getEventName();

    int getEventType();

    Class<?> getEventBusType();

    Boolean isEventSourcing();

    Class<?> getEventClass();

    Class<?> getEventListenerType();

    Method getMehtodOfHandler();

    EventListener getEventListener();
}
