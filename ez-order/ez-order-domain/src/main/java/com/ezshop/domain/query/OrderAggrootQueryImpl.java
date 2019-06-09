package com.ezshop.domain.query;

import com.ezddd.core.annotation.EzDomainService;
import com.ezshop.domain.convertor.OrderAggrootConvertor;
import com.ezshop.domain.vo.OrderAggrootVo;
import com.ezshop.infra.tunnel.OrderAggrootTunnel;
import com.ezshop.infra.tunnel.dataobject.OrderAggrootDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@EzDomainService(interfaceType = OrderAggrootQuery.class)
public class OrderAggrootQueryImpl implements OrderAggrootQuery {

    @Autowired
    private OrderAggrootConvertor orderAggrootConvertor;

    @Autowired
    private OrderAggrootTunnel orderAggrootTunnel;

    @Override
    public List<OrderAggrootVo> queryOrderAggrootList() {
        List<OrderAggrootDo> orderAggrootDoList = orderAggrootTunnel.queryOrderAggrootList();
        if (!CollectionUtils.isEmpty(orderAggrootDoList)) {
            List<OrderAggrootVo> orderAggrootVoList = new ArrayList<>(orderAggrootDoList.size());
            for (OrderAggrootDo orderAggrootDo : orderAggrootDoList) {
                orderAggrootVoList.add(orderAggrootConvertor.DataObjectToViewObject(orderAggrootDo));
            }
            return orderAggrootVoList;
        } else {
            return null;
        }
    }

    @Override
    public OrderAggrootVo queryOrderAggrootById(String orderId) {
        OrderAggrootDo orderAggrootDo = orderAggrootTunnel.queryOrderAggroot(orderId);
        return  orderAggrootConvertor.DataObjectToViewObject(orderAggrootDo);
    }
}
