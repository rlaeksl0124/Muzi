package com.Toy2.productcategory.db.dao;

import com.Toy2.productcategory.db.dto.ProductCategoryDto;
import com.Toy2.productcategory.db.dto.request.ProductCategoryUpdateRequestDto;

import java.util.List;

public interface ProductCategoryDao {
    int count();
    boolean insert(String categoryName);

    boolean update(ProductCategoryUpdateRequestDto categoryUpdateRequestDto);

    ProductCategoryDto findCategoryByCategoryName(String categoryName);

    ProductCategoryDto findCategoryByParentId(int parentId);

    ProductCategoryDto selectLastCategoryOrderByDesc();

    List<ProductCategoryDto> findAllCategory();

    boolean deleteCategory(ProductCategoryDto productCategoryDto);
}
