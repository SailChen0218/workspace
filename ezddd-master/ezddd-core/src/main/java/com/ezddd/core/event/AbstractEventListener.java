package com.ezddd.core.event;

import com.ezddd.core.repository.Repository;
import com.ezddd.core.repository.RepositoryProvider;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractEventListener<T> implements EventListener<T> {
    protected Class<T> aggregateType;
    protected Repository<T> repository;

    @Autowired
    protected RepositoryProvider repositoryProvider;

    @Override
    public void listening(Class<T> aggregateType) {
        this.aggregateType = aggregateType;
        if (repository == null) {
            repository = repositoryProvider.repositoryFor(aggregateType);
        }
    }
}
