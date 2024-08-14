package com.Toy2.order.service;

import com.Toy2.order.entity.DeliveryDto;
import com.Toy2.order.entity.OrderDetailDto;
import com.Toy2.order.entity.OrderDto;
import com.Toy2.order.entity.OrderResponseDto;

import java.util.List;

public interface OrderService {
    int addOrder(OrderDto orderDto, List<OrderDetailDto> orderDetailList) throws Exception;
    List<OrderDetailDto> getOrderDetailList(int orderNo) throws Exception;
    List<OrderResponseDto> getOrderList(String customerEmail) throws Exception;
    int updateOrderDetail(int orderDetailNo, String orderDetailStatus) throws Exception;
    DeliveryDto getDeliveryList(int orderNo) throws Exception;
    int addDelivery(DeliveryDto deliveryDto) throws Exception;
}
