package com.smartInventory.backend.service;

import com.smartInventory.backend.dtos.SupplierDTO;
import com.smartInventory.backend.model.Supplier;
import com.smartInventory.backend.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierrepository;

    //Create Supplier
    public Supplier createSupplier(SupplierDTO supplierDTO) {
        Supplier supplier = new Supplier();
        supplier.setName(supplierDTO.getName().toLowerCase());
        supplier.setAddress(supplierDTO.getAddress().toLowerCase());
        supplier.setContact_info(supplierDTO.getContact_info());

        return supplierrepository.save(supplier);

    }

    //Get all Supplier
    public List<Supplier> allSupplier(){
        return supplierrepository.findAll();
    }

    //Get Supplier by name
    public Optional<Supplier> getSupplierByName(String name){
        return supplierrepository.findByName(name);
    }

    //  Update Supplier
    public Supplier updateSupplier(Long id, SupplierDTO supplierDTO) {
        Supplier supplier = supplierrepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        supplier.setName(supplierDTO.getName());
        supplier.setContact_info(supplierDTO.getContact_info());
        supplier.setAddress(supplierDTO.getAddress());

        return supplierrepository.save(supplier);
    }

    //Delete Supplier
    public void deleteSupplier(Long id) {
        supplierrepository.deleteById(id);
    }
}
