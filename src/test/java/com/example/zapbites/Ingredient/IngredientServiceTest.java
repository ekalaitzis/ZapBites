package com.example.zapbites.Ingredient;

import com.example.zapbites.Ingredient.Exceptions.DuplicateIngredientException;
import com.example.zapbites.Ingredient.Exceptions.IngredientNotFoundException;
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
public class IngredientServiceTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientService ingredientService;

    @Test
    void getAllIngredients_ShouldReturnListOfIngredients() {
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(new Ingredient());
        ingredientList.add(new Ingredient());
        when(ingredientRepository.findAll()).thenReturn(ingredientList);

        List<Ingredient> result = ingredientService.getAllIngredients();

        assertEquals(2, result.size());
    }

    @Test
    void getIngredientById_WithValidId_ShouldReturnIngredient() {
        Long ingredientId = 1L;
        Ingredient ingredient = new Ingredient();
        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));

        Optional<Ingredient> result = ingredientService.getIngredientById(ingredientId);

        assertTrue(result.isPresent());
        assertEquals(ingredient, result.get());
    }

    @Test
    void getIngredientById_WithInvalidId_ShouldReturnEmptyOptional() {
        Long ingredientId = 1L;
        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.empty());

        Optional<Ingredient> result = ingredientService.getIngredientById(ingredientId);

        assertTrue(result.isEmpty());
    }

    @Test
    void createIngredient_WithValidIngredient_ShouldReturnCreatedIngredient() {
        Ingredient ingredient = new Ingredient();
        when(ingredientRepository.findById(any())).thenReturn(Optional.empty());
        when(ingredientRepository.save(ingredient)).thenReturn(ingredient);

        Ingredient result = ingredientService.createIngredient(ingredient);

        assertEquals(ingredient, result);
    }

    @Test
    void createIngredient_WithDuplicateId_ShouldThrowDuplicateIngredientException() {
        Ingredient ingredient = new Ingredient();
        when(ingredientRepository.findById(any())).thenReturn(Optional.of(new Ingredient()));

        assertThrows(DuplicateIngredientException.class, () -> ingredientService.createIngredient(ingredient));
    }

    @Test
    void updateIngredient_WithValidIngredient_ShouldReturnUpdatedIngredient() {
        Ingredient updatedIngredient = new Ingredient();
        updatedIngredient.setId(1L);
        // Mocking getAllIngredients() to return a list containing the updated ingredient
        when(ingredientService.getAllIngredients()).thenReturn(Collections.singletonList(updatedIngredient));
        when(ingredientRepository.save(updatedIngredient)).thenReturn(updatedIngredient);

        Ingredient result = ingredientService.updateIngredient(updatedIngredient);

        assertEquals(updatedIngredient, result);
    }

    @Test
    void updateIngredient_WithNonExistingId_ShouldThrowIngredientNotFoundException() {
        Ingredient updatedIngredient = new Ingredient();
        // Mocking getAllIngredients() to return an empty list, simulating that the ingredient does not exist
        when(ingredientService.getAllIngredients()).thenReturn(Collections.emptyList());

        assertThrows(IngredientNotFoundException.class, () -> ingredientService.updateIngredient(updatedIngredient));
    }

    @Test
    void deleteIngredient_WithValidId_ShouldDeleteIngredient() {
        Long ingredientId = 1L;

        ingredientService.deleteIngredient(ingredientId);

        verify(ingredientRepository, times(1)).deleteById(ingredientId);
    }

    @Test
    void deleteIngredient_WithNonExistingId_ShouldThrowIngredientNotFoundException() {
        Long ingredientId = 1L;
        doThrow(new EmptyResultDataAccessException(1)).when(ingredientRepository).deleteById(ingredientId);

        assertThrows(IngredientNotFoundException.class, () -> ingredientService.deleteIngredient(ingredientId));
    }
}
