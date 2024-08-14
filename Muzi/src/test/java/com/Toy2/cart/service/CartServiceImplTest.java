package com.Toy2.cart.service;

import com.Toy2.cart.entity.CartDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
//@Transactional
public class CartServiceImplTest {
    @Autowired
    private CartService cartService;

    private static final String customerEmail = "admin@";
    private static final String failEmail = "failEmail";
    @Test
    public void 장바구니_추가_성공() throws Exception {
        CartDto dto = new CartDto(1, 10, "XL", "admin@");
        int result = cartService.addCart(dto); //성공적인 장바구니에 물건넣기
        assertEquals(1, result);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void 장바구니_추가_실패() throws Exception {
        CartDto dto = new CartDto(1, 1, "XL", "admin");
        int result = cartService.addCart(dto); // 없는 회원으로 장바구니에 넣기
        assertEquals(0, result);
        dto = new CartDto(9999, 1, "XL", "admin@");
        result = cartService.addCart(dto); // 없는 제품을 장바구니에 넣기
        assertEquals(0, result);
    }
    @Test
    public void 장바구니_수정_성공() throws Exception {
        CartDto dto = new CartDto(1, 1, "XL", "admin@");
        int result = cartService.addCart(dto); //성공적인 장바구니에 물건넣기
        assertEquals(1, result);
        CartDto changeDto = new CartDto(3,"L");
        cartService.modifyCart(dto.getCartNo(),changeDto);
        boolean test = dto.getCartProductCnt() != changeDto.getCartProductCnt();
        assertTrue(test);
    }

    @Test(expected = Exception.class)
    public void 장바구니_수정_실패() throws Exception {
        CartDto dto = new CartDto(1, 1, "XL", "admin@");
        int result = cartService.addCart(dto);
        assertEquals(1, result);
        CartDto changeDto = new CartDto(3,"L");
        cartService.modifyCart(dto.getCartNo()+1,changeDto);
        boolean test = changeDto.getCartNo() == dto.getCartNo();
        assertTrue(test);
    }
    @Test
    public void 장바구니_리스트불러오기_성공() throws Exception {
        CartDto dto = new CartDto(1, 1, "XL", "admin@");
        int result = cartService.addCart(dto);
        assertEquals(1, result);

        assertTrue(cartService.getCarts(customerEmail).get(0).getCartNo() == dto.getCartNo());
        assertEquals(cartService.getCarts(customerEmail).get(0).getCartProductOption(), dto.getCartProductOption());
        assertTrue(cartService.getCarts(customerEmail).get(0).getCartProductNo() == dto.getCartProductNo());
    }

    @Test(expected = Exception.class)
    public void 장바구니_리스트불러오기_실패() throws Exception {
        CartDto dto = new CartDto(1, 1, "XL", "admin@");
        int result = cartService.addCart(dto);
        assertEquals(1, result);

        assertTrue(cartService.getCarts(failEmail).get(0).getCartNo() != dto.getCartNo());
        assertNotEquals(cartService.getCarts(failEmail).get(0).getCartProductOption(), dto.getCartProductOption());
        assertTrue(cartService.getCarts(failEmail).get(0).getCartProductNo() != dto.getCartProductNo());
    }
    @Test
    public void 장바구니_선택삭제_성공() throws Exception {
        CartDto dto = new CartDto(1, 1, "XL", "admin@");
        int result = cartService.addCart(dto);
        assertEquals(1, result);

        int removeResult = cartService.removeCart(dto.getCartNo());
        assertEquals("삭제 실패", 1, removeResult);
    }

    @Test(expected = Exception.class)
    public void 장바구니_선택삭제_실패() throws Exception {
        CartDto dto = new CartDto(1, 1, "XL", "admin@");
        int result = cartService.addCart(dto);
        assertEquals(1, result);

        int removeResult = cartService.removeCart(dto.getCartNo()+1);
        assertEquals("삭제 실패", 0, removeResult);

    }
}