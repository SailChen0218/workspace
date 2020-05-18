package com.ezshop.business;

import com.ezshop.common.dto.OrderDto;

public interface IOrderBusiness {
    OrderDto queryOrder(String orderId);
}
