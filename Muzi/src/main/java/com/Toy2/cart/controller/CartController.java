package com.Toy2.cart.controller;

import com.Toy2.cart.entity.CartDto;
import com.Toy2.cart.service.CartService;
import com.Toy2.order.entity.OrderDetailDto;
import com.Toy2.order.entity.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    private CartService cartService;
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    private static final String customerEmail = "admin@";

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
     * 장바구니에서 장바구니 수량과 옵션을 변경하여 DB에 저장
     * @param cartNo
     * @param cartDto
     * @return "redirect:/cart"
     * @throws Exception
     */
    @PostMapping("/modify")
    public String modifyCart(@RequestParam(name = "cartNo") Integer cartNo, CartDto cartDto) throws Exception {
        System.out.println(cartNo + cartDto.getCartProductCnt()+cartDto.getCartProductOption());
        cartService.modifyCart(cartNo, cartDto);
        return "redirect:/cart";
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
        OrderDto orDto = new OrderDto((String) session.getAttribute("c_email"));
        List<OrderDetailDto> orderDetailList = new ArrayList<>();
        //제품을 담을 dto가 없어서 이렇게 우선 작성 차후에 변경 예정
        String[] productPrice = request.getParameterValues("productPrice");
        String[] productDeliveryPrice = request.getParameterValues("productDeliveryPrice");
        String[] productNos = request.getParameterValues("productNo");
        String[] productCnts = request.getParameterValues("productCnt");
        String[] productOptions = request.getParameterValues("productOption");


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
        }
        model.addAttribute("orderDto", orDto);
        model.addAttribute("orderDetailList", orderDetailList);

        return "order";
    }

    /**
     * 장바구니 번호로 장바구니 한개 삭제
     * @param cartNo
     * @return
     * @throws Exception
     */
    @PostMapping("/remove")
    public String removeCart(@RequestParam(name = "cartNo") Integer cartNo) throws Exception {
        System.out.println(cartNo);
        cartService.removeCart(cartNo);
        return "redirect:/cart";
    }
}
