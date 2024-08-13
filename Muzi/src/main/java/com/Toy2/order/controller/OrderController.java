package com.Toy2.order.controller;

import com.Toy2.order.entity.DeliveryDto;
import com.Toy2.order.entity.OrderDetailDto;
import com.Toy2.order.entity.OrderDto;
import com.Toy2.order.entity.OrderResponseDto;
import com.Toy2.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    private static final String customerEmail = "admin@";

    /**
     * 주문페이지에서 주문하기 버튼을 클릭하면 주문이 들어가고 주문상세에대한 정보와 배송지 정보가 저장ㄴ
     * @param orderDto
     * @param deliveryDto
     * @param model
     * @return orderSuccess
     * @throws Exception
     */
    @PostMapping("/complete")
    public String addOrder(@ModelAttribute OrderDto orderDto,
                           @ModelAttribute DeliveryDto deliveryDto,
                           Model model) throws Exception {
        List<OrderDetailDto> orderDetails = orderDto.getOrderDetails();
        orderDto.setCustomerEmail(customerEmail);

        orderService.addOrder(orderDto, orderDetails);

        deliveryDto.setDeliveryStreetAddress("zz");

        deliveryDto.setOrderNo(orderDto.getOrderNo());
        orderService.addDelivery(deliveryDto);

        model.addAttribute("orderProductName", orderService.getOrderDetailList(
                        orderService.getOrderList(customerEmail)
                                .get(orderService.getOrderList(customerEmail).size()-1).getOrderNo())
                .get(0).getOrderDetailProductName());
        model.addAttribute("orderProductPrice",orderService.getOrderList(customerEmail)
                .get(orderService.getOrderList(customerEmail).size()-1).getOrderPrices());
        model.addAttribute("orderProductDate",orderService.getOrderList(customerEmail)
                .get(orderService.getOrderList(customerEmail).size()-1).getOrderDate());
        return "orderSuccess";
    }

    /**
     * 회원의 주문내역들 출력
     * @param model
     * @return "orderList"
     * @throws Exception
     */
    @GetMapping("/orderList")
    public String orderList(Model model) throws Exception {
        List<OrderResponseDto> orderDto = orderService.getOrderList(customerEmail);
        model.addAttribute("orderList", orderDto);
        return "orderList";
    }

    /**
     * 회원의 주문내역에서 그 안에 주문상세로 이동
     * @param orderNo
     * @param model
     * @return "orderDetail"
     * @throws Exception
     */
    @GetMapping("/orderDetailList")
    public String orderDetailList(@RequestParam("orderNo") int orderNo, Model model) throws Exception {
        model.addAttribute("orderDetailList",orderService.getOrderDetailList(orderNo));//주문상세에 대한 정보
        model.addAttribute("delivery",orderService.getDeliveryList(orderNo));//배송지에대한정보
        return "orderDetail";
    }


}
