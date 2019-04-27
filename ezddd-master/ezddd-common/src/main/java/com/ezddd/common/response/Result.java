package com.ezddd.common.response;

import java.io.Serializable;

public interface Result<T> extends Serializable {

    /**
     * Get invoke result.
     *
     * @return result. if no result return null.
     */
    T getValue();

    /**
     * Get exception.
     *
     * @return exception. if no exception return null.
     */
    Throwable getException();

    /**
     * Has exception.
     *
     * @return has exception.
     */
    boolean hasException();

}