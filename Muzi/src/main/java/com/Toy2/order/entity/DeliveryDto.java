package com.Toy2.order.entity;

public class DeliveryDto {
    private Integer orderNo;//주문 NO
    private String deliveryName;//받는사람이름
    private String deliveryAddressName;//배송지명
    private String deliveryPhone;//받는사람 번호
    private String deliveryRoadAddress;//도로명주소
    private String deliveryStreetAddress; //지번주소
    private String deliveryDetailAddress;  //상세주소
    private String deliveryMessage; // 배송메시지

    public DeliveryDto() {}
    public DeliveryDto(Integer orderNo, String deliveryName,
                       String deliveryAddressName, String deliveryPhone,
                       String deliveryRoadAddress, String deliveryStreetAddress,
                       String deliveryDetailAddress, String deliveryMessage) {
        this.orderNo = orderNo;
        this.deliveryName = deliveryName;
        this.deliveryAddressName = deliveryAddressName;
        this.deliveryPhone = deliveryPhone;
        this.deliveryRoadAddress = deliveryRoadAddress;
        this.deliveryMessage = deliveryMessage;
        this.deliveryStreetAddress = deliveryStreetAddress;
        this.deliveryDetailAddress = deliveryDetailAddress;
    }
    public DeliveryDto( String deliveryName,
                       String deliveryAddressName, String deliveryPhone,
                       String deliveryRoadAddress,
                       String deliveryDetailAddress, String deliveryMessage) {
        this.deliveryName = deliveryName;
        this.deliveryAddressName = deliveryAddressName;
        this.deliveryPhone = deliveryPhone;
        this.deliveryRoadAddress = deliveryRoadAddress;
        this.deliveryMessage = deliveryMessage;
        this.deliveryDetailAddress = deliveryDetailAddress;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getDeliveryAddressName() {
        return deliveryAddressName;
    }

    public void setDeliveryAddressName(String deliveryAddressName) {
        this.deliveryAddressName = deliveryAddressName;
    }

    public String getDeliveryPhone() {
        return deliveryPhone;
    }

    public void setDeliveryPhone(String deliveryPhone) {
        this.deliveryPhone = deliveryPhone;
    }

    public String getDeliveryRoadAddress() {
        return deliveryRoadAddress;
    }

    public void setDeliveryRoadAddress(String deliveryRoadAddress) {
        this.deliveryRoadAddress = deliveryRoadAddress;
    }

    public String getDeliveryStreetAddress() {
        return deliveryStreetAddress;
    }

    public void setDeliveryStreetAddress(String deliveryStreetAddress) {
        this.deliveryStreetAddress = deliveryStreetAddress;
    }

    public String getDeliveryDetailAddress() {
        return deliveryDetailAddress;
    }

    public void setDeliveryDetailAddress(String deliveryDetailAddress) {
        this.deliveryDetailAddress = deliveryDetailAddress;
    }

    public String getDeliveryMessage() {
        return deliveryMessage;
    }

    public void setDeliveryMessage(String deliveryMessage) {
        this.deliveryMessage = deliveryMessage;
    }
}
