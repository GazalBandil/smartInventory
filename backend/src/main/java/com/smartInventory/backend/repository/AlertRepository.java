package com.smartInventory.backend.repository;

import com.smartInventory.backend.model.Alert;
import com.smartInventory.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByResolvedFalse(); // Get all active alerts
    List<Alert> findByProduct(Product product);
}