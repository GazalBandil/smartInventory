package com.smartInventory.backend.controller;

import com.smartInventory.backend.dtos.CategoryDTO;
import com.smartInventory.backend.service.CategoryService;
import com.smartInventory.backend.service.UserActivityLogService;
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
    @Autowired
    private UserActivityLogService userActivityLogService;



//fetch all category
    @GetMapping("/all-category")
    public ResponseEntity<List<Category>>getAllCategory(){
        return ResponseEntity.ok(categoryservice.getAllCategory());
    }

// to add new category
    @PostMapping("/add-category")
    public ResponseEntity<Category> addCategory(@RequestBody CategoryDTO categoryDTO , @RequestParam String username) {
        Category savedCategory = categoryservice.createCategory(categoryDTO);
        userActivityLogService.logActivity(username, "ADD_PRODUCT", "Added new Category: " + savedCategory.getName());
        return ResponseEntity.ok(savedCategory);
    }
// to update a category
    @PutMapping("/update-category/{Id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long Id , @RequestBody CategoryDTO categoryDTO ,  @RequestParam String username){
        Category updatedCategory = categoryservice.updateCategory(Id,categoryDTO);
        userActivityLogService.logActivity(username, "UPDATE_PRODUCT", "Updated the Category: " + updatedCategory.getName());
        return ResponseEntity.ok(updatedCategory);
    }
//to delete a category
    @DeleteMapping("/delete-category/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id ,  @RequestParam String username ){
        categoryservice.removeCategory(id);
        userActivityLogService.logActivity(username, "DELETE_PRODUCT", "Deleted category with ID: " + id );
        return ResponseEntity.ok("Category deleted Successfully!!");
    }
}
