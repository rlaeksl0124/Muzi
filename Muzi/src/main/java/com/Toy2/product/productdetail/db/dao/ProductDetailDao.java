package com.Toy2.product.productdetail.db.dao;

import com.Toy2.product.productdetail.db.dto.ProductPictureDto;

import java.util.List;

public interface ProductDetailDao {

    int count();

    //제품 사진
    List<ProductPictureDto> selectProductPicture(int productNumber);

    //제품 설명 사진
    List<ProductPictureDto> selectProductDetailPicture(int productNumber);
}
