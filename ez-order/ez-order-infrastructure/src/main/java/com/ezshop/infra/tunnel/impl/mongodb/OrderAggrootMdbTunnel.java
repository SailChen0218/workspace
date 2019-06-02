package com.ezshop.infra.tunnel.impl.mongodb;

import com.ezddd.core.annotation.EzComponent;
import com.ezshop.infra.tunnel.OrderAggrootTunnel;
import com.ezshop.infra.tunnel.dataobject.OrderAggrootDo;

import javax.annotation.PostConstruct;

@EzComponent
public class OrderAggrootMdbTunnel extends AbstractAggrootMdbTunnel<OrderAggrootDo> implements OrderAggrootTunnel {
    @PostConstruct
    @Override
    public void init() {
        this.collection = "order";
        this.aggrootDoType = OrderAggrootDo.class;
    }
}
