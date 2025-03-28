package com.smartInventory.backend.service;

import com.smartInventory.backend.model.StockMovement;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;

@Service
public class ReportGenerationService {

    // Generate CSV report for stock movements
    public void generateCsvReport(List<StockMovement> stockMovements, PrintWriter writer) {
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                .withHeader("Movement ID", "Item ID", "User ID", "Quantity Changed", "Movement Type", "Timestamp"))) {

            for (StockMovement movement : stockMovements) {
                csvPrinter.printRecord(
                        movement.getMovementId(),
                        movement.getItemId(),
                        movement.getUserId(),
                        movement.getQuantityChanged(),
                        movement.getMovementType(),
                        movement.getTimestamp()
                );
            }

            csvPrinter.flush();
        } catch (Exception e) {
            throw new RuntimeException("Error while writing CSV report: " + e.getMessage());
        }
    }
}

