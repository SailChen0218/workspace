package com.ezddd.core.event;

public interface EventGateway {
    void publish(Event event) throws Exception;
}
