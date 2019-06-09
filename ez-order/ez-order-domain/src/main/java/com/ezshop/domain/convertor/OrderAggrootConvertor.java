package com.ezshop.domain.convertor;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.tunnel.Convertor;
import com.ezddd.core.utils.BeanCopyUtils;
import com.ezshop.domain.aggregate.OrderAggroot;
import com.ezshop.domain.vo.OrderAggrootVo;
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

    public OrderAggrootVo DataObjectToViewObject(OrderAggrootDo orderAggrootDo) {
        OrderAggrootVo orderAggrootVo = BeanCopyUtils.cloneByProperties(orderAggrootDo, OrderAggrootVo.class);
        return orderAggrootVo;
    }

    public OrderAggrootVo EntityToViewObject(OrderAggroot entity) {
        OrderAggrootVo orderAggrootVo = BeanCopyUtils.cloneByProperties(entity, OrderAggrootVo.class);
        return orderAggrootVo;
    }
}
