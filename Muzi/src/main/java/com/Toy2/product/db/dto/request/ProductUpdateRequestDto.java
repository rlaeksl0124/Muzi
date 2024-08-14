package com.Toy2.product.db.dto.request;

//사용자가 수정할 수 있는 데이터

/**
 * <br> 상품 이름
 * <br> 가격
 * <br> 옵션
 * <br> 내용
 * <br> 신상품 여부
 * <br> 할인 가능 여부
 * <br> 재고
 * <br> 참고 사항
 * <br> 상품 코드
 */
public class ProductUpdateRequestDto {
    public final int productNumber;
    private final int productPrice;
    private final String productName;
    private final boolean newItem;
    private final boolean discountable;
    private final int productAmount;
    private final String notice;
    private final String productCode;
    private final int deliveryFee;
    private final boolean privateProduct;

    public ProductUpdateRequestDto(Builder builder) {
        this.productNumber = builder.productNumber;
        this.productPrice = builder.productPrice;
        this.productName = builder.productName;
        this.newItem = builder.newItem;
        this.discountable = builder.discountable;
        this.productAmount = builder.productAmount;
        this.notice = builder.notice;
        this.productCode = builder.productCode;
        this.deliveryFee = builder.deliveryFee;
        this.privateProduct = builder.privateProduct;
    }


    public int getProductNumber() {
        return productNumber;
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

    public boolean isDiscountable() {
        return discountable;
    }

    public int getProductAmount() {
        return productAmount;
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

    public static class Builder {
        private int productNumber;
        private int productPrice;
        private String productName;
        private boolean newItem;
        private boolean discountable;
        private int productAmount;
        private String notice;
        private String productCode;
        private int deliveryFee;
        private boolean privateProduct;

        public Builder productNumber(int productNumber) {
            this.productNumber = productNumber;
            return this;
        }

        public Builder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public Builder productPrice(int productPrice) {
            this.productPrice = productPrice;
            return this;
        }

        public Builder newItem(boolean newItem) {
            this.newItem = newItem;
            return this;
        }

        public Builder discountable(boolean discountable) {
            this.discountable = discountable;
            return this;
        }

        public Builder amount(int productAmount) {
            this.productAmount = productAmount;
            return this;
        }

        public Builder notice(String notice) {
            this.notice = notice;
            return this;
        }

        public Builder productCode(String productCode) {
            this.productCode = productCode;
            return this;
        }

        public Builder deliveryFee(int deliveryFee) {
            this.deliveryFee = deliveryFee;
            return this;
        }

        public Builder privateProduct(boolean privateProduct) {
            this.privateProduct = privateProduct;
            return this;
        }

        public ProductUpdateRequestDto build() {
            return new ProductUpdateRequestDto(this);
        }

    }
}
