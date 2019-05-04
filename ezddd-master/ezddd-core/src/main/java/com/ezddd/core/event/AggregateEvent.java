package com.ezddd.core.event;

import com.ezddd.core.utils.IdentifierUtil;

import java.time.Instant;

public class AggregateEvent<S> implements Event<S> {
    private static final long serialVersionUID = -6975653243726439083L;
    private String eventId;
    private String eventName;
    private int eventType;
    private S sender;
    private EventArgs eventArgs;
    private Instant timestamp;

    protected AggregateEvent(String eventName, S sender, EventArgs args, int eventType) {
        this.eventId = IdentifierUtil.generateID();
        this.eventName = eventName;
        this.sender = sender;
        this.eventArgs = args;
        this.eventType = eventType;
        this.timestamp = Instant.now();
    }

    @Override
    public String getEventId() {
        return eventId;
    }

    @Override
    public String getEventName() {
        return eventName;
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public S getSender() {
        return this.sender;
    }

    @Override
    public <A> A getArgs() {
        if (eventArgs != null) {
            return (A)eventArgs.getArgs();
        }
        return null;
    }

    @Override
    public int getEventType() {
        return eventType;
    }

    public static class Factory {
        public static <S> AggregateEvent<S> createEvent(String eventName, S sender, EventArgs args, int eventType) {
            return new AggregateEvent(eventName, sender, args, eventType);
        }
    }
}
