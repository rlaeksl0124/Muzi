package com.Toy2.order.dao;

import com.Toy2.order.entity.OrderDetailDto;
import com.Toy2.order.entity.OrderDto;
import com.Toy2.order.entity.OrderResponseDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Transactional
public class OrderDaoImplTest {
    @Autowired
    private OrderDao oDao;
    @Autowired
    private OrderDetailDao odDao;

    private static final String customerEmail = "admin@";
    private static final String failEmail = "failEmail";
    /* 주문이 들어오면 주문이 생성되고 주문상품이 들어오고 주문의 내용에 총가격과 총배송비 수정 하는중~*/

    @Test
    public void 주문_추가_성공() throws Exception {
        OrderDto order = new OrderDto(customerEmail);

        int result = oDao.orderInsert(order);
        assertTrue("주문_추가_성공",result == 1);
        Integer generatedOrderNo = order.getOrderNo();
        System.out.println(order.getOrderNo());
        assertNotNull(generatedOrderNo);
    }

    @Test
    public void 주문_추가_실패() throws Exception {
        String failEmail = "fail";
        OrderDto order = new OrderDto(failEmail);
        try{
            int result = oDao.orderInsert(order);
            assertTrue("주문_추가_실패",result != 1);
        } catch (Exception e) {
            System.out.println("주문_추가_실패");
        }
    }

    @Test
    public void 주문_수정_성공() throws Exception {
        OrderDto order = new OrderDto(customerEmail);
        int result = oDao.orderInsert(order);
        assertTrue(result == 1);

        OrderDetailDto orderDetail = new OrderDetailDto(order.getOrderNo(), 2, 1, "주문완료", "L", "일반배송");
        //orderDetail 객체를 만들어서 위에서 생성한 order 안에 넣고
        int odResult = odDao.orderDetailInsert(orderDetail);
        assertTrue(odResult == 1);
        //update 를 실행시켜서 위 정보가 합쳐지게
        int upResult = oDao.orderUpdate(order.getOrderNo());
        assertTrue(upResult == 1);
    }

    @Test
    public void 주문_수정_실패() throws Exception {
        // 새로운 주문을 생성하여 저장
        OrderDto order = new OrderDto(customerEmail);
        int result = oDao.orderInsert(order);
        assertTrue(result == 1); // 주문 삽입 성공 확인

        // 잘못된 주문 번호를 사용하여 OrderDetail 생성
        int invalidOrderNo = order.getOrderNo() + 9999; // 존재하지 않는 주문 번호
        OrderDetailDto orderDetail = new OrderDetailDto(invalidOrderNo, 2, 1, "주문완료", "L", "일반배송");

        try {
            int odResult = odDao.orderDetailInsert(orderDetail);
            assertTrue(odResult == 1);
        } catch (Exception e) {
            // 예외가 발생할 것으로 예상
            System.out.println("주문 상세 추가 실패");
        }

        // 존재하지 않는 주문 번호로 업데이트 시도
        int upResult = oDao.orderUpdate(invalidOrderNo);
        assertTrue("주문 수정 실패 테스트 성공", upResult == 0); // 수정 실패 확인
    }

    @Test
    public void 주문_리스트불러오기_성공() throws Exception {
        OrderDto order = new OrderDto(customerEmail);

        int result = oDao.orderInsert(order);
        assertTrue("주문_추가_성공",result == 1);
        Integer generatedOrderNo = order.getOrderNo();
        System.out.println(order.getOrderNo());
        assertNotNull(generatedOrderNo);

        List<OrderResponseDto> orderList = oDao.orderList(customerEmail);
        assertNotNull(orderList);
        assertFalse(orderList.isEmpty());

        System.out.println("OrderList Size : " + orderList.size());
        //or_no, pdt_name,or_prices, or_delvs, or_date,
        for(OrderResponseDto orders : orderList){
            System.out.println("orderNo : " + orders.getOrderNo());
            System.out.println("productName : " + orders.getProductName());
            System.out.println("orderDate : " + orders.getOrderDate());
            System.out.println("orderPrice : " + orders.getOrderPrices());
            System.out.println("orderDeliveryPrice : " + orders.getOrderDeliveryPrices());
            System.out.println("=============================");
        }
        OrderResponseDto firstOrder = orderList.get(0);
        assertNotNull(firstOrder.getOrderNo());
        assertNotNull(firstOrder.getOrderPrices());
        assertTrue(firstOrder.getOrderNo() > 0);
    }

    @Test
    public void 주문_리스트불러오기_실패() throws Exception {
        List<OrderResponseDto> orderList = oDao.orderList(failEmail);

        assertTrue(orderList.isEmpty());

        System.out.println("OrderList Size : " + orderList.size());
        //or_no, pdt_name,or_prices, or_delvs, or_date,
        for(OrderResponseDto order : orderList){
            System.out.println("orderNo : " + order.getOrderNo());
            System.out.println("productName : " + order.getProductName());
            System.out.println("orderDate : " + order.getOrderDate());
            System.out.println("orderPrice : " + order.getOrderPrices());
            System.out.println("orderDeliveryPrice : " + order.getOrderDeliveryPrices());
            System.out.println("=============================");
        }
        assertTrue(orderList.size() == 0);
    }

    @Test
    public void 주문_전부삭제() throws Exception {
        assertEquals(odDao.orderDetailDeleteAll() ,6);
        //주문상세 삭제한 개수 - 외래키설정때문에 안에 주문상세를먼저다 삭제해야 주문을 삭제가능
        assertEquals(oDao.orderDelete(), 13);//주문 삭제한 개수

        List<OrderResponseDto> orderList = oDao.orderList(customerEmail);

        for(OrderResponseDto order : orderList){
            assertNull(order.getOrderNo());
            assertNull(order.getProductName());
            assertNull(order.getOrderDate());
            assertNull(order.getOrderPrices());
            assertNull(order.getOrderDeliveryPrices());
            System.out.println("=============================");
        }
        assertTrue(orderList.size() == 0);
    }
}