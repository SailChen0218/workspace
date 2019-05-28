package com.ezddd.core.repository;

import org.springframework.util.Assert;

public abstract class AbstractRepository<T> implements Repository<T> {
    protected Class<T> aggregateType;

    public AbstractRepository(Class<T> aggregateType) {
        Assert.notNull("aggregateType must not be null. ");
        this.aggregateType = aggregateType;
    }

    public Class<T> getAggregateType() {
        return aggregateType;
    }
}
