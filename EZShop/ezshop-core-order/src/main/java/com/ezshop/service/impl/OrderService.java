package com.ezshop.service.impl;

import com.ezshop.business.IOrderBusiness;
import com.ezshop.common.dto.OrderDto;
import com.ezshop.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements IOrderService {

    @Autowired
    IOrderBusiness orderBusiness;

    @Override
    public OrderDto queryOrder(String orderId) {
        return orderBusiness.queryOrder(orderId);
    }
}
