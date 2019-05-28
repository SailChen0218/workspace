package com.ezddd.core.repository;

import com.ezddd.core.aggregate.AggregateNotFoundException;

/**
 * The repository provides an abstraction of the storage of aggregates.
 *
 * @param <T> The type of aggregate this repository stores.
 */
public interface Repository<T> {

    /**
     * Load the aggregate with the given unique identifier. No version checks are done when loading an aggregate,
     * meaning that concurrent access will not be checked for.
     *
     * @param identifier The identifier of the aggregate to load
     * @return The aggregate root with the given identifier.
     *
     * @throws AggregateNotFoundException if aggregate with given id cannot be found
     */
    T load(String identifier) throws AggregateNotFoundException;

    /**
     * Load the aggregate with the given unique identifier.
     *
     * @param identifier The identifier of the aggregate to load
     * @param expectedVersion     The expected version of the loaded aggregate
     * @return The aggregate root with the given identifier.
     *
     * @throws AggregateNotFoundException if aggregate with given id cannot be found
     */
    T load(String identifier, Long expectedVersion) throws AggregateNotFoundException;

    void create(T aggregate);

    int save(T aggregate);

    void removeByIdentifier(String identifier) throws AggregateNotFoundException;
}
