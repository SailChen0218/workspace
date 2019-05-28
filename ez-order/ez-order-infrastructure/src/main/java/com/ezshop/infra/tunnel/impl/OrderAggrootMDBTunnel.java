package com.ezshop.infra.tunnel.impl;

import com.ezddd.core.annotation.EzComponent;
import com.ezshop.infra.tunnel.OrderAggrootTunnel;
import com.ezshop.infra.tunnel.dataobject.OrderAggrootDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@EzComponent
public class OrderAggrootMDBTunnel implements OrderAggrootTunnel {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public int removeByIdentifier(String identifier) {
        Criteria criteria = Criteria.where("id").is(identifier);
        Query query = new Query(criteria);
        OrderAggrootDo orderAggrootDo = mongoTemplate.findAndRemove(query, OrderAggrootDo.class);
        if (orderAggrootDo != null) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int create(OrderAggrootDo aggregateDo) {
        mongoTemplate.insert(aggregateDo);
        return 1;
    }

    @Override
    public int save(OrderAggrootDo aggregateDo) {
        mongoTemplate.save(aggregateDo);
        return 1;
    }

    @Override
    public OrderAggrootDo findByIdentifier(String identifier) {
        Criteria criteria = Criteria.where("id").is(identifier);
        Query query = new Query(criteria);
        return mongoTemplate.findById(identifier, OrderAggrootDo.class);
    }
}
