package com.Toy2.product.db.dto;

public class ResultResponseDto<D> {
    private D data;
    private boolean isSuccess;

    public ResultResponseDto(D data, boolean isSuccess) {
        this.data = data;
        this.isSuccess = isSuccess;
    }

    public D getDto() {
        return data;
    }

    public boolean isSuccess() {
        return isSuccess;
    }


    @Override
    public String toString() {
        return "ResultResponseDto{" +
                "data=" + data +
                ", isSuccess=" + isSuccess +
                '}';
    }
}
