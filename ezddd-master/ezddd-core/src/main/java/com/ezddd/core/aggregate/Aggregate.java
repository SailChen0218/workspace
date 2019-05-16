package com.ezddd.core.aggregate;

/**
 *
 * @param <T> The aggregate root type
 */
public interface Aggregate<T> {

    String identifier();

    Long version();

    boolean isDeleted();

    Class<? extends T> rootType();
}
