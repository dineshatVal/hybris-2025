package com.sample.module.facades.dto;


public class ResponseDTO {
    private String status;
    private String message;
    private CustomOrderResult orderResult;
    private DownloadDataResult downloadDataResult;


    public ResponseDTO() {
        // Default constructor
    }

    public ResponseDTO(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CustomOrderResult getOrderResult() {
        return orderResult;
    }

    public void setOrderResult(CustomOrderResult orderResult) {
        this.orderResult = orderResult;
    }

    public DownloadDataResult getDownloadDataResult() {
        return downloadDataResult;
    }

    public void setDownloadDataResult(DownloadDataResult downloadDataResult) {
        this.downloadDataResult = downloadDataResult;
    }
}
