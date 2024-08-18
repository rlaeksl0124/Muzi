package com.Toy2.order.controller;

import com.Toy2.cart.service.CartService;
import com.Toy2.order.entity.DeliveryDto;
import com.Toy2.order.entity.OrderDetailDto;
import com.Toy2.order.entity.OrderDto;
import com.Toy2.order.entity.OrderResponseDto;
import com.Toy2.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

//서비스, 컨트롤러 왔다갔다하면서 예외처리
@Controller
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;
    private CartService cartService;
    @Autowired
    public OrderController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    /**
     * 주문페이지에서 주문하기 버튼을 클릭하면 주문이 들어가고 주문상세에대한 정보와 배송지 정보가 저장
     * @param orderDto
     * @param deliveryDto
     * @param model
     * @return orderSuccess
     * @throws Exception
     */
    @PostMapping("/complete")
    public String addOrder(@ModelAttribute OrderDto orderDto,
                           @ModelAttribute DeliveryDto deliveryDto,
                           Model model,
                           HttpSession session) throws Exception {
        List<OrderDetailDto> orderDetails = orderDto.getOrderDetails();
        orderDto.setCustomerEmail((String) session.getAttribute("c_email"));
        String customerEmail = (String) session.getAttribute("c_email");

        try {
            deliveryDto.setDeliveryStreetAddress("zz");
            orderService.addOrder(orderDto, orderDetails, deliveryDto);
            model.addAttribute("orderProductName", orderService.getOrderDetailList(
                            orderService.getOrderList(customerEmail)
                                    .get(orderService.getOrderList(customerEmail).size()-1).getOrderNo())
                    .get(0).getOrderDetailProductName());
            model.addAttribute("orderProductPrice", orderService.getOrderList(customerEmail)
                    .get(orderService.getOrderList(customerEmail).size()-1).getOrderPrices());
            model.addAttribute("orderProductDate", orderService.getOrderList(customerEmail)
                    .get(orderService.getOrderList(customerEmail).size()-1).getOrderDate());
            return "orderSuccess";
        } catch (Exception e) {
            return "redirect:/cart/order";
        }
    }

    /**
     * 회원의 주문내역들 출력
     * @param model
     * @return "orderList"
     * @throws Exception
     */
    @GetMapping("/orderList")
    public String orderList(Model model, HttpSession session) throws Exception {
        if(session.getAttribute("c_email") == null || session.getAttribute("c_email") == ""){
            model.addAttribute("errorMessage", "로그인을 해주세요.");
            return "redirect:/login";
        }
        try {
            List<OrderResponseDto> orderDto = orderService.getOrderList((String) session.getAttribute("c_email"));
            model.addAttribute("orderList", orderDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "주문 내역을 가져오는 중 문제가 발생했습니다.");
            return "orderList";
        }
        return "orderList";
    }

    /**
     * 회원의 주문내역에서 그 안에 주문상세로 이동하여 주문상세 출력
     * @param orderNo
     * @param model
     * @return "orderDetail"
     * @throws Exception
     */
    @GetMapping("/orderDetailList")
    public String orderDetailList(@RequestParam("orderNo") int orderNo, Model model, HttpSession session) throws Exception {
        try {
            if(session.getAttribute("c_email") == null)
                throw new Exception("로그인을 해주세요");
            model.addAttribute("orderDetailList",orderService.getOrderDetailList(orderNo));//주문상세에 대한 정보
            model.addAttribute("delivery",orderService.getDeliveryList(orderNo));//배송지에대한정보
        } catch (Exception e) {
            return "orderList";
        }
        return "orderDetail";
    }

    @GetMapping("/cancel")
    public String orderDetailUpdate(@RequestParam("orderDetailNo") int orderDetailNo,
                                    @RequestParam("status") String status,
                                    @RequestParam("orderNo") int orderNo) throws Exception {
        orderService.updateOrderDetail(orderDetailNo, status);
        return "redirect:/orders/orderDetailList?orderNo=" + orderNo;
    }

}
