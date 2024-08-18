package com.Toy2.order.dao;

import com.Toy2.order.entity.OrderDetailDto;
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
public class OrderDetailDaoImplTest {
    @Autowired
    private OrderDetailDao odDao;

    @Test
    public void 주문상세_추가_성공() throws Exception {
        OrderDetailDto odd = new OrderDetailDto(52, 2,
                1, "주문완료", "L", "일반배송");
        //DB에 저장되어있는 orderNo와 ProductNo를 입력해야한다.
        assertTrue(odDao.orderDetailInsert(odd) == 1);
        //생성 확인
    }

    @Test
    public void 주문상세_추가_실패() {
        OrderDetailDto odd = new OrderDetailDto(9999, 1,
                1, "주문완료", "L", "일반배송");
        assertThrows(Exception.class, () -> odDao.orderDetailInsert(odd));
    }

    @Test
    public void 주문상세_수정_성공() throws Exception {
        OrderDetailDto odd = new OrderDetailDto(52, 2,
                1, "주문완료", "L", "일반배송");
        assertTrue(odDao.orderDetailInsert(odd) == 1);

        // Insert 후에 생성된 orderDetailNo를 사용하여 업데이트
        Integer orderDetailNo = odd.getOrderDetailNo();
        assertNotNull(orderDetailNo);

        assertTrue(odDao.orderDetailUpdate(orderDetailNo, "주문취소") == 1);
    }

    @Test
    public void 주문상세_수정_실패() throws Exception {
        OrderDetailDto odd = new OrderDetailDto(52, 2,
                1, "주문완료", "L", "일반배송");
        assertTrue(odDao.orderDetailInsert(odd) == 1);

        assertTrue(odDao.orderDetailUpdate(0, "주문취소") != 1);
        // 존재하지 않는 orderDetailNo로 업데이트 시도
    }

    @Test
    public void 주문상세_리스트_성공() throws Exception {
        int orderNo = 89; //주문상세가 있는 주문 No 입력

        List<OrderDetailDto> orderDetailList = odDao.orderDetailList(orderNo);

        assertNotNull(orderDetailList);
        assertFalse(orderDetailList.isEmpty());

        // 리스트의 크기를 출력
        System.out.println("OrderDetail List Size: " + orderDetailList.size());

        // 리스트의 각 요소를 출력
        for (OrderDetailDto detail : orderDetailList) {
            System.out.println("ProductNo: " + detail.getProductNo());
            System.out.println("ProductName: " + detail.getOrderDetailProductName());
            System.out.println("OrderDetail Count: " + detail.getOrderDetailCnt());
            System.out.println("OrderDetail Price: " + detail.getOrderDetailPrice());
            System.out.println("OrderDetail Delivery Price: " + detail.getOrderDetailDeliveryPrice());
            System.out.println("OrderDetail Status: " + detail.getOrderDetailStatus());
            System.out.println("OrderDetail Option: " + detail.getOrderDetailOption());
            System.out.println("OrderDetail Delivery Info: " + detail.getOrderDetailDeliveryInfo());
            System.out.println("OrderDetail Date: " + detail.getOrderDetailDate());
            System.out.println("===================================");
        }

        // 실제 데이터의 유효성을 확인 (예시로 첫 번째 요소를 검증)
        OrderDetailDto firstDetail = orderDetailList.get(0);
        assertNotNull(firstDetail.getProductNo());
        assertNotNull(firstDetail.getOrderDetailProductName());
        assertTrue(firstDetail.getOrderDetailCnt() > 0);
    }
    @Test
    public void 주문상세_리스트_실패() throws Exception {
        int invalidOrderNo = 9999;  // 존재하지 않는 주문 번호

        // 주문 상세 목록을 가져옴
        List<OrderDetailDto> orderDetailList = odDao.orderDetailList(invalidOrderNo);

        // 리스트가 비어있음을 확인
        assertNotNull(orderDetailList);
        assertTrue(orderDetailList.isEmpty());
    }

    @Test
    public void 주문상세_다삭제() throws Exception {
        int deleteAlltest = odDao.orderDetailDeleteAll();
        System.out.println(deleteAlltest);
        assertEquals(deleteAlltest , 6);//삭제한 개수 입력

        // 삭제 후 리스트가 비어있음을 확인
        List<OrderDetailDto> orderDetailList = odDao.orderDetailList(1);
        assertNotNull(orderDetailList);
        assertTrue(orderDetailList.isEmpty());
    }
}