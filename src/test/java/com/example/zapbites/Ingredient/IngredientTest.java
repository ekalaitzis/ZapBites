package com.example.zapbites.Ingredient;

import com.example.zapbites.Product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class IngredientTest {

    @Test
    public void testIngredientEntity() {
        // Given
        List<Product> products = new ArrayList<>();
        Ingredient ingredient = new Ingredient(1L, "Test Ingredient", true, false, true, products);

        // When - No action needed as this is just a simple entity test

        // Then
        assertNotNull(ingredient.getId());
        assertEquals(1L, ingredient.getId());
        assertEquals("Test Ingredient", ingredient.getName());
        assertEquals(true, ingredient.isVegan());
        assertEquals(false, ingredient.isSpicy());
        assertEquals(true, ingredient.isGlutenFree());
        assertEquals(products, ingredient.getProducts());
    }
}
