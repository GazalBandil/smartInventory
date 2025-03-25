package com.smartInventory.backend.service;

import com.smartInventory.backend.dtos.CategoryDTO;
import com.smartInventory.backend.model.Category;
import com.smartInventory.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryrepository;

    // get api called logic
    public List<Category> getAllCategory(){
         return categoryrepository.findAll();

    }
    //post api called logic
    public Category createCategory(CategoryDTO categoryDTO) {
        String lowercaseName = categoryDTO.getName().toLowerCase();
        if (categoryrepository.existsByName(lowercaseName)) {
            throw new RuntimeException("Category already exists!");
        }
        Category category = new Category();
        category.setName(lowercaseName);
        return categoryrepository.save(category);
    }

    //put api called logic
    public  Category updateCategory(Long Id , CategoryDTO categoryDTO){
        Optional<Category> existingCategory = categoryrepository.findById(Id);
        if (existingCategory.isEmpty()) {
            throw new RuntimeException("Category not found!");
        }
        Category category = existingCategory.get();
        category.setName(categoryDTO.getName().toLowerCase());
        return categoryrepository.save(category);
    }

    //delete category api
    public void removeCategory(Long id) {
        if (!categoryrepository.existsById(id)) {
            throw new RuntimeException("Category not found!");
        }
        categoryrepository.deleteById(id);
    }




}
