package com.smartInventory.backend.dtos;

import java.time.LocalDate;

public class ProductExpiryAlert {
    private String productName;
    private LocalDate expiryDate;
    private String alertMessage;

    public ProductExpiryAlert(String productName, LocalDate expiryDate, String alertMessage) {
        this.productName = productName;
        this.expiryDate = expiryDate;
        this.alertMessage = alertMessage;
    }

    public String getProductName() {
        return productName;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public String getAlertMessage() {
        return alertMessage;
    }
}
