package com.example.zapbites.Ingredient;

import com.example.zapbites.Ingredient.Exceptions.DuplicateIngredientException;
import com.example.zapbites.Ingredient.Exceptions.IngredientNotFoundException;
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

public class IngredientServiceTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientServiceImpl ingredientService;

    private Ingredient ingredient1, ingredient2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        ingredient1.setName("Ingredient 1");

        ingredient2 = new Ingredient();
        ingredient2.setId(2L);
        ingredient2.setName("Ingredient 2");
    }

    @Test
    void getAllIngredients() {
        List<Ingredient> ingredientList = Arrays.asList(ingredient1, ingredient2);
        when(ingredientRepository.findAll()).thenReturn(ingredientList);

        List<Ingredient> result = ingredientService.getAllIngredients();

        assertEquals(ingredientList, result);
        verify(ingredientRepository, times(1)).findAll();
    }

    @Test
    void getIngredientById() {
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient1));

        Optional<Ingredient> result = ingredientService.getIngredientById(1L);

        assertTrue(result.isPresent());
        assertEquals(ingredient1, result.get());
        verify(ingredientRepository, times(1)).findById(1L);
    }

    @Test
    void createIngredient() {
        when(ingredientRepository.findByName(ingredient1.getName())).thenReturn(Optional.empty());
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(ingredient1);

        Ingredient result = ingredientService.createIngredient(ingredient1);

        assertNotNull(result);
        assertEquals(ingredient1, result);
        verify(ingredientRepository, times(1)).findByName(ingredient1.getName());
        verify(ingredientRepository, times(1)).save(any(Ingredient.class));
    }

    @Test
    void createIngredient_DuplicateException() {
        when(ingredientRepository.findByName(ingredient1.getName())).thenReturn(Optional.of(ingredient1));

        assertThrows(DuplicateIngredientException.class, () -> ingredientService.createIngredient(ingredient1));
        verify(ingredientRepository, times(1)).findByName(ingredient1.getName());
        verify(ingredientRepository, never()).save(any());
    }

    @Test
    void updateIngredient() {
        when(ingredientRepository.findAll()).thenReturn(Arrays.asList(ingredient1, ingredient2));
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(ingredient1);

        Ingredient result = ingredientService.updateIngredient(ingredient1);

        assertNotNull(result);
        assertEquals(ingredient1, result);
        verify(ingredientRepository, times(1)).findAll();
        verify(ingredientRepository, times(1)).save(any(Ingredient.class));
    }

    @Test
    void updateIngredient_IngredientNotFoundException() {
        Ingredient nonExistingIngredient = new Ingredient();
        nonExistingIngredient.setId(3L);
        when(ingredientRepository.findAll()).thenReturn(Arrays.asList(ingredient1, ingredient2));

        assertThrows(IngredientNotFoundException.class, () -> ingredientService.updateIngredient(nonExistingIngredient));
        verify(ingredientRepository, times(1)).findAll();
        verify(ingredientRepository, never()).save(any());
    }

    @Test
    void deleteIngredient() {
        ingredientService.deleteIngredient(1L);
        verify(ingredientRepository, times(1)).deleteById(1L);
    }
}