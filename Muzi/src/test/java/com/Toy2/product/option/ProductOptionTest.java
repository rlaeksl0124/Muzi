package com.Toy2.product.option;

import com.Toy2.product.option.db.dao.ProductOptionDao;
import com.Toy2.product.option.db.dto.ProductOptionDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class ProductOptionTest {

    @Autowired
    private ProductOptionDao productOption;

    @Test
    public void optionCountTest() {
        int count = productOption.count();
        System.out.println("count = " + count);
    }

    @Test
    public void selectOptionsTest() {
        List<ProductOptionDto> productOptionDtos = productOption.selectOptions(1);
        System.out.println(productOptionDtos);
    }
}
