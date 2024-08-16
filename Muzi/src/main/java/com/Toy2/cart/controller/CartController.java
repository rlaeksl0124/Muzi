package com.Toy2.cart.controller;

import com.Toy2.cart.entity.CartDto;
import com.Toy2.cart.service.CartService;
import com.Toy2.order.entity.OrderDetailDto;
import com.Toy2.order.entity.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

//예외처리
@Controller
@RequestMapping("/cart")
public class CartController {
    private CartService cartService;
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * 회원의 장바구니 페이지 출력
     * @param model
     * @return "cart"
     * @throws Exception
     */
    @GetMapping("/cart")
    public String cartPage(Model model, HttpSession session) throws Exception {
            List<CartDto> cartDto = cartService.getCarts((String) session.getAttribute("c_email"));
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
        CartDto cartDto = new CartDto(productCnt);
        cartDto.setCartNo(cartNo);
        cartService.modifyCart(cartNo, cartDto);
        return "redirect:/cart/cart";
    }

    /**
     * 장바구니 페이지에 제품 정보를 주문페이지로 이동
     * 제품에 대한 Dto가 없어서 이렇게 작성 추후 변경 예정
     * @param request
     * @param model
     * @return order
     * @throws Exception
     */
    @PostMapping("/order")
    public String orderPageGo(HttpServletRequest request, Model model, HttpSession session) throws Exception {
        String customerEmail = (String) session.getAttribute("c_email");

        OrderDto orDto = new OrderDto(customerEmail);
        List<OrderDetailDto> orderDetailList = new ArrayList<>();
        //제품을 담을 dto가 없어서 이렇게 우선 작성 차후에 변경 예정
        String[] productPrice = request.getParameterValues("productPrice");
        String[] productDeliveryPrice = request.getParameterValues("productDeliveryPrice");
        String[] productNos = request.getParameterValues("productNo");
        String[] productCnts = request.getParameterValues("productCnt");
        String[] productOptions = request.getParameterValues("productOption");

        if (customerEmail == null) {
            throw new Exception("로그인이 필요합니다.");
        }
        if (productNos != null) {
            for (int i = 0; i < productNos.length; i++) {
                Long pp  = Long.parseLong(productPrice[i]);
                Integer dp = Integer.valueOf(productDeliveryPrice[i]);
                if(Integer.parseInt(productCnts[i]) <= 0)
                    throw new Exception("0이하의 값 입력 불가");
                OrderDetailDto odDto = new OrderDetailDto(
                        orDto.getOrderNo(),
                        Integer.parseInt(productNos[i]),
                        Integer.parseInt(productCnts[i]),
                        "주문완료",
                        productOptions[i],
                        "일반배송",
                        pp,
                        dp
                );
                orderDetailList.add(odDto);
            }
        }else{
            throw new NullPointerException();
        }
        model.addAttribute("orderDto", orDto);
        model.addAttribute("orderDetailList", orderDetailList);

        return "order";

    }

    /**
     * 장바구니 번호로 장바구니 선택삭제
     * 전체삭제를 위해 리스트로 받아서 하나씩 삭제
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
    public String addCar(HttpServletRequest request) throws Exception{
        int productNumber = Integer.parseInt(request.getParameter("productNo"));
        int productCnt = Integer.parseInt(request.getParameter("productCnt"));
        String productOption = request.getParameter("productOption");

        // 세션에서 사용자 이메일을 가져옴 (예: 로그인된 사용자)
        String customerEmail = (String) request.getSession().getAttribute("c_email");
        if (customerEmail == null) {
            throw new Exception("로그인이 필요합니다.");
        }

        // CartDto 객체를 생성하고 장바구니에 추가
        CartDto cartDto = new CartDto(productNumber, productCnt, productOption, customerEmail);
        cartService.addCart(cartDto);
        return "redirect:/cart/cart";
    }

}
