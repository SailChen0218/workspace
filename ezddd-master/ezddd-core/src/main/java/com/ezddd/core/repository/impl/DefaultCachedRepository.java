package com.ezddd.core.repository.impl;

import com.ezddd.core.aggregate.AggregateNotFoundException;
import com.ezddd.core.tunnel.Convertor;
import com.ezddd.core.tunnel.Tunnel;
import com.ezddd.core.utils.AggregateUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @param <E> aggregateRoot
 * @param <D> aggregateRootDo
 */
public abstract class DefaultCachedRepository<E, D> extends AbstractCachedRepository<E> {

    @Autowired
    protected Convertor<E, D> convertor;

    @Autowired
    protected Tunnel<D> tunnel;

    @Override
    public E load(String identifier) throws AggregateNotFoundException {
        return loadFromCacheAndDB_Cached(identifier, () -> {
                    D aggrootDo = tunnel.findByIdentifier(identifier);
                    return convertor.DataObjectToEntity(aggrootDo);
                }
        );
    }

    @Override
    public E load(String identifier, Long expectedVersion) throws AggregateNotFoundException {
        return loadFromCacheAndDB_Cached(identifier, expectedVersion, () -> {
                    D aggrootDo = tunnel.findByIdentifier(identifier);
                    return convertor.DataObjectToEntity(aggrootDo);
                }
        );
    }

    @Override
    public void create(E aggroot) {
        createToDB_Cached(aggroot, () -> {
            D aggrootDo = convertor.EntityToDataObject(aggroot);
            return tunnel.create(aggrootDo);
        });
    }

    @Override
    public int save(E aggroot) {
        return saveToDB_Cached(aggroot, () -> {
            D aggrootDo = convertor.EntityToDataObject(aggroot);
            return tunnel.save(aggrootDo);
        });
    }

    @Override
    public void removeByIdentifier(String identifier) throws AggregateNotFoundException {
        removeFromDBAndCache(identifier, () -> tunnel.removeByIdentifier(identifier));
    }

    @Override
    public void remove(E aggroot) throws AggregateNotFoundException {
        String identifier = AggregateUtil.getIdentifierFrom(aggroot);
        removeFromDB_Cached(aggroot, identifier, () -> tunnel.removeByIdentifier(identifier));
    }
}
