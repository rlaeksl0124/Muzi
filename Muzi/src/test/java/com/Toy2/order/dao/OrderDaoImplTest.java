package com.Toy2.order.dao;

import com.Toy2.order.entity.DeliveryDto;
import com.Toy2.order.entity.OrderDetailDto;
import com.Toy2.order.entity.OrderDto;
import com.Toy2.order.entity.OrderResponseDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    @Autowired
    private DeliveryDao dDao;

    private static final String customerEmail = "admin@";
    private static final String failEmail = "failEmail";
    /* 주문이 들어오면 주문이 생성되고 주문상품이 들어오고 주문의 내용에 총가격과 총배송비 수정 하는중~*/

    @Test
    public void 주문_추가_성공() throws Exception {
        odDao.orderDetailDeleteAll();
        dDao.deliveryDelete();
        oDao.orderDelete();
        OrderDto order = new OrderDto(customerEmail);//회원의 주문 생성할 객체
        assertEquals(oDao.orderCount(customerEmail) ,0);//생성전 길이 확인

        int result = oDao.orderInsert(order); //주문생성 확인
        assertEquals("주문_추가_성공",result,1);//주문 추가 생성 확인

        assertEquals(oDao.orderCount(customerEmail) ,1);
    }

    @Test
    public void 주문_추가_실패() throws Exception {
        odDao.orderDetailDeleteAll();
        dDao.deliveryDelete();
        oDao.orderDelete();
        assertEquals(oDao.orderCount(failEmail) ,0);
        OrderDto order = new OrderDto(failEmail);//없는 회원의 정보를 입력할경우
        assertThrows(DataIntegrityViolationException.class, () ->oDao.orderInsert(order));//예외 발생

        assertEquals(oDao.orderCount(failEmail) ,0);
    }

    @Test
    @Transactional
    public void 주문_수정_성공() throws Exception {
        odDao.orderDetailDeleteAll();
        dDao.deliveryDelete();
        oDao.orderDelete();
        OrderDto order = new OrderDto(customerEmail); //객체 생성
        int result = oDao.orderInsert(order);//주문 생성
        assertEquals(result, 1);//실행확인

        OrderDetailDto orderDetail = new OrderDetailDto(order.getOrderNo(), 2, 1, "주문완료", "L", "일반배송");
        //orderDetail 객체를 만들어서 위에서 생성한 order 안에 넣고
        int odResult = odDao.orderDetailInsert(orderDetail);
        assertEquals(odResult, 1);
        //update 를 실행시켜서 위 정보가 합쳐지게
        int upResult = oDao.orderUpdate(order.getOrderNo());
        assertEquals(upResult , 1);
    }

    @Test
    public void 주문_수정_실패() throws Exception {
        odDao.orderDetailDeleteAll();
        dDao.deliveryDelete();
        oDao.orderDelete();
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
//        odDao.orderDetailDeleteAll();
//        dDao.deliveryDelete();
//        oDao.orderDelete();//전부삭제후
        OrderDto order = new OrderDto(customerEmail);//주문객체생성

        int result = oDao.orderInsert(order);//주문생성
        assertTrue("주문_추가_성공",result == 1);//주문생성확인

        List<OrderResponseDto> orderList = oDao.orderList(customerEmail); //주문리스트 저장
        odDao.orderDetailInsert(new OrderDetailDto(order.getOrderNo(),
                2, 1, "주문완료", "L", "일반배송"));
        dDao.deliveryInsert(new DeliveryDto(order.getOrderNo(), "유민우", "집",
                "010-0000-0000", "서울 강남구 강남대로 364",
                "역삼동 826-21","10층","안전배달"));
        assertNotNull(orderList);//주문리스트확인
        assertFalse(orderList.isEmpty());//주문리스트비어있는지확인

        System.out.println("OrderList Size : " + orderList.size()); //데이터확인
        //or_no, pdt_name,or_prices, or_delvs, or_date,
        for(OrderResponseDto orders : orderList){
            System.out.println("orderNo : " + orders.getOrderNo());
            System.out.println("productName : " + orders.getProductName());
            System.out.println("orderDate : " + orders.getOrderDate());
            System.out.println("orderPrice : " + orders.getOrderPrices());
            System.out.println("orderDeliveryPrice : " + orders.getOrderDeliveryPrices());
            System.out.println("=============================");
        }
        OrderResponseDto firstOrder = orderList.get(0);//첫번째 주문 확인
        assertNotNull(firstOrder.getOrderNo());//첫번째 주문번호 확인
        assertNotNull(firstOrder.getOrderPrices());//첫번째 주문가격확인
        assertTrue(firstOrder.getOrderNo() > 0);//주문확인
    }

    @Test
    public void 주문_없는아이디_리스트불러오기_실패() throws Exception {
        List<OrderResponseDto> orderList = oDao.orderList(failEmail);//없는 이메일로 주문리스트부르기 없는 이메일인데 작동이된다?
        //서비스단에서 막기
        assertTrue(orderList.isEmpty());//주문이 비어있는지 확인

        System.out.println("OrderList Size : " + orderList.size());//
        //or_no, pdt_name,or_prices, or_delvs, or_date,
        for(OrderResponseDto order : orderList){
            System.out.println("orderNo : " + order.getOrderNo());
            System.out.println("productName : " + order.getProductName());
            System.out.println("orderDate : " + order.getOrderDate());
            System.out.println("orderPrice : " + order.getOrderPrices());
            System.out.println("orderDeliveryPrice : " + order.getOrderDeliveryPrices());
            System.out.println("=============================");
        }
        assertTrue(orderList.size() == 0);//주문리스트사이즈확인
    }

    @Test
    public void 주문_전부삭제() throws Exception {
        dDao.deliveryDelete();
        odDao.orderDetailDeleteAll();
        oDao.orderDelete();
        // 외래키설정때문에 안에 주문상세와 배송지를 먼저다 삭제해야 주문을 삭제가능
        List<OrderResponseDto> orderList = oDao.orderList(customerEmail);
        //리스트를 불러와서 삭제가 되었는지 확인
        for(OrderResponseDto order : orderList){//모두 null인지 확인
            assertNull(order.getOrderNo());
            assertNull(order.getProductName());
            assertNull(order.getOrderDate());
            assertNull(order.getOrderPrices());
            assertNull(order.getOrderDeliveryPrices());
            System.out.println("=============================");
        }
        assertTrue(orderList.size() == 0);//사이즈도 확인
    }
}