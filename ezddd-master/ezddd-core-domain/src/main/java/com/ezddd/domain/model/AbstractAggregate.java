package com.ezddd.domain.model;

import org.springframework.util.Assert;

public abstract class AbstractAggregate<T> implements Aggregate<T> {
    private String type;
    private String identifier;
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
    public String identifier() {
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
