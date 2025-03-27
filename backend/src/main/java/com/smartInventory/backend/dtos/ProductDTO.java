package com.smartInventory.backend.dtos;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductDTO {
    private String name;
    private Integer quantity;
    private BigDecimal price;
    private LocalDateTime expiryDate;
    private Long categoryId;
    private Long supplierId;
}
