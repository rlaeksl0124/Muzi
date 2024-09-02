package com.Toy2.cart.service;

import com.Toy2.Cust.Dao.CustDao;
import com.Toy2.cart.dao.CartDao;
import com.Toy2.cart.entity.CartDto;
import com.Toy2.product.db.dao.ProductDao;
import com.Toy2.product.db.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartServiceImpl implements CartService{
    private CartDao cartDao;
    private CustDao custDao;
    private ProductDao productDao;

    @Autowired
    public CartServiceImpl(CartDao cartDao, CustDao custDao, ProductDao productDao) {
        this.cartDao = cartDao;
        this.custDao = custDao;
        this.productDao = productDao;
    }
    /**
     * 상품추가
     * 같은 제품이 들어갈 경우 예외 던지기
     * 상품이 insert가 안될 경우 예외 던지기
     * insert가 안되는 경우의 수 : 없는 제품이 들어갈경우, 없는 회원이 들어갈 경우
     */
    @Override
    @Transactional
    public int addCart(CartDto cartDto) throws Exception {
        List<CartDto> check = cartDao.cartSelectAll(cartDto.getCustomerEmail());
        cartCheck(check, "addCart is Failed");
        return cartCheck(check, cartDto);
    }

    /**
     * 없는 회원이 리스트를 부를경우
     * 모든 회원 또는 비회원이 장바구니를 누를경우 리스트는 떠야하기때문에 예외던지기 불가능
     * @param customerEmail
     * @return List<CartDto>
     * @throws Exception
     * 회원의 장바구니 리스트 출력
     */
    @Override
    @Transactional
    public List<CartDto> getCarts(String customerEmail) throws Exception {
        List<CartDto> emptyCheck = cartDao.cartSelectAll(customerEmail);
        for (CartDto cart : emptyCheck) {
            ProductDto product = productDao.select(cart.getCartProductNo());
            cart.setCartProductName(product.getProductName()); // 제품 이름 설정
        }
        return emptyCheck;
    }

    /**
     * 회원의 장바구니 삭제
     * 여러개를 한번에 삭제 가능
     * 한개만도 가능
     * 삭제가 실패할 경우 예외 던지기
     * @param cartNos
     * @return int
     * @throws Exception
     */
    @Override
    @Transactional
    public int removeCarts(List<Integer> cartNos) throws Exception {
        if(cartNos.isEmpty())
            throw new Exception("cartNo is not empty");
        for (Integer cartNo : cartNos) {
            int removeCheck = cartDao.cartDelete(cartNo);
            validateResult(removeCheck, "removeCart is Failed" + cartNo);
        }
        return 1;
    }

    /**
     * 회원의 장바구니 옵션과 수량 수정
     * 장바구니 수정이 실패할 경우 예외 발생
     * 장바구니 값이 수정이 잘못될 경우 예외 발생
     * @param cartNo
     * @param cartDto
     * @return int
     * @throws Exception
     */
    @Override
    public int modifyCart(int cartNo, CartDto cartDto) throws Exception {
        int modifyCheck = cartDao.cartUpdate(cartNo, cartDto);
        validateResult(modifyCheck, "modifyCart is Failed");
        return modifyCheck;
    }

    @Override
    public int cartEmailDelete(String email) throws Exception {
        int deleteCheck = cartDao.cartEmailDelete(email);
        validateResult(deleteCheck, "cartEmailDelete is Failed");
        return deleteCheck;
    }

    @Override
    public CartDto getCart(int cartNo) throws Exception {
        CartDto cartDto = cartDao.cartSelect(cartNo);
        cartGetValid(cartDto,"getCart is Failed");
        return cartDao.cartSelect(cartNo);
    }

    private void cartGetValid(CartDto cartDto, String errorMessage){
        if((cartDto.equals("")||cartDto==null))
            throw new NullPointerException(errorMessage);
    }





    private void validateResult(int result, String errorMessage) throws Exception{
        if(result != 1){
            throw new Exception(errorMessage);
        }
    }
    private int cartCheck(List<CartDto> check, CartDto cartDto) throws Exception {
        for (int i = 0; i < check.size(); i++) {
            if(check.get(i).getCartProductNo() == cartDto.getCartProductNo()) {
                cartDto.setCartProductCnt(cartDto.getCartProductCnt() + 1);
                cartDto.setCartNo(check.get(i).getCartProductNo());
                return cartDao.cartUpdate(cartDto.getCartNo(), cartDto);
            }
        }
        return cartDao.cartInsert(cartDto);
    }
    private void cartCheck(List<CartDto> check, String errorMessage){
        if(check.isEmpty()||check.size()==0)
            throw new NullPointerException(errorMessage);
    }

}
