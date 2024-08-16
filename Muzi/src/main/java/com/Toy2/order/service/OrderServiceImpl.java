package com.Toy2.order.service;

import com.Toy2.order.dao.DeliveryDao;
import com.Toy2.order.dao.OrderDao;
import com.Toy2.order.dao.OrderDetailDao;
import com.Toy2.order.entity.DeliveryDto;
import com.Toy2.order.entity.OrderDetailDto;
import com.Toy2.order.entity.OrderDto;
import com.Toy2.order.entity.OrderResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    private OrderDao orderDao;
    private OrderDetailDao orderDetailDao;
    private DeliveryDao deliveryDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, OrderDetailDao orderDetailDao, DeliveryDao deliveryDao) {
        this.orderDetailDao = orderDetailDao;
        this.orderDao = orderDao;
        this.deliveryDao = deliveryDao;
    }

    /**
     * 주문만들고 주문안에 주문상세를 넣고 주문 update를 하여 총가격과 총배송비 수정
     * Transactional 모두 실행되야 저장
     * @param orderDto
     * @param orderDetailList
     * @return int
     */
    @Override
    @Transactional
    public int addOrder(OrderDto orderDto, List<OrderDetailDto> orderDetailList){
        try {
            int orderResult = orderDao.orderInsert(orderDto);
            if(orderResult != 1)
                throw new Exception("order insert failed");
            int orderNo = orderDto.getOrderNo();
            if( orderDetailList.isEmpty())
                throw new NullPointerException("orderDetailList is empty");

            for (OrderDetailDto detailDto : orderDetailList) {
                detailDto.setOrderNo(orderNo);  // 각 주문 상세에 orderNo 설정
                int orderDetailResult = orderDetailDao.orderDetailInsert(detailDto);
                if(orderDetailResult != 1)
                    throw new Exception("orderDetail insert failed");
            }
            orderDao.orderUpdate(orderNo);

            return 1;
        }catch (Exception e){
            throw new UnexpectedRollbackException("RollbackException",e);
        }
        //주문이 들어와 -> 주문이 생성되고 주문번호로 -> 주문상세에 대한값이 들어가고 -> 주문상세가 들어올때마다 주문 update
    }


    /**
     * 주문상세에 대한 정보 출력
     * @param orderNo
     * @return List<OrderDetailDto>
     * @throws Exception
     */
    @Override
    public List<OrderDetailDto> getOrderDetailList(int orderNo) throws Exception {
        List<OrderDetailDto> orderDetailList = orderDetailDao.orderDetailList(orderNo);
        if(orderDetailList.isEmpty())
            throw new Exception("OrderDetailList is empty");
        return orderDetailList;
    }

    /**
     * 회원의 주문내역 정보 출력
     * @param customerEmail
     * @return List<OrderResponseDto>
     * @throws Exception
     */
    @Override
    public List<OrderResponseDto> getOrderList(String customerEmail) throws Exception {
        List<OrderResponseDto> orderList = orderDao.orderList(customerEmail);
        return orderList;
    }

    /**
     * 주문상세에서 주문상태 변경 (주문완료 -> 주문취소 등)
     * @param orderDetailNo
     * @param orderDetailStatus
     * @return int
     * @throws Exception
     */
    @Override
    public int updateOrderDetail(int orderDetailNo, String orderDetailStatus) throws Exception {
        int result = orderDetailDao.orderDetailUpdate(orderDetailNo, orderDetailStatus);
        if(result != 1)
            throw new Exception("OrderDetail update failed");
        return result;
    }

    /**
     * 주문에 대한 배송지 출력
     * @param orderNo
     * @return DeliveryDto
     * @throws Exception
     */
    @Override
    public DeliveryDto getDeliveryList(int orderNo) throws Exception {
        DeliveryDto deliveryList = deliveryDao.deliverySelect(orderNo);
        if(deliveryList == null)
            throw new Exception("Delivery list is empty");
        return deliveryList;
    }

    /**
     * 주문에 배송지 추가
     * @param deliveryDto
     * @return int
     * @throws Exception
     */
    @Override
    public int addDelivery(DeliveryDto deliveryDto) throws Exception {
        int deliveryResult = deliveryDao.deliveryInsert(deliveryDto);
        if(deliveryResult != 1){
            throw new Exception("delivery insert failed");
        }
        return deliveryResult;
    }
}
