package com.ezddd.core.event;

import com.ezddd.core.repository.Repository;

public class AbstractEventListener<T> implements EventListener<T> {
    protected Class<T> aggregateType;
    protected Repository<T> repository;
}
