package com.Toy2.product.productdetail.db.dao;

import com.Toy2.product.productdetail.db.dto.ProductPictureDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDetailDaoImpl implements ProductDetailDao{

    private static final String nameSpace = "ProductMapper.";

    @Autowired
    SqlSession sqlSession;


    @Override
    public int count() {
        return 0;
    }

    @Override
    public List<ProductPictureDto> selectProductPicture(int productNumber) {
        return sqlSession.selectList(nameSpace + "selectProductImage", productNumber);
    }

    @Override
    public List<ProductPictureDto> selectProductDetailPicture(int productNumber) {
        return sqlSession.selectList(nameSpace + "selectProductDetailImage", productNumber);
    }
}
