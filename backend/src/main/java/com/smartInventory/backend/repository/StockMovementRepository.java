package com.smartInventory.backend.repository;

import com.smartInventory.backend.model.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    List<StockMovement> findByItemId(Long itemId);
        List<StockMovement> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

}
