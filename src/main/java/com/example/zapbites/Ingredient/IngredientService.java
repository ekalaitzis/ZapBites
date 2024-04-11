package com.example.zapbites.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientService {
    List<Ingredient> getAllIngredients();

    Optional<Ingredient> getIngredientById(Long id);

    Ingredient createIngredient(Ingredient ingredient);

    Ingredient updateIngredient(Ingredient updatedIngredient);

    void deleteIngredient(Long id);
}
