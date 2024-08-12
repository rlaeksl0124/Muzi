package com.Toy2.productcategory.db.dto;


public class ProductCategoryDto {

    private final int categoryNumber;
    private final String categoryName;

    public ProductCategoryDto(int categoryNumber, String categoryName) {
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