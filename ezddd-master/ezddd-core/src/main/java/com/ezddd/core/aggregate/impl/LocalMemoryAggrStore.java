package com.ezddd.core.aggregate.impl;

import com.ezddd.core.aggregate.Aggregate;
import com.ezddd.core.aggregate.AggregateStore;
import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.utils.SerializationUtil;

import java.util.concurrent.ConcurrentHashMap;

@EzComponent
public class LocalMemoryAggrStore implements AggregateStore {
    private final ConcurrentHashMap<String, byte[]> aggregateHolder = new ConcurrentHashMap<>();

    @Override
    public <T> Aggregate<T> find(String identifier) {
        if (!aggregateHolder.contains(identifier)) {
            return null;
        } else {
            return SerializationUtil.readFromByteArray(aggregateHolder.get(identifier));
        }
    }

    @Override
    public <T> boolean save(Aggregate<T> aggregate) {
        aggregateHolder.put(aggregate.identifier(), SerializationUtil.writeToByteArray(aggregate));
        return true;
    }

    @Override
    public boolean remove(String identifier) {
        aggregateHolder.remove(identifier);
        return true;
    }

    @Override
    public boolean contains(String identifier) {
        return aggregateHolder.containsKey(identifier);
    }

}
