package com.ezddd.domain.model;

import org.springframework.util.Assert;

public abstract class AbstractAggregate<T, I> implements Aggregate<T, I> {
    private String type;
    private I identifier;
    private Long version;
    private boolean isDeleted;
    private Class<? extends T> rootType;

    @Override
    public String type() {
        return type;
    }

    @Override
    public Long version() {
        return version;
    }

    @Override
    public boolean isDeleted() {
        return isDeleted;
    }

    @Override
    public Class<? extends T> rootType() {
        return rootType;
    }

    @Override
    public I identifier() {
        return identifier;
    }

    @Override
    public boolean equals(Object obj) {
        Assert.notNull(obj, "equals parameter must not be null.");
        if (obj instanceof Aggregate) {
            return ((Aggregate) obj).identifier().equals(this.identifier);
        } else {
            return false;
        }
    }
}
