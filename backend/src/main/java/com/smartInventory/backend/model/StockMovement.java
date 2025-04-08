package com.smartInventory.backend.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;
// package com.smartInventory.backend.model;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor // Required by JPA
@AllArgsConstructor
@Table(name = "stock_movement") 
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movementId;
    private Long itemId;
    private String productName;
    private int quantityChanged;

    // @Enumerated(EnumType.STRING)
    private String movementType; // Changed to Enum

    private LocalDateTime timestamp;

    // Constructor for easy instantiation
    public StockMovement(Long itemId, String productName, int quantityChanged, String movementType) {
        this.itemId = itemId;
        this.productName = productName;
        this.quantityChanged = quantityChanged;
        this.movementType = movementType;
        this.timestamp = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }

    // Enum for movement types
    public enum MovementType {
        ADDED, UPDATED, DELETED, CONSUMED
    }
}
