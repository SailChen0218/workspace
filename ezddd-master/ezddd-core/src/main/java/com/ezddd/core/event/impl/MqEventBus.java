package com.ezddd.core.event.impl;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.event.*;
import com.ezddd.core.mq.Producer;
import com.ezddd.core.response.MqSendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.util.Assert;

@EzComponent
@ConditionalOnBean(Producer.class)
public class MqEventBus extends AbstractEventBus {

    @Autowired
    EventRegistry eventRegistry;

    @Autowired
    EventStore eventStore;

    @Autowired
    Producer<Event> producer;

    @Override
    public void publish(Event event) throws Exception {
        Assert.notNull(event, "event must not be null.");
        EventDefinition eventDefinition = eventRegistry.findEventDefinition(event.getEventName());
        if (eventDefinition.isEventSourcing()) {
            // store event
            eventStore.appendEvent(event);
        }

        MqSendResult result = producer.send(event);
        if (!result.isSuccess()) {
            throw new EventPublishFailedException(result.getExceptionContent());
        }
    }
}
