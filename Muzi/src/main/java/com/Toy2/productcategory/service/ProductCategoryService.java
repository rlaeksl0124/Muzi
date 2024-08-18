package com.Toy2.productcategory.service;

import com.Toy2.productcategory.categoryclosure.db.dto.ProductCategoryClosureDto;
import com.Toy2.productcategory.categoryclosure.db.dto.request.CategoryClosureInsertRequestDto;
import com.Toy2.productcategory.db.dao.ProductCategoryClosureDao;
import com.Toy2.productcategory.db.dao.ProductCategoryDao;
import com.Toy2.productcategory.db.dto.ProductCategoryDto;
import com.Toy2.productcategory.db.dto.request.CategoryInsertRequestDto;
import com.Toy2.productcategory.db.dto.request.ProductCategoryUpdateRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Autowired
    private ProductCategoryClosureDao productCategoryClosureDao;

    /**
     * @return category와 closure 삽입 성공 여부
     * @apiNote 삽입
     */
    @Transactional
    public boolean addCategory(CategoryInsertRequestDto categoryInsertRequestDto) {
        System.out.println("ProductCategoryService.addCategory");
        ProductCategoryDto productCategoryDto = insertCategory(categoryInsertRequestDto);
        if (productCategoryDto != null) {
            return insertClosure(categoryInsertRequestDto, productCategoryDto.getCategoryNumber());
        }
        return false;
    }


    @Transactional
    private ProductCategoryDto insertCategory(CategoryInsertRequestDto categoryInsertRequestDto) {
        System.out.println("ProductCategoryService.insertCategory");
        boolean insert = productCategoryDao.insert(categoryInsertRequestDto.getCategoryName());
        if (insert) {
            //방금 삽입한 카테고리 리턴
            return productCategoryDao.selectLastCategoryOrderByDesc();
        }
        throw new IllegalArgumentException("카테고리를 추가하는 과정에서 오류가 발생 했습니다.");
    }

    @Transactional
    private boolean insertClosure(CategoryInsertRequestDto categoryInsertRequestDto, int categoryNumber) {
        System.out.println("ProductCategoryService.insertClosure");
        int parentCategoryNumber = categoryInsertRequestDto.getParentCategoryNumber();
        //삽입 하려는 카테고리의 부모 카테고리를 자식으로 갖는 카테고리 모두 조회
        List<ProductCategoryClosureDto> parentCategories
                = productCategoryClosureDao.findByParentCategory(parentCategoryNumber);
        List<CategoryClosureInsertRequestDto> generateClosure = generateClosure(parentCategories, categoryNumber);
        return productCategoryClosureDao.insertClosureTables(generateClosure);
    }

    //클로저 테이블에 삽입 할 때 들어갈 것
    //조상 노드| 자기 자신 | 조상 노드의 깊이 + 1
    private List<CategoryClosureInsertRequestDto> generateClosure(List<ProductCategoryClosureDto> productCategoryClosures,
                                                                  int categoryNumber) {

        List<CategoryClosureInsertRequestDto> collect = productCategoryClosures
                .stream()
                .map(cl ->
                        new CategoryClosureInsertRequestDto(
                                cl.getCategoryParent(), categoryNumber, cl.getDepth())).collect(Collectors.toList());
        collect.add(new CategoryClosureInsertRequestDto(categoryNumber, categoryNumber, 0));
        return collect;
    }
    //부모 카테고리를 자식으로 갖는 노드 조회 -> 부모를 포함한 조상 노드 모두 조회됨
    //조상 노드 | 삽입 할 노드 |조상 노드의 깊이 + 1
    // -> 조회된 모든 카테고리에 대해 위 과정을 반복하고 자기 자신을 삽입
    //삽입 하려는 카테고리가 최상위인 경우 부모 카테고리는 0(없음)
    //0회 반복 후 자기 자신 삽입

    /**
     * 전체 카테고리의 트리구조를 리턴 <br>
     *
     * @return
     */
    public Map<Integer, List<ProductCategoryDto>> getCategories() {
        List<ProductCategoryDto> allCategories = productCategoryDao.findAllCategory();
        Map<Integer, List<ProductCategoryDto>> collect = allCategories.stream()
                .collect(Collectors.toMap(
                        ProductCategoryDto::getCategoryNumber,
                        productCategoryDto -> productCategoryClosureDao.findDirectChildren(productCategoryDto)
                ));
        collect.put(0, productCategoryClosureDao.findRoot());

        return collect;
    }
    /* expect
     * 0 : {1, 2, 3}
     * 1 : {4, 5}
     * 2 : {6}
     * 3 : {}
     * 4 : {7}
     * 5 : {}
     * 6 : {}
     * */
    // 모든 카테고리 번호 전체 조회
    // 모든 카테고리 번호에 대해 깊이가 1이면서 자기 자신을 부모로 갖는 closure 조회


    @Transactional
    public boolean deleteCategory(ProductCategoryDto productCategoryDto) {
        boolean deleteClosure = productCategoryClosureDao.delete(productCategoryDto.getCategoryNumber());//클로저 테이블이 먼저 삭제 되어야함
        boolean deleteCategory = productCategoryDao.deleteCategory(productCategoryDto);
        return deleteCategory && deleteClosure;
    }

    @Transactional
    public boolean updateCategoryName(ProductCategoryUpdateRequestDto productCategoryUpdateRequestDto) {
        return productCategoryDao.update(productCategoryUpdateRequestDto);
    }

}
