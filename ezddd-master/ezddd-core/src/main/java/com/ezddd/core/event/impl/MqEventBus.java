package com.ezddd.core.event.impl;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.event.*;
import com.ezddd.core.mq.Consumer;
import com.ezddd.core.mq.Producer;
import com.ezddd.core.response.MqSendResult;
import com.ezddd.core.spring.EzApplicationContextUtil;
import com.ezddd.core.utils.load.PollingLoadStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${rocketmq.producer.instance-number:1}")
    private int producerNumber;

    @Value("${rocketmq.consumer.instance-number:1}")
    private int consumerNumber;

    private Producer<Event>[] producers;
    private Consumer<List<Event>>[] consumers;
    private PollingLoadStrategy<Producer<Event>> producerLoadStrategy;

    @PostConstruct
    public void init() {
        producers = new Producer[producerNumber];
        for (int i = 0; i < producerNumber; i++) {
            producers[i] = EzApplicationContextUtil.getBean(Producer.class);
            producers[i].start("EventProducer", "producer" + i);
        }
         consumers = new Consumer[consumerNumber];
        for (int i = 0; i < consumerNumber; i++) {
            consumers[i] = EzApplicationContextUtil.getBean(Consumer.class);
            consumers[i].start("EventConsumer", "consumer" + i);
        }
        producerLoadStrategy = new PollingLoadStrategy(producers);
    }

    @Override
    public void publish(Event event) throws EventPublishFailedException {
        try {
            Assert.notNull(event, "event must not be null.");
            EventDefinition eventDefinition = eventRegistry.findEventDefinition(event.getEventName());
            if (eventDefinition.isEventSourcing()) {
                // store event
                eventStore.appendEvent(event);
            }

            MqSendResult result = producerLoadStrategy.choose().send(event);
            if (!result.isSuccess()) {
                log.error("failed to send event message to the mq server. eventId:" + event.getEventId()
                        + ". error:" + result.getExceptionContent());
                throw new EventPublishFailedException(result.getExceptionContent());
            }
        } catch (Exception ex) {
            throw new EventPublishFailedException("failed to send event message to the mq server. eventId:" + event.getEventId() +
                    ". error:" + ex.getMessage(), ex);
        }

    }
}
