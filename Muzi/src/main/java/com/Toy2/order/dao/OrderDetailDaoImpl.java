package com.Toy2.order.dao;

import com.Toy2.order.entity.OrderDetailDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("OrderDetailDto")
public class OrderDetailDaoImpl implements OrderDetailDao{

    private SqlSession session;
    @Autowired
    public OrderDetailDaoImpl(SqlSession session){
        this.session = session;
    }

    private static String namespace = "cart.dao.OrderDao.";

    @Override
    public int orderDetailInsert(OrderDetailDto orderDetailDto) throws Exception {
        return session.insert(namespace + "orderDetailInsert", orderDetailDto);
    }

    @Override
    public int orderDetailUpdate(int orderDetailNo, String orderDetailStatus) throws Exception {
        Map<String, Object> paramMap = new HashMap<>();//map으로 묶어서 전달
        paramMap.put("orderDetailNo", orderDetailNo);//어떤제품의
        paramMap.put("orderDetailStatus", orderDetailStatus);
        return session.update(namespace + "orderDetailUpdate", paramMap);
    }

    @Override
    public List<OrderDetailDto> orderDetailList(int orderNo, String customerEmail) throws Exception {
        Map<String, Object> paramMap = new HashMap<>();//map으로 묶어서 전달
        paramMap.put("orderNo", orderNo);//어떤제품의
        paramMap.put("customerEmail", customerEmail);
        return session.selectList(namespace + "orderDetailList", paramMap);
    }

    @Override
    public int orderDetailDeleteAll() throws Exception {
        return session.delete(namespace + "orderDetailDeleteAll");
    }

}
