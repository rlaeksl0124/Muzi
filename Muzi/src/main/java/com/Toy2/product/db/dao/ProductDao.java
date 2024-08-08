package com.Toy2.product.db.dao;

import com.Toy2.product.db.dto.ProductDto;
import com.Toy2.product.db.dto.request.ProductUpdateRequestDto;

import java.util.List;
import java.util.Map;

public interface ProductDao {

    int count();

    boolean insert(ProductDto build);

    ProductDto select(int productNumber);
    List<ProductDto> selectPage(Map<String, Integer> limitAndOffset);

    boolean update(ProductUpdateRequestDto updateRequestDto);
    boolean delete(int productNumber);
    boolean disable(int productNumber);
}
