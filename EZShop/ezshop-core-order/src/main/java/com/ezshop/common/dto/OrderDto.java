package com.ezshop.common.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderDto {
    private String orderId;

    private BigDecimal productNumbers;

    private String productInfo;

    private BigDecimal productTotalPrice;

    private String orderDeliveryStatus;

    private String expressDeliveryCompany;

    private String address;

    private Integer userEvaluationGrade;

    private String userEvaluationContent;

    private String userId;

    private String userName;

    private String orderPaymentStatus;

    private BigDecimal productProfit;

    private Date tradingTime;

    private Integer isDeleted;

    private Date createDate;

    private Date updateDate;
}