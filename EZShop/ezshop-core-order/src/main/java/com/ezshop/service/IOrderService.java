package com.ezshop.service;

import com.ezshop.common.dto.OrderDto;

public interface IOrderService {
    OrderDto queryOrder(String orderId);
}
