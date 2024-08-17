package com.Toy2.productcategory.db.dao;

import com.Toy2.productcategory.categoryclosure.db.dto.ProductCategoryClosureDto;
import com.Toy2.productcategory.categoryclosure.db.dto.request.CategoryClosureInsertRequestDto;
import com.Toy2.productcategory.db.dto.ProductCategoryDto;

import java.util.List;

public interface ProductCategoryClosureDao {
    int count();

    boolean insertClosureTable(ProductCategoryClosureDto productCategoryClosureDto);

    boolean insertClosureTables(List<CategoryClosureInsertRequestDto> generateClosure);

    ProductCategoryClosureDto findByCategoryNumber(int categoryNumber);

    boolean delete(int categoryNumber);

    List<ProductCategoryClosureDto> findByParentCategory(int parentCategory);

    List<ProductCategoryDto> findDirectChildren(ProductCategoryDto categories);
    List<ProductCategoryDto> findRoot();
}
