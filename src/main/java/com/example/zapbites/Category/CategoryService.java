package com.example.zapbites.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getAllCategories();

    Optional<Category> getCategoryById(Long id);

    Category createCategory(Category category);

    Category updateCategory(Category updatedCategory);

    void deleteCategoryById(Long id);
}
