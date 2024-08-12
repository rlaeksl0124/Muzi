package com.Toy2.productcategory;

import com.Toy2.productcategory.categoryclosure.db.dto.ProductCategoryClosureDto;
import com.Toy2.productcategory.db.dao.ProductCategoryClosureDao;
import com.Toy2.productcategory.db.dao.ProductCategoryDao;
import com.Toy2.productcategory.db.dto.ProductCategoryDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class ProductCategoryClosureTest {
    @Autowired
    private ProductCategoryClosureDao productCategoryClosureDao;

    @Autowired
    private ProductCategoryDao categoryDao;

    @Test
    public void closureCountTest() {
        int count = productCategoryClosureDao.count();
        assertThat(count).isGreaterThanOrEqualTo(0);
        System.out.println("count = " + count);
    }

    @Test
    @Transactional
    //삽입 테스트
    public void closureInsertTest() {
        categoryDao.insert("카테고리" + Integer.MAX_VALUE);
        ProductCategoryDto categoryByName = categoryDao.findCategoryByCategoryName("카테고리" + Integer.MAX_VALUE);

        ProductCategoryClosureDto productCategoryClosureDto = new ProductCategoryClosureDto(categoryByName.getCategoryNumber()
                , categoryByName.getCategoryNumber(), 0);
        boolean count = productCategoryClosureDao.insertClosureTable(productCategoryClosureDto);
        assertThat(count).isTrue();
    }

    @Test
    @Transactional
    //조회 테스트
    public void closureSelectTest() {
        categoryDao.insert("카테고리" + Integer.MAX_VALUE);
        ProductCategoryDto categoryByName = categoryDao.findCategoryByCategoryName("카테고리" + Integer.MAX_VALUE);
        productCategoryClosureDao.insertClosureTable(new ProductCategoryClosureDto(categoryByName.getCategoryNumber(), categoryByName.getCategoryNumber(), 0));

        ProductCategoryClosureDto select = productCategoryClosureDao.findByCategoryNumber(categoryByName.getCategoryNumber());

        System.out.println("select = " + select);
        assertThat(select.getCategoryChild()).isEqualTo(categoryByName.getCategoryNumber());
    }

    @Test
    @Transactional
    public void closureDeleteTest() {
        categoryDao.insert("카테고리" + Integer.MAX_VALUE);
        ProductCategoryDto categoryByName = categoryDao.findCategoryByCategoryName("카테고리" + Integer.MAX_VALUE);
        productCategoryClosureDao.insertClosureTable(new ProductCategoryClosureDto(categoryByName.getCategoryNumber(), categoryByName.getCategoryNumber(), 1));

        boolean delete = productCategoryClosureDao.delete(categoryByName.getCategoryNumber());
        System.out.println("delete = " + delete);
        assertThat(delete).isTrue();
    }

    @Test
    public void findDirectChildren() {
        List<ProductCategoryDto> allCategories = categoryDao.findAllCategory();

        Map<Integer, List<ProductCategoryDto>> collect = allCategories.stream()
                .collect(Collectors.toMap(
                        ProductCategoryDto::getCategoryNumber,
                        productCategoryDto -> productCategoryClosureDao.findDirectChildren(productCategoryDto)
                ));//가져온 카테고리들의 각 번호를 키로 정렬하고 그 키로 가져온 자식 카테고리를 값으로 넣는다
        System.out.println("map = " + collect);
    }
}
