package com.Toy2.product.controller;

import com.Toy2.product.db.dto.ProductDto;
import com.Toy2.product.db.dto.ResultResponseDto;
import com.Toy2.product.db.dto.request.ProductPageRequestDto;
import com.Toy2.product.domain.service.ProductService;
import com.Toy2.product.productdetail.db.dto.ProductPictureDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("product/")
    public ModelAndView productHome(ModelAndView modelAndView) {
            modelAndView.setViewName("product");
            return modelAndView;
    }

    @GetMapping("product/list")
    @ResponseBody
    public ResultResponseDto<List<ProductDto>> getProductList(@ModelAttribute ProductPageRequestDto pageRequestDto) {
        List<ProductDto> productDtos = productService.selectProductPage(pageRequestDto);
        return new ResultResponseDto<>(productDtos, productDtos != null);
    }

    @GetMapping("product/detail")
    public ModelAndView getProductDetail(@RequestParam int productNumber, ModelAndView modelAndView) {
        Map<Integer, List<ProductPictureDto>> map = productService.selectPictures(productNumber);
        ProductDto productDto = productService.selectProduct(productNumber);
        modelAndView.setViewName("product-detail");
        modelAndView.addObject("attribute", map);
        modelAndView.addObject("product", productDto);
        return modelAndView;
    }

    @GetMapping("product/pages")
    @ResponseBody
    public ResultResponseDto<List<Integer>> getPages(int limit) {
        List<Integer> pages = productService.countPages(limit);
        return new ResultResponseDto<>(pages, pages != null);
    }
}
