package com.Toy2.product.option.db.dto.request;

import java.util.List;

public class OptionRequestDto {
    private final int productNumber;
    private final String optionName;
    private final List<String> optionDetails;
    private final boolean status;

    public OptionRequestDto(int productNumber, String optionName, List<String> optionDetails, boolean status) {
        this.productNumber = productNumber;
        this.optionName = optionName;
        this.optionDetails = optionDetails;
        this.status = status;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public String getOptionName() {
        return optionName;
    }

    public List<String> getOptionDetails() {
        return optionDetails;
    }

    public boolean isStatus() {
        return status;
    }
}
