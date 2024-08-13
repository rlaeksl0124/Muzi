package com.Toy2.order.entity;

import java.util.Date;

public class OrderDetailDto {
    private Integer orderDetailNo; //주문상세번호
    private Integer orderNo; //주문번호
    private Integer productNo;//제품번호
    private String orderDetailProductName; //주문상세제품이름
    private Integer orderDetailCnt;//주문상세수량
    private Long orderDetailPrice;//주문상세가격
    private Integer orderDetailDeliveryPrice;//주문상세배송비
    private String orderDetailStatus;//주문상세상태
    private String orderDetailOption;//주문상세옵션
    private String orderDetailDeliveryInfo;//주문상세 배송정보
    private Date orderDetailDate;//주문상세 날짜

    public OrderDetailDto() {}
    public OrderDetailDto(Integer orderNo, Integer productNo, Integer orderDetailCnt
                          ,String orderDetailStatus, String orderDetailOption, String orderDetailDeliveryInfo) {
        this.orderNo = orderNo;
        this.productNo = productNo;
        this.orderDetailCnt = orderDetailCnt;
        this.orderDetailStatus = orderDetailStatus;
        this.orderDetailOption = orderDetailOption;
        this.orderDetailDeliveryInfo = orderDetailDeliveryInfo;
    }
    public OrderDetailDto(Integer orderNo, Integer productNo, Integer orderDetailCnt
            ,String orderDetailStatus, String orderDetailOption, String orderDetailDeliveryInfo,
                          Long ProductPrice, Integer orderDetailDeliveryPrice) {
        this.orderNo = orderNo;
        this.productNo = productNo;
        this.orderDetailCnt = orderDetailCnt;
        this.orderDetailStatus = orderDetailStatus;
        this.orderDetailOption = orderDetailOption;
        this.orderDetailDeliveryInfo = orderDetailDeliveryInfo;
        this.orderDetailPrice = ProductPrice;
        this.orderDetailDeliveryPrice = orderDetailDeliveryPrice;
    }
    public OrderDetailDto(Integer orderNo){
        this.orderNo = orderNo;
    }


    public Integer getOrderDetailNo() {
        return orderDetailNo;
    }

    public String getOrderDetailProductName() {
        return orderDetailProductName;
    }

    public void setOrderDetailProductName(String orderDetailProductName) {
        this.orderDetailProductName = orderDetailProductName;
    }

    public void setOrderDetailNo(Integer orderDetailNo) {
        this.orderDetailNo = orderDetailNo;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getProductNo() {
        return productNo;
    }

    public void setProductNo(Integer productNo) {
        this.productNo = productNo;
    }

    public Integer getOrderDetailCnt() {
        return orderDetailCnt;
    }

    public void setOrderDetailCnt(Integer orderDetailCnt) {
        this.orderDetailCnt = orderDetailCnt;
    }

    public Long getOrderDetailPrice() {
        return orderDetailPrice;
    }

    public void setOrderDetailPrice(Long orderDetailPrice) {
        this.orderDetailPrice = orderDetailPrice;
    }

    public Integer getOrderDetailDeliveryPrice() {
        return orderDetailDeliveryPrice;
    }

    public void setOrderDetailDeliveryPrice(Integer orderDetailDeliveryPrice) {
        this.orderDetailDeliveryPrice = orderDetailDeliveryPrice;
    }

    public String getOrderDetailStatus() {
        return orderDetailStatus;
    }

    public void setOrderDetailStatus(String orderDetailStatus) {
        this.orderDetailStatus = orderDetailStatus;
    }

    public String getOrderDetailOption() {
        return orderDetailOption;
    }

    public void setOrderDetailOption(String orderDetailOption) {
        this.orderDetailOption = orderDetailOption;
    }

    public String getOrderDetailDeliveryInfo() {
        return orderDetailDeliveryInfo;
    }

    public void setOrderDetailDeliveryInfo(String orderDetailDeliveryInfo) {
        this.orderDetailDeliveryInfo = orderDetailDeliveryInfo;
    }

    public Date getOrderDetailDate() {
        return orderDetailDate;
    }

    public void setOrderDetailDate(Date orderDetailDate) {
        this.orderDetailDate = orderDetailDate;
    }
}
