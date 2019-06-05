package com.ezddd.extension.mq.rocketmq;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.event.Event;
import com.ezddd.core.event.impl.AggregateEvent;
import com.ezddd.core.mq.AbstractEventProducer;
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
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

@EzComponent
public class RmqEventProducer extends AbstractEventProducer {
    private static final Logger log = LoggerFactory.getLogger(RmqEventProducer.class);

    private DefaultMQProducer producer;

    @PostConstruct
    public void init() throws MQClientException {
        producer = new DefaultMQProducer("RmqEventProducer");
        producer.setInstanceName("RmqEventProducer1");
        producer.setRetryTimesWhenSendAsyncFailed(3);
        producer.setNamesrvAddr("172.16.1.4:9876");
        //30000 milliseconds
        producer.setSendMsgTimeout(10000);
        producer.start();
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
