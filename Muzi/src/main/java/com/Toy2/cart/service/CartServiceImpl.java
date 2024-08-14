package com.Toy2.cart.service;

import com.Toy2.cart.dao.CartDao;
import com.Toy2.cart.entity.CartDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService{
    private CartDao cartDao;

    @Autowired
    public CartServiceImpl(CartDao cartDao) {
        this.cartDao = cartDao;
    }
    /**
     * 상품추가
     */
    @Override
    public int addCart(CartDto cartDto) throws Exception {
        List<CartDto> check = cartDao.cartSelectAll("admin@");
        for (int i = 0; i < check.size(); i++) {
            if(check.get(i).getCartProductNo() == cartDto.getCartProductNo())
                throw new Exception("ProductNo is Same");
        }
        int addCheck = cartDao.cartInsert(cartDto);
        if(addCheck != 1)
            throw new Exception("cartInsert is not success");

        return addCheck;
    }

    /**
     *
     * @param customerEmail
     * @return List<CartDto>
     * @throws Exception
     * 회원의 장바구니 리스트 출력
     */
    @Override
    public List<CartDto> getCarts(String customerEmail) throws Exception {
        List<CartDto> emptyCheck = cartDao.cartSelectAll(customerEmail);
        if(emptyCheck.size() == 0) //주문이 없으면 예외
            throw new Exception("cartGets is empty");
        return emptyCheck;
    }

    /**
     * 회원의 장바구니 삭제
     * @param cartNo
     * @return int
     * @throws Exception
     */
    @Override
    public int removeCart(int cartNo) throws Exception {
        int removeCheck = cartDao.cartDelete(cartNo);
        if(removeCheck != 1)
            throw new Exception("cartRemove is not success");
        return removeCheck;
    }

    /**
     * 회원의 장바구니 옵션과 수량 수정
     * @param cartNo
     * @param cartDto
     * @return int
     * @throws Exception
     */
    @Override
    public int modifyCart(int cartNo, CartDto cartDto) throws Exception {
        int modifyCheck = cartDao.cartUpdate(cartNo, cartDto);
        if(modifyCheck != 1)
            throw new Exception("cartModify is not success");
        return modifyCheck;
    }
}
