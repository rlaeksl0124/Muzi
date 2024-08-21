package com.Toy2.product.option;

import com.Toy2.product.option.db.dao.ProductOptionDao;
import com.Toy2.product.option.db.dto.ProductOptionDto;
import com.Toy2.product.option.db.dto.request.OptionRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class ProductOptionTest {

    @Autowired
    private ProductOptionDao productOptionDao;

    @Test
    public void optionCountTest() {
        int count = productOptionDao.count();
        System.out.println("count = " + count);
    }

    @Test
    public void selectOptionsTest() {
        List<ProductOptionDto> productOptionDtos = productOptionDao.selectOptions(1);
        System.out.println(productOptionDtos);
    }

    @Test
    @Transactional
    public void insertOptionTest() {
        boolean insert = productOptionDao.insert(new OptionRequestDto(1, "옵션1", List.of("옵션 내용1", "옵션 내용2", "옵션 내용3"), true));
        System.out.println("insert = " + insert);
    }
}
