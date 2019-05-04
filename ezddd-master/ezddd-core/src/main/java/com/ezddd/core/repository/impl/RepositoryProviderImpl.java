package com.ezddd.core.repository.impl;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.repository.Repository;
import com.ezddd.core.repository.RepositoryProvider;

import java.util.HashMap;
import java.util.Map;

@EzComponent
public class RepositoryProviderImpl implements RepositoryProvider {
    private Map<String, Repository> repositoryHolder = new HashMap<>();

    @Override
    public <T> Repository<T> repositoryFor(Class<T> aggregateType) {
        Repository<T> repository = repositoryHolder.get(aggregateType.getSimpleName());
        if (repository == null) {
            repository = new InMemeryRepository<T>(aggregateType);
        }
        return repository;
    }
}
