package com.example.zapbites.Category;

import com.example.zapbites.Category.Exceptions.CategoryNotFoundException;
import com.example.zapbites.Category.Exceptions.DuplicateCategoryException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category createCategory(Category category) {
        String name = category.getName();
        Optional<Category> existingCategory = categoryRepository.findByName(name);

        if (existingCategory.isPresent()) {
            throw new DuplicateCategoryException("Category:" + name + " already exists.");
        }
        return categoryRepository.save(category);
    }

    public Category updateCategory(Category updatedCategory) {
        Long categoryId = updatedCategory.getId();
        List<Category> allCategories = getAllCategories();

        if (allCategories.stream().anyMatch(c -> c.getId().equals(updatedCategory.getId()))) {
            return categoryRepository.save(updatedCategory);
        } else {
            throw new CategoryNotFoundException("Category doesn't exist.");
        }
    }

    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
}
