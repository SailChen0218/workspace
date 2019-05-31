package com.ezddd.core.repository;

import com.ezddd.core.registry.Registry;

public interface RepositoryRegistry extends Registry {
    Repository findRepository(Class<?> aggregateType);
}
