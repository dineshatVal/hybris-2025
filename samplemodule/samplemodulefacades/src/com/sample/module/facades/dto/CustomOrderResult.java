package com.sample.module.facades.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomOrderResult {

    @JsonProperty("orderNum")
    private String orderNum;
    @JsonProperty("productCodes")
    private String productCodes;


    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }


    public void setProductCodes(String productCodes) {
        this.productCodes = productCodes;
    }


    public String getOrderNum() {
        return orderNum;
    }

    public String getProductCodes() {
        return productCodes;
    }

}
