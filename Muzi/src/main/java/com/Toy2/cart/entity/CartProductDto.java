package com.Toy2.cart.entity;

public class CartProductDto {
    private Integer productNo;
    private Long productPrice;
    private Integer productDeliveryPrice;
    private Integer productCnt;
    private String productOption;


    public CartProductDto() {}
    public CartProductDto(Integer productNo, Long productPrice, Integer productDeliveryPrice,
                          Integer productCnt, String productOption) {
        this.productNo = productNo;
        this.productPrice = productPrice;
        this.productDeliveryPrice = productDeliveryPrice;
        this.productCnt = productCnt;
        this.productOption = productOption;
    }
    public Integer getProductNo() {
        return productNo;
    }

    public void setProductNo(Integer productNo) {
        this.productNo = productNo;
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Long productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductDeliveryPrice() {
        return productDeliveryPrice;
    }

    public void setProductDeliveryPrice(Integer productDeliveryPrice) {
        this.productDeliveryPrice = productDeliveryPrice;
    }

    public Integer getProductCnt() {
        return productCnt;
    }

    public void setProductCnt(Integer productCnt) {
        this.productCnt = productCnt;
    }

    public String getProductOption() {
        return productOption;
    }

    public void setProductOption(String productOption) {
        this.productOption = productOption;
    }
}
