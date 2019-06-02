package com.ezddd.core.event.impl;

import com.ezddd.core.event.Event;
import com.ezddd.core.event.EventStore;

import java.util.List;

public class RocketMQEventStore implements EventStore {
    @Override
    public List<Event> listEventsByIdentifier(Object identifier) {
        return null;
    }

    @Override
    public void appendEvent(Event event) {

    }
}
