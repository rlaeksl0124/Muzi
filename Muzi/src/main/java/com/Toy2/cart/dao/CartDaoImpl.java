package com.Toy2.cart.dao;

import com.Toy2.cart.entity.CartDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("CartDao")
public class CartDaoImpl implements CartDao{

    @Autowired
    private SqlSession session;
    private static String namespace = "cart.dao.CartDao.";

    @Override
    public int cartInsert(CartDto cartDto) throws Exception{//물건의 객체의 값을 받아 삽입
        return session.insert(namespace + "cartInsert", cartDto);
    }

    @Override
    public List<CartDto> cartSelectAll(String email) throws Exception {//이메일을 통해 유저의 장바구니 리스트 출력
        return session.selectList(namespace + "cartSelectAll", email);
    }

    @Override
    public int cartUpdate(int cartNo, CartDto cartDto) throws Exception {//물건의 객체의 값을 변경(수정)
        Map<String, Object> paramMap = new HashMap<>();//map으로 묶어서 전달
        paramMap.put("cartNo", cartNo);//어떤제품의
        paramMap.put("cartProductCnt", cartDto.getCartProductCnt());//개수를 바꾸고
        paramMap.put("cartProductOption", cartDto.getCartProductOption());//옵션을 바꿔 던질지
        return session.update(namespace + "cartUpdate",paramMap);
    }

    @Override
    public int cartDelete(int cartNo) throws Exception {//번호로 삭제
        return session.delete(namespace + "cartDelete",cartNo);
    }

    @Override
    public CartDto cartSelect(int cartNo) throws Exception { //장바구니 번호로 한개를 검색
        return session.selectOne(namespace + "cartSelect",cartNo);
    }

    @Override
    public int cartCount(String email) throws Exception {//유저의 장바구니안에 물건 개수
        return session.selectOne(namespace+"cartCount",email);
    }

    @Override
    public int cartDeleteAll() throws Exception {//전부삭제
        return session.delete(namespace + "cartDeleteAll");
    }
}
