package com.Toy2.cart.controller;

import com.Toy2.cart.entity.CartDto;
import com.Toy2.cart.service.CartService;
import com.Toy2.order.entity.OrderDetailDto;
import com.Toy2.order.entity.OrderDto;
import com.Toy2.order.service.OrderService;
import com.Toy2.product.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

//예외처리
@Controller
@RequestMapping("/cart")
public class CartController {
    private CartService cartService;
    private ProductService productService;
    private OrderService orderService;

    @Autowired
    public CartController(CartService cartService, ProductService productService
    , OrderService orderService) {
        this.cartService = cartService;
        this.productService = productService;
        this.orderService = orderService;
    }
    @Value("#{tossProperties['payment.toss.test_client_api_key']}")
    private String tossClientKey;

    /**
     * 회원의 장바구니 페이지 출력
     * @param model
     * @return "cart"
     * @throws Exception
     */
    @GetMapping("/cart")
    public String cartPage(Model model, HttpSession session) throws Exception {
        String customerEmail = (String) session.getAttribute("c_email");
        List<CartDto> cartDto = cartService.getCarts(customerEmail);
        model.addAttribute("cartDto", cartDto);
        return "cart";
    }

    /**
     * 장바구니에서 장바구니 수량과 옵션을 변경하여 DB에 저장(지금 productOption 수정이
     * @param cartNo
     * @param productCnt
     * @return "redirect:/cart"
     * @throws Exception
     */
    @PostMapping("/modify")
    public String modifyCart(@RequestParam(name = "cartNo") int cartNo,
                             @RequestParam(name = "productCnt") int productCnt) throws Exception {
        if(productCnt <= 0) //0이하의
            return "redirect:/cart/cart";
        CartDto cartDto = new CartDto(productCnt);

        cartDto.setCartNo(cartNo);
        cartService.modifyCart(cartNo, cartDto);
        return "redirect:/cart/cart";
    }



    /**
     * 장바구니 번호로 장바구니 선택삭제
     * 전체삭제를 위해 리스트로 받아서 하나씩 삭제
     * 비회원도 만들계획이기 때문에 비회원도 삭제기능을 사용할수 있게
     * @param cartNos
     * @return "redirect:/cart/cart"
     * @throws Exception
     */
    @PostMapping("/remove")
    public String removeSelectedItems(@RequestParam(name = "cartNo") List<Integer> cartNos) throws Exception {
        cartService.removeCarts(cartNos);
        return "redirect:/cart/cart";
    }

    @PostMapping("/add")
    public String addCar(HttpServletRequest request, Model model) throws Exception{
        int productNumber = Integer.parseInt(request.getParameter("productNo"));
        int productCnt = Integer.parseInt(request.getParameter("productCnt"));
        String productOption = request.getParameter("productOption");

        // 세션에서 사용자 이메일을 가져옴 (예: 로그인된 사용자)
        String customerEmail = (String) request.getSession().getAttribute("c_email");
        System.out.println(customerEmail);
        if (customerEmail == null || customerEmail.isEmpty()) {
            model.addAttribute("errorMessage", "로그인이 필요합니다.");
            return "redirect:/login";
        }

        // CartDto 객체를 생성하고 장바구니에 추가
        CartDto cartDto = new CartDto(productNumber, productCnt, productOption, customerEmail);
        cartService.addCart(cartDto);
        // 장바구니를 담았던 그 화면으로 다시 들어가게
        return "redirect:" + request.getHeader("Referer");
    }


    /**
     * 장바구니 페이지에 제품 정보 그대로를 주문페이지로 이동
     * 바로구매 페이지에서도 그정보를 그대로 이동
     * 여기를 고쳐야 선택 주문이 된다.
     * @param request
     * @param model
     * @return order
     * @throws Exception
     */
    @PostMapping("/order")//주문하기
    public String orderPageGo(HttpServletRequest request, Model model, HttpSession session, RedirectAttributes re) throws Exception {
        String customerEmail = (String) session.getAttribute("c_email");
        if (customerEmail == null || customerEmail.isEmpty() || customerEmail.equals("")) {
            return "redirect:/login";
        }
        try {
            String orderType = request.getParameter("orderType");
            OrderDto orderDto = new OrderDto(customerEmail);

            List<OrderDetailDto> orderDetailList = createOrderDetailList(request, orderDto);
            model.addAttribute("orderDto", orderDto);
            model.addAttribute("orderDetailList", orderDetailList);
            model.addAttribute("orderType", orderType);
            model.addAttribute("tossClientKey", tossClientKey);
            model.addAttribute("orderProductFirstName", orderDetailList.get(0).getOrderDetailProductName());
            model.addAttribute("orderID", orderService.getOrderList(customerEmail).size()+1);//여기를 결제 테이블의 id로 바꾸면될듯
            //order테이블에 컬럼하나 추가해서 orderId값을 받고 랜덤의 값으로 그거랑 payment를 저장할까
            model.addAttribute("orderDetailSize", orderDetailList.size()-1);
        } catch (Exception e) {
            re.addFlashAttribute("errorMessage", "잘못된 접근입니다.");
            String referer = request.getHeader("Referer");
            System.out.println("referer = " + referer);
            return "redirect:" + referer;
        }
        return "order";
    }


//    @ExceptionHandler(Exception.class)
//    public String handleException(Exception e,HttpServletRequest request, Model model) {
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

    //제품정보획득로직
    private List<OrderDetailDto> createOrderDetailList(HttpServletRequest request, OrderDto orderDto) throws Exception {
        String[] productPrice = request.getParameterValues("productPrice");
        String[] productDeliveryPrice = request.getParameterValues("productDeliveryPrice");
        String[] productNos = request.getParameterValues("productNo");
        String[] productCnts = request.getParameterValues("productCnt");
        String[] productOptions = request.getParameterValues("productOption");


        List<OrderDetailDto> orderDetailList = new ArrayList<>();
        if (productNos != null) {
            orderDetailCreateList(productPrice, productDeliveryPrice,
                    productNos, productCnts, productOptions, orderDto, orderDetailList);
        }else{
            throw new NullPointerException("상품 번호가 없습니다.");
        }
        return orderDetailList;
    }
    //DTO 생성 로직
    private void orderDetailCreateList(String[] productPrice,String[] productDeliveryPrice,String[] productNos,
                                       String[] productCnts,String[] productOptions, OrderDto orderDto,
                                       List<OrderDetailDto> orderDetailList) throws Exception {
        for (int i = 0; i < productNos.length; i++) {
            Long odProductPrice  = Long.parseLong(productPrice[i]);
            Integer odProductDeliveryPrice = Integer.valueOf(productDeliveryPrice[i]);
            String productName = productService.selectProduct(Integer.parseInt(productNos[i])).getProductName();

            if(Integer.parseInt(productCnts[i]) <= 0)
                throw new Exception("0이하의 값 입력 불가");
            OrderDetailDto odDto = new OrderDetailDto(
                    orderDto.getOrderNo(),
                    Integer.parseInt(productNos[i]),
                    Integer.parseInt(productCnts[i]),
                    "주문완료",
                    productOptions[i],
                    "일반배송",
                    odProductPrice,
                    odProductDeliveryPrice
            );
            odDto.setOrderDetailProductName(productName);
            orderDetailList.add(odDto);
        }
    }
}
