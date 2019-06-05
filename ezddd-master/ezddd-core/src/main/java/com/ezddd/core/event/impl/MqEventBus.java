package com.ezddd.core.event.impl;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.event.*;
import com.ezddd.core.mq.Consumer;
import com.ezddd.core.mq.Producer;
import com.ezddd.core.response.MqSendResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.List;

@EzComponent
public class MqEventBus extends AbstractEventBus {
    private static final Logger log = LoggerFactory.getLogger(MqEventBus.class);

    @Autowired
    EventRegistry eventRegistry;

    @Autowired
    EventStore eventStore;

    @Autowired
    Producer<Event> producer;

    @Autowired
    Consumer<List<Event>> consumer;

    @PostConstruct
    public void init() {
        consumer.start();
    }

    @Override
    public void publish(Event event) throws EventPublishFailedException {
        Assert.notNull(event, "event must not be null.");
        EventDefinition eventDefinition = eventRegistry.findEventDefinition(event.getEventName());
        if (eventDefinition.isEventSourcing()) {
            // store event
            eventStore.appendEvent(event);
        }

        MqSendResult result = producer.send(event);
        if (!result.isSuccess()) {
            log.error("failed to send event message to the mq server. eventId:" + event.getEventId()
                    + ". error:" + result.getExceptionContent());
            throw new EventPublishFailedException(result.getExceptionContent());
        }
    }
}
