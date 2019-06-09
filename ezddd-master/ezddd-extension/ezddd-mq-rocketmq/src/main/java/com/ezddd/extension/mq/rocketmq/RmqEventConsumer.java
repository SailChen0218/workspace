package com.ezddd.extension.mq.rocketmq;


import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.event.Event;
import com.ezddd.core.event.EventDefinition;
import com.ezddd.core.event.EventRegistry;
import com.ezddd.core.event.EventStore;
import com.ezddd.core.event.impl.AggregateEvent;
import com.ezddd.core.event.impl.EventInvoker;
import com.ezddd.core.mq.AbstractEventConsumer;
import com.ezddd.core.mq.ConsumeMessageFailedException;
import com.ezddd.core.mq.Consumer;
import com.ezddd.core.mq.ConsumerStartFailedException;
import com.ezddd.core.utils.SerializationUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@EzComponent
@Scope("prototype")
public class RmqEventConsumer extends AbstractEventConsumer<List<Event>> {
    private static final Logger log = LoggerFactory.getLogger(RmqEventConsumer.class);

    @Value("${rocketmq.nameserver.address:172.16.1.4:9876}")
    private String namesrvAddr;

    @Autowired
    EventRegistry eventRegistry;

    @Autowired
    EventStore eventStore;

    private DefaultMQPushConsumer consumer;

    private AtomicBoolean isStarted = new AtomicBoolean(false);

    private String groupName;
    private String instanceName;

    @Override
    public void onMessage(List<Event> eventList) throws ConsumeMessageFailedException {
        Assert.notNull(eventList, "event must not be null.");
        try {
//            TimeUnit.MINUTES.sleep(1);
            for (Event event: eventList) {
                EventDefinition eventDefinition = eventRegistry.findEventDefinition(event.getEventName());
                if (eventDefinition.isEventSourcing()) {
                    // store event
                    eventStore.appendEvent(event);
                }
                EventInvoker.handle(event, eventDefinition);
            }
        } catch (Exception e) {
            throw new ConsumeMessageFailedException(e);
        }
    }

    @Override
    public void start(String groupName, String instanceName) throws ConsumerStartFailedException {
        try {
            if (isStarted.compareAndSet(false, true)) {
                this.groupName = groupName;
                this.instanceName = instanceName;
                System.out.println("start consumer. groupName:" + groupName + ", instanceName:" + instanceName);
                consumer = new DefaultMQPushConsumer(groupName);
                consumer.setInstanceName(instanceName);
                consumer.setNamesrvAddr(namesrvAddr);
                consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
                consumer.subscribe(AggregateEvent.class.getSimpleName(), "*");
                consumer.registerMessageListener(new RmqEventListener(this));
                consumer.start();
            }
        } catch (MQClientException e) {
            throw new ConsumerStartFailedException(e);
        }
    }

    @Override
    public void shutdown() {
        if (isStarted.compareAndSet(true, false)) {
            System.out.println("shutdown consumer. groupName:" + groupName + ", instanceName:" + instanceName);
            consumer.shutdown();
        }
    }

    private class RmqEventListener implements MessageListenerConcurrently {
        private final Consumer consumer;
        public RmqEventListener(Consumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            try {
                if (msgs != null && msgs.size() > 0) {
                    List<Event> eventList = new ArrayList<>(msgs.size());
                    for (MessageExt messageExt : msgs) {
                        Event event = SerializationUtil.readFromByteArray(messageExt.getBody());
                        eventList.add(event);
                    }
                    this.consumer.onMessage(eventList);
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } catch (ConsumeMessageFailedException e) {
                log.error(e.getMessage(), e);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        }
    }
}
