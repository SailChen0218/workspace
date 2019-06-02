package com.ezddd.core.repository.impl;

import com.ezddd.core.aggregate.AggregateNotFoundException;
import com.ezddd.core.aggregate.AggregateStore;
import com.ezddd.core.aggregate.AggregateWrapper;
import com.ezddd.core.annotation.EzUnitOfWork;
import com.ezddd.core.repository.AbstractRepository;
import com.ezddd.core.tunnel.TunnelFunctional.TunnelCreate;
import com.ezddd.core.tunnel.TunnelFunctional.TunnelFind;
import com.ezddd.core.tunnel.TunnelFunctional.TunnelRemove;
import com.ezddd.core.tunnel.TunnelFunctional.TunnelSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

public abstract class AbstractCachedRepository<E> extends AbstractRepository<E> {

    @Autowired
    protected AggregateStore aggregateStore;

    public E loadFromCache(String identifier) throws AggregateNotFoundException {
        if (aggregateStore.contains(identifier)) {
            AggregateWrapper<E> aggregate = (AggregateWrapper)aggregateStore.find(identifier);
            return aggregate.getAggregateRoot();
        } else {
            throw new AggregateNotFoundException(identifier, "aggregate of " + aggregateType.getName()
                    + " not found. aggregateIdentifier:" + identifier);
        }
    }

    public E loadFromDB_Cached(String identifier, TunnelFind<E> tTunnelFind) throws AggregateNotFoundException {
        E result = tTunnelFind.find();
        if (result == null) {
            throw new AggregateNotFoundException(identifier, "aggregate of " + aggregateType.getName()
                    + " not found. aggregateIdentifier:" + identifier);
        }
        // save to cache
        AggregateWrapper<E> aggregate = new AggregateWrapper<>(result);
        aggregateStore.save(aggregate);
        return result;
    }

    public E loadFromCacheAndDB_Cached(String identifier, TunnelFind<E> tTunnelFind) throws AggregateNotFoundException {
        if (aggregateStore.contains(identifier)) {
            AggregateWrapper<E> aggregate = (AggregateWrapper)aggregateStore.find(identifier);
            return aggregate.getAggregateRoot();
        } else {
            return loadFromDB_Cached(identifier, tTunnelFind);
        }
    }

    public E loadFromCache(String identifier, Long expectedVersion) throws AggregateNotFoundException {
        if (aggregateStore.contains(identifier)) {
            AggregateWrapper<E> aggregate = (AggregateWrapper)aggregateStore.find(identifier);
            if (aggregate.version().equals(expectedVersion)) {
                throw new AggregateNotFoundException(identifier, "aggregate of " + aggregateType.getName()
                        + " not found. aggregateIdentifier:" + identifier + ", expectedVersion:"
                        + expectedVersion.toString());
            }
            return aggregate.getAggregateRoot();
        }

        throw new AggregateNotFoundException(identifier,
                "aggregate of " + aggregateType.getName() + " not found. aggregateIdentifier:"
                        + identifier);
    }

    public E loadFromDB_Cached(String identifier, Long expectedVersion, TunnelFind<E> tunnelFind) throws AggregateNotFoundException {
        E result = tunnelFind.find();
        if (result == null) {
            throw new AggregateNotFoundException(identifier, "aggregate of " + aggregateType.getName()
                    + " not found. aggregateIdentifier:" + identifier + ", expectedVersion:"
                    + expectedVersion.toString());
        }
        // save to cache
        AggregateWrapper<E> aggregate = new AggregateWrapper<>(result);
        aggregateStore.save(aggregate);
        return result;
    }

    public E loadFromCacheAndDB_Cached(String identifier, Long expectedVersion, TunnelFind<E> tunnelFind) throws AggregateNotFoundException {
        if (aggregateStore.contains(identifier)) {
            AggregateWrapper<E> aggregate = (AggregateWrapper)aggregateStore.find(identifier);
            if (aggregate.version().equals(expectedVersion)) {
                throw new AggregateNotFoundException(identifier, "aggregate of " + aggregateType.getName()
                        + " not found. aggregateIdentifier:" + identifier + ", expectedVersion:"
                        + expectedVersion.toString());
            }
            return aggregate.getAggregateRoot();
        } else {
            return loadFromDB_Cached(identifier, expectedVersion, tunnelFind);
        }
    }

    @EzUnitOfWork
    public int createToDB_Cached(E aggregate, TunnelCreate tunnelCreate) {
        Assert.notNull(aggregate, "parameter of aggregate must not be null.");
        // persistence
        int result = tunnelCreate.create();
        // save to cache
        AggregateWrapper<E> aggregateWrapper = new AggregateWrapper(aggregate);
        aggregateStore.save(aggregateWrapper);
        return result;
    }

    @EzUnitOfWork
    public int saveToDB_Cached(E aggregate, TunnelSave tunnelSave) {
        // persistence
        int result = tunnelSave.save();
        // save to cache
        AggregateWrapper<E> aggregateWrapper = new AggregateWrapper<>(aggregate);
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

    public int removeFromDB_Cached(E aggregate, String identifier, TunnelRemove tunnelRemove) throws AggregateNotFoundException {
        Assert.notNull(aggregate, "parameter of aggregate must not be null.");

        // delete from DB
        int result = tunnelRemove.remove();
        if (result == 0) {
            throw new AggregateNotFoundException(identifier, "aggregate of " + aggregateType.getName()
                    + " not found. aggregateIdentifier:" + identifier);
        }

        // save to cache
        AggregateWrapper<E> aggregateWrapper = new AggregateWrapper<>(aggregate);
        aggregateStore.save(aggregateWrapper);
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
