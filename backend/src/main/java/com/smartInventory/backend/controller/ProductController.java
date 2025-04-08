package com.smartInventory.backend.controller;

import com.smartInventory.backend.dtos.ProductDTO;
import com.smartInventory.backend.dtos.ProductExpiryAlert;
import com.smartInventory.backend.model.Product;
import com.smartInventory.backend.model.StockMovement;
import com.smartInventory.backend.repository.ProductRepository;
import com.smartInventory.backend.service.ProductService;
import com.smartInventory.backend.service.StockReportService;
import com.smartInventory.backend.service.UserActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

     @Autowired
     private ProductRepository productRepository;

    @Autowired
    private UserActivityLogService userActivityLogService;
    @Autowired
    private StockReportService stockReportService;

    //new product add api
    @PostMapping("/add-item")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO , @RequestParam String username) {
        Product product = productService.createProduct(productDTO);
        userActivityLogService.logActivity(username, "ADD_PRODUCT", "Added new product: " + product.getName());
        // Record stock movement
        StockMovement movement = new StockMovement();
        movement.setItemId(product.getItemId());
        movement.setProductName(product.getName());
        movement.setQuantityChanged(product.getQuantity());
        movement.setMovementType("ADDED");
        movement.setTimestamp(LocalDateTime.now());
        stockReportService.recordStockMovement(movement);

        return ResponseEntity.ok(product);
    }

    // get product by name
    @GetMapping("/search/{name}")
    public ResponseEntity<List<Product>> searchProductsByName(@PathVariable String name) {
        List<Product> products = productService.getProductByName(name);
        return ResponseEntity.ok(products);
    }

    // GET All Products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // UPDATE Product by ID (PUT)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productRequest,  @RequestParam String username) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        int oldQuantity = existingProduct.getQuantity();

        Product updatedProduct = productService.updateProduct(id, productRequest);
        int newQuantity = updatedProduct.getQuantity();
        int quantityDiff = newQuantity - oldQuantity;
        userActivityLogService.logActivity(username, "UPDATE_PRODUCT", "Updated product: " + updatedProduct.getName());

        // Record stock movement
        if(quantityDiff!=0) {
            StockMovement movement = new StockMovement();
            movement.setItemId(updatedProduct.getItemId());
            movement.setProductName(updatedProduct.getName());
            movement.setQuantityChanged(quantityDiff); 
            movement.setMovementType("Restocked");
            movement.setTimestamp(LocalDateTime.now());
            stockReportService.recordStockMovement(movement);
        }
        return ResponseEntity.ok(updatedProduct);
    }

    //DELETE Product by ID
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id,  @RequestParam String username) {
        productService.deleteProduct(id);
        userActivityLogService.logActivity(username, "DELETE_PRODUCT", "Deleted product with ID: " + id );
        return ResponseEntity.ok("Product deleted successfully.");
    }

    // API to get expiry alerts
    @GetMapping("/expiry")
    public List<ProductExpiryAlert> getExpiryAlerts() {
        return productService.getExpiryAlerts();
    }

    @PostMapping("/consume/{id}/{quantity}")
    public ResponseEntity<String> consumeProduct(@PathVariable Long id , @PathVariable Integer quantity){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        if (product.getQuantity() < quantity) {
            return ResponseEntity.badRequest().body("Not enough stock available.");
        }
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);

        // Record stock movement
        StockMovement movement = new StockMovement();
        movement.setItemId(product.getItemId());
        movement.setProductName(product.getName()); 
        movement.setQuantityChanged(-quantity); 
        movement.setMovementType("CONSUME");
        movement.setTimestamp(LocalDateTime.now());

        stockReportService.recordStockMovement(movement);
        return ResponseEntity.ok("Product quantity updated successfully.");
    }



}
