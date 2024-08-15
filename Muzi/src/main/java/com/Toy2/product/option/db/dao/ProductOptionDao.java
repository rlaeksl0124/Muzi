package com.Toy2.product.option.db.dao;

import com.Toy2.product.option.db.dto.ProductOptionDto;

import java.util.List;

public interface ProductOptionDao {
    int count();
    List<ProductOptionDto> selectOptions(int productNumber);
}
