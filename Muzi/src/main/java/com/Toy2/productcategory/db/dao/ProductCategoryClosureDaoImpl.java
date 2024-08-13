package com.Toy2.productcategory.db.dao;

import com.Toy2.productcategory.categoryclosure.db.dto.ProductCategoryClosureDto;
import com.Toy2.productcategory.categoryclosure.db.dto.request.CategoryClosureInsertRequestDto;
import com.Toy2.productcategory.db.dto.ProductCategoryDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductCategoryClosureDaoImpl implements ProductCategoryClosureDao{

    private static final String nameSpace = "ProductCategoryClosureMapper.";

    @Autowired
    private SqlSession sqlSession;

    @Override
    public int count() {
        return sqlSession.selectOne(nameSpace + "countCategoryClosure");
    }

    @Override
    public boolean insertClosureTable(ProductCategoryClosureDto productCategoryClosureDto) {
        return sqlSession.update(nameSpace + "insertCategoryClosure", productCategoryClosureDto) != 0;
    }

    /**
     *
     * @param generateClosure
     * @return 파라미터로 받은 리스트의 길이 == 변경된 행 수
     */
    @Override
    public boolean insertClosureTables(List<CategoryClosureInsertRequestDto> generateClosure) {
        return sqlSession.insert(nameSpace + "insertCategoryClosures", generateClosure) == generateClosure.size();
    }

    @Override
    public ProductCategoryClosureDto findByCategoryNumber(int categoryNumber) {
        return sqlSession.selectOne(nameSpace + "selectCategoryClosure", categoryNumber);
    }

    @Override
    public boolean delete(int categoryNumber) {
        int delete = sqlSession.delete(nameSpace + "deleteCategoryClosure", categoryNumber);
        return delete != 0;
    }

    @Override
    public List<ProductCategoryClosureDto> findByParentCategory(int parentCategory) {
        return sqlSession.selectList(nameSpace + "findByParentsCategoryNumber", parentCategory);
    }

    @Override
    public List<ProductCategoryDto> findDirectChildren(ProductCategoryDto category) {
        return sqlSession.selectList(nameSpace + "findDirectChildren", category);
    }
}
