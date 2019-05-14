package com.ezddd.core.event.impl;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.event.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

@EzComponent
public class DefaultEventGateway implements EventGateway {

    @Autowired
    EventBusRegistry eventBusRegistry;

    @Override
    public void publish(Event event) throws Exception {
        Assert.notNull(event, "event must not be null.");
        EventBus eventBus = eventBusRegistry.findEventBus(event.getEventName());
        eventBus.publish(event);
    }
}
