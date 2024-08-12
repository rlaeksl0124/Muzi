package com.Toy2.productcategory.service;


import com.Toy2.productcategory.db.dao.ProductCategoryDao;
import com.Toy2.productcategory.db.dto.ProductCategoryDto;
import com.Toy2.productcategory.db.dto.request.CategoryInsertRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class ProductCategoryServiceTest {

    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    @Transactional
    public void addCategorytest() {
        productCategoryDao.insert("테스트 카테고리" + Integer.MAX_VALUE);
        ProductCategoryDto categoryByCategoryName = productCategoryDao.findCategoryByCategoryName("테스트 카테고리" + Integer.MAX_VALUE);

        boolean addCategory = productCategoryService.addCategory(
                new CategoryInsertRequestDto("서비스 카테고리", categoryByCategoryName.getCategoryNumber(), 1));
        assertThat(addCategory).isTrue();
    }

    @Test
    @Transactional
    public void deleteCategoryTest() {
        String uuid = UUID.randomUUID().toString().substring(0, 10);
        productCategoryService.addCategory(new CategoryInsertRequestDto("테스트 카테고리" + uuid, 0, 0));
        ProductCategoryDto categoryByCategoryName = productCategoryDao.findCategoryByCategoryName("테스트 카테고리" + uuid);


        boolean b = productCategoryService.deleteCategory(categoryByCategoryName);
        assertThat(b).isTrue();
        assertThat(productCategoryDao.findCategoryByParentId(categoryByCategoryName.getCategoryNumber()))
                .isNull();
    }
}
