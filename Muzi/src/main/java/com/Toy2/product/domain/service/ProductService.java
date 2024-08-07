package com.Toy2.product.domain.service;

import com.Toy2.product.db.dao.ProductDao;
import com.Toy2.product.db.dto.ProductDto;
import com.Toy2.product.db.dto.request.ProductUpdateRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public ProductService() {
    }


    /**
     * @param productDto
     * @return int
     *@throws IllegalArgumentException
     */
    public boolean insertProduct(ProductDto productDto) {
        return productDao.insert(productDto);
    }

    /**
     * @param productNumber
     * @return #{@link }ProductDto
     *@throws IllegalArgumentException 어떤 이유에서든 Dao가 null을 리턴 할 때
     * */
    public ProductDto selectProduct(int productNumber) {
        ProductDto select = productDao.select(productNumber);
        if (select != null) {
            return select;
        }
        throw new IllegalArgumentException("오류가 발생 했습니다.");
    }

    /**
     * @param productUpdateRequestDto
     * @return boolean
     *@throws IllegalArgumentException
     */

    public boolean updateProduct(ProductUpdateRequestDto productUpdateRequestDto) {
        return productDao.update(productUpdateRequestDto);
    }

    /**
     * @param productNumber
     * @return boolean
     *@throws IllegalArgumentException
     */

    public boolean deleteService(int productNumber) {
        return productDao.delete(productNumber);
    }
}
