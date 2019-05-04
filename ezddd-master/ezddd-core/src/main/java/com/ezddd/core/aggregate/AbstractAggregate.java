package com.ezddd.core.aggregate;

import org.springframework.util.Assert;

public abstract class AbstractAggregate<T> implements Aggregate<T> {
    protected String type;
    protected String identifier;
    protected Long version;
    protected boolean isDeleted;
    protected Class<? extends T> rootType;

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
