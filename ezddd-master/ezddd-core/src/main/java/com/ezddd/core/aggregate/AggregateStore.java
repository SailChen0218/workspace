package com.ezddd.core.aggregate;

public interface AggregateStore {
    <T> Aggregate<T> find(String identifier);
    <T> boolean save(Aggregate<T> aggregate);
    boolean remove(String identifier);
    boolean contains(String identifier);
}
