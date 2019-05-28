package com.ezshop.infra.tunnel;

import com.ezddd.core.tunnel.Tunnel;
import com.ezshop.infra.tunnel.dataobject.OrderAggrootDo;

public interface OrderAggrootTunnel extends Tunnel {
    int removeByIdentifier(String identifier);
    int create(OrderAggrootDo aggregateDo);
    int save(OrderAggrootDo aggregateDo);
    OrderAggrootDo findByIdentifier(String identifier);
}
