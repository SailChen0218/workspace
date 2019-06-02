package com.ezddd.core.event.impl;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.event.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

@EzComponent
public class DefaultEventGateway implements EventGateway {

    @Autowired
    EventBusRegistry eventBusRegistry;

    @Autowired
    EventRegistry eventRegistry;

    @Override
    public void publish(Event event) throws EventPublishFailedException {
        try {
            Assert.notNull(event, "event must not be null.");
            EventDefinition eventDefinition = eventRegistry.findEventDefinition(event.getEventName());
            EventBus eventBus = eventBusRegistry.findEventBus(eventDefinition.getEventBusType());
            eventBus.publish(event);
        } catch (Exception ex) {
            throw new EventPublishFailedException("failed to publish event:" + event.getEventName(), ex.getCause());
        }
    }
}
