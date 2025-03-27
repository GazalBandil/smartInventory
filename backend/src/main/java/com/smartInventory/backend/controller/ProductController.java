package com.smartInventory.backend.controller;

import com.smartInventory.backend.dtos.ProductDTO;
import com.smartInventory.backend.dtos.ProductExpiryAlert;
import com.smartInventory.backend.model.Product;
import com.smartInventory.backend.repository.ProductRepository;
import com.smartInventory.backend.service.ProductService;
import com.smartInventory.backend.service.UserActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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


    @PostMapping("/add-item")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO , @RequestParam String username) {
        Product product = productService.createProduct(productDTO);
        userActivityLogService.logActivity(username, "ADD_PRODUCT", "Added new product: " + product.getName());
        return ResponseEntity.ok(product);
    }

    // ✅ GET Product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    // ✅ GET All Products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // ✅ UPDATE Product by ID (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productRequest,  @RequestParam String username) {
        Product updatedProduct = productService.updateProduct(id, productRequest);
        userActivityLogService.logActivity(username, "UPDATE_PRODUCT", "Updated product: " + updatedProduct.getName());
        return ResponseEntity.ok(updatedProduct);
    }

    // ✅ DELETE Product by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id,  @RequestParam String username) {
        productService.deleteProduct(id);
        userActivityLogService.logActivity(username, "DELETE_PRODUCT", "Deleted product with ID: " + id);
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

        return ResponseEntity.ok("Product quantity updated successfully.");


    }


}
