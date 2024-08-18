package com.Toy2.product.option.db.dao;

import com.Toy2.product.option.db.dto.ProductOptionDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductOptionDaoImpl implements ProductOptionDao {
    private static final String nameSpace = "ProductOptionMapper.";

    @Autowired
    private SqlSession sqlSession;


    @Override
    public int count() {
        return sqlSession.selectOne(nameSpace + "countOption");
    }

    @Override
    public List<ProductOptionDto> selectOptions(int productNumber) {
        return sqlSession.selectList(nameSpace + "selectOptions", productNumber);
    }

    @Override
    public ProductOptionDto selectOption(int optionNumber) {
        return sqlSession.selectOne(nameSpace + "selectOption", optionNumber);
    }

    @Override
    public int insert(ProductOptionDto productOptionDto) {
        return sqlSession.insert(nameSpace + "insertProductOption", productOptionDto);
    }
}
