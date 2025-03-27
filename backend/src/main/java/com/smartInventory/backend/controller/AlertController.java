package com.smartInventory.backend.controller;

import com.smartInventory.backend.dtos.LowStockAlertDTO;
import com.smartInventory.backend.model.Product;
import com.smartInventory.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {
    @Autowired
    private ProductRepository productRepository;

    private static final int LOW_STOCK_THRESHOLD = 10;

    @GetMapping("/low-stock")
    public ResponseEntity<List<LowStockAlertDTO>> getLowStockAlerts() {
        List<Product> lowStockProducts = productRepository.findByQuantityLessThan(LOW_STOCK_THRESHOLD);

        List<LowStockAlertDTO> alerts = lowStockProducts.stream()
                .map(product -> new LowStockAlertDTO(
                        product.getName(),
                        product.getQuantity(),
                        "Product " + product.getName() + " is low on stock. Only " + product.getQuantity() + " units left!"
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(alerts);
    }
}
