package com.ezshop.infra.tunnel.impl.mongodb;

import com.ezddd.core.tunnel.Tunnel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;

import java.util.List;

public abstract class AbstractAggrootMdbTunnel<T> implements Tunnel<T> {
    protected String collection;
    protected Class<T> aggrootDoType;

    @Autowired
    protected MongoTemplate mongoTemplate;

    /**
     * set collection and aggrootDoType.
     */
    public abstract void init();

    @Override
    public int removeByIdentifier(String identifier) {
        Criteria criteria = Criteria.where("_id").is(identifier);
        Query query = new Query(criteria);
        T aggrootDo = mongoTemplate.findAndRemove(query, this.aggrootDoType, this.collection);
        if (aggrootDo != null) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int create(T aggregateDo) {
        mongoTemplate.insert(aggregateDo);
        return 1;
    }

    @Override
    public int save(T aggregateDo) {
        mongoTemplate.save(aggregateDo);
        return 1;
    }

    @Override
    public T findByIdentifier(String identifier) {
        return mongoTemplate.findById(identifier, this.aggrootDoType, this.collection);
    }

    @Override
    public T findByIdentifier(String identifier, String version) {
        Criteria criteria = Criteria.where("_id").is(identifier).and("version").is(version);
        Query query = new Query(criteria);
        List<T> aggrootDos= mongoTemplate.find(query, this.aggrootDoType, this.collection);
        if (!CollectionUtils.isEmpty(aggrootDos)) {
            return aggrootDos.get(0);
        } else {
            return null;
        }
    }
}
