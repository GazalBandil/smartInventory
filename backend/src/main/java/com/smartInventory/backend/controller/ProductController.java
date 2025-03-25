package com.smartInventory.backend.controller;

import com.smartInventory.backend.dtos.ProductDTO;
import com.smartInventory.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productservice;

  // create product
  @PostMapping("/add-item")
  @PreAuthorize("hasRole('ADMIN')")
  public ProductDTO addProduct(@RequestBody ProductDTO productDTO) {
      return productservice.addProduct(productDTO);
  }

  //get all the product
  @GetMapping("/get-item")
    public List<ProductDTO> getAllProducts() {
        return productservice.getAllProducts();
  }

  // update product
  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
      return productservice.updateProduct(id, productDTO);
  }

   //Delete the product
  @DeleteMapping("/{id}")
  public String deleteProduct(@PathVariable Long id) {
      productservice.deleteProduct(id);
      return "Product is deleted";
  }
    // update product consumption
    @PutMapping("/{id}/consume")
    public void consumeProduct(@PathVariable Long id, @RequestParam int quantity) {
        productservice.consumeProduct(id, quantity);
    }


    //low stock api
    @GetMapping("/low-stock")
    public List<ProductDTO> getLowStockProducts() {
        return productservice.getLowStockProducts();
    }

    //ecpiring product api alert
    @GetMapping("/expiring")
    public List<ProductDTO> getExpiringProducts() {
        return productservice.getExpiringProducts();
    }

  






}
