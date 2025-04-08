package com.smartInventory.backend.controller;
import com.smartInventory.backend.model.StockMovement;
import com.smartInventory.backend.service.ReportGenerationService;
import com.smartInventory.backend.service.StockReportService;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/api/stock-movement")
public class StockMovementController {

    private final StockReportService stockMovementService;
    private final ReportGenerationService reportGenerationService;

    public StockMovementController(StockReportService stockMovementService, ReportGenerationService reportGenerationService) {
        this.stockMovementService = stockMovementService;
        this.reportGenerationService = reportGenerationService;
    }
    
    // Download daily stock movement report in CSV
    @GetMapping("/report/daily")
    public void downloadDailyStockReport(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=daily_stock_report.csv");
        List<StockMovement> dailyReport = stockMovementService.getDailyStockMovementReport();
        try (PrintWriter writer = response.getWriter()) {
            reportGenerationService.generateCsvReport(dailyReport, writer);
        }
    }
    // Download weekly stock movement report in CSV
    @GetMapping("/report/weekly")
    public void downloadWeeklyStockReport(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=weekly_stock_report.csv");

        List<StockMovement> weeklyReport = stockMovementService.getWeeklyStockMovementReport();
        try (PrintWriter writer = response.getWriter()) {
            reportGenerationService.generateCsvReport(weeklyReport, writer);
        }
    }
}
