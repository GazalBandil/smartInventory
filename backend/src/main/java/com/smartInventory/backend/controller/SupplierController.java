package com.smartInventory.backend.controller;


import com.smartInventory.backend.dtos.SupplierDTO;
import com.smartInventory.backend.model.Supplier;
import com.smartInventory.backend.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/supplier")
//@PreAuthorize("hasRole('ADMIN')")
public class SupplierController {

    @Autowired
    private SupplierService supplierservice;

    // create supplier
    @PostMapping("/add-supplier")
    public ResponseEntity<Supplier> addSupplier(@RequestBody SupplierDTO supplierDTO){
         return ResponseEntity.ok(supplierservice.createSupplier(supplierDTO));

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
