package com.ezddd.core.repository.impl;

import com.ezddd.core.aggregate.Aggregate;
import com.ezddd.core.aggregate.AggregateNotFoundException;
import com.ezddd.core.aggregate.AggregateWrapper;
import com.ezddd.core.repository.AbstractRepository;
import org.springframework.util.Assert;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class InMemeryRepository<T> extends AbstractRepository {
    private static ConcurrentHashMap<String, Aggregate> aggregateHolder = new ConcurrentHashMap<>();

    public InMemeryRepository(Class<T> clazz) {
        super(clazz);
    }

    @Override
    public Aggregate load(String aggregateIdentifier) throws AggregateNotFoundException {
        return aggregateHolder.get(aggregateIdentifier);
    }

    @Override
    public Aggregate load(String aggregateIdentifier, Long expectedVersion) throws AggregateNotFoundException {
        return null;
    }

    @Override
    public Aggregate newInstance(Callable factoryMethod) throws Exception {
        return null;
    }

    @Override
    public void add(Aggregate aggregate) {
        Assert.notNull(aggregate, "aggregate must not be null.");
        aggregateHolder.put(aggregate.identifier(), aggregate);
    }

    @Override
    public void addWithWrapper(Object aggregateObj) {
        Assert.notNull(aggregateObj, "aggregate object must not be null.");

//        Enhancer enhancer = new Enhancer();
//        Class<?> aggregateObjType = aggregateObj.getClass();
//
//        enhancer.setSuperclass(aggregateObjType);
//        Class[] interfaces = {Aggregate.class};
//        enhancer.setInterfaces(interfaces);
//        enhancer.create();
//
//        aggregateHolder.put(aggregateObjType.getSimpleName(), );
//
//        enhancer.setCallback(new MethodInterceptorImpl ());

        AggregateWrapper aggregateWrapper = new AggregateWrapper(aggregateObj);
        aggregateHolder.put(aggregateObj.getClass().getSimpleName(), aggregateWrapper);
    }

    @Override
    public void remove(String aggregateIdentifier) throws AggregateNotFoundException {

    }
}
