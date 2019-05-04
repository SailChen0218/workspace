/*
 * Copyright (c) 2010-2018. Axon Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ezddd.core.aggregate;

/**
 *
 * @param <T> The aggregate root type
 */
public interface Aggregate<T> {

    /**
     * Get the String representation of the aggregate's type. This defaults to the simple name of the {@link
     * #rootType()} unless configured otherwise.
     *
     * @return The aggregate's type
     */
    String type();

    /**
     * Get the unique identifier of this aggregate
     *
     * @return The aggregate's identifier
     */
    String identifier();

    /**
     * Get the aggregate's version. For event sourced aggregates this is identical to the sequence number of the last
     * applied event.
     *
     * @return The aggregate's version
     */
    Long version();

    /**
     * Check if this aggregate has been deleted. This is checked by aggregate repositories when an aggregate is loaded.
     * In case the repository is asked to load a deleted aggregate the repository will refuse by throwing an {@link
     * com.ezddd.domain.model.AggregateDeletedException}.
     *
     * @return {@code true} in case the aggregate was deleted, {@code false} otherwise
     */
    boolean isDeleted();

    /**
     * Get the class type of the wrapped aggregate root that the Aggregate defers to for command handling.
     *
     * @return The aggregate root type
     */
    Class<? extends T> rootType();
}
