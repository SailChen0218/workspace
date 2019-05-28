package com.ezshop.domain.repository;

import com.ezddd.core.aggregate.AggregateNotFoundException;
import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.repository.impl.CachedRepository;
import com.ezshop.domain.Convertor.OrderAggrootConvertor;
import com.ezshop.domain.aggregate.OrderAggroot;
import com.ezshop.infra.tunnel.OrderAggrootTunnel;
import com.ezshop.infra.tunnel.dataobject.OrderAggrootDo;
import org.springframework.beans.factory.annotation.Autowired;

@EzComponent
public class OrderAggrRepository extends CachedRepository<OrderAggroot> {

    @Autowired
    OrderAggrootConvertor orderAggrootConvertor;

    @Autowired
    OrderAggrootTunnel orderAggrootTunnel;

    public OrderAggrRepository() {
        super(OrderAggroot.class);
    }

    @Override
    public OrderAggroot load(String identifier) throws AggregateNotFoundException {
        return loadFromCacheAndDB_Cached(identifier, () -> {
                    OrderAggrootDo orderAggrootDo = orderAggrootTunnel.findByIdentifier(identifier);
                    return orderAggrootConvertor.DataObjectToEntity(orderAggrootDo);
                }
        );
    }

    @Override
    public OrderAggroot load(String identifier, Long expectedVersion) throws AggregateNotFoundException {
        return loadFromCacheAndDB_Cached(identifier, expectedVersion, () -> {
                    OrderAggrootDo orderAggrootDo = orderAggrootTunnel.findByIdentifier(identifier);
                    return orderAggrootConvertor.DataObjectToEntity(orderAggrootDo);
                }
        );
    }

    @Override
    public void create(OrderAggroot orderAggroot) {
        createToDB_Cached(orderAggroot, () -> {
            OrderAggrootDo orderAggrootDo = orderAggrootConvertor.EntityToDataObject(orderAggroot);
            return orderAggrootTunnel.create(orderAggrootDo);
        });
    }

    @Override
    public int save(OrderAggroot orderAggroot) {
        return saveToDB_Cached(orderAggroot, () -> {
            OrderAggrootDo orderAggrootDo = orderAggrootConvertor.EntityToDataObject(orderAggroot);
            return orderAggrootTunnel.save(orderAggrootDo);
        });
    }

    @Override
    public void removeByIdentifier(String identifier) throws AggregateNotFoundException {
        removeFromDBAndCache(identifier, () -> orderAggrootTunnel.removeByIdentifier(identifier));
    }
}
