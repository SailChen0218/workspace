package com.ezshop.infra.tunnel.impl.mongodb;

import com.ezddd.core.annotation.EzComponent;
import com.ezshop.infra.tunnel.CommodityAggrootTunnel;
import com.ezshop.infra.tunnel.dataobject.CommodityAggrootDo;

import javax.annotation.PostConstruct;

@EzComponent
public class CommodityAggrootMdbTunnel extends AbstractAggrootMdbTunnel<CommodityAggrootDo>
        implements CommodityAggrootTunnel {
    @PostConstruct
    @Override
    public void init() {
        this.collection = "commodity";
        this.aggrootDoType = CommodityAggrootDo.class;
    }
}
