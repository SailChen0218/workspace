package com.ezshop.domain.repository;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.repository.impl.DefaultCachedRepository;
import com.ezshop.domain.aggregate.CommodityAggroot;
import com.ezshop.infra.tunnel.dataobject.CommodityAggrootDo;

@EzComponent
public class CommodityAggrootRepository extends DefaultCachedRepository<CommodityAggroot, CommodityAggrootDo> {
}
