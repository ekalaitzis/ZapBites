package com.example.zapbites.Category;

import com.example.zapbites.Menu.Menu;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CategoryTest {
    @Test
    public void testCategoryEntity() {
        // Given
        Menu menu = new Menu();
        menu.setId(1L); // Assuming menu ID 1 exists
        Category category = new Category(1L, "Test Category", menu);

        // When - No action needed as this is just a simple entity test

        // Then
        assertNotNull(category.getId());
        assertEquals(1L, category.getId());
        assertEquals("Test Category", category.getName());
        assertEquals(menu, category.getMenu());
    }

}
