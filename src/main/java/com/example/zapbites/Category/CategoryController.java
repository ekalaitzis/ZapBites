package com.example.zapbites.Category;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
@Validated
public class  CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('BUSINESS')") //this is not secured properly as it is not going to be used in production
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@businessOwnerEvaluator.checkForOwnerByCategoryId(#id)")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Optional<Category> optionalCategory = categoryService.getCategoryById(id);
        return optionalCategory.map(category -> new ResponseEntity<>(category, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createCategory(@Valid @RequestBody Category category) {
            Category createdCategory = categoryService.createCategory(category);
            return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@businessOwnerEvaluator.checkForOwnerByCategory(#category)")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category) {
            Category updatedCategory = categoryService.updateCategory(category);
            return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@businessOwnerEvaluator.checkForOwnerByCategoryId(#id)")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
