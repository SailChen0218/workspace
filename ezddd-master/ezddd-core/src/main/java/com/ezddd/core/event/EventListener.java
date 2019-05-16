package com.ezddd.core.event;

public interface EventListener<T> {
    void listening(Class<T> aggregateType);
}
