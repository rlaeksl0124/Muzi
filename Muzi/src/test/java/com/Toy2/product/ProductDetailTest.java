package com.Toy2.product;

import com.Toy2.product.productdetail.db.dao.ProductDetailDao;
import com.Toy2.product.productdetail.db.dto.ProductPictureDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class ProductDetailTest {

    @Autowired
    private ProductDetailDao productDetailDao;

    @Test
    public void selectTest() {
        List<ProductPictureDto> productPictureDtos = productDetailDao.selectProductPicture(1);
        System.out.println("productPictureDtos = " + productPictureDtos);
    }

    @Test
    public void selectDetailTest() {
        List<ProductPictureDto> productPictureDtos = productDetailDao.selectProductDetailPicture(1);
        System.out.println("productPictureDtos = " + productPictureDtos);
    }
}
