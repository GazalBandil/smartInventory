package com.smartInventory.backend.controller;

import com.smartInventory.backend.dtos.ProductDTO;
import com.smartInventory.backend.dtos.ProductExpiryAlert;
import com.smartInventory.backend.model.Product;
import com.smartInventory.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // @Autowired
    // private ProductRepository productRepository;


    @PostMapping("/add-item")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO) {
        Product product = productService.createProduct(productDTO);
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
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productRequest) {
        Product updatedProduct = productService.updateProduct(id, productRequest);
        return ResponseEntity.ok(updatedProduct);
    }

    // ✅ DELETE Product by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully.");
    }

//
//    //low stock api
//    @GetMapping("/low-stock")
//    public List<ProductDTO> getLowStockProducts() {
//        return productservice.getLowStockProducts();
//    }
//

    // API to get expiry alerts
    @GetMapping("/expiry")
    public List<ProductExpiryAlert> getExpiryAlerts() {
        return productService.getExpiryAlerts();
    }
  






}
