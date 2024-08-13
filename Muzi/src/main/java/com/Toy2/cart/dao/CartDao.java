package com.Toy2.cart.dao;

import com.Toy2.cart.entity.CartDto;

import java.util.List;
/*
    cartInsert : 장바구니를 추가를 누르면 장바구니 값 추가
    cartSelectAll : 회원의 아이디로 비교하여 회원의 장바구니 리스트 출력
    cartUpdate : 회원이 클릭한 장바구니 번호로 변경하고 싶은 수량과 옵션을 수정
    cartDelete : 회원이 클릭한 장바구니 번호로 선택삭제
    cartCount : 회원의 장바구니 안의 상품 개수 확인

    cartDeleteAll : 모든 장바구니 삭제
 */
public interface CartDao {
    int cartInsert(CartDto cartDto)throws Exception; //장바구니 값 입력
    List<CartDto> cartSelectAll(String email) throws Exception; //회원의 아이디 값으로 장바구니 리스트 출력
    int cartUpdate(int cartNo, CartDto cartDto)throws Exception; //장바구니의 번호와 바꿀 객체로 update
    int cartDelete(int cartNo)throws Exception; //장바구니 번호로 삭제
    CartDto cartSelect(int cartNo)throws Exception; //장바구니 번호로 검색
    int cartCount(String email)throws Exception; //사용자의 장바구니에 들어있는 개수
    int cartDeleteAll() throws Exception;//카트의 모든 데이터 삭제
}
