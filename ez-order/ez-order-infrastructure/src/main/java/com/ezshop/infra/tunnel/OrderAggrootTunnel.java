package com.ezshop.infra.tunnel;

import com.ezddd.core.tunnel.Tunnel;
import com.ezshop.infra.tunnel.dataobject.OrderAggrootDo;

import java.util.List;

public interface OrderAggrootTunnel extends Tunnel<OrderAggrootDo> {
    List<OrderAggrootDo> queryOrderAggrootList();
    OrderAggrootDo queryOrderAggroot(String orderId);
}
