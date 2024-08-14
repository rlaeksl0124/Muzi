package com.Toy2.cart.entity;

import java.util.Objects;

public class CartDto {
    private Integer cartNo; //장바구니번호
    private Integer cartProductNo; // 장바구니 선택 제품번호
    private Long cartProductPrice; //장바구니 제품가격
    private Integer cartProductCnt;//장바구니 선택제품수량
    private String cartProductOption; //장바구니 선택제품옵션
    private Integer cartDelivery;  //장바구니 예상 배송비
    private String customerEmail; //고객 이메일
    //ca_no, pdt_no, ca_cnt, ca_price, ca_option, ca_delv
    //삽입을 위한 생성자
    public CartDto(Integer cartProductNo, Integer cartProductCnt, String cartProductOption, String customerEmail) {;
        this.cartProductNo = cartProductNo;
        this.cartProductCnt = cartProductCnt;
        this.cartProductOption = cartProductOption;
        this.customerEmail = customerEmail;
    }
    public CartDto(Integer cartProductCnt, String cartProductOption) {//update의 수량과 옵션에 대한 생성자
        this.cartProductCnt = cartProductCnt;
        this.cartProductOption = cartProductOption;
    }
    //검색을 위한 생성자
    public CartDto(Integer cartNo, Integer cartProductNo, Integer cartProductCnt, Long cartProductPrice, String cartProductOption, Integer cartDelivery) {
        this.cartNo = cartNo;
        this.cartProductNo = cartProductNo;
        this.cartProductCnt = cartProductCnt;
        this.cartProductPrice = cartProductPrice;
        this.cartProductOption = cartProductOption;
        this.cartDelivery = cartDelivery;
    }

    public Integer getCartNo() {
        return cartNo;
    }

    public void setCartNo(Integer cartNo) {
        this.cartNo = cartNo;
    }

    public Integer getCartProductNo() {
        return cartProductNo;
    }

    public void setCartProductNo(Integer cartProductNo) {
        this.cartProductNo = cartProductNo;
    }

    public Long getCartProductPrice() {
        return cartProductPrice;
    }

    public void setCartProductPrice(Long cartProductPrice) {
        this.cartProductPrice = cartProductPrice;
    }

    public Integer getCartProductCnt() {
        return cartProductCnt;
    }

    public void setCartProductCnt(Integer cartProductCnt) {
        this.cartProductCnt = cartProductCnt;
    }

    public String getCartProductOption() {
        return cartProductOption;
    }

    public void setCartProductOption(String cartProductOption) {
        this.cartProductOption = cartProductOption;
    }

    public Integer getCartDelivery() {
        return cartDelivery;
    }

    public void setCartDelivery(Integer cartDelivery) {
        this.cartDelivery = cartDelivery;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartDto cartDto = (CartDto) o;
        return Objects.equals(cartNo, cartDto.cartNo) && Objects.equals(cartProductNo, cartDto.cartProductNo) && Objects.equals(cartProductPrice, cartDto.cartProductPrice) && Objects.equals(cartProductCnt, cartDto.cartProductCnt) && Objects.equals(cartProductOption, cartDto.cartProductOption) && Objects.equals(cartDelivery, cartDto.cartDelivery) && Objects.equals(customerEmail, cartDto.customerEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartNo, cartProductNo, cartProductPrice, cartProductCnt, cartProductOption, cartDelivery, customerEmail);
    }

    @Override
    public String toString() {
        return "CartDto{" +
                "cartNo=" + cartNo +
                ", cartProductNo=" + cartProductNo +
                ", cartProductPrice=" + cartProductPrice +
                ", cartProductCnt=" + cartProductCnt +
                ", cartProductOption='" + cartProductOption + '\'' +
                ", cartDelivery=" + cartDelivery +
                ", customerEmail='" + customerEmail + '\'' +
                '}';
    }
}

