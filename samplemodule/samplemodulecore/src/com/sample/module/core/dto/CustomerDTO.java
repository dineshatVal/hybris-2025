package com.sample.module.core.dto;

public class CustomerDTO {
    private String email;
    private String customerID;
    private boolean expressDelivery;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public boolean isExpressDelivery() {
        return expressDelivery;
    }

    public void setExpressDelivery(boolean expressDelivery) {
        this.expressDelivery = expressDelivery;
    }
}
