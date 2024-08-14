package com.Toy2.productcategory.categoryclosure.db.dto;

public class ProductCategoryClosureDto {
    private int categoryParent;
    private int categoryChild;
    private int depth;

    public ProductCategoryClosureDto(int categoryParent, int categoryChild, int depth) {
        this.categoryParent = categoryParent;
        this.categoryChild = categoryChild;
        this.depth = depth;
    }

    public int getCategoryParent() {
        return categoryParent;
    }

    public int getCategoryChild() {
        return categoryChild;
    }

    public int getDepth() {
        return depth;
    }

    @Override
    public String toString() {
        return "ProductCategoryClosureDto{" +
                "categoryParent=" + categoryParent +
                ", categoryChild=" + categoryChild +
                ", depth=" + depth +
                '}';
    }
}
