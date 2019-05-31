package com.ezshop.domain.Convertor;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.tunnel.Convertor;
import com.ezddd.core.utils.BeanCopyUtils;
import com.ezshop.domain.aggregate.OrderAggroot;
import com.ezshop.infra.tunnel.dataobject.OrderAggrootDo;
import org.springframework.beans.BeanUtils;

@EzComponent
public class OrderAggrootConvertor implements Convertor<OrderAggroot, OrderAggrootDo> {

    @Override
    public OrderAggrootDo EntityToDataObject(OrderAggroot entity) {
        OrderAggrootDo orderAggrootDo = BeanCopyUtils.cloneByProperties(entity, OrderAggrootDo.class);

//        OrderAggrootDo orderAggrootDo = new OrderAggrootDo();
//        BeanUtils.copyProperties(entity, orderAggrootDo);
        return orderAggrootDo;
    }

    @Override
    public OrderAggroot DataObjectToEntity(OrderAggrootDo orderAggrootDo) {
        OrderAggroot orderAggroot = BeanCopyUtils.cloneByProperties(orderAggrootDo, OrderAggroot.class);
        return orderAggroot;
    }
}
