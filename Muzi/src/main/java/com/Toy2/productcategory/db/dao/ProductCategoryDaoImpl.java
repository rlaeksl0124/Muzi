package com.Toy2.productcategory.db.dao;

import com.Toy2.productcategory.db.dto.ProductCategoryDto;
import com.Toy2.productcategory.db.dto.request.ProductCategoryUpdateRequestDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductCategoryDaoImpl implements ProductCategoryDao {
    private static final String nameSpace = "ProductCategoryMapper.";

    @Autowired
    private SqlSession sqlSession;

    @Override
    public int count() {
        int count = sqlSession.selectOne(nameSpace + "countProductCategory");
        return count;
    }

    @Override
    public boolean insert(String categoryName) {
        int insert = sqlSession.insert(nameSpace + "insertProductCategory", categoryName);
        return insert != 0;
    }

    @Override
    public boolean update(ProductCategoryUpdateRequestDto categoryUpdateRequestDto) {
        int update = sqlSession.update(nameSpace + "updateProductCategoryName", categoryUpdateRequestDto);
        return update != 0;
    }

    @Override
    public ProductCategoryDto findCategoryByCategoryName(String categoryName) {
        return sqlSession.selectOne(nameSpace + "findCategoryByCategoryName", categoryName);
    }

    @Override
    public ProductCategoryDto findCategoryByParentId(int parentId) {
        return sqlSession.selectOne(nameSpace + "findCategoryByParentId", parentId);
    }

    @Override
    public ProductCategoryDto selectLastCategoryOrderByDesc() {
        return sqlSession.selectOne(nameSpace + "selectLastCategoryOrderByDesc");
    }

    @Override
    public List<ProductCategoryDto> findAllCategory() {
        return sqlSession.selectList(nameSpace + "findAllCategory");
    }

    @Override
    public boolean deleteCategory(ProductCategoryDto productCategoryDto) {
        return sqlSession.delete(nameSpace + "deleteCategory", productCategoryDto) != 0;
    }
}
