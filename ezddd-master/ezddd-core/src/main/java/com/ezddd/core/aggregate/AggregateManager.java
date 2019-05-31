package com.ezddd.core.aggregate;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.event.*;
import com.ezddd.core.event.impl.AggregateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@EzComponent
public class AggregateManager {
    private static final Logger log = LoggerFactory.getLogger(AggregateManager.class);

    @Autowired
    EventGateway eventGateway;

    @Autowired
    EventRegistry eventRegistry;

    private static AggregateManager manager;

    @PostConstruct
    public void init() {
        manager = this;
        manager.eventGateway = this.eventGateway;
        manager.eventRegistry = this.eventRegistry;
    }

    public static <S, A> void apply(String eventName, S sender, A args) throws Exception {
        EventArgs<A> eventArgs = new EventArgs<>(args);
        EventDefinition eventDefinition = manager.eventRegistry.findEventDefinition(eventName);
        Event<S> event = AggregateEvent.Factory.createEvent(
                eventName, sender, eventArgs, eventDefinition.getEventType());
        manager.eventGateway.publish(event);
    }
}
