package com.ezddd.core.event;

import java.lang.reflect.Method;

public abstract class AbstractEventDefinition implements EventDefinition {
    private String eventName;
    private int eventType;
    private String eventBus;
    private boolean eventSourcing;
    private EventListener eventListener;
    private Method mehtodOfHandler;

    protected AbstractEventDefinition(String eventName, int eventType) {
        if (eventName == null) {
            throw new IllegalArgumentException(
                    "The eventName must not be null. ");
        }
        this.eventName = eventName;
        this.eventType = eventType;
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
    public String getEventBus() {
        return "defaultEventBus";
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
}
