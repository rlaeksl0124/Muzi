package com.Toy2.product.service;

import com.Toy2.product.db.dto.ProductDto;
import com.Toy2.product.db.dto.request.ProductUpdateRequestDto;
import com.Toy2.product.domain.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
@Transactional
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Test
    public void productServiceSelectTest() {
        ProductDto productDto = productService.selectProduct(1);
        System.out.println("productDto = " + productDto);
        assertThat(productDto.getProductNumber()).isGreaterThan(0);
    }

    @Test
    public void productServiceSelectPageTest() {
        List<ProductDto> productDtos = productService.selectProductPage(10, 1);
        System.out.println("productDtos = " + productDtos);
    }

    @Test
    public void productServiceInsertTest() {
        ProductDto build = new ProductDto.Builder()
                .productNumber(Integer.MAX_VALUE)
                .productName("테스트 상품")
                .productPrice(10000)
                .postingStatus(true)
                .amount(100)
                .discountable(true)
                .newItem(true)
                .viewCount(0)
                .productCode("")
                .notice("없음").build();
        boolean b = productService.insertProduct(build);
        assertThat(b).isTrue();
    }

    @Test
    public void productServiceUpdateTest() {
        productService.insertProduct(new ProductDto.Builder()
                .productNumber(Integer.MAX_VALUE)
                .productName("테스트 상품")
                .productPrice(10000)
                .postingStatus(true)
                .amount(100)
                .discountable(true)
                .newItem(true)
                .viewCount(0)
                .productCode("")
                .notice("없음").build());

        ProductUpdateRequestDto build = new ProductUpdateRequestDto.Builder()
                .productNumber(Integer.MAX_VALUE)
                .productPrice(999999)
                .productName("테스트 수정 상품")
                .newItem(false)
                .discountable(true)
                .amount(10000)
                .notice("없음")
                .productCode("qqqqqq")
                .build();

        boolean update = productService.updateProduct(build);
        ProductDto productDto = productService.selectProduct(Integer.MAX_VALUE);
        assertThat(productDto.getProductName()).isEqualTo("테스트 수정 상품");
        assertThat(update).isTrue();
    }

    @Test
    @Transactional
    public void productServiceManyInsertTest() {
        List<ProductDto> productDtos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ProductDto build = new ProductDto.Builder()
                    .productNumber(i + 10000)
                    .productName("테스트 상품")
                    .productPrice(10000)
                    .postingStatus(true)
                    .amount(100)
                    .discountable(true)
                    .newItem(true)
                    .viewCount(0)
                    .productCode("")
                    .notice("없음").build();
            productDtos.add(build);
        }
        productService.insertProduct(productDtos);
        for (int i = 0; i < 10; i++) {
            ProductDto productDto = productService.selectProduct(10000 + i);
            assertThat(productDto).isNotNull();
        }
    }

    @Test
    public void productServiceDeleteTest() {
        boolean delete = productService.deleteService(1);
        assertThat(delete).isTrue();
    }
}
