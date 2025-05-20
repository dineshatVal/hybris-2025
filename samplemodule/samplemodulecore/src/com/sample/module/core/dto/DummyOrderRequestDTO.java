package com.sample.module.core.dto;

import java.util.List;

public class DummyOrderRequestDTO {
    private String userId;
    private List<ProductEntry> productEntries;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ProductEntry> getProductEntries() {
        return productEntries;
    }

    public void setProductEntries(List<ProductEntry> productEntries) {
        this.productEntries = productEntries;
    }

    public static class ProductEntry{
        private String productCode;
        private Long quantity;

        public ProductEntry() {
            // Default constructor
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public Long getQuantity() {
            return quantity;
        }

        public void setQuantity(Long quantity) {
            this.quantity = quantity;
        }
    }
}
