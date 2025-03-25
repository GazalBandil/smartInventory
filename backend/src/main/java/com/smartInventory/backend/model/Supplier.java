package com.smartInventory.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "supplier")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplier_id;

    @NotBlank(message = "message is required")
    private String name;

    @NotBlank(message = "contactInfo is required")
    private String contact_info;

    private String address;


}
