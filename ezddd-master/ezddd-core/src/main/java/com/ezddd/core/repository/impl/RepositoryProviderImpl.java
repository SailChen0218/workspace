package com.ezddd.core.repository.impl;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.repository.Repository;
import com.ezddd.core.repository.RepositoryProvider;

import java.util.HashMap;
import java.util.Map;

@EzComponent
public class RepositoryProviderImpl implements RepositoryProvider {
    private static Map<String, Repository> repositoryHolder = new HashMap<>();

    @Override
    public <T> Repository<T> repositoryFor(Class<?> aggregateType) {
        Repository<T> repository = null;
        if (!repositoryHolder.containsKey(aggregateType.getName())) {
            repository = new InMemeryRepository(aggregateType);
            repositoryHolder.put(aggregateType.getName(), repository);
        } else {
            repository = repositoryHolder.get(aggregateType.getName());
        }
        return repository;
    }
}
