package com.ezddd.core.event;

import java.lang.reflect.Method;

public abstract class AbstractEventDefinition {
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

    public final String getEventName() {
        return eventName;
    }

    public final int getEventType() {
        return eventType;
    }

    public String getEventBus() {
        return "defaultEventBus";
    }

    public boolean isEventSourcing() {
        return true;
    }


}
