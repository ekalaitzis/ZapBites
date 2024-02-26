package com.example.zapbites.Menu;

import com.example.zapbites.Business.Business;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class MenuTest {

    @Test
    public void testMenuEntity() {
        // Given
        Business business = new Business();
        business.setId(1L); // Assuming business ID 1 exists
        Menu menu = new Menu(1L, "Test Menu", business);


        // Then
        assertNotNull(menu.getId());
        assertEquals(1L, menu.getId());
        assertEquals("Test Menu", menu.getName());
        assertEquals(business, menu.getBusiness());
    }

}
