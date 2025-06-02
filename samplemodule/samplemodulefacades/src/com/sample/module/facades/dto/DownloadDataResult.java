package com.sample.module.facades.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DownloadDataResult {

    @JsonProperty("orderNum")
    private String orderNum;
    @JsonProperty("productCode")
    private String productCode;
    @JsonProperty("remainingDownloads")
    private int remainingDownloads;


    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public void setRemainingDownloads(int remainingDownloads) {
        this.remainingDownloads = remainingDownloads;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public String getProductCode() {
        return productCode;
    }

    public int getRemainingDownloads() {
        return remainingDownloads;
    }
}
