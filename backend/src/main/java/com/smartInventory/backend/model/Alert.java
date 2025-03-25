package com.smartInventory.backend.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private LocalDateTime createdAt;
    private boolean resolved; // If alert has been seen/handled

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
