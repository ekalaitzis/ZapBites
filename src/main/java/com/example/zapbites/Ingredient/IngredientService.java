package com.example.zapbites.Ingredient;

import com.example.zapbites.Ingredient.Exceptions.DuplicateIngredientException;
import com.example.zapbites.Ingredient.Exceptions.IngredientNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Optional<Ingredient> getIngredientById(Long id) {
        return ingredientRepository.findById(id);
    }

    public Ingredient createIngredient(Ingredient ingredient) {
        if (ingredientRepository.findById(ingredient.getId()).isPresent()) {
            throw new DuplicateIngredientException("Ingredient  " + ingredient.getName() + " already exists");
        }
        return ingredientRepository.save(ingredient);
    }

    public Ingredient updateIngredient(Ingredient updatedIngredient) {
        List<Ingredient> allIngredients = getAllIngredients();

        if (allIngredients.stream().anyMatch(i -> i.getId().equals(updatedIngredient.getId()))) {
            return ingredientRepository.save(updatedIngredient);
        } else {
            throw new IngredientNotFoundException("Ingredient with id " + updatedIngredient.getId() + " not found.");
        }
    }

    public void deleteIngredient(Long id) {
        try {
            ingredientRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new IngredientNotFoundException("The ingredient with id " + id + " not found.", e);
        }
    }
}
