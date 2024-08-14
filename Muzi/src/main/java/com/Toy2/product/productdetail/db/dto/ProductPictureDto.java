package com.Toy2.product.productdetail.db.dto;

public class ProductPictureDto {
    private final String productDetailTitle;
    private final String productDetailContent;
    private final int pictureId;
    private final int pictureVertical;
    private final int pictureHorizontal;
    private final String resolution;
    private final String pictureUrl;
    private final int productDetailNumber;
    private final int productNumber;

    public ProductPictureDto(String productDetailTitle, String productDetailContent,
                             int pictureId, int pictureVertical,
                             int pictureHorizontal, String resolution,
                             String pictureUrl, int productDetailNumber, int productNumber) {
        this.productDetailTitle = productDetailTitle;
        this.productDetailContent = productDetailContent;
        this.pictureId = pictureId;
        this.pictureVertical = pictureVertical;
        this.pictureHorizontal = pictureHorizontal;
        this.resolution = resolution;
        this.pictureUrl = pictureUrl;
        this.productDetailNumber = productDetailNumber;
        this.productNumber = productNumber;
    }

    public int getPictureId() {
        return pictureId;
    }

    public int getPictureVertical() {
        return pictureVertical;
    }

    public String getProductDetailTitle() {
        return productDetailTitle;
    }

    public String getProductDetailContent() {
        return productDetailContent;
    }

    public int getPictureHorizontal() {
        return pictureHorizontal;
    }

    public String getResolution() {
        return resolution;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public int getProductDetailNumber() {
        return productDetailNumber;
    }

    public int getProductNumber() {
        return productNumber;
    }

    @Override
    public String toString() {
        return "ProductPictureDto{" +
                "productDetailTitle='" + productDetailTitle + '\'' +
                ", productDetailContent='" + productDetailContent + '\'' +
                ", pictureId=" + pictureId +
                ", pictureVertical=" + pictureVertical +
                ", pictureHorizontal=" + pictureHorizontal +
                ", resolution='" + resolution + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", productDetailNumber=" + productDetailNumber +
                ", productNumber=" + productNumber +
                '}';
    }
}
