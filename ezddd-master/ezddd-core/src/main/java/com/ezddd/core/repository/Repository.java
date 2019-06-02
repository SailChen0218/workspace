package com.ezddd.core.repository;

import com.ezddd.core.aggregate.AggregateNotFoundException;

public interface Repository<E> {
    
    E load(String identifier) throws AggregateNotFoundException;

    E load(String identifier, Long expectedVersion) throws AggregateNotFoundException;

    void create(E aggregate) throws Exception;

    int save(E aggregate) throws Exception;

    void removeByIdentifier(String identifier) throws AggregateNotFoundException;

    void remove(E aggregate) throws AggregateNotFoundException;
}
