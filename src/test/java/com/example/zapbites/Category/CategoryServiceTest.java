package com.example.zapbites.Category;

import com.example.zapbites.Category.Exceptions.CategoryNotFoundException;
import com.example.zapbites.Menu.Menu;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void getAllCategories_ShouldReturnListOfCategories() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category());
        categoryList.add(new Category());
        when(categoryRepository.findAll()).thenReturn(categoryList);

        List<Category> result = categoryService.getAllCategories();

        assertEquals(2, result.size());
    }

    @Test
    void getCategoryById_WithValidId_ShouldReturnCategory() {
        Long categoryId = 1L;
        Category category = new Category();
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        Optional<Category> result = categoryService.getCategoryById(categoryId);

        assertTrue(result.isPresent());
        assertEquals(category, result.get());
    }

    @Test
    void getCategoryById_WithInvalidId_ShouldReturnEmptyOptional() {
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        Optional<Category> result = categoryService.getCategoryById(categoryId);

        assertTrue(result.isEmpty());
    }

    @Test
    void createCategory_WithValidCategory_ShouldReturnCreatedCategory() {
        Category category = new Category();
        when(categoryRepository.save(category)).thenReturn(category);

        Category result = categoryService.createCategory(category);

        assertEquals(category, result);
    }

    @Test
    void updateCategory_WithValidCategory_ShouldReturnUpdatedCategory() {
        Category updatedCategory = new Category();
        updatedCategory.setId(1L);
        updatedCategory.setMenu(new Menu());
        updatedCategory.setName("name");
        // Mocking getAllCategories() to return a list containing the updated category
        when(categoryService.getAllCategories()).thenReturn(Collections.singletonList(updatedCategory));
        when(categoryRepository.save(updatedCategory)).thenReturn(updatedCategory);

        Category result = categoryService.updateCategory(updatedCategory);

        assertEquals(updatedCategory, result);
    }

    @Test
    void updateCategory_WithNonExistingId_ShouldThrowCategoryNotFoundException() {
        Category updatedCategory = new Category();
        // Mocking getAllCategories() to return an empty list, simulating that the category does not exist
        when(categoryService.getAllCategories()).thenReturn(Collections.emptyList());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.updateCategory(updatedCategory));
    }


    @Test
    void deleteCategoryById_WithValidId_ShouldDeleteCategory() {
        Long categoryId = 1L;

        categoryService.deleteCategoryById(categoryId);

        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    void deleteCategoryById_WithNonExistingId_ShouldThrowCategoryNotFoundException() {
        Long categoryId = 1L;
        doThrow(new EmptyResultDataAccessException(1)).when(categoryRepository).deleteById(categoryId);

        assertThrows(CategoryNotFoundException.class, () -> categoryService.deleteCategoryById(categoryId));
    }
}
