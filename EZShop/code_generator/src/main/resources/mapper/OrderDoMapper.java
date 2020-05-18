package main.resources.mapper;

import main.java.com.ezshop.domain.OrderDo;

public interface OrderDoMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(OrderDo record);

    int insertSelective(OrderDo record);

    OrderDo selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(OrderDo record);

    int updateByPrimaryKey(OrderDo record);
}