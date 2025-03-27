package com.smartInventory.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_activity_log")
public class UserActivityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;   // Who performed the action
    private String action;     // Login, logout, added, updated, deleted, etc.
    private String description; // Detailed information
    private LocalDateTime timestamp; // When it happened

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }
}
