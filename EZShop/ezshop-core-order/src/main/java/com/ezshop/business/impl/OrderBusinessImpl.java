package com.ezshop.business.impl;

import com.ezshop.business.IOrderBusiness;
import com.ezshop.common.domain.OrderDo;
import com.ezshop.common.dto.OrderDto;
import com.ezshop.dao.orderdb.IOrderDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderBusinessImpl implements IOrderBusiness {

    @Autowired
    IOrderDao orderDao;

    @Override
    public OrderDto queryOrder(String orderId) {
        OrderDo orderDo = orderDao.selectByPrimaryKey(orderId);
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderDo, orderDto);
        List<OrderDo> orderDos = new ArrayList<>();
        orderDos.add(orderDo);
        orderDos.add(null);


        return orderDto;
    }
}
