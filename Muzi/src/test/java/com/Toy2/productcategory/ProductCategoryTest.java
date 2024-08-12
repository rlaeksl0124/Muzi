package com.Toy2.productcategory;

import com.Toy2.productcategory.db.dao.ProductCategoryClosureDao;
import com.Toy2.productcategory.db.dao.ProductCategoryDao;
import com.Toy2.productcategory.db.dto.ProductCategoryDto;
import com.Toy2.productcategory.db.dto.request.ProductCategoryUpdateRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class ProductCategoryTest {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void productCategoryCountTest() {
        int count = productCategoryDao.count();
        assertThat(count).isGreaterThanOrEqualTo(0);
        System.out.println("count = " + count);
    }

    @Test
    public void productCategoryInsertTest() {
        boolean inserted = productCategoryDao.insert("새로운 카테고리");
        assertThat(inserted).isTrue();
    }

    @Test
    public void productCategoryUpdateTest() {
        boolean updated = productCategoryDao.update(new ProductCategoryUpdateRequestDto(5, "변경된 카테고리"));
        System.out.println("updated = " + updated);
    }

    @Test
    @Transactional
    public void findCategoryByCategoryNameTest() {
        boolean insert = productCategoryDao.insert("카테고리" + Integer.MAX_VALUE);
        assertThat(insert).isTrue();//테스트 데이터 삽입
        ProductCategoryDto categoryByCategoryName = productCategoryDao.findCategoryByCategoryName("카테고리" + Integer.MAX_VALUE);
        assertThat(categoryByCategoryName.getCategoryName()).isEqualTo("카테고리" + Integer.MAX_VALUE);
    }

    @Test
    public void findAllCategoryTest() {
        List<ProductCategoryDto> allCategory = productCategoryDao.findAllCategory();
        System.out.println("allCategory = " + allCategory);
    }
}
