package com.Toy2.product.db.dto.request;

import com.Toy2.product.db.dto.ProductDto;
import com.Toy2.product.option.db.dto.ProductOptionDto;

import java.util.Map;

public class OrderRequestDto {
    private Map<String, ProductOptionDto> options;
    private Integer quantity;
    private Integer productNumber;

    public OrderRequestDto(Map<String, ProductOptionDto> options, Integer quantity, Integer productNumber) {
        this.options = options;
        this.quantity = quantity;
        this.productNumber = productNumber;
    }

    // Getters and Setters

    public Map<String, ProductOptionDto> getOptions() {
        return options;
    }


    public Integer getQuantity() {
        return quantity;
    }


    public Integer getProductNumber() {
        return productNumber;
    }


    @Override
    public String toString() {
        return "OrderRequestDto{" +
                "options=" + options +
                ", quantity=" + quantity +
                ", productNumber=" + productNumber +
                '}';
    }

}
