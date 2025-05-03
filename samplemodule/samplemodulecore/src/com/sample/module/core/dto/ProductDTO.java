package com.sample.module.core.dto;

public class ProductDTO {
    private String code;
    private String name;
    private String description;
    private boolean expressDeliveryEligibility;


    // --- Getters and Setters ---

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isExpressDeliveryEligibility() {
        return expressDeliveryEligibility;
    }

    public void setExpressDeliveryEligibility(boolean expressDeliveryEligibility) {
        this.expressDeliveryEligibility = expressDeliveryEligibility;
    }

}


