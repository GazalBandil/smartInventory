package com.smartInventory.backend.dtos;

public class LowStockAlertDTO {
    private String productName;
    private int quantity;
    private String message;

    public LowStockAlertDTO(String productName, int quantity, String message) {
        this.productName = productName;
        this.quantity = quantity;
        this.message = message;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
