package com.smartInventory.backend.repository;

import com.smartInventory.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    // expiry of product between dates
    List<Product> findByExpiryDateBetween(LocalDate startDate, LocalDate endDate);

    //low stock product with quantity threshold
    List<Product> findByQuantityLessThan(int threshold);

    List<Product> findByNameContainingIgnoreCase(String name);


}
