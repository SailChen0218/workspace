package com.ezddd.domain.Event;

import java.util.EventListener;

public interface EventBus {
    void publish(Event<?> event) throws Exception;
}
