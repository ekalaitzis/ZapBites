package com.example.zapbites.Category;

import com.example.zapbites.Category.Exceptions.CategoryNotFoundException;
import com.example.zapbites.Menu.Menu;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private CategoryService categoryService;
    @InjectMocks
    private CategoryController categoryController;
    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    @DisplayName("Should return a list of all categories")
    void getAllCategories_ShouldReturnListOfCategories() throws Exception {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "Category 1", null));
        categories.add(new Category(2L, "Category 2", null));
        when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Category 1"))
                .andExpect(jsonPath("$[1].name").value("Category 2"));

        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    @DisplayName("Should return a category when a valid id is given")
    void getCategoryById_WithValidId_ShouldReturnCategory() throws Exception {
        Long categoryId = 1L;
        Category category = new Category(categoryId, "Category 1", null);
        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.of(category));

        mockMvc.perform(get("/category/{id}", categoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(categoryId))
                .andExpect(jsonPath("$.name").value("Category 1"));

        verify(categoryService, times(1)).getCategoryById(categoryId);
    }

    @Test
    @DisplayName("Should return not found when an invalid id is given")
    void getCategoryById_WithInvalidId_ShouldReturnNotFound() throws Exception {
        Long categoryId = 99L;
        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/category/{id}", categoryId))
                .andExpect(status().isNotFound());

        verify(categoryService, times(1)).getCategoryById(categoryId);
    }

    @Test
    @DisplayName("Should create a category when valid category attributes are given")
    void createCategory_WithValidCategory_ShouldReturnCreatedCategory() throws Exception {
        Category category = new Category(1L, "Category 1", null);
        when(categoryService.createCategory(any(Category.class))).thenReturn(category);

        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Category 1"));

        verify(categoryService, times(1)).createCategory(any(Category.class));
    }

    @Test
    @DisplayName("Should update a category when valid updated attributes are given")
    void updateCategory_WithValidCategory_ShouldReturnUpdatedCategory() throws Exception {
        Long categoryId = 1L;
        Category updatedCategory = new Category(categoryId, "Updated Category", new Menu());
        when(categoryService.updateCategory(any(Category.class))).thenReturn(updatedCategory);

        mockMvc.perform(put("/category/{id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCategory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(categoryId))
                .andExpect(jsonPath("$.name").value("Updated Category"));

        verify(categoryService, times(1)).updateCategory(any(Category.class));
    }

    @Test
    @DisplayName("Should delete a category when a valid id is given")
    void deleteCategoryById_WithValidId_ShouldReturnNoContent() throws Exception {
        Long categoryId = 1L;

        mockMvc.perform(delete("/category/{id}", categoryId))
                .andExpect(status().isNoContent());

        verify(categoryService, times(1)).deleteCategoryById(categoryId);
    }

//    @Test
//    @DisplayName("Should return not found when trying to delete a non-existing category")
//    void deleteCategoryById_WithNonExistingId_ShouldReturnNotFound() throws Exception {
//        Long categoryId = 99L;
//        doThrow(new CategoryNotFoundException("")).when(categoryService).deleteCategoryById(categoryId);
//
//        mockMvc.perform(delete("/category/{id}", categoryId))
//                .andExpect(status().isNotFound());
//    }
}
