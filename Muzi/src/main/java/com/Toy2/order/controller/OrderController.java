package com.Toy2.order.controller;


import com.Toy2.cart.service.CartService;
import com.Toy2.order.domain.PageHandler;
import com.Toy2.order.entity.DeliveryDto;
import com.Toy2.order.entity.OrderDetailDto;
import com.Toy2.order.entity.OrderDto;
import com.Toy2.order.entity.OrderResponseDto;
import com.Toy2.order.service.OrderService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//서비스, 컨트롤러 왔다갔다하면서 예외처리
@Controller
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;
    private CartService cartService;
    @Autowired
    public OrderController(OrderService orderService,
                           CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @Value("#{tossProperties['payment.toss.test_secrete_api_key']}")
    private String tossSecreteApiKey;


    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @PostMapping("/complete")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addOrder(
            @Valid @ModelAttribute OrderDto orderDto,
            @Valid @ModelAttribute DeliveryDto deliveryDto,
            @RequestParam("orderType") String cartOrder,
            BindingResult result,
            HttpSession session) {
        System.out.println(session.getId());
        Map<String, Object> response = new HashMap<>();

        String customerEmail = (String) session.getAttribute("c_email");
        orderDto.setCustomerEmail(customerEmail);
        List<OrderDetailDto> orderDetails = orderDto.getOrderDetails();

        if (result.hasErrors()) {
            response.put("success", false);
            return ResponseEntity.ok(response);
        }
        try {
            orderService.addOrder(orderDto, orderDetails, deliveryDto);
            if (cartOrder.equals("장바구니구매"))
                cartService.cartEmailDelete(customerEmail); //q
            // Assuming getOrderList and getOrderDetailList return the required data
            orderSuccess(response, customerEmail);
        } catch (Exception e) {
            response.put("success", false);
        }
        return ResponseEntity.ok(response);
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
            Map pagingMap = new HashMap();

            pagingMap.put("offset", (page) * pageSize);// + 되어있었음 * 로 바꿈
            pagingMap.put("pageSize", pageSize);
            pagingMap.put("customerEmail", customerEmail);

            List<OrderResponseDto> list = orderService.getOrderListPage(pagingMap);
            model.addAttribute("orderList", list);
            model.addAttribute("ph", pageHandler);
            model.addAttribute("page", page);
            model.addAttribute("pageSize", pageSize);
        } catch (Exception e) {
            return "redirect:/";
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
    public String orderDetailList(@RequestParam("orderNo") int orderNo,@SessionAttribute String c_email ,Model model, HttpSession session) throws Exception {
        if (session.getAttribute("c_email") != c_email) {
            model.addAttribute("errorMessage", "로그인을 해주세요.");
            return "redirect:/login";
        }
        try {
            model.addAttribute("orderDetailList",orderService.getOrderDetailList(orderNo, c_email));//주문상세에 대한 정보
            model.addAttribute("delivery",orderService.getDeliveryList(orderNo));//배송지에대한정보
        } catch (Exception e) {
            return "redirect:/orders/orderList";
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
    @RequestMapping(value = "/confirm")//결제 승인 paySuccess에서 값을 뛰워주고 있음.(payment, orderId, amount)
    public ResponseEntity<JSONObject> confirmPayment(@RequestBody String jsonBody) throws Exception {
        JSONParser parser = new JSONParser();
        String orderId;
        String amount;
        String paymentKey;
        try {
            // 클라이언트에서 받은 JSON 요청 바디입니다.
            JSONObject requestData = (JSONObject) parser.parse(jsonBody);
            paymentKey = (String) requestData.get("paymentKey");
            orderId = (String) requestData.get("orderId");
            amount = (String) requestData.get("amount");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        };
        JSONObject obj = new JSONObject();
        obj.put("orderId", orderId);
        obj.put("amount", amount);
        obj.put("paymentKey", paymentKey);

        // 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
        // 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
        String widgetSecretKey = tossSecreteApiKey;
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
        String authorizations = "Basic " + new String(encodedBytes);

        // 결제를 승인하면 결제수단에서 금액이 차감돼요.
        URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(obj.toString().getBytes("UTF-8"));

        int code = connection.getResponseCode();
        boolean isSuccess = code == 200;

        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

        // 결제 성공 및 실패 비즈니스 로직을 구현하세요.
        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        responseStream.close();
        System.out.println(jsonObject);
        return ResponseEntity.status(code).body(jsonObject);
    }

    @GetMapping("/orderSuccess")//결제 완료 화면
    public String orderSuccess(Model model, HttpSession session) throws Exception {
        String customerEmail = (String) session.getAttribute("c_email");

        model.addAttribute("orderId", orderService.getOrderList(customerEmail)
                .get(orderService.getOrderList(customerEmail).size()-1).getOrderNo());
        model.addAttribute("amount", orderService.getOrderList(customerEmail)
                .get(orderService.getOrderList(customerEmail).size()-1).getOrderPrices()+
                orderService.getOrderList(customerEmail)
                        .get(orderService.getOrderList(customerEmail).size()-1).getOrderDeliveryPrices());
        model.addAttribute("orderDate", orderService.getOrderList(customerEmail)
                .get(orderService.getOrderList(customerEmail).size()-1).getOrderDate());
        model.addAttribute("orderProductName", orderService.getOrderDetailList(
                        orderService.getOrderList(customerEmail)
                                .get(orderService.getOrderList(customerEmail).size()-1).getOrderNo(),customerEmail)
                .get(0).getOrderDetailProductName());
        return "orderSuccess"; // orderSuccess.jsp로 이동
    }


//    @ExceptionHandler(Exception.class)
//    public String handleException(Exception e, HttpServletRequest request, Model model) {
//        model.addAttribute("ex", e);
//
//        String requestURI = request.getRequestURI();
//        // 현재 요청이 GET 요청인지 POST 요청인지 확인
//        if ("POST".equalsIgnoreCase(request.getMethod())) {
//            // POST 요청이라면 GET 요청으로 리다이렉트
//            return "redirect:" + requestURI;
//        } else {
//            // GET 요청이라면 예외가 발생한 페이지를 다시 렌더링
//            return "forward:" + requestURI;
//        }
//    }

    //주문성공 후 모델속성 설정하는 로직
    private void orderSuccess(Map<String, Object> response, String customerEmail) throws Exception {
        String orderProductName = orderService.getOrderDetailList(
                        orderService.getOrderList(customerEmail)
                                .get(orderService.getOrderList(customerEmail).size() - 1).getOrderNo(),customerEmail)
                .get(0).getOrderDetailProductName();

        response.put("success", true);
        response.put("orderProductName", orderProductName);
        response.put("orderPrice", orderService.getOrderList(customerEmail).get(
                orderService.getOrderList(customerEmail).size() - 1
        ).getOrderPrices());
        response.put("orderDate", orderService.getOrderList(customerEmail).get(
                orderService.getOrderList(customerEmail).size() - 1
        ).getOrderDate());
    }
}
