package com.Toy2.product.option.db.dto;

public class ProductOptionDto {
    private final int optionNumber;
    private final int productNumber;
    private final String optionName;
    private final String optionDetail;
    private final boolean status;

    public ProductOptionDto(int optionNumber, int productNumber, String optionName, String optionDetail, boolean status) {
        this.optionNumber = optionNumber;
        this.productNumber = productNumber;
        this.optionName = optionName;
        this.optionDetail = optionDetail;
        this.status = status;
    }

    public int getOptionNumber() {
        return optionNumber;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public String getOptionName() {
        return optionName;
    }

    public String getOptionDetail() {
        return optionDetail;
    }

    public boolean isStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ProductOptionDto{" +
                "optionNumber=" + optionNumber +
                ", productNumber=" + productNumber +
                ", optionName='" + optionName + '\'' +
                ", optionDetail='" + optionDetail + '\'' +
                ", status=" + status +
                '}';
    }
}
