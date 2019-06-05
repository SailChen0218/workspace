package com.ezddd.core.mq;

public interface Consumer<T> {
    void onMessage(T msg) throws ConsumeMessageFailedException;
    void start() throws ConsumerStartFailedException;
}
