package com.ezddd.core.aggregate;

import java.io.Serializable;

/**
 *
 * @param <T> The aggregate root type
 */
public interface Aggregate<T> extends Serializable {

    String identifier();

    Long version();

    boolean isDeleted();

    Class<? extends T> rootType();
}
