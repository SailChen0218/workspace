package com.ezddd.core.event;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractDeclaredEvent {
    private static final Map<String, AbstractDeclaredEvent> evenMap = new ConcurrentHashMap<>();
    private String eventName;
    private int eventType;
    private String eventBus;
    private boolean eventSourcing;

    protected AbstractDeclaredEvent(String eventName, int eventType) {
        if (!evenMap.containsKey(eventName)) {
            evenMap.put(eventName, this);
        } else {
            throw new IllegalArgumentException(
                    "The event named " + eventName + " already exist. ");
        }
        this.eventName = eventName;
        this.eventType = eventType;
    }

    public boolean equals(AbstractDeclaredEvent abstractDeclaredEvent) {
        return this.eventName == null ||
                abstractDeclaredEvent == null ? false : this.eventName.equals(abstractDeclaredEvent.getEventName());
    }

    @Override
    public String toString() {
        return this.eventName;
    }

    public static AbstractDeclaredEvent valueOf(String eventName) {
        if (eventName == null) {
            throw new NullPointerException("eventName must not be null. ");
        }

        AbstractDeclaredEvent result = evenMap.get(eventName);
        if (result != null) {
            return result;
        }

        throw new IllegalArgumentException(
                "There is no event named " + eventName + " exists. ");
    }

    public String getEventName() {
        return eventName;
    }

    public int getEventType() {
        return eventType;
    }

    public String getEventBus() {
        return "defaultEventBus";
    }

    public boolean isEventSourcing() {
        return true;
    }
}
