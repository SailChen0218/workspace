package com.ezshop.domain.Convertor;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.tunnel.Convertor;
import com.ezddd.core.utils.BeanCopyUtils;
import com.ezshop.domain.aggregate.OrderAggroot;
import com.ezshop.infra.tunnel.dataobject.OrderAggrootDo;

@EzComponent
public class OrderAggrootConvertor implements Convertor<OrderAggroot, OrderAggrootDo> {

    @Override
    public OrderAggrootDo EntityToDataObject(OrderAggroot entity) {
        OrderAggrootDo orderAggrootDo = BeanCopyUtils.cloneByProperties(entity, OrderAggrootDo.class);
        return orderAggrootDo;
    }

    @Override
    public OrderAggroot DataObjectToEntity(OrderAggrootDo orderAggrootDo) {
        OrderAggroot orderAggroot = BeanCopyUtils.cloneByProperties(orderAggrootDo, OrderAggroot.class);
        return orderAggroot;
    }
}
