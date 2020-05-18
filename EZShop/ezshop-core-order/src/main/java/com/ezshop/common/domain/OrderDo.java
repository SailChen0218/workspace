package com.ezshop.common.domain;

import java.math.BigDecimal;
import java.util.Date;

public class OrderDo {
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public BigDecimal getProductNumbers() {
        return productNumbers;
    }

    public void setProductNumbers(BigDecimal productNumbers) {
        this.productNumbers = productNumbers;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo == null ? null : productInfo.trim();
    }

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public String getOrderDeliveryStatus() {
        return orderDeliveryStatus;
    }

    public void setOrderDeliveryStatus(String orderDeliveryStatus) {
        this.orderDeliveryStatus = orderDeliveryStatus == null ? null : orderDeliveryStatus.trim();
    }

    public String getExpressDeliveryCompany() {
        return expressDeliveryCompany;
    }

    public void setExpressDeliveryCompany(String expressDeliveryCompany) {
        this.expressDeliveryCompany = expressDeliveryCompany == null ? null : expressDeliveryCompany.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getUserEvaluationGrade() {
        return userEvaluationGrade;
    }

    public void setUserEvaluationGrade(Integer userEvaluationGrade) {
        this.userEvaluationGrade = userEvaluationGrade;
    }

    public String getUserEvaluationContent() {
        return userEvaluationContent;
    }

    public void setUserEvaluationContent(String userEvaluationContent) {
        this.userEvaluationContent = userEvaluationContent == null ? null : userEvaluationContent.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getOrderPaymentStatus() {
        return orderPaymentStatus;
    }

    public void setOrderPaymentStatus(String orderPaymentStatus) {
        this.orderPaymentStatus = orderPaymentStatus == null ? null : orderPaymentStatus.trim();
    }

    public BigDecimal getProductProfit() {
        return productProfit;
    }

    public void setProductProfit(BigDecimal productProfit) {
        this.productProfit = productProfit;
    }

    public Date getTradingTime() {
        return tradingTime;
    }

    public void setTradingTime(Date tradingTime) {
        this.tradingTime = tradingTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}