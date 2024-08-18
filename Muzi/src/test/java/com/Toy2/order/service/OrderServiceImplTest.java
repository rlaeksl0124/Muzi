//package com.Toy2.order.service;
//
//import com.Toy2.order.entity.DeliveryDto;
//import com.Toy2.order.entity.OrderDetailDto;
//import com.Toy2.order.entity.OrderDto;
//import com.Toy2.order.entity.OrderResponseDto;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.UnexpectedRollbackException;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.*;
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
//@Transactional
//public class OrderServiceImplTest {
//    @Autowired
//    private OrderService orderService;
//
//    @Test
//    public void 주문_주문상세_생성_성공() throws Exception {
//        OrderDto orDto = new OrderDto("admin@");
//        //주문생성
//        List<OrderDetailDto> odDto = new ArrayList<>();//주문상세 리스트생성 후 주문상세객체2개생성
//        OrderDetailDto odDto1 = new OrderDetailDto(orDto.getOrderNo(), 1,
//                1, "주문완료", "L", "일반배송");
//        odDto.add(odDto1);//주문상세 추가
//        OrderDetailDto odDto2 = new OrderDetailDto(orDto.getOrderNo(), 2,
//                2, "주문완료", "L", "일반배송");
//        odDto.add(odDto2);//주문상세추가
//        int result = orderService.addOrder(orDto, odDto);//주문과 주문생성 추가
//        assertEquals(1, result);//실행확인
//    }
//
//    @Test
//    public void 주문_주문상세_생성_빈값이들어올때_실패() throws Exception {
//        OrderDto orDto = new OrderDto("admin@");//주문객체 생성
//        List<OrderDetailDto> odDto = new ArrayList<>();//주문상세리스트생성
//
//        assertThrows(UnexpectedRollbackException.class, () -> orderService.addOrder(orDto, odDto));
//        //없는 주문상세를 넣을 경우 트랜잭션처리로 롤백
//    }
//    @Test
//    public void 주문_주문상세_필수필드_누락으로_생성_실패() throws Exception {
//        OrderDto orDto = new OrderDto(null); // 필수 필드 누락 (예: customerEmail)
//        List<OrderDetailDto> odDto = new ArrayList<>();//주문상세리스트생성
//        odDto.add(new OrderDetailDto(orDto.getOrderNo(), 1,
//                1, "주문완료", "L", "일반배송"));
//        //주문상세추가
//        DeliveryDto deliveryDto = new DeliveryDto(odDto.get(0).getOrderNo(), "유민우", "집",
//                "010-0000-0000", "서울 강남구 강남대로 364",
//                "역삼동 826-21","10층","안전배달");
//
//        assertThrows(UnexpectedRollbackException.class, () -> {
//            orderService.addOrder(orDto, odDto);
//        });
//        //없는 주문생성으로 트랜잭션처리로 롤백
//        assertThrows(DataIntegrityViolationException.class, ()-> orderService.addDelivery(deliveryDto));
//        //없는 주문으로 배송지를 입력해서 예외발생확인
//    }
//
//    @Test
//    public void 주문리스트_불러오기_성공() throws Exception {
//        OrderDto orDto = new OrderDto("admin@");
//        //주문 생성 객체
//        List<OrderDetailDto> odDto = new ArrayList<>();//주문상세리스트생성
//        OrderDetailDto odDto1 = new OrderDetailDto(orDto.getOrderNo(), 1,
//                1, "주문완료", "L", "일반배송");
//        odDto.add(odDto1);//주문상세추가
//        OrderDetailDto odDto2 = new OrderDetailDto(orDto.getOrderNo(), 2,
//                2, "주문완료", "L", "일반배송");
//        odDto.add(odDto2);//주문상세추가
//        DeliveryDto deliveryDto = new DeliveryDto(odDto.get(0).getOrderNo(), "유민우", "집",
//                "010-0000-0000", "서울 강남구 강남대로 364",
//                "역삼동 826-21","10층","안전배달");
//        //배송지객체생성
//
//        int result = orderService.addOrder(orDto, odDto);//주문,주문상세 추가
//        assertEquals(1, result);//실행확인
//
//        List<OrderResponseDto> orResponseDto = orderService.getOrderList("admin@");
//        //주문리스트 출력항 담기
//
//        int cnt = 0;
//        for (int i = 0; i < orResponseDto.size(); i++) {
//            System.out.println(orResponseDto.get(i).getOrderNo());//주문확인
//            cnt++;//개수저장
//        }//주문리스트 확인
//        assertEquals(orResponseDto.get(orResponseDto.size()-1).getOrderNo(), orDto.getOrderNo());
//        //주문번호로 잘생성이 되었는지 확인
//        assertEquals(cnt, orResponseDto.size());//사이즈 확인
//    }
//
//    @Test
//    public void 주문리스트_불러오기_실패() throws Exception {
//        OrderDto orDto = new OrderDto("failEmail");//없는아이디로 주문 생성
//        List<OrderResponseDto> orderResponseDtos = orderService.getOrderList(orDto.getCustomerEmail());
//        //주문출력
//        assertEquals(orderResponseDtos.size(),0);
////        assertThrows(Exception.class, () -> {
////            orderService.getOrderList(orDto.getCustomerEmail());
////        });//예외처리를 했었으나 문제가 되어 잠시 삭제
//    }
//
//    @Test
//    public void 주문상세리스트_불러오기_성공() throws Exception {
//        OrderDto orDto = new OrderDto("admin@");//주문 객체 생성
//        int orderNo = orderService.getOrderList(orDto.getCustomerEmail()).get(0).getOrderNo();
//        //주문번호
//        List<OrderDetailDto> odDto = orderService.getOrderDetailList(orderNo);
//        //주문번호로 주문상세목록 저장
//
//        assertNotNull(odDto);//비어있는지 null인지 확인
//        assertFalse(odDto.isEmpty());
//
//        System.out.println("OrderDetail List Size : " + odDto.size());//주문상품의 사이즈 확인
//
//        for( OrderDetailDto detail : odDto ) {//주문상세에 주문 넣기
//            System.out.println("ProductNo: " + detail.getProductNo());
//            System.out.println("ProductName: " + detail.getOrderDetailProductName());
//            System.out.println("OrderDetail Count: " + detail.getOrderDetailCnt());
//            System.out.println("OrderDetail Price: " + detail.getOrderDetailPrice());
//            System.out.println("OrderDetail Delivery Price: " + detail.getOrderDetailDeliveryPrice());
//            System.out.println("OrderDetail Status: " + detail.getOrderDetailStatus());
//            System.out.println("OrderDetail Option: " + detail.getOrderDetailOption());
//            System.out.println("OrderDetail Delivery Info: " + detail.getOrderDetailDeliveryInfo());
//            System.out.println("OrderDetail Date: " + detail.getOrderDetailDate());
//            System.out.println("===================================");
//        }//주문상품에 대한 내용 출력
//
//        OrderDetailDto firstDetail = odDto.get(0);//첫 주문상세 저장
//        assertNotNull(firstDetail.getProductNo());//값이 있는지확인
//        assertNotNull(firstDetail.getOrderDetailProductName());//제품이름있는지확인
//        assertTrue(firstDetail.getOrderDetailCnt() > 0);//0보다적은지 확인
//    }
//
//    @Test
//    public void 주문상세리스트_불러오기_실패() throws Exception {
//        int invalidOrderNo = 99999; //없는 주문을 입력
//        assertThrows(Exception.class, () -> {
//            orderService.getOrderDetailList(invalidOrderNo);
//        });
//        //없는 주문을 넣어서 예외 발생
//    }
//
//    @Test
//    public void 주문상세_상태변경_성공() throws Exception {
//        OrderDto orDto = new OrderDto("admin@");//주문객체생성
//        List<OrderResponseDto> ordDto =orderService.getOrderList(orDto.getCustomerEmail());
//        //주문이메일로 주문목록 뽑아 저장
//        List<OrderDetailDto> odDto = orderService.getOrderDetailList(ordDto.get(0).getOrderNo());
//        //주문목록의 첫번재 번호로 주문상세리스트 출력하여 저장
//        DeliveryDto deliveryDto = new DeliveryDto(odDto.get(0).getOrderNo(), "유민우", "집",
//                "010-0000-0000", "서울 강남구 강남대로 364",
//                "역삼동 826-21","10층","안전배달");
//        //배송지 객체 생성
//        int addCheck = orderService.addOrder(orDto, odDto);
//        //주문과 주문상세 저장
//        assertEquals(1, addCheck);
//        //저장 확인
//
//        int updateCheck = orderService.updateOrderDetail(
//                odDto.get(0).getOrderDetailNo(),
//                "주문취소");
//        //주문 상태 변경 실행
//        assertEquals(1, updateCheck);
//        //실행확인
//    }
//
//    @Test
//    public void 주문상세_상태변경_실패() throws Exception {
//        OrderDto orDto = new OrderDto("admin@");
//        //주문객체생성
//        List<OrderResponseDto> ordDto =orderService.getOrderList(orDto.getCustomerEmail());
//        //주문 목록 출력 저장
//        List<OrderDetailDto> odDto = orderService.getOrderDetailList(ordDto.get(0).getOrderNo());
//        //주문상세 목록 출력 저장
//
//        assertThrows(NullPointerException.class, () -> {
//            orderService.updateOrderDetail(
//                    odDto.get(0).getOrderDetailNo(),
//                    "주문취소");
//        });
//        //변경할 주문상세 목록이 없어서 Null Pointer 예외 발생
//    }
//
//    @Test
//    public void 배송지_추가_성공() throws Exception {
//        OrderDto orDto = new OrderDto("admin@");
//        //주문 객체 생성
//        List<OrderDetailDto> odDto = new ArrayList<>();//주문상세리스트 생성
//        OrderDetailDto odDto1 = new OrderDetailDto(orDto.getOrderNo(), 1, 1, "주문완료", "L", "일반배송");
//        odDto.add(odDto1);//주문상세 저장
//        OrderDetailDto odDto2 = new OrderDetailDto(orDto.getOrderNo(), 2, 2, "주문완료", "L", "일반배송");
//        odDto.add(odDto2);//주문상세 저장2
//
//        int result = orderService.addOrder(orDto, odDto);//주문과 주문상세 저장
//        assertEquals(1, result);//실행확인
//
//        List<OrderResponseDto> orResponseDto = orderService.getOrderList("admin@");
//        //주문목록 출력
//
//        DeliveryDto deliveryDto = new DeliveryDto(orResponseDto.get(orResponseDto.size()-1).getOrderNo(), "유민우", "집",
//                "010-0000-0000", "서울 강남구 강남대로 364",
//                "역삼동 826-21","10층","안전배달");
//        //주문 마지막 주문번호로 배송지 객체 생성
//        int deliveryResult = orderService.addDelivery(deliveryDto);
//        //배송지 저장
//        assertEquals(deliveryResult, 1);
//        //배송지저장 실행확인
//        assertEquals(deliveryDto.getOrderNo(), orResponseDto.get(orResponseDto.size()-1).getOrderNo());
//        //배송지 주문번호와 주문안에있는 주문번호 비교확인
//
//    }
//    @Test
//    public void 배송지_추가_실패() throws Exception {
//        OrderDto orDto = new OrderDto("admin@");
//        //객체생성
//        List<OrderDetailDto> odDto = new ArrayList<>();//주문목록리스트생성
//        OrderDetailDto odDto1 = new OrderDetailDto(orDto.getOrderNo(), 1, 1, "주문완료", "L", "일반배송");
//        odDto.add(odDto1);//주문목록추가
//        OrderDetailDto odDto2 = new OrderDetailDto(orDto.getOrderNo(), 2, 2, "주문완료", "L", "일반배송");
//        odDto.add(odDto2);//주문목록추가2
//        //orDto.getOrderNo가 null이 떠서 생성이 불가능
//        //delivery는 따로 넣어야하나? 모두 생성이 되고 그뒤에 orNo가 생기기 때문에
//
//        int result = orderService.addOrder(orDto, odDto);//주문, 주문상세 추가
//        assertEquals(1, result);//실행확인
//
//        List<OrderResponseDto> orResponseDto = orderService.getOrderList("admin@");
//        //주문목록 출력저장
//
//
//        DeliveryDto deliveryDto = new DeliveryDto(9999, "유민우", "집",
//                "010-0000-0000", "서울 강남구 강남대로 364",
//                "역삼동 826-21","10층","안전배달");
//        //없는 주문번호에 배송지 객체생성
//        assertThrows(DataIntegrityViolationException.class, () -> {
//            orderService.addDelivery(deliveryDto);
//        });
//        //없는 주문번호 배송지에 배송지 저장하여 예외발생
//        DeliveryDto deliveryDto2 = new DeliveryDto(orResponseDto.get(orResponseDto.size()-1).getOrderNo(), "유민우", "집",
//                "010-0000-0000", "서울 강남구 강남대로 364",
//                "역삼동 826-21","10층","안전배달");
//        //있는 주문번호에 배송지 저장
//
//        assertEquals(orderService.addDelivery(deliveryDto2), 1);
//        //배송지 저장 확인
//        DeliveryDto deliveryDto3= new DeliveryDto(orResponseDto.get(orResponseDto.size()-1).getOrderNo(), "유민우", "집",
//                "010-0000-0000", "서울 강남구 강남대로 364",
//                "역삼동 826-21","10층","안전배달");
//        //배송지 중복 저장확인을 위해생성
//        assertThrows(DataIntegrityViolationException.class, () -> {
//            orderService.addDelivery(deliveryDto3);
//        });
//        //중복 주문번호 데이터를 저장하여 예외 발생
//    }
//
//    @Test
//    public void 배송지_출력_성공() throws Exception {
//        OrderDto orDto = new OrderDto("admin@");
//        //주문객체생성
//
//        List<OrderDetailDto> odDto = new ArrayList<>();//주문목록리스트생성
//        OrderDetailDto odDto1 = new OrderDetailDto(orDto.getOrderNo(), 1, 1, "주문완료", "L", "일반배송");
//        odDto.add(odDto1);//주문목록추가
//        OrderDetailDto odDto2 = new OrderDetailDto(orDto.getOrderNo(), 2, 2, "주문완료", "L", "일반배송");
//        odDto.add(odDto2);//주문목록추가2
//        //orDto.getOrderNo가 null이 떠서 생성이 불가능
//        //delivery는 따로 넣어야하나? 모두 생성이 되고 그뒤에 orNo가 생기기 때문에
//
//        int result = orderService.addOrder(orDto, odDto);//주문,주문목록생성
//        assertEquals(1, result);//생성확인
//
//        List<OrderResponseDto> orResponseDto = orderService.getOrderList("admin@");
//        //주문 목록 저장
//
//
//        DeliveryDto deliveryDto = new DeliveryDto(orResponseDto.get(orResponseDto.size()-1).getOrderNo(),
//                "유민우", "집",
//                "010-0000-0000", "서울 강남구 강남대로 364",
//                "역삼동 826-21","10층","안전배달");
//        //배송지 객체 생성 마지막 저장된 주문번호로
//        int deliveryResult = orderService.addDelivery(deliveryDto);
//        //배송지저장
//        assertEquals(deliveryDto.getOrderNo(), orResponseDto.get(orResponseDto.size()-1).getOrderNo());
//        //배송지 주문번호, 주문의 주문번호 비교
//        assertEquals(deliveryResult, 1);
//        //배송지 생성확인
//        DeliveryDto deliveryDto1 =
//                orderService.getDeliveryList(orResponseDto.get(orResponseDto.size()-1).getOrderNo());
//        //배송지 확인
//        assertEquals(deliveryDto1.getDeliveryDetailAddress(), deliveryDto.getDeliveryDetailAddress());
//        assertEquals(deliveryDto1.getDeliveryPhone(), deliveryDto.getDeliveryPhone());
//        assertEquals(deliveryDto1.getDeliveryMessage(), deliveryDto.getDeliveryMessage());
//        assertEquals(deliveryDto1.getDeliveryRoadAddress(), deliveryDto.getDeliveryRoadAddress());
//        assertEquals(deliveryDto1.getDeliveryName(), deliveryDto.getDeliveryName());
//        //배송지 저장한 값과 출력한 값이 맞는지 확인
//    }
//
//    @Test
//    public void 배송지_출력_실패() throws Exception {
//        assertThrows(Exception.class, () -> {
//            orderService.getDeliveryList(9999);
//        });
//        //없는 주문번호로 출력하여 예외발생
//    }
//}