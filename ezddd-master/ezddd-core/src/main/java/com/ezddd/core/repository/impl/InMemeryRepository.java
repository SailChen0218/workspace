package com.ezddd.core.repository.impl;

import com.ezddd.core.aggregate.AggregateNotFoundException;
import com.ezddd.core.aggregate.AggregateWrapper;
import com.ezddd.core.repository.AbstractRepository;
import org.springframework.util.Assert;

import java.util.concurrent.ConcurrentHashMap;

public class InMemeryRepository<T> extends AbstractRepository<T> {
    private volatile ConcurrentHashMap<String, AggregateWrapper<T>> aggregateHolder = new ConcurrentHashMap<>();

    public InMemeryRepository(Class<T> aggregateType) {
        super(aggregateType);
    }

    @Override
    public T load(String aggregateIdentifier) throws AggregateNotFoundException {
        if (isExists(aggregateIdentifier)) {
            AggregateWrapper<T> aggregate = aggregateHolder.get(aggregateIdentifier);
            return aggregate.getConcreteComponent();
        } else {
            return null;
        }
    }

    @Override
    public T load(String aggregateIdentifier, Long expectedVersion) throws AggregateNotFoundException {
        if (isExists(aggregateIdentifier)) {
            AggregateWrapper<T> aggregate = aggregateHolder.get(aggregateIdentifier);
            if (aggregate.version().equals(expectedVersion)) {
                throw new AggregateNotFoundException(aggregateIdentifier,
                        "version of " + aggregateType.getName() + "not found");
            }
            return aggregate.getConcreteComponent();
        }  else {
            return null;
        }
    }

    @Override
    public void create(T aggregate) {
        Assert.notNull(aggregate, "parameter of aggregate must not be null.");
        AggregateWrapper<T> aggregateWrapper = new AggregateWrapper(aggregate);
        String identifier = this.getIdentifierFrom(aggregate);
        aggregateHolder.put(identifier, aggregateWrapper);
    }

    @Override
    public void update(T aggregate) {

    }

    @Override
    public void remove(String aggregateIdentifier) throws AggregateNotFoundException {
        if (isExists(aggregateIdentifier)) {
            aggregateHolder.remove(aggregateIdentifier);
        }
    }

    private boolean isExists(String aggregateIdentifier) throws AggregateNotFoundException {
        if (!aggregateHolder.contains(aggregateIdentifier)) {
            throw new AggregateNotFoundException(aggregateIdentifier,
                    "Aggregate of " + aggregateType.getName() + "not found");
        }
        return true;
    }
}
