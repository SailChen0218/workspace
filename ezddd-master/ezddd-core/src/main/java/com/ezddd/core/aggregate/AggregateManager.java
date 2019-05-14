package com.ezddd.core.aggregate;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.context.CommandContext;
import com.ezddd.core.context.CommandContextHolder;
import com.ezddd.core.event.EventArgs;
import com.ezddd.core.event.EventDefinition;
import com.ezddd.core.event.EventGateway;
import com.ezddd.core.event.EventRegistry;
import com.ezddd.core.event.impl.AggregateEventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@EzComponent
public class AggregateManager {
    private static final Logger log = LoggerFactory.getLogger(AggregateManager.class);

    @Autowired
    EventGateway eventGateway;

    @Autowired
    EventRegistry eventRegistry;

    private static AggregateManager manager;

    public static <S, A> void apply(String eventName, S sender, A args) throws Exception {
        EventArgs<A> eventArgs = new EventArgs<>(args);
        EventDefinition eventDefinition = manager.eventRegistry.findEventDefinition(eventName);
        AggregateEventImpl<S> event = AggregateEventImpl.Factory.createEvent(
                eventName, sender, eventArgs, eventDefinition.getEventType());
        manager.eventGateway.publish(event);
    }
}
