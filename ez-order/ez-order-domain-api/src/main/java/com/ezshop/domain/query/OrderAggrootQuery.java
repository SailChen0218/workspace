package com.ezshop.domain.query;

import com.ezshop.domain.vo.OrderAggrootVo;

import java.util.List;

public interface OrderAggrootQuery {
    List<OrderAggrootVo> queryOrderAggrootList();
    OrderAggrootVo queryOrderAggrootById(String orderId);
}
