package com.ezddd.core.repository;

public abstract class AbstractRepository<E> implements Repository<E> {
    protected Class<E> aggregateType;

    public Class<E> getAggregateType() {
        return aggregateType;
    }

    public void setAggregateType(Class<E> aggregateType) {
        this.aggregateType = aggregateType;
    }
}
