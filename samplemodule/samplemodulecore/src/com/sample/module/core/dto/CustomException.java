package com.sample.module.core.dto;

public class CustomException extends RuntimeException {
    private final String customMessage;

    public CustomException(String customMessage) {
        super(customMessage);
        this.customMessage = customMessage;
    }

    public String getCustomMessage() {
        return customMessage;
    }
}