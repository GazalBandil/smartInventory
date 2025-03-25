package com.smartInventory.backend.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
    @NotBlank(message = "name is required")
    private String name;
}
