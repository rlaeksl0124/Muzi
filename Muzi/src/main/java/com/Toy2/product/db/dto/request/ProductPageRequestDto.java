package com.Toy2.product.db.dto.request;

public class ProductPageRequestDto {
    private final int page;

    private final int limit;

    public ProductPageRequestDto(int page, int limit) {
        this.page = limit * page;
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public int getLimit() {
        return limit;
    }

    @Override
    public String toString() {
        return "ProductPageRequestDto{" +
                "page=" + page +
                ", limit=" + limit +
                '}';
    }
}
