package com.Toy2.order.dao;

import com.Toy2.order.entity.OrderDetailDto;

import java.util.List;

public interface OrderDetailDao {
    int orderDetailInsert(OrderDetailDto orderDetailDto) throws Exception;
    int orderDetailUpdate(int orderDetailNo, String orderDetailStatus) throws Exception;
    List<OrderDetailDto> orderDetailList(int orderNo) throws Exception;
    int orderDetailDeleteAll() throws Exception;
}
