package com.Toy2.order.dao;

import com.Toy2.order.entity.DeliveryDto;

public interface DeliveryDao {
    int deliveryInsert(DeliveryDto deliveryDto) throws Exception;
    DeliveryDto deliverySelect(int orderNo) throws Exception;
    int deliveryDelete() throws Exception;
}
