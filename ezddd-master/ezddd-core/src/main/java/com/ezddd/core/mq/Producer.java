package com.ezddd.core.mq;

import com.ezddd.core.response.MqSendResult;

public interface Producer<T> {
    MqSendResult<?> send(T msg);
    void start(String groupName, String instanceName) throws ProducerStartFailedException;
    void stutdown();
}
