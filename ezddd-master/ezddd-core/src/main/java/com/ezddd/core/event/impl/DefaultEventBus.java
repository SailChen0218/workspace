package com.ezddd.core.event.impl;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.event.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.lang.reflect.Method;

@EzComponent
public class DefaultEventBus extends AbstractEventBus {

    @Autowired
    EventRegistry eventRegistry;

    @Autowired
    EventStore eventStore;

    @Override
    public void publish(Event event) throws Exception {
        Assert.notNull(event, "event must not be null.");

        EventDefinition eventDefinition = eventRegistry.findEventDefinition(event.getEventName());
        if (eventDefinition.isEventSourcing()) {
            // store event
            eventStore.appendEvent(event);
        }
        EventListener eventListener = eventDefinition.getEventListener();
        Method method = eventDefinition.getMehtodOfHandler();
        method.invoke(eventListener, event);
    }

}
