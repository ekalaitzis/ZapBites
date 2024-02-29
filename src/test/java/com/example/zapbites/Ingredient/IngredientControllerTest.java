package com.example.zapbites.Ingredient;

import com.example.zapbites.Ingredient.Exceptions.IngredientNotFoundException;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class IngredientControllerTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private IngredientService ingredientService;

    @InjectMocks
    private IngredientController ingredientController;

    private MockMvc mockMvc;

    private Ingredient testIngredient;

    @BeforeEach
    void init() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
        testIngredient = new Ingredient(1L, "Test Ingredient", true, false, true, new ArrayList<>());
    }

    @Test
    @DisplayName("Should return a list of all ingredients")
    public void getAllIngredientsShouldReturnListOfIngredients() throws Exception {
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(new Ingredient(1L, "Test Ingredient 1", true, false, true, new ArrayList<>()));
        ingredientList.add(new Ingredient(2L, "Test Ingredient 2", false, true, false, new ArrayList<>()));
        when(ingredientService.getAllIngredients()).thenReturn(ingredientList);

        mockMvc.perform(get("/ingredient"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
        verify(ingredientService, times(1)).getAllIngredients();
    }

    @Test
    @DisplayName("Should return an ingredient when a valid id is given")
    void getIngredientByIdWithValidIdShouldReturnIngredient() throws Exception {
        when(ingredientService.getIngredientById(testIngredient.getId())).thenReturn(Optional.of(testIngredient));

        mockMvc.perform(get("/ingredient/{id}", testIngredient.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testIngredient.getId()));
        verify(ingredientService, times(1)).getIngredientById(testIngredient.getId());
    }

    @Test
    @DisplayName("Should return not found when an invalid id is given")
    void getIngredientByIdWithInvalidIdShouldReturnNotFound() throws Exception {
        Long ingredientId = 99L;
        when(ingredientService.getIngredientById(ingredientId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/ingredient/{id}", ingredientId))
                .andExpect(status().isNotFound());
        verify(ingredientService, times(1)).getIngredientById(ingredientId);
    }

    @Test
    @DisplayName("Should create an ingredient when valid ingredient attributes are given")
    void createIngredientWithValidAttributesShouldReturnCreatedIngredient() throws Exception {
        when(ingredientService.createIngredient(testIngredient)).thenReturn(testIngredient);

        mockMvc.perform(post("/ingredient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testIngredient)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(testIngredient.getId()));
        verify(ingredientService, times(1)).createIngredient(testIngredient);
    }

    @Test
    @DisplayName("Should return an updated ingredient when valid updated attributes are given")
    void updateIngredientWithValidAttributesShouldReturnUpdatedIngredient() throws Exception {
        Ingredient updatedIngredient = new Ingredient(1L, "Updated Ingredient", true, false, false, new ArrayList<>());

        when(ingredientService.updateIngredient(any(Ingredient.class))).thenReturn(updatedIngredient);

        mockMvc.perform(put("/ingredient/{id}", updatedIngredient.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedIngredient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updatedIngredient.getName()));
        verify(ingredientService, times(1)).updateIngredient(updatedIngredient);
    }

    @Test
    @DisplayName("Should return not found when trying to update a non-existing ingredient")
    void updateIngredientWithNonExistingIdShouldReturnNotFound() throws Exception {
        Long ingredientId = 1L;
        Ingredient ingredient = new Ingredient(ingredientId, "Test Ingredient", true, false, true, new ArrayList<>());

        when(ingredientService.updateIngredient(ingredient)).thenThrow(new IngredientNotFoundException(""));

        mockMvc.perform(put("/ingredient/{id}", ingredientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ingredient)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return no content when deleting an ingredient")
    void deleteIngredientByIdWithValidIdShouldReturnNoContent() throws Exception {
        Long ingredientId = 1L;

        mockMvc.perform(delete("/ingredient/{id}", ingredientId))
                .andExpect(status().isNoContent());
        verify(ingredientService, times(1)).deleteIngredient(ingredientId);
    }
}
