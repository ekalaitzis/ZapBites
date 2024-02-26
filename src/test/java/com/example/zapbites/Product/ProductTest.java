package com.example.zapbites.Product;

import com.example.zapbites.Category.Category;
import com.example.zapbites.Ingredient.Ingredient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ProductTest {

    @Test
    public void testProductEntity() {
        // Given
        Category category = new Category();
        category.setId(1L); // Assuming category ID 1 exists

        List<Ingredient> ingredients = new ArrayList<>();
        // Add some ingredients to the list if needed

        Product product = new Product(1L, "Test Product", "Test Description", category, ingredients);
        product.setPrice(BigDecimal.valueOf(10.99)); // Set a sample price

        // When - No action needed as this is just a simple entity test

        // Then
        assertNotNull(product.getId());
        assertEquals(1L, product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals("Test Description", product.getDescription());
        assertEquals(BigDecimal.valueOf(10.99), product.getPrice());
        assertEquals(category, product.getCategory());
        assertEquals(ingredients, product.getIngredients());
    }
}
