package com.smartInventory.backend.controller;


import com.smartInventory.backend.dtos.SupplierDTO;
import com.smartInventory.backend.model.Supplier;
import com.smartInventory.backend.service.SupplierService;
import com.smartInventory.backend.service.UserActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/supplier")

public class SupplierController {

    @Autowired
    private SupplierService supplierservice;

    @Autowired
    private UserActivityLogService userActivityLogService;

    // create supplier
    @PostMapping("/add-supplier")
    public ResponseEntity<Supplier> addSupplier(@RequestBody SupplierDTO supplierDTO, @RequestParam String username){
         Supplier savedSupplier = supplierservice.createSupplier(supplierDTO);
        userActivityLogService.logActivity(username, "ADD_Supplier", "Added new Supplier: " + savedSupplier.getName());
        return ResponseEntity.ok(savedSupplier);

    }

    //Get all the supplier
    @GetMapping("/all-supplier")
    public ResponseEntity<List<Supplier>> getSupplier(){
        return ResponseEntity.ok(supplierservice.allSupplier());
    }

    //Get supplier by name
    @GetMapping("/name/{name}")  // Explicitly specify 'name'
    public ResponseEntity<Supplier> getByName(@PathVariable String name) {
        Optional<Supplier> supplier = supplierservice.getSupplierByName(name);
        return supplier.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //  Update Supplier
    @PutMapping("/update/{id}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable Long id, @RequestBody SupplierDTO supplierDTO) {
        return ResponseEntity.ok(supplierservice.updateSupplier(id, supplierDTO));
    }

    //  Delete Supplier
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSupplier(@PathVariable Long id) {
        supplierservice.deleteSupplier(id);
        return ResponseEntity.ok("Supplier deleted successfully!");
    }
}
