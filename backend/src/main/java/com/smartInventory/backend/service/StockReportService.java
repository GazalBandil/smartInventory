package com.smartInventory.backend.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.stereotype.Service;

import com.smartInventory.backend.model.StockMovement;
import com.smartInventory.backend.repository.StockMovementRepository;

@Service
public class StockReportService {

   private final StockMovementRepository stockMovementRepository;

    public StockReportService(StockMovementRepository stockMovementRepository) {
        this.stockMovementRepository = stockMovementRepository;
    }

    // Record stock movement
    public StockMovement recordStockMovement(StockMovement movement) {
        return stockMovementRepository.save(movement);
    }

    // Get daily stock movement report
    public List<StockMovement> getDailyStockMovementReport() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.truncatedTo(ChronoUnit.DAYS);
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);

        return stockMovementRepository.findByTimestampBetween(startOfDay, endOfDay);
    }

    // Get weekly stock movement report
    public List<StockMovement> getWeeklyStockMovementReport() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.truncatedTo(ChronoUnit.DAYS).minusDays(now.getDayOfWeek().getValue() - 1);
        LocalDateTime endOfWeek = startOfWeek.plusDays(7).minusSeconds(1);

        return stockMovementRepository.findByTimestampBetween(startOfWeek, endOfWeek);
    }
}