package com.Toy2.order.dao;

import com.Toy2.order.entity.DeliveryDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("DeliveryDto")
public class DeliveryDaoImpl implements DeliveryDao{
    private SqlSession session;
    @Autowired
    public DeliveryDaoImpl(SqlSession session){
        this.session = session;
    }

    private static String namespace = "order.dao.DeliveryDao.";

    @Override
    public int deliveryInsert(DeliveryDto deliveryDto) throws Exception {
        return session.insert(namespace + "deliveryInsert", deliveryDto);
    }

    @Override
    public DeliveryDto deliverySelect(int orderNo) throws Exception {
        return session.selectOne(namespace + "deliverySelect", orderNo);
    }

    @Override
    public int deliveryDelete() throws Exception {
        return session.delete(namespace + "deliveryDelete");
    }
}
