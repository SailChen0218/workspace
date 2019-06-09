package com.ezddd.extension.mq.rocketmq;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.event.Event;
import com.ezddd.core.event.impl.AggregateEvent;
import com.ezddd.core.mq.AbstractEventProducer;
import com.ezddd.core.mq.ProducerStartFailedException;
import com.ezddd.core.response.MqSendResult;
import com.ezddd.core.utils.SerializationUtil;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.util.Assert;

import java.util.concurrent.atomic.AtomicBoolean;

@EzComponent
@Scope("prototype")
public class RmqEventProducer extends AbstractEventProducer {
    private static final Logger log = LoggerFactory.getLogger(RmqEventProducer.class);

    private DefaultMQProducer producer;

    @Value("${rocketmq.nameserver.address:172.16.1.4:9876}")
    private String namesrvAddr;

    /**
     * milliseconds
     */
    @Value("${rocketmq.producer.send-msg-timeout:10000}")
    private int sendMsgTimeout;

    @Value("${rocketmq.producer.retrytimes-when-sendasync-failed:3}")
    private int retryTimesWhenSendAsyncFailed;

    private AtomicBoolean isStarted = new AtomicBoolean(false);
    private String groupName;
    private String instanceName;

    @Override
    public void start(String groupName, String instanceName) throws ProducerStartFailedException {
        try {
            if (isStarted.compareAndSet(false, true)) {
                this.groupName = groupName;
                this.instanceName = instanceName;
                System.out.println("start producer. groupName:" + groupName + ", instanceName:" + instanceName);
                producer = new DefaultMQProducer(groupName);
                producer.setInstanceName(instanceName);
                producer.setRetryTimesWhenSendAsyncFailed(retryTimesWhenSendAsyncFailed);
                producer.setNamesrvAddr(namesrvAddr);
                producer.setSendMsgTimeout(sendMsgTimeout);
                producer.start();
            }
        } catch (MQClientException e) {
            throw new ProducerStartFailedException(e);
        }
    }

    @Override
    public void stutdown() {
        if (isStarted.compareAndSet(true, false)) {
            System.out.println("stutdown producer. groupName:" + groupName + ", instanceName:" + instanceName);
            producer.shutdown();
        }
    }

    @Override
    public MqSendResult<?> send(Event event) {
        Assert.notNull(event, "event must not be null when producer sending message.");
        try {
            Message message = new Message(
                    AggregateEvent.class.getSimpleName(),
                    event.getClass().getName(),
                    event.getEventName(),
                    SerializationUtil.writeToByteArray(event));

            SendResult sendResult = producer.send(message, 10000);
            if (sendResult.getSendStatus() == SendStatus.SEND_OK) {
                return MqSendResult.valueOfSuccess();
            } else {
                return MqSendResult.valueOfError("failed to send event message." +
                        " EventName:" + event.getEventName() +
                        " Identifier:" + event.getIdentifier() +
                        " SendStatus:" + sendResult.getSendStatus().toString());
            }
        } catch (MQClientException e) {
            log.error(e.getErrorMessage(), e);
            return MqSendResult.valueOfError(e);
        } catch (RemotingException e) {
            log.error(e.getMessage(), e);
            return MqSendResult.valueOfError(e);
        } catch (MQBrokerException e) {
            log.error(e.getErrorMessage(), e);
            return MqSendResult.valueOfError(e);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            return MqSendResult.valueOfError(e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return MqSendResult.valueOfError(e);
        }
    }
}
