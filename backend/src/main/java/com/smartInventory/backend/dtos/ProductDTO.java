package com.smartInventory.backend.dtos;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private String name;
    private Integer quantity;
    private BigDecimal price;
    private LocalDateTime expiryDate;
    private String imageUrl;
    private String categoryName;
    private String supplierName;

    public ProductDTO(String name, int quantity, BigDecimal price, LocalDateTime expiryDate,
                      String imageUrl, String categoryName, String supplierName) {

        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.expiryDate = expiryDate;
        this.imageUrl = imageUrl;
        this.categoryName = categoryName;
        this.supplierName = supplierName;
    }



}
