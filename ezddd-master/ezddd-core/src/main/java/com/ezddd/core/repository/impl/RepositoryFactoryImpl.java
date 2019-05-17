package com.ezddd.core.repository.impl;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.repository.Repository;
import com.ezddd.core.repository.RepositoryFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@EzComponent
public class RepositoryFactoryImpl implements RepositoryFactory {
    private static Map<String, Repository> repositoryHolder = new ConcurrentHashMap<>();

    @Override
    public <T> Repository<T> getRepository(Class<?> aggregateType) {
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
