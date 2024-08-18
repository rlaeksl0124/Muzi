package com.Toy2.product.db.dto.request;

import com.Toy2.product.db.dto.ProductDto;
import com.Toy2.product.option.db.dto.ProductOptionDto;

public class ProductInsertRequestDto {
    private int productPrice;
    private String productName;
    private boolean newItem;
    private boolean postingStatus;
    private boolean discountable;
    private String notice;
    private String productCode;
    private int deliveryFee;
    private boolean privateProduct;


    public ProductInsertRequestDto(int productPrice, String productName, boolean newItem, boolean postingStatus, boolean discountable, String notice, String productCode, int deliveryFee, boolean privateProduct) {
        this.productPrice = productPrice;
        this.productName = productName;
        this.newItem = newItem;
        this.postingStatus = postingStatus;
        this.discountable = discountable;
        this.notice = notice;
        this.productCode = productCode;
        this.deliveryFee = deliveryFee;
        this.privateProduct = privateProduct;
    }

    public ProductInsertRequestDto() {
    }

    public int getProductPrice() {
        return productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public boolean isNewItem() {
        return newItem;
    }

    public boolean isPostingStatus() {
        return postingStatus;
    }

    public boolean isDiscountable() {
        return discountable;
    }

    public String getNotice() {
        return notice;
    }

    public String getProductCode() {
        return productCode;
    }

    public int getDeliveryFee() {
        return deliveryFee;
    }

    public boolean isPrivateProduct() {
        return privateProduct;
    }

    @Override
    public String toString() {
        return "ProductInsertRequestDto{" +
                "productPrice=" + productPrice +
                ", productName='" + productName + '\'' +
                ", newItem=" + newItem +
                ", postingStatus=" + postingStatus +
                ", discountable=" + discountable +
                ", notice='" + notice + '\'' +
                ", productCode='" + productCode + '\'' +
                ", deliveryFee=" + deliveryFee +
                ", privateProduct=" + privateProduct +
                '}';
    }
}
