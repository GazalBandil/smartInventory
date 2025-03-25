package com.smartInventory.backend.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table (name = "stockMovement")
public class StockMovement {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long movementId;
    private Long itemId;
    private Long userId;
    private int quantityChanged;
    private String movementType;  // "ADDED", "UPDATED", "DELETED", "CONSUMED"
    private LocalDateTime timestamp;

    public StockMovement(Long itemId, Long userId, int quantityChanged, String movementType) {
        this.itemId = itemId;
        this.userId = userId;
        this.quantityChanged = quantityChanged;
        this.movementType = movementType;
        this.timestamp = LocalDateTime.now();
    }

    public Long getMovementId() {
        return movementId;
    }
    public void setMovementId(Long movementId) {
        this.movementId = movementId;
    }
    public Long getItemId() {
        return itemId;
    }
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public int getQuantityChanged() {
        return quantityChanged;
    }
    public void setQuantityChanged(int quantityChanged) {
        this.quantityChanged = quantityChanged;
    }
    public String getMovementType() {
        return movementType;
    }
    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
