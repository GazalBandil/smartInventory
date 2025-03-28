package com.smartInventory.backend.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;
// package com.smartInventory.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    private Long userId;
    private int quantityChanged;

    // @Enumerated(EnumType.STRING)
    private String movementType; // Changed to Enum

    private LocalDateTime timestamp;

    // Constructor for easy instantiation
    public StockMovement(Long itemId, Long userId, int quantityChanged, String movementType) {
        this.itemId = itemId;
        this.userId = userId;
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
