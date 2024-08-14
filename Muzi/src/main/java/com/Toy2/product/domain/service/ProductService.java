package com.Toy2.product.domain.service;

import com.Toy2.product.db.dao.ProductDao;
import com.Toy2.product.db.dto.ProductDto;
import com.Toy2.product.db.dto.request.ProductPageRequestDto;
import com.Toy2.product.db.dto.request.ProductUpdateRequestDto;
import com.Toy2.product.productdetail.db.dao.ProductDetailDao;
import com.Toy2.product.productdetail.db.dto.ProductPictureDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductDetailDao productDetailDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public ProductService() {
    }

    //같을 때 = count / limit
    //작을 때 = (count / limit) + 1
    //
    public List<Integer> countPages(int limit) {
        int count = productDao.count();

        if (count/limit * limit == count) {
            return IntStream.rangeClosed(0, (count/limit) - 1).boxed().collect(Collectors.toList());
        }
        return IntStream.rangeClosed(0, (count/limit)).boxed().collect(Collectors.toList());
    }


    /**
     * @param productDto
     * @return int
     * @throws IllegalArgumentException
     */
    public boolean insertProduct(ProductDto productDto) {
        return productDao.insert(productDto);
    }

    /**
     * @param productDtos
     * @apiNote 복수개 삽입
     * @TODO: 성공 하다가 중간에 실패하는 경우에 대해 생각해보기
     */
    public boolean insertProduct(List<ProductDto> productDtos) {
        int inserted = 0;
        for (int i = 0; i < productDtos.size(); i++) {
            productDao.insert(productDtos.get(i));
            inserted++;
        }
        return inserted != productDtos.size();
    }

    /**
     * @param productNumber
     * @return #{@link }ProductDto
     * @throws IllegalArgumentException 어떤 이유에서든 Dao가 null을 리턴 할 때
     */
    public ProductDto selectProduct(int productNumber) {
        ProductDto select = productDao.select(productNumber);
        if (select != null) {
            return select;
        }
        throw new IllegalArgumentException("오류가 발생 했습니다.");
    }

    /**
     * @return
     */
    public List<ProductDto> selectProductPage(ProductPageRequestDto pageRequestDto) {
        if (pageRequestDto.getPage() >= 100000) {
            throw new IllegalArgumentException();
        }

        List<ProductDto> productDtos = productDao.selectPage(pageRequestDto);
        if (productDtos.size() != 0) {
            return productDtos;
        }
        throw new IllegalArgumentException("오류가 발생 했습니다.");
    }

    /**
     * @param productUpdateRequestDto
     * @return boolean
     * @throws IllegalArgumentException
     */
    public boolean updateProduct(ProductUpdateRequestDto productUpdateRequestDto) {
        return productDao.update(productUpdateRequestDto);
    }

    /**
     * @param productNumber
     * @return boolean
     * @throws IllegalArgumentException
     */
    public boolean deleteService(int productNumber) {
        return productDao.delete(productNumber);
    }

    public Map<Integer, List<ProductPictureDto>> selectPictures(int productNumber) {
        List<ProductPictureDto> productPictures = productDetailDao.selectProductPicture(productNumber);
        List<ProductPictureDto> productDetailPicturePictures = productDetailDao.selectProductDetailPicture(productNumber);
        Map<Integer, List<ProductPictureDto>> map = new HashMap<>();
        map.put(0, productPictures);
        map.put(1, productDetailPicturePictures);
        return map;
    }
}
