package com.ezddd.core.event;

import java.lang.reflect.Method;

public interface EventDefinition {
    String getEventName();

    int getEventType();

    String getEventBus();

    boolean isEventSourcing();

    Class<?> getEventClass();

    EventListener getEventListener();

    Method getMehtodOfHandler();
}
