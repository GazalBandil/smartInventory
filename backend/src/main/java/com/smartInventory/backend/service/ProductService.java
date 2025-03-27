package com.smartInventory.backend.service;


import com.smartInventory.backend.dtos.ProductDTO;

import com.smartInventory.backend.dtos.ProductExpiryAlert;

import com.smartInventory.backend.model.Category;
import com.smartInventory.backend.model.Product;
import com.smartInventory.backend.model.Supplier;
import com.smartInventory.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;


    @Transactional
    public Product createProduct(ProductDTO productRequest) {
        // Fetch category by ID
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + productRequest.getCategoryId()));

        // Fetch supplier by ID
        Supplier supplier = supplierRepository.findById(productRequest.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found with ID: " + productRequest.getSupplierId()));

        // Create new product entity
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setQuantity(productRequest.getQuantity());
        product.setPrice(productRequest.getPrice());
        product.setExpiryDate(productRequest.getExpiryDate());
        product.setCategory(category);
        product.setSupplier(supplier);
        product.setCreatedAt(LocalDate.now());

        // Save product to DB
        return productRepository.save(product);
    }

    // GET Product by ID
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
    }

    // GET All Products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // UPDATE Product by ID
    @Transactional
    public Product updateProduct(Long id, ProductDTO productRequest) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));

        // Fetch category and supplier if IDs are provided
        if (productRequest.getCategoryId() != null) {
            Category category = categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with ID: " + productRequest.getCategoryId()));
            existingProduct.setCategory(category);
        }

        if (productRequest.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(productRequest.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found with ID: " + productRequest.getSupplierId()));
            existingProduct.setSupplier(supplier);


        }

        // Update product details
        existingProduct.setName(productRequest.getName());
        existingProduct.setQuantity(productRequest.getQuantity());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setExpiryDate(productRequest.getExpiryDate());

        return productRepository.save(existingProduct);
    }

    //  DELETE Product by ID
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));

        productRepository.delete(product);
    }

    // Product expiry alert
    public List<ProductExpiryAlert> getExpiryAlerts() {
        LocalDate today = LocalDate.now();
        LocalDate alertThreshold = today.plusDays(15);

        List<Product> expiringProducts = productRepository.findByExpiryDateBetween(today, alertThreshold);

        return expiringProducts.stream()
                .map(product -> {
                    long daysLeft = ChronoUnit.DAYS.between(today, product.getExpiryDate());
                    String message = "This product will expire in " + daysLeft + " days.";
                    return new ProductExpiryAlert(product.getName(), product.getExpiryDate(), message);
                })
                .collect(Collectors.toList());
    }





}
