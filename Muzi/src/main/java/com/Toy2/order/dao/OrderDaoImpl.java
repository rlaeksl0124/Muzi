package com.Toy2.order.dao;

import com.Toy2.order.entity.OrderDto;
import com.Toy2.order.entity.OrderResponseDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("OrderDto")
public class OrderDaoImpl implements OrderDao{
    @Autowired
    private SqlSession session;

    private static String namespace = "cart.dao.OrderDao.";

    @Override
    public int orderInsert(OrderDto orderDto) throws Exception {
        return session.insert(namespace + "orderInsert", orderDto);
    }

    @Override
    public int orderUpdate(int orderNo) throws Exception {
        return session.update(namespace + "orderUpdate", orderNo);
    }

    @Override
    public List<OrderResponseDto> orderList(String customerEmail) throws Exception {
        return session.selectList(namespace + "orderList", customerEmail);
    }

    @Override
    public int orderDelete() throws Exception {
        return session.delete(namespace + "orderDelete");
    }
}
