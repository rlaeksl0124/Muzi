package com.Toy2.productcategory.categoryclosure.db.dto.request;

public class CategoryClosureInsertRequestDto {
    private final int categoryParent;
    private final int categoryChild;
    private final int depth;

    public CategoryClosureInsertRequestDto(int categoryParent, int categoryChild, int depth) {
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
        return "CategoryClosureInsertRequestDto{" +
                "categoryParent=" + categoryParent +
                ", categoryChild=" + categoryChild +
                ", depth=" + depth +
                '}';
    }
}
