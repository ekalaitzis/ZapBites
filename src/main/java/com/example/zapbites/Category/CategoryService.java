package com.example.zapbites.Category;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
        return categoryRepository.save(category);
    }

    public Category updateCategory(Category category) {
        try {
            return categoryRepository.save(category);
        } catch (IllegalArgumentException e) {
            throw new EntityNotFoundException("Category with id " + category.getId() + " not found.");
        }
    }

    public void deleteCategoryById(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Category with id " + id + " not found");
        }
    }


}
