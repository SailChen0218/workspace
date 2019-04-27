package com.ezddd.domain.Event;

import java.util.EventListener;

public interface EventBus {
    void publishEvent(Event<?> event) throws Exception;
}
