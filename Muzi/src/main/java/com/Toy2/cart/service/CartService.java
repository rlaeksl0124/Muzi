package com.Toy2.cart.service;

import com.Toy2.cart.entity.CartDto;

import java.util.List;

public interface CartService {
    //장바구니 추가
    int addCart(CartDto cartDto) throws Exception;
    //장바구니 리스트
    List<CartDto> getCarts(String customerEmail) throws Exception;
    //장바구니 삭제
    int removeCarts(List<Integer> cartNos) throws Exception;

    //장바구니 옵션 수정
    int modifyCart(int cartNo, CartDto cartDto) throws Exception;

    int cartEmailDelete(String email) throws Exception;
    //장바구니 값
    CartDto getCart(int cartNo) throws Exception;
}
