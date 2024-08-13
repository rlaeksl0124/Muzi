package com.Toy2.productcategory.db.dto.request;

public class CategoryInsertRequestDto {
    private final String categoryName;
    private final int parentCategoryNumber;
    private final int depth;

    public CategoryInsertRequestDto(String categoryName, int parentCategoryNumber, int depth) {
        this.categoryName = categoryName;
        this.parentCategoryNumber = parentCategoryNumber;
        this.depth = depth;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getParentCategoryNumber() {
        return parentCategoryNumber;
    }

    public int getDepth() {
        return depth;
    }
}

