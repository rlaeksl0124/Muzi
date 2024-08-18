package com.Toy2.cart.service;

import com.Toy2.cart.dao.CartDao;
import com.Toy2.cart.entity.CartDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

//있는 값으로 불러와서 하기
//경우의 수 생각
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Transactional
public class CartServiceImplTest {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartDao dao;

    private static final String customerEmail = "admin@";
    private static final String failEmail = "failEmail";
    @Test
    public void 장바구니_추가_성공() throws Exception {
        //장바구니에 추가할 객체 생성
        CartDto dto = new CartDto(2, 10, "XL", "admin");
        int result = cartService.addCart(dto); //성공적인 장바구니에 물건넣기
        //잘들어가면 1
        assertEquals(1, result);
    }
    @Test
    public void 장바구니_같은제품추가_실패() throws Exception {
        dao.cartDeleteAll(); //모두삭제
        //장바구니에 들어갈 객체 생성
        CartDto dto = new CartDto(2, 10, "XL", "admin@");
        //장바구니에 객체 추가
        int result = cartService.addCart(dto);
        assertEquals(1, result);
        //같은제품이 두번들어가면 예외 발생
        assertThrows(Exception.class, () -> cartService.addCart(dto));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void 장바구니_없는제품_추가_실패() throws Exception {
        dao.cartDeleteAll();//모두 삭제
        //객체 생성
        CartDto dto = new CartDto(2, 1, "XL", "admin");
        int result = cartService.addCart(dto); // 없는 회원으로 장바구니에 넣기
        assertEquals(1, result);// 결과확인
        dto = new CartDto(9999, 1, "XL", "admin@");
        result = cartService.addCart(dto); // 없는 제품을 장바구니에 넣기
        assertEquals(0, result); // 결과확인
    }//제품이있는지 어떻게 , 제품이 있는지 , 없는지 어떻게 알까
    @Test
    public void 장바구니_수정_성공() throws Exception {//set
        dao.cartDeleteAll();//모두삭제후
        CartDto dto = new CartDto(1, 1, "XL", "admin@");
        int result = cartService.addCart(dto); //성공적인 장바구니에 물건넣기
        assertEquals(1, result);//장바구니 넣기 확인
        CartDto changeDto = new CartDto(3);//바꿀 내용 객체 생성
        int modifyResult = cartService.modifyCart(dto.getCartNo(),changeDto);//바꿀 번호와 바꿀객체 내용 실행
        assertEquals(1, modifyResult);//수정확인
        //모두 삭제했기 때문에 첫번재의 리스트로 잘바뀌었는지 확인
        assertEquals(cartService.getCarts(customerEmail).get(0).getCartProductCnt(), changeDto.getCartProductCnt());
        assertEquals(cartService.getCarts(customerEmail).get(0).getCartProductOption(), changeDto.getCartProductOption());
    //item key
    }//수량만 변경되게

    @Test
    public void 장바구니_수정_실패() throws Exception {//수정이 실패할경우 뭐가있을까? 없는 제품을 수정할경우, 이상한 제품번호와 이메일이 들어갈경우
        dao.cartDeleteAll();//모두삭제
        CartDto dto = new CartDto(1, 1, "XL", "admin@");
        int result = cartService.addCart(dto);//객체생성후 장바구니에 물건 넣기
        assertEquals(1, result);//장바구니 넣기 확인
        CartDto changeDto = new CartDto(3);//장바구니
        assertThrows(Exception.class, () -> cartService.modifyCart(9999,changeDto));//없는 제품이 변경될경우 예외

        //이상한 제품번호와 이메일이 들어올경우
        CartDto changeDto2 = new CartDto(9999, 1, "XL", "0000");
        assertThrows(Exception.class, ()-> cartService.modifyCart(dto.getCartNo(),changeDto2));//이상한 번호가 들어올경우 예외
        assertEquals((int)cartService.getCarts(customerEmail).get(0).getCartProductNo(),1);//변경이 되지않음을 확인
        assertEquals(cartService.getCarts(customerEmail).get(0).getCartProductOption(),"XL");
        assertEquals((int)cartService.getCarts(customerEmail).get(0).getCartProductCnt(),1);
    }
    @Test
    public void 장바구니_리스트불러오기_성공() throws Exception {
        dao.cartDeleteAll();//모두삭제
        CartDto dto = new CartDto(1, 1, "XL", "admin@");
        int result = cartService.addCart(dto);//장바구니 담기 확인
        assertEquals(1, result);
        //장바구니에 잘들어갓는지 확인
        assertTrue(cartService.getCarts(customerEmail).get(0).getCartNo() == dto.getCartNo());
        assertEquals(cartService.getCarts(customerEmail).get(0).getCartProductOption(), dto.getCartProductOption());
        assertTrue(cartService.getCarts(customerEmail).get(0).getCartProductNo() == dto.getCartProductNo());
    }
    @Test
    public void 장바구니_리스트_여러개_확인() throws Exception {
        // 모든 장바구니 항목 삭제 (테스트 초기화)
        dao.cartDeleteAll();
        // 예상 리스트 크기
        int expectedSize = 3;
        // 장바구니에 여러 항목 추가
        List<CartDto> list = new ArrayList<>();
        for (int i = 1; i <= expectedSize; i++) {
            list.add( new CartDto(i , i, "XL", "admin@"));//리스트에 담기
            int result = cartService.addCart(list.get(i-1));
            assertEquals(1, result); // 각 항목이 성공적으로 추가되었는지 확인
        }

        // Then: 장바구니 리스트 검증
        List<CartDto> cartList = cartService.getCarts("admin@");//같은 비교군

        // 리스트 크기 검증
        assertEquals(expectedSize, cartList.size());

        // 각 항목의 속성 검증
        for (int i = 0; i < expectedSize; i++) {
            CartDto addedCart = cartList.get(i);
            assertEquals(i + 1,(int) (addedCart.getCartProductNo()));//list 비교로 변경
            assertEquals(i + 1, (int)(addedCart.getCartProductCnt()));
            assertEquals("XL", addedCart.getCartProductOption());
        }
    }

    @Test(expected = Exception.class)
    public void 장바구니_없는아이디_리스트불러오기_실패() throws Exception {
        CartDto dto = new CartDto(1, 1, "XL", "admin@");
        int result = cartService.addCart(dto);
        assertEquals(1, result);

        assertTrue(cartService.getCarts(failEmail).get(0).getCartNo() != dto.getCartNo());
        assertNotEquals(cartService.getCarts(failEmail).get(0).getCartProductOption(), dto.getCartProductOption());
        assertTrue(cartService.getCarts(failEmail).get(0).getCartProductNo() != dto.getCartProductNo());
    }

    @Test
    public void 장바구니_리스트불러올떄_실패() throws Exception {
        // 모든 장바구니 항목 삭제 (테스트 초트화)
        dao.cartDeleteAll();
        // 예상 리스트 크기
        int expectedSize = 3;
        // 장바구니에 여러 항목 추가
        for (int i = 1; i <= expectedSize; i++) {
            CartDto dto = new CartDto(i , i, "XL", customerEmail);
            int result = cartService.addCart(dto);
            assertEquals(1, result); // 각 항목이 성공적으로 추가되었는지 확인
        }
        //없는 회원이 들어갈 경우에도 장바구니 들어가야하기 때문에 예외던지기 불가능
    }
    @Test
    public void 장바구니_삭제여러개_성공() throws Exception {
        dao.cartDeleteAll();
        int expectedSize = 3;
        // 장바구니에 여러 항목 추가
        List<Integer> cartNo = new ArrayList<>(); //삭제할걸 담아둘 리스트 생성
        for (int i = 1; i <= expectedSize; i++) {
            CartDto dto = new CartDto(i , i, "XL", customerEmail);
            int result = cartService.addCart(dto); // 상품만큼의 객체 생성해서 장바구니 추가
            assertEquals(1, result); // 각 항목이 성공적으로 추가되었는지 확인
            cartNo = Collections.singletonList(cartService.getCarts(customerEmail).get(i-1).getCartNo());
            //장바구니에 있는 모든 상품을 담기
        }
        assertEquals(cartService.removeCarts(cartNo), 1);
        //삭제 성공
    }
    @Test
    public void 장바구니_삭제1개_성공() throws Exception {
        dao.cartDeleteAll();
        int expectedSize = 1; //1개만
        // 장바구니에 여러 항목 추가
        List<Integer> cartNo = new ArrayList<>(); //삭제할걸 담아둘 리스트 생성
        for (int i = 1; i <= expectedSize; i++) {
            CartDto dto = new CartDto(i , i, "XL", customerEmail);
            int result = cartService.addCart(dto); // 상품만큼의 객체 생성해서 장바구니 추가
            assertEquals(1, result); // 각 항목이 성공적으로 추가되었는지 확인
            cartNo = Collections.singletonList(cartService.getCarts(customerEmail).get(i-1).getCartNo());
            //장바구니에 있는 모든 상품을 담기
        }
        assertEquals(cartService.removeCarts(cartNo), 1);
        //삭제 성공
    }

    @Test
    public void 장바구니_아무것도_없는데_삭제_실패() throws Exception{ //아무것도 없는데 삭제할경우
        dao.cartDeleteAll();
        List<Integer> cartNo = new ArrayList<>();//아무것도 없는데 삭제 발동할경우
        assertThrows(Exception.class, ()-> cartService.removeCarts(cartNo)); //예외 발생
    }

    @Test
    public void 장바구니_존재하지_않는_장바구니번호로_삭제_실패() throws Exception {
        dao.cartDeleteAll();
        List<Integer> cartNo = new ArrayList<>();
        CartDto dto = new CartDto(1,1,"xl",customerEmail);
        dto.setCartNo(9999);//없는 장바구니 번호
        cartNo.add(dto.getCartNo()); //이상한 번호가 들어올경우
        assertThrows(Exception.class, ()-> cartService.removeCarts(cartNo));//예외처리 확인
    }
}