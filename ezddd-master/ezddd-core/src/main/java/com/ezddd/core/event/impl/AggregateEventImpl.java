package com.ezddd.core.event.impl;

import com.ezddd.core.event.Event;
import com.ezddd.core.event.EventArgs;
import com.ezddd.core.utils.IdentifierUtil;

import java.time.Instant;

public class AggregateEventImpl<S> implements Event<S> {
    private static final long serialVersionUID = -6975653243726439083L;

    private String eventId;
    private String eventName;
    private int eventType;
    private S sender;
    private EventArgs eventArgs;
    private Instant timestamp;

    protected AggregateEventImpl(String eventName, S sender, EventArgs args, int eventType) {
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
        public static <S> AggregateEventImpl<S> createEvent(String eventName, S sender, EventArgs args, int eventType) {
            return new AggregateEventImpl(eventName, sender, args, eventType);
        }
    }
}
