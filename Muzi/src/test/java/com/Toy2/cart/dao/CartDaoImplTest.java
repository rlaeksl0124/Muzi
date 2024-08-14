package com.Toy2.cart.dao;

import com.Toy2.cart.entity.CartDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
//@Transactional
public class CartDaoImplTest {
    @Autowired
    private CartDao cartDao;

    @Test
    public void cartInsert() throws Exception {
        /* 장바구니에 제품이 잘 들어가고 삭제가 잘되는지  */
        cartDao.cartDeleteAll();
        CartDto dto = new CartDto(2, 1, "XL", "admin@");
        // 카트 삽입 테스트
        int insertResult = cartDao.cartInsert(dto);
        assertEquals("Insert return 1", 1, insertResult);
        //select 확인
    }

    @Test
    public void cartUpdate() throws Exception {
        /* 장바구니에 추가를 하고 옵션변경이 잘되는지 확인 */
        cartDao.cartDeleteAll();
        CartDto dto = new CartDto(1, 1, "XL", "admin@");
        CartDto dto2 = new CartDto(2, "L");

        // 카트 삽입 테스트
        int insertResult = cartDao.cartInsert(dto);
        assertEquals("Insert return 1", 1, insertResult);

        // 카트 수정 테스트
        int updateResult = cartDao.cartUpdate(dto.getCartNo(), dto2);
        assertEquals("Update return 1", 1, updateResult);

        //카트 삭제 테스트
        int deleteResult = cartDao.cartDelete(dto.getCartNo());
        assertEquals("Delete return 1", 1, deleteResult);
    }

    @Test
    public void cartList() throws Exception {
        /* 회원의 장바구니가 잘 출력이 되는지 확인 */

        cartDao.cartDeleteAll();// 전부 삭제
        //admin@ 사용자의 장바구니 개수 확인
        assertEquals(cartDao.cartCount("admin@"), 0);

        CartDto dto = new CartDto(1, 1, "XL", "admin@");
        CartDto dto2 = new CartDto(2, 1, "XL", "admin@");

        /* 다른 회원의 장바구니도 불러와지는 지 확인하기 위해 추가 */
        CartDto dto3 = new CartDto(1, 1, "XL", "admin@2");

        // 카트 삽입 테스트3개
        int insertResult = cartDao.cartInsert(dto);
        assertTrue(insertResult == 1);// 장바구니 insert 확인
        int insertResult2 = cartDao.cartInsert(dto2);
        assertTrue(insertResult2 == 1);// 장바구니 insert 확인
        assertEquals(cartDao.cartCount("admin@"), 2);//admin@ 사용자의 장바구니 개수 확인
        int insertResult3 = cartDao.cartInsert(dto3);
        assertTrue(insertResult3 == 1);// 장바구니 insert 확인

        assertEquals(cartDao.cartCount(dto.getCustomerEmail()), 2);//사용자의 카트 안에 들어있는 개수
        assertEquals(cartDao.cartCount(dto3.getCustomerEmail()), 1);
        int cnt =cartDao.cartCount(dto.getCustomerEmail()); // 사용자의 카트 안에 몇개가 있는지에 대한 개수

        for (int i = 1; i < cnt; i++) { //값이 다 있는지 확인
            assertNotNull(cartDao.cartSelectAll("admin@").get(i).getCartProductNo());//null이 없는지 확인
            System.out.println(cartDao.cartSelectAll("admin@").get(i).getCartProductNo());
        }
        assertEquals(cartDao.cartSelectAll("admin@").size(), cnt);//개수 확인
        assertEquals(cnt, cartDao.cartCount("admin@")); //사용자의 개수와 반복된 개수가 같은지

        cartDao.cartDelete(dto.getCartNo());//삽입했던거 다 삭제
        cartDao.cartDelete(dto2.getCartNo());
        cartDao.cartDelete(dto3.getCartNo());
    }

    @Test
    public void cartSelect() throws Exception{
        /* 장바구니에 번호로 상품이 잘 들어갔는지 확인*/

        cartDao.cartDeleteAll();
        CartDto dto = new CartDto(1, 1, "XL", "admin@");
        int insertResult = cartDao.cartInsert(dto); //장바구니 1번째 상품 추가
        assertEquals("Insert return 1", 1, insertResult); //장바구니 추가 잘들어갔는지 확인
        CartDto dto2 = new CartDto(1, 3, "M", "admin@");
        int insertResult2 = cartDao.cartInsert(dto2); //장바구니 2번쨰 상품추가
        assertEquals("Insert return 1", 1, insertResult2); //장바구니 추가 잘들어갔는지 확인
        //데이터2개를 넣고
        assertTrue(cartDao.cartSelect(dto.getCartNo()).getCartProductNo().equals(dto.getCartProductNo()));
        //장바구니 번호로 검색이 되는지 확인
    }

    @Test
    @Transactional
    public void 장바구니_삭제_성공() throws Exception{
        CartDto dto = new CartDto(1, 1, "XL", "admin@");
        int insertResult = cartDao.cartInsert(dto); //장바구니 1번째 상품 추가
        assertEquals("Insert return 1", 1, insertResult);
        System.out.println(dto.getCartNo());
        int deleteResult = cartDao.cartDelete(dto.getCartNo());
        assertEquals("Delete return 1", deleteResult, 1 );
    }
}