package com.Toy2.order.controller;


import com.Toy2.cart.entity.CartDto;
import com.Toy2.cart.service.CartService;
import com.Toy2.order.domain.PageHandler;
import com.Toy2.order.entity.DeliveryDto;
import com.Toy2.order.entity.OrderDetailDto;
import com.Toy2.order.entity.OrderDto;
import com.Toy2.order.entity.OrderResponseDto;
import com.Toy2.order.service.OrderService;
import com.Toy2.pay.entity.ReadyResponse;
import com.Toy2.pay.service.KakaoPayService;
import com.Toy2.pay.service.KakaoPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//서비스, 컨트롤러 왔다갔다하면서 예외처리
@Controller
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;
    private CartService cartService;
    private KakaoPayService kakaoPayService;
    @Autowired
    public OrderController(OrderService orderService,
                           CartService cartService,
                           KakaoPayServiceImpl kakaoPayService) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.kakaoPayService = kakaoPayService;
    }

    /**
     * 주문페이지에서 주문하기 버튼을 클릭하면 주문이 들어가고 주문상세에대한 정보와 배송지 정보가 저장
     * @param orderDto
     * @param deliveryDto
     * @param model
     * @return orderSuccess
     * @throws Exception
     */
    @PostMapping("/complete")//결제하기
    public String addOrder(@ModelAttribute OrderDto orderDto,
                           @ModelAttribute DeliveryDto deliveryDto,
                           @RequestParam("orderType") String cartOrder,
                           Model model,
                           HttpSession session) throws Exception {
        String customerEmail = (String) session.getAttribute("c_email");
        orderDto.setCustomerEmail(customerEmail);
        List<OrderDetailDto> orderDetails = orderDto.getOrderDetails();

        try {
            orderService.addOrder(orderDto, orderDetails, deliveryDto);
            if(cartOrder.equals("장바구니구매"))
                cartService.cartEmailDelete(customerEmail);
            model.addAttribute("orderProductName", orderService.getOrderDetailList(
                            orderService.getOrderList(customerEmail)
                                    .get(orderService.getOrderList(customerEmail).size()-1).getOrderNo())
                    .get(0).getOrderDetailProductName());
            model.addAttribute("orderProductList", orderService.getOrderList(customerEmail)
                    .get(orderService.getOrderList(customerEmail).size()-1));
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
    public String orderList(Integer page, Integer pageSize, Model model, HttpSession session) throws Exception {
        if (session.getAttribute("c_email") == null || session.getAttribute("c_email") == "") {
            model.addAttribute("errorMessage", "로그인을 해주세요.");
            return "redirect:/login";
        }
        String customerEmail = (String) session.getAttribute("c_email");
        if (page == null) page = 0;
        if (pageSize == null) pageSize = 10;

        try {
            int totalCnt = orderService.orderCnt(customerEmail);
            PageHandler pageHandler = new PageHandler(totalCnt, page, pageSize);

            Map map = new HashMap();
            map.put("offset", (page) * pageSize);// + 되어있었음 * 로 바꿈
            map.put("pageSize", pageSize);
            map.put("customerEmail", customerEmail);

            List<OrderResponseDto> list = orderService.getOrderListPage(map);
            model.addAttribute("orderList", list);
            model.addAttribute("ph", pageHandler);
            model.addAttribute("page", page);
            model.addAttribute("pageSize", pageSize);
        } catch (Exception e) {
            return "redirect:/";
        }

//        try {
//            List<OrderResponseDto> orderDto = orderService.getOrderList((String) session.getAttribute("c_email"));
//            model.addAttribute("orderList", orderDto);
//        } catch (Exception e) {
//            model.addAttribute("errorMessage", "주문 내역을 가져오는 중 문제가 발생했습니다.");
//            return "orderList";
//        }
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

    /**
     * 주문 상품에서
     * 주문취소를 클릭시 OC1->OC2로 바뀌면서 주문취소로 바뀌고 주문취소버튼 사라짐
     * @param orderDetailNo
     * @param status
     * @param orderNo
     * @return "redirect:/orders/orderDetailList?orderNo=" + orderNo
     * @throws Exception
     */

    @GetMapping("/cancel")
    public String orderDetailUpdate(@RequestParam("orderDetailNo") int orderDetailNo,
                                    @RequestParam("status") String status,
                                    @RequestParam("orderNo") int orderNo) throws Exception {
        orderService.updateOrderDetail(orderDetailNo, status);
        return "redirect:/orders/orderDetailList?orderNo=" + orderNo;
    }

    @GetMapping("/orderPay")
    public @ResponseBody ReadyResponse payReady(OrderDto orderDto, int totalAmount, HttpSession session, Model model) throws Exception{
        String mem_id = (String) session.getAttribute("c_email");
        List<CartDto> cartList = cartService.getCarts(mem_id);

        String itemName = cartList.get(0).getCartProductName()+"외 " + String.valueOf(cartList.size()-1)+ " 개";
        int quantity = cartList.size()-1;

        ReadyResponse readyResponse =kakaoPayService.payRead(itemName, quantity, mem_id, totalAmount);
        System.out.println("결제 고유번호 : " +readyResponse.getTid());
        System.out.println(("결제 요청 URL : " + readyResponse.getNext_redirect_pc_url()));

        return readyResponse;
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, HttpServletRequest request, Model model) {
        model.addAttribute("ex", e);

        String requestURI = request.getRequestURI();
        // 현재 요청이 GET 요청인지 POST 요청인지 확인
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            // POST 요청이라면 GET 요청으로 리다이렉트
            return "redirect:" + requestURI;
        } else {
            // GET 요청이라면 예외가 발생한 페이지를 다시 렌더링
            return "forward:" + requestURI;
        }
    }
}
