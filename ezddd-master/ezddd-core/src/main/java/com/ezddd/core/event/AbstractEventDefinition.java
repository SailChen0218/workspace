package com.ezddd.core.event;

import com.ezddd.core.event.impl.DefaultEventBus;

import java.lang.reflect.Method;

public abstract class AbstractEventDefinition implements EventDefinition {
    private String eventName;
    private int eventType;
    private boolean eventSourcing;
    private Class<?> eventBusType;
    private EventListener eventListener;
    private Class<?> eventListenerType;
    private Method mehtodOfHandler;

    protected AbstractEventDefinition(String eventName) {
        if (eventName == null) {
            throw new IllegalArgumentException(
                    "The eventName must not be null. ");
        }
        this.eventName = eventName;
        this.eventType = resolveEventType(eventName);
    }

    private int resolveEventType(String eventName) {
        String eventType = eventName.substring(eventName.length() - 7, eventName.length());
        switch (eventType) {
            case "Created":
                return EventType.CREATED;
            case "Editing":
                return EventType.EDITING;
            case "Updated":
                return EventType.UPDATED;
            case "Deleted":
                return EventType.DELETED;
            default:
                if ("Viewed".equals(eventType.substring(1, 7))) {
                    return EventType.VIEWED;
                }
        }
        throw new IllegalArgumentException("EventName:" + eventName + " is invalid.");
    }

    public final boolean equals(AbstractEventDefinition eventDefinition) {
        return this.eventName.equals(eventDefinition.getEventName());
    }

    @Override
    public final String toString() {
        return this.eventName;
    }

    @Override
    public final String getEventName() {
        return eventName;
    }

    @Override
    public final int getEventType() {
        return eventType;
    }

    @Override
    public Class<?> getEventBusType() {
        return DefaultEventBus.class;
    }

    @Override
    public boolean isEventSourcing() {
        return true;
    }

    @Override
    public Class<?> getEventClass() {
        return this.getClass();
    }

    @Override
    public EventListener getEventListener() {
        return eventListener;
    }

    @Override
    public Method getMehtodOfHandler() {
        return mehtodOfHandler;
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void setMehtodOfHandler(Method mehtodOfHandler) {
        this.mehtodOfHandler = mehtodOfHandler;
    }

    @Override
    public Class<?> getEventListenerType() {
        return eventListenerType;
    }

    public void setEventListenerType(Class<?> eventListenerType) {
        this.eventListenerType = eventListenerType;
    }
}
