package com.smartInventory.backend.service;

import com.smartInventory.backend.dtos.ProductDTO;
import com.smartInventory.backend.model.Alert;
import com.smartInventory.backend.model.Category;
import com.smartInventory.backend.model.Product;
import com.smartInventory.backend.model.Supplier;
import com.smartInventory.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {


    private static final int LOW_STOCK_THRESHOLD = 10; // Alert if stock is below 10
    private static final int EXPIRY_DAYS_THRESHOLD = 7; // Alert if expiry is within 7 days

    @Autowired
    private ProductRepository productrepository;

    @Autowired
    private CategoryRepository categoryrepository;

    @Autowired
    private SupplierRepository supplierrepository;

    @Autowired
    private StockMovementRepository stockmovementrepository;

    @Autowired
    private AlertRepository alertrepository;

    // Convert Product to ProductDTO
    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(

                product.getName(),
                product.getQuantity(),
                product.getPrice(),
                product.getExpiryDate(),
                product.getImageUrl(),
                product.getCategory() != null ? product.getCategory().getName() : "Unknown",
                product.getSupplier() != null ? product.getSupplier().getName() : "Unknown"
        );
    }

    // Convert all string fields to lowercase before saving
    private void convertToLowercase(ProductDTO productDTO) {
        if (productDTO.getName() != null) {
            productDTO.setName(productDTO.getName().toLowerCase());
        }
        if (productDTO.getCategoryName() != null) {
            productDTO.setCategoryName(productDTO.getCategoryName().toLowerCase());
        }
        if (productDTO.getSupplierName() != null) {
            productDTO.setSupplierName(productDTO.getSupplierName().toLowerCase());
        }
    }


    // add product api
    @Transactional
    public ProductDTO addProduct(ProductDTO productDTO) {
        convertToLowercase(productDTO);
        Category category = categoryrepository.findByName(productDTO.getCategoryName())
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(productDTO.getCategoryName());
                    return categoryrepository.save(newCategory);
                });

        Supplier supplier = supplierrepository.findByName(productDTO.getSupplierName())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setQuantity(productDTO.getQuantity());
        product.setPrice(productDTO.getPrice());
        product.setExpiryDate(productDTO.getExpiryDate());
        product.setImageUrl(productDTO.getImageUrl());
        product.setCategory(category);
        product.setSupplier(supplier);

        productrepository.save(product);
        return convertToDTO(product);
    }

    // Get all the product Get api
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productrepository.findAll();
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //Put api to update the product
    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        convertToLowercase(productDTO);
        Product product = productrepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Category category = categoryrepository.findByName(productDTO.getCategoryName())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Supplier supplier = supplierrepository.findByName(productDTO.getSupplierName())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        product.setName(productDTO.getName());
        product.setQuantity(productDTO.getQuantity());
        product.setPrice(productDTO.getPrice());
        product.setExpiryDate(productDTO.getExpiryDate());
        product.setImageUrl(productDTO.getImageUrl());
        product.setCategory(category);
        product.setSupplier(supplier);

        productrepository.save(product);
        return convertToDTO(product);
    }

    // Delete a product (Admin only)
    @Transactional
    public void deleteProduct(Long id) {
        if (!productrepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productrepository.deleteById(id);
    }

    @Transactional
    public void consumeProduct(Long productId, int quantity) {
        Product product = productrepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getQuantity() < quantity) {
            throw new RuntimeException("Not enough stock available");
        }

        product.setQuantity(product.getQuantity() - quantity);
        productrepository.save(product);
        checkForAlerts(product);
    }


    private void checkForAlerts(Product product) {
        if (product.getQuantity() <= LOW_STOCK_THRESHOLD) {
            createAlert(product, "Low Stock Alert: " + product.getName() + " is below threshold.");
        }

        if (product.getExpiryDate() != null && product.getExpiryDate().isBefore(LocalDateTime.now().plusDays(EXPIRY_DAYS_THRESHOLD))) {
            createAlert(product, "Expiry Alert: " + product.getName() + " is expiring soon.");
        }
    }

    private void createAlert(Product product, String message) {
        Alert alert = new Alert();
        alert.setMessage(message);
        alert.setCreatedAt(LocalDateTime.now());
        alert.setProduct(product);
        alert.setResolved(false);
        alertrepository.save(alert);
    }


    public List<ProductDTO> getLowStockProducts() {
        return productrepository.findAll()
                .stream()
                .filter(product -> product.getQuantity() <= LOW_STOCK_THRESHOLD)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getExpiringProducts() {
        return productrepository.findAll()
                .stream()
                .filter(product -> product.getExpiryDate() != null && product.getExpiryDate().isBefore(LocalDateTime.now().plusDays(EXPIRY_DAYS_THRESHOLD)))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }



}
