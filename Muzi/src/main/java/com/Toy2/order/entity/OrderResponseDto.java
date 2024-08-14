package com.Toy2.order.entity;

import java.util.Date;

public class OrderResponseDto {
    private Integer orderNo;
    private String productName;
    private Long orderPrices;
    private Long orderDeliveryPrices;
    private Date orderDate;

    public OrderResponseDto(Integer orderNo,String productName, Long orderPrices, Long orderDeliveryPrices,  Date orderDate) {
        this.orderDate = orderDate;
        this.orderDeliveryPrices = orderDeliveryPrices;
        this.orderPrices = orderPrices;
        this.productName = productName;
        this.orderNo = orderNo;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getOrderPrices() {
        return orderPrices;
    }

    public void setOrderPrices(Long orderPrices) {
        this.orderPrices = orderPrices;
    }

    public Long getOrderDeliveryPrices() {
        return orderDeliveryPrices;
    }

    public void setOrderDeliveryPrices(Long orderDeliveryPrices) {
        this.orderDeliveryPrices = orderDeliveryPrices;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
