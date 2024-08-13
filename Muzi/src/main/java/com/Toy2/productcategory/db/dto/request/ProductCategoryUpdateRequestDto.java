package com.Toy2.productcategory.db.dto.request;

public class ProductCategoryUpdateRequestDto {

    private final int categoryNumber;
    private final String categoryName;

    public ProductCategoryUpdateRequestDto(int categoryNumber, String categoryName) {
        this.categoryNumber = categoryNumber;
        this.categoryName = categoryName;
    }

    public int getCategoryNumber() {
        return categoryNumber;
    }

    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public String toString() {
        return "ProductCategoryDto{" +
                "categoryNumber=" + categoryNumber +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }

}
