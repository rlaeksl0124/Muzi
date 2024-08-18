package com.Toy2.order.dao;

import com.Toy2.order.entity.OrderDto;
import com.Toy2.order.entity.OrderResponseDto;

import java.util.List;

/*
    orderInsert : 주문상세가 들어오기전에 insert 하여서 공간을 만들고
    orderUpdate : 주문상세에 값이 들어오면 같이 update를 해서 값을 추가
 */
public interface OrderDao {
    int orderInsert(OrderDto orderDto) throws Exception;
    int orderUpdate(int orderNo) throws Exception;
    List<OrderResponseDto> orderList(String customerEmail) throws Exception;
    int orderDelete() throws Exception;
    int orderCount(String customerEmail) throws Exception;
    OrderDto orderSelect (int orderNo) throws Exception;
}
