package com.Toy2.order.entity;

import java.util.Date;
import java.util.List;

public class OrderDto {//주문이 들어오면 생성이 되야한다. 주문Detail보다 먼저
    private Integer orderNo; //주문 NO
    private String customerEmail; //회원이메일
    private Long orderPrices; //주문총가격
    private Long orderDeliveryPrices;//주문총배송비
    private Date orderDate; //주문날짜
    private List<OrderDetailDto> orderDetails;

    public OrderDto(String userEmail){
        this.customerEmail = userEmail;
        orderPrices = 0L;
        orderDeliveryPrices = 0L;
    }

    public List<OrderDetailDto> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailDto> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
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
