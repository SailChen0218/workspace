package com.ezddd.core.repository;

public abstract class AbstractRepository<T> implements Repository {
    protected Class<T> aggregateType;

    public AbstractRepository(Class<T> clazz) {
        this.aggregateType = clazz;
    }

    public Class<T> getAggregateType() {
        return aggregateType;
    }
}
