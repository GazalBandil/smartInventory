package com.smartInventory.backend.controller;

import com.smartInventory.backend.dtos.CategoryDTO;
import com.smartInventory.backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.smartInventory.backend.model.Category;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryservice;
//fetch all category
    @GetMapping("/all-category")
    public ResponseEntity<List<Category>>getAllCategory(){
        return ResponseEntity.ok(categoryservice.getAllCategory());
    }
// to add new category
    @PostMapping("/add-category")
    public ResponseEntity<Category> addCategory(@RequestBody CategoryDTO categoryDTO) {
        Category savedCategory = categoryservice.createCategory(categoryDTO);
        return ResponseEntity.ok(savedCategory);
    }
// to update a category
    @PutMapping("/update-category/{Id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long Id , @RequestBody CategoryDTO categoryDTO){
        return ResponseEntity.ok(categoryservice.updateCategory(Id,categoryDTO));

    }
//to delete a category
    @DeleteMapping("/delete-category/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        categoryservice.removeCategory(id);
        return ResponseEntity.ok("Category deleted Successfully!!");
    }
}
