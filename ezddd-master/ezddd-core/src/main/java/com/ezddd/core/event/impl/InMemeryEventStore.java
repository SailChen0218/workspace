package com.ezddd.core.event.impl;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.event.Event;
import com.ezddd.core.event.EventStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@EzComponent
public class InMemeryEventStore implements EventStore {
    private static Map<String, List<Event>> eventMap = new ConcurrentHashMap<>();

    @Override
    public List<Event> listEventsByIdentifier(Object identifier) {
        if (eventMap.containsKey(identifier)) {
            List<Event> eventList = eventMap.get(identifier);
            return eventList;
        } else {
            return null;
        }
    }

    @Override
    public void appendEvent(Event event) throws Exception {
        String identifier = event.getIdentifier();
        List<Event> eventList = null;
        if (eventMap.containsKey(identifier)) {
            eventList = eventMap.get(identifier);
        } else {
            eventList = new ArrayList<>();
        }
        eventList.add(event);
    }
}
