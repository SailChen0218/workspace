package com.ezddd.core.repository.impl;

import com.ezddd.core.aggregate.AggregateNotFoundException;
import com.ezddd.core.aggregate.AggregateStore;
import com.ezddd.core.aggregate.AggregateWrapper;
import com.ezddd.core.annotation.EzUnitOfWork;
import com.ezddd.core.repository.AbstractRepository;
import com.ezddd.core.tunnel.TunnelFunctional.TunnelCreate;
import com.ezddd.core.tunnel.TunnelFunctional.TunnelFind;
import com.ezddd.core.tunnel.TunnelFunctional.TunnelSave;
import com.ezddd.core.tunnel.TunnelFunctional.TunnelRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

public abstract class CachedRepository<T> extends AbstractRepository<T> {

    public CachedRepository(Class<T> aggregateType) {
        super(aggregateType);
    }

    @Autowired
    AggregateStore aggregateStore;

    public T loadFromCache(String identifier) throws AggregateNotFoundException {
        if (isExists(identifier)) {
            AggregateWrapper<T> aggregate = (AggregateWrapper)aggregateStore.find(identifier);
            return aggregate.getConcreteComponent();
        } else {
            throw new AggregateNotFoundException(identifier, "aggregate of " + aggregateType.getName()
                    + " not found. aggregateIdentifier:" + identifier);
        }
    }

    public T loadFromDB_Cached(String identifier, TunnelFind<T> tTunnelFind) throws AggregateNotFoundException {
        T result = tTunnelFind.find();
        if (result == null) {
            throw new AggregateNotFoundException(identifier, "aggregate of " + aggregateType.getName()
                    + " not found. aggregateIdentifier:" + identifier);
        }
        // save to cache
        AggregateWrapper<T> aggregate = new AggregateWrapper<>(result);
        aggregateStore.save(aggregate);
        return result;
    }

    public T loadFromCacheAndDB_Cached(String identifier, TunnelFind<T> tTunnelFind) throws AggregateNotFoundException {
        if (isExists(identifier)) {
            AggregateWrapper<T> aggregate = (AggregateWrapper)aggregateStore.find(identifier);
            return aggregate.getConcreteComponent();
        } else {
            return loadFromDB_Cached(identifier, tTunnelFind);
        }
    }

    public T loadFromCache(String identifier, Long expectedVersion) throws AggregateNotFoundException {
        if (isExists(identifier)) {
            AggregateWrapper<T> aggregate = (AggregateWrapper)aggregateStore.find(identifier);
            if (aggregate.version().equals(expectedVersion)) {
                throw new AggregateNotFoundException(identifier, "aggregate of " + aggregateType.getName()
                        + " not found. aggregateIdentifier:" + identifier + ", expectedVersion:"
                        + expectedVersion.toString());
            }
            return aggregate.getConcreteComponent();
        }

        throw new AggregateNotFoundException(identifier,
                "aggregate of " + aggregateType.getName() + " not found. aggregateIdentifier:"
                        + identifier);
    }

    public T loadFromDB_Cached(String identifier, Long expectedVersion, TunnelFind<T> tunnelFind) throws AggregateNotFoundException {
        T result = tunnelFind.find();
        if (result == null) {
            throw new AggregateNotFoundException(identifier, "aggregate of " + aggregateType.getName()
                    + " not found. aggregateIdentifier:" + identifier + ", expectedVersion:"
                    + expectedVersion.toString());
        }
        // save to cache
        AggregateWrapper<T> aggregate = new AggregateWrapper<>(result);
        aggregateStore.save(aggregate);
        return result;
    }

    public T loadFromCacheAndDB_Cached(String identifier, Long expectedVersion, TunnelFind<T> tunnelFind) throws AggregateNotFoundException {
        if (isExists(identifier)) {
            AggregateWrapper<T> aggregate = (AggregateWrapper)aggregateStore.find(identifier);
            if (aggregate.version().equals(expectedVersion)) {
                throw new AggregateNotFoundException(identifier, "aggregate of " + aggregateType.getName()
                        + " not found. aggregateIdentifier:" + identifier + ", expectedVersion:"
                        + expectedVersion.toString());
            }
            return aggregate.getConcreteComponent();
        } else {
            return loadFromDB_Cached(identifier, expectedVersion, tunnelFind);
        }
    }

    @EzUnitOfWork
    public int createToDB_Cached(T aggregate, TunnelCreate tunnelCreate) {
        Assert.notNull(aggregate, "parameter of aggregate must not be null.");
        // persistence
        int result = tunnelCreate.create();
        // save to cache
        AggregateWrapper<T> aggregateWrapper = new AggregateWrapper(aggregate);
        aggregateStore.save(aggregateWrapper);
        return result;
    }

    @EzUnitOfWork
    public int saveToDB_Cached(T aggregate, TunnelSave tunnelSave) {
        // persistence
        int result = tunnelSave.save();
        // save to cache
        AggregateWrapper<T> aggregateWrapper = new AggregateWrapper<>(aggregate);
        aggregateStore.save(aggregateWrapper);
        return result;
    }

    public int removeFromDBAndCache(String identifier, TunnelRemove tunnelRemove) throws AggregateNotFoundException {
        // delete from DB
        int result = tunnelRemove.remove();
        if (result == 0) {
            throw new AggregateNotFoundException(identifier, "aggregate of " + aggregateType.getName()
                    + " not found. aggregateIdentifier:" + identifier);
        }

        // remove from cache
        aggregateStore.remove(identifier);
        return result;
    }

    private boolean isExists(String aggregateIdentifier) throws AggregateNotFoundException {
        if (!aggregateStore.contains(aggregateIdentifier)) {
            throw new AggregateNotFoundException(aggregateIdentifier,
                    "Aggregate of " + aggregateType.getName() + "not found");
        }
        return true;
    }
}
