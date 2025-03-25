package com.smartInventory.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smartInventory.backend.model.Supplier;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository< Supplier, Long> {
    Optional<Supplier> findByName(String name);
}
