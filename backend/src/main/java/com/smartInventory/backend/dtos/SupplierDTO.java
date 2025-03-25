package com.smartInventory.backend.dtos;

import lombok.*;

@Getter
@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDTO {
    private String name;
    private String contact_info;
    private String address;
}
