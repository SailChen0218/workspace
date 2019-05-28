package com.ezddd.core.aggregate;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.event.*;
import com.ezddd.core.event.impl.AggregateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@EzComponent
public class AggregateManager {
    private static final Logger log = LoggerFactory.getLogger(AggregateManager.class);

    @Autowired
    private static EventGateway eventGateway;

    @Autowired
    private static EventRegistry eventRegistry;

    private static AggregateManager manager;

    public static <S, A> void apply(String eventName, S sender, A args) throws Exception {
        EventArgs<A> eventArgs = new EventArgs<>(args);
        EventDefinition eventDefinition = eventRegistry.findEventDefinition(eventName);
        Event<S> event = AggregateEvent.Factory.createEvent(
                eventName, sender, eventArgs, eventDefinition.getEventType());
        eventGateway.publish(event);
    }
}
