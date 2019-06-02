package com.ezshop.domain.repository;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.repository.impl.DefaultCachedRepository;
import com.ezshop.domain.aggregate.OrderAggroot;
import com.ezshop.infra.tunnel.dataobject.OrderAggrootDo;

@EzComponent
public class OrderAggrootRepository extends DefaultCachedRepository<OrderAggroot, OrderAggrootDo> {
}
