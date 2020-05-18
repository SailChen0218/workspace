package com.ezshop.controller;

import cn.com.servyou.common.dto.ResultDto;
import com.ezshop.common.dto.OrderDto;
import com.ezshop.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    IOrderService orderService;

    @RequestMapping(path="/queryOrder", method = RequestMethod.POST)
    public ResultDto<OrderDto> queryUser(String orderId) {
        OrderDto orderDto = orderService.queryOrder(orderId);
        if (orderDto == null) {
            return ResultDto.valueOfError("查询不到订单信息。");
        }
        return ResultDto.valueOfSuccess(orderDto);
    }
}
