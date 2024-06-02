package com.example.zapbites.Category;

import com.example.zapbites.Category.Exceptions.CategoryNotFoundException;
import com.example.zapbites.Category.Exceptions.DuplicateCategoryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category1, category2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category1 = new Category();
        category1.setId(1L);
        category1.setName("Category 1");

        category2 = new Category();
        category2.setId(2L);
        category2.setName("Category 2");
    }

    @Test
    void getAllCategories() {
        List<Category> categoryList = Arrays.asList(category1, category2);
        when(categoryRepository.findAll()).thenReturn(categoryList);

        List<Category> result = categoryService.getAllCategories();

        assertEquals(categoryList, result);
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void getCategoryById() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));

        Optional<Category> result = categoryService.getCategoryById(1L);

        assertTrue(result.isPresent());
        assertEquals(category1, result.get());
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void createCategory() {
        when(categoryRepository.findByName(category1.getName())).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenReturn(category1);

        Category result = categoryService.createCategory(category1);

        assertNotNull(result);
        assertEquals(category1, result);
        verify(categoryRepository, times(1)).findByName(category1.getName());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void createCategory_DuplicateException() {
        when(categoryRepository.findByName(category1.getName())).thenReturn(Optional.of(category1));

        assertThrows(DuplicateCategoryException.class, () -> categoryService.createCategory(category1));
        verify(categoryRepository, times(1)).findByName(category1.getName());
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void updateCategory() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));
        when(categoryRepository.save(any(Category.class))).thenReturn(category1);

        Category result = categoryService.updateCategory(category1);

        assertNotNull(result);
        assertEquals(category1, result);
        verify(categoryRepository, times(1)).findAll();
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void updateCategory_CategoryNotFoundException() {
        Category nonExistingCategory = new Category();
        nonExistingCategory.setId(3L);
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        assertThrows(CategoryNotFoundException.class, () -> categoryService.updateCategory(nonExistingCategory));
        verify(categoryRepository, times(1)).findAll();
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void deleteCategoryById() {
        categoryService.deleteCategoryById(1L);
        verify(categoryRepository, times(1)).deleteById(1L);
    }
}