package com.Toy2.cart.service;

import com.Toy2.cart.entity.CartDto;

import java.util.List;

public interface CartService {
    //장바구니 추가
    int addCart(CartDto cartDto) throws Exception;
    //장바구니 리스트
    List<CartDto> getCarts(String customerEmail) throws Exception;
    //장바구니 삭제
    int removeCart(int cartNo) throws Exception;
    //장바구니 옵션 수정
    int modifyCart(int cartNo, CartDto cartDto) throws Exception;
}
