package com.Toy2.product.controller;

import com.Toy2.product.db.dto.ProductDto;
import com.Toy2.product.db.dto.ResultResponseDto;
import com.Toy2.product.db.dto.request.ProductInsertRequestDto;
import com.Toy2.product.db.dto.request.ProductPageRequestDto;
import com.Toy2.product.domain.service.ProductService;
import com.Toy2.product.option.db.dto.ProductOptionDto;
import com.Toy2.product.productdetail.db.dto.ProductPictureDto;
import com.Toy2.productcategory.db.dto.ProductCategoryDto;
import com.Toy2.productcategory.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

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
        Map<String, List<ProductOptionDto>> options = productService.selectProductOption(productNumber);

        modelAndView.setViewName("product-detail");
        modelAndView.addObject("attribute", map);
        modelAndView.addObject("product", productDto);
        modelAndView.addObject("productOption", options);
        return modelAndView;
    }

    @GetMapping("product/pages")
    @ResponseBody
    public ResultResponseDto<List<Integer>> getPages(int limit) {
        List<Integer> pages = productService.countPages(limit);
        return new ResultResponseDto<>(pages, pages != null);
    }

    @GetMapping("product/categories")
    @ResponseBody
    public ResultResponseDto<Map<Integer, List<ProductCategoryDto>>> getCategories() {
        Map<Integer, List<ProductCategoryDto>> categories = productCategoryService.getCategories();
        return new ResultResponseDto<>(categories, categories != null);
    }


    @PostMapping(value = "/product/submit")
    public void test(@RequestParam Map<String, String> request) {
        System.out.println("ProductController.test");
        List<ProductOptionDto> option = request.keySet().stream().filter(k -> k.contains("option"))
                .map(request::get).mapToInt(Integer::parseInt).boxed().map(k -> productService.selectOption(k))
                .collect(Collectors.toList());
        System.out.println(option);
        System.out.println(request);
    }

    @GetMapping("product/add-product")
    public String addProductPage() {
        return "add-product";
    }

    @PostMapping("/product/add-product")
    @ResponseBody
    public boolean submitProduct(@RequestBody ProductInsertRequestDto productInsertRequestDto) {
        System.out.println("productDto = " + productInsertRequestDto);
        return productService.insertProduct(productInsertRequestDto);
    }

}
