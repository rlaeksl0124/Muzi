package com.Toy2.order.service;

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
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Transactional
public class OrderServiceImplTest {
    @Autowired
    private OrderService orderService;

    @Test
    public void 주문_주문상세_생성_성공() throws Exception {
        OrderDto orDto = new OrderDto("admin@");

        List<OrderDetailDto> odDto = new ArrayList<>();
        OrderDetailDto odDto1 = new OrderDetailDto(orDto.getOrderNo(), 1, 1, "주문완료", "L", "일반배송");
        odDto.add(odDto1);
        OrderDetailDto odDto2 = new OrderDetailDto(orDto.getOrderNo(), 2, 2, "주문완료", "L", "일반배송");
        odDto.add(odDto2);
        //orDto.getOrderNo가 null이 떠서 생성이 불가능
        //delivery는 따로 넣어야하나? 모두 생성이 되고 그뒤에
        int result = orderService.addOrder(orDto, odDto);
        assertEquals(1, result);
    }

    @Test
    public void 주문_주문상세_생성_빈값이들어올때_실패() throws Exception {
        OrderDto orDto = new OrderDto("admin@");
        List<OrderDetailDto> odDto = new ArrayList<>();

        assertThrows(UnexpectedRollbackException.class, () -> orderService.addOrder(orDto, odDto));
    }
    @Test
    public void 주문_주문상세_필수필드_누락으로_생성_실패() {
        OrderDto orDto = new OrderDto(null); // 필수 필드 누락 (예: customerEmail)
        List<OrderDetailDto> odDto = new ArrayList<>();
        odDto.add(new OrderDetailDto(orDto.getOrderNo(), 1, 1, "주문완료", "L", "일반배송"));

        DeliveryDto deliveryDto = new DeliveryDto(odDto.get(0).getOrderNo(), "유민우", "집",
                "010-0000-0000", "서울 강남구 강남대로 364",
                "역삼동 826-21","10층","안전배달");
        assertThrows(UnexpectedRollbackException.class, () -> {
            orderService.addOrder(orDto, odDto);
        });
    }

    @Test
    public void 주문리스트_불러오기_성공() throws Exception {
        OrderDto orDto = new OrderDto("admin@");

        List<OrderDetailDto> odDto = new ArrayList<>();
        OrderDetailDto odDto1 = new OrderDetailDto(orDto.getOrderNo(), 1, 1, "주문완료", "L", "일반배송");
        odDto.add(odDto1);
        OrderDetailDto odDto2 = new OrderDetailDto(orDto.getOrderNo(), 2, 2, "주문완료", "L", "일반배송");
        odDto.add(odDto2);
        DeliveryDto deliveryDto = new DeliveryDto(odDto.get(0).getOrderNo(), "유민우", "집",
                "010-0000-0000", "서울 강남구 강남대로 364",
                "역삼동 826-21","10층","안전배달");

        int result = orderService.addOrder(orDto, odDto);
        assertEquals(1, result);

        List<OrderResponseDto> orResponseDto = orderService.getOrderList("admin@");

        for (int i = 0; i < orResponseDto.size(); i++) {
            System.out.println(orResponseDto.get(i).getOrderNo());
        }
        assertEquals(4, orResponseDto.size());
    }

    @Test
    public void 주문리스트_불러오기_실패() {
        OrderDto orDto = new OrderDto("admin@2");

        assertThrows(Exception.class, () -> {
            orderService.getOrderList(orDto.getCustomerEmail());
        });
    }

    @Test
    public void 주문상세리스트_불러오기_성공() throws Exception {
        OrderDto orDto = new OrderDto("admin@");
        int orderNo = orderService.getOrderList(orDto.getCustomerEmail()).get(0).getOrderNo();
        List<OrderDetailDto> odDto = orderService.getOrderDetailList(orderNo);

        assertNotNull(odDto);
        assertFalse(odDto.isEmpty());

        System.out.println("OrderDetail List Size : " + odDto.size());

        for( OrderDetailDto detail : odDto ) {
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

        OrderDetailDto firstDetail = odDto.get(0);
        assertNotNull(firstDetail.getProductNo());
        assertNotNull(firstDetail.getOrderDetailProductName());
        assertTrue(firstDetail.getOrderDetailCnt() > 0);
    }

    @Test
    public void 주문상세리스트_불러오기_실패() throws Exception {
        int invalidOrderNo = 99999; //없는 주문을 입력
        assertThrows(Exception.class, () -> {
            orderService.getOrderDetailList(invalidOrderNo);
        });
    }

    @Test
    public void 주문상세_상태변경_성공() throws Exception {
        OrderDto orDto = new OrderDto("admin@");
        List<OrderResponseDto> ordDto =orderService.getOrderList(orDto.getCustomerEmail());
        List<OrderDetailDto> odDto = orderService.getOrderDetailList(ordDto.get(0).getOrderNo());
        DeliveryDto deliveryDto = new DeliveryDto(odDto.get(0).getOrderNo(), "유민우", "집",
                "010-0000-0000", "서울 강남구 강남대로 364",
                "역삼동 826-21","10층","안전배달");
        int addCheck = orderService.addOrder(orDto, odDto);
        assertEquals(1, addCheck);

        int updateCheck = orderService.updateOrderDetail(
                odDto.get(0).getOrderDetailNo(),
                "주문취소");

        assertEquals(1, updateCheck);
    }

    @Test
    public void 주문상세_상태변경_실패() throws Exception {
        OrderDto orDto = new OrderDto("admin@");
        List<OrderResponseDto> ordDto =orderService.getOrderList(orDto.getCustomerEmail());
        List<OrderDetailDto> odDto = orderService.getOrderDetailList(ordDto.get(0).getOrderNo());

        assertThrows(NullPointerException.class, () -> {
            orderService.updateOrderDetail(
                    odDto.get(0).getOrderDetailNo(),
                    "주문취소");
        });
    }

    @Test
    public void 배송지_추가_성공() throws Exception {
        OrderDto orDto = new OrderDto("admin@");

        List<OrderDetailDto> odDto = new ArrayList<>();
        OrderDetailDto odDto1 = new OrderDetailDto(orDto.getOrderNo(), 1, 1, "주문완료", "L", "일반배송");
        odDto.add(odDto1);
        OrderDetailDto odDto2 = new OrderDetailDto(orDto.getOrderNo(), 2, 2, "주문완료", "L", "일반배송");
        odDto.add(odDto2);
        //orDto.getOrderNo가 null이 떠서 생성이 불가능
        //delivery는 따로 넣어야하나? 모두 생성이 되고 그뒤에 orNo가 생기기 때문에

        int result = orderService.addOrder(orDto, odDto);
        assertEquals(1, result);

        List<OrderResponseDto> orResponseDto = orderService.getOrderList("admin@");


        DeliveryDto deliveryDto = new DeliveryDto(orResponseDto.get(orResponseDto.size()-1).getOrderNo(), "유민우", "집",
                "010-0000-0000", "서울 강남구 강남대로 364",
                "역삼동 826-21","10층","안전배달");
        int deliveryResult = orderService.addDelivery(deliveryDto);
        assertEquals(deliveryDto.getOrderNo(), orResponseDto.get(orResponseDto.size()-1).getOrderNo());
        assertEquals(deliveryResult, 1);
    }
    @Test
    public void 배송지_추가_실패() throws Exception {
        OrderDto orDto = new OrderDto("admin@");

        List<OrderDetailDto> odDto = new ArrayList<>();
        OrderDetailDto odDto1 = new OrderDetailDto(orDto.getOrderNo(), 1, 1, "주문완료", "L", "일반배송");
        odDto.add(odDto1);
        OrderDetailDto odDto2 = new OrderDetailDto(orDto.getOrderNo(), 2, 2, "주문완료", "L", "일반배송");
        odDto.add(odDto2);
        //orDto.getOrderNo가 null이 떠서 생성이 불가능
        //delivery는 따로 넣어야하나? 모두 생성이 되고 그뒤에 orNo가 생기기 때문에

        int result = orderService.addOrder(orDto, odDto);
        assertEquals(1, result);

        List<OrderResponseDto> orResponseDto = orderService.getOrderList("admin@");


        DeliveryDto deliveryDto = new DeliveryDto(9999, "유민우", "집",
                "010-0000-0000", "서울 강남구 강남대로 364",
                "역삼동 826-21","10층","안전배달");
        assertThrows(DataIntegrityViolationException.class, () -> {
            orderService.addDelivery(deliveryDto);
        });
        DeliveryDto deliveryDto2 = new DeliveryDto(orResponseDto.get(orResponseDto.size()-1).getOrderNo(), "유민우", "집",
                "010-0000-0000", "서울 강남구 강남대로 364",
                "역삼동 826-21","10층","안전배달");


        assertEquals(orderService.addDelivery(deliveryDto2), 1);

        DeliveryDto deliveryDto3= new DeliveryDto(orResponseDto.get(orResponseDto.size()-1).getOrderNo(), "유민우", "집",
                "010-0000-0000", "서울 강남구 강남대로 364",
                "역삼동 826-21","10층","안전배달");
        assertThrows(DataIntegrityViolationException.class, () -> {
            orderService.addDelivery(deliveryDto3);
        });
    }

    @Test
    public void 배송지_출력_성공() throws Exception {
        OrderDto orDto = new OrderDto("admin@");

        List<OrderDetailDto> odDto = new ArrayList<>();
        OrderDetailDto odDto1 = new OrderDetailDto(orDto.getOrderNo(), 1, 1, "주문완료", "L", "일반배송");
        odDto.add(odDto1);
        OrderDetailDto odDto2 = new OrderDetailDto(orDto.getOrderNo(), 2, 2, "주문완료", "L", "일반배송");
        odDto.add(odDto2);
        //orDto.getOrderNo가 null이 떠서 생성이 불가능
        //delivery는 따로 넣어야하나? 모두 생성이 되고 그뒤에 orNo가 생기기 때문에

        int result = orderService.addOrder(orDto, odDto);
        assertEquals(1, result);

        List<OrderResponseDto> orResponseDto = orderService.getOrderList("admin@");


        DeliveryDto deliveryDto = new DeliveryDto(orResponseDto.get(orResponseDto.size()-1).getOrderNo(), "유민우", "집",
                "010-0000-0000", "서울 강남구 강남대로 364",
                "역삼동 826-21","10층","안전배달");
        int deliveryResult = orderService.addDelivery(deliveryDto);
        assertEquals(deliveryDto.getOrderNo(), orResponseDto.get(orResponseDto.size()-1).getOrderNo());
        assertEquals(deliveryResult, 1);
        DeliveryDto deliveryDto1 =
                orderService.getDeliveryList(orResponseDto.get(orResponseDto.size()-1).getOrderNo());
        //deliveryDto1 : orderNo = null, StreetAddress = null
        assertEquals(deliveryDto1.getDeliveryDetailAddress(), deliveryDto.getDeliveryDetailAddress());
        assertEquals(deliveryDto1.getDeliveryPhone(), deliveryDto.getDeliveryPhone());
        assertEquals(deliveryDto1.getDeliveryMessage(), deliveryDto.getDeliveryMessage());
        assertEquals(deliveryDto1.getDeliveryRoadAddress(), deliveryDto.getDeliveryRoadAddress());
        assertEquals(deliveryDto1.getDeliveryName(), deliveryDto.getDeliveryName());
    }

    @Test
    public void 배송지_출력_실패() throws Exception {
        assertThrows(Exception.class, () -> {
            orderService.getDeliveryList(9999);
        });
    }
}