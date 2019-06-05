package com.ezddd.core.event;

import java.lang.reflect.Method;

public abstract class AbstractEventDefinition implements EventDefinition {
    private String eventName;
    private int eventType;
    private Boolean eventSourcing;
    private Class<?> eventBusType;
    private EventListener eventListener;
    private Class<?> eventListenerType;
    private Method mehtodOfHandler;
    private Class<?> eventBeanType;

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
            case EventType.CREATED_:
                return EventType.CREATED;
            case EventType.EDITING_:
                return EventType.EDITING;
            case EventType.UPDATED_:
                return EventType.UPDATED;
            case EventType.DELETED_:
                return EventType.DELETED;
            default:
                if (EventType.VIEWED_.equals(eventType.substring(1, 7))) {
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
        return this.eventName;
    }

    @Override
    public final int getEventType() {
        return this.eventType;
    }

    @Override
    public Class<?> getEventBusType() {
        return this.eventBusType;
    }

    @Override
    public Boolean isEventSourcing() {
        return this.eventSourcing;
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

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public void setEventSourcing(Boolean eventSourcing) {
        this.eventSourcing = eventSourcing;
    }

    public void setEventBusType(Class<?> eventBusType) {
        this.eventBusType = eventBusType;
    }

    public Class<?> getEventBeanType() {
        return eventBeanType;
    }

    public void setEventBeanType(Class<?> eventBeanType) {
        this.eventBeanType = eventBeanType;
    }
}
