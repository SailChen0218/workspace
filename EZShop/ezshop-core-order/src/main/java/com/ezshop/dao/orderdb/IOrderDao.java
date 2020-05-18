package com.ezshop.dao.orderdb;

import com.ezshop.common.domain.OrderDo;

public interface IOrderDao {
    int deleteByPrimaryKey(String orderId);

    int insert(OrderDo record);

    int insertSelective(OrderDo record);

    OrderDo selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(OrderDo record);

    int updateByPrimaryKey(OrderDo record);
}