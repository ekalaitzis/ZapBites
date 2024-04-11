package com.example.zapbites.Ingredient;

import com.example.zapbites.Ingredient.Exceptions.DuplicateIngredientException;
import com.example.zapbites.Ingredient.Exceptions.IngredientNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IngredientServiceImpl implements IngredientService{
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }
    @Override
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }
    @Override
    public Optional<Ingredient> getIngredientById(Long id) {
        return ingredientRepository.findById(id);
    }
    @Override
    public Ingredient createIngredient(Ingredient ingredient) {
        String name = ingredient.getName();
        Optional<Ingredient> existingIngredient = ingredientRepository.findByName(name);
        if (existingIngredient.isPresent()) {
            throw new DuplicateIngredientException("The ingredient already exists.");
        }
        return ingredientRepository.save(ingredient);
    }
    @Override
    public Ingredient updateIngredient(Ingredient updatedIngredient) {
        List<Ingredient> allIngredients = getAllIngredients();

        if (allIngredients.stream().anyMatch(i -> i.getId().equals(updatedIngredient.getId()))) {
            return ingredientRepository.save(updatedIngredient);
        } else {
            throw new IngredientNotFoundException("Ingredient: " + updatedIngredient.getName() + " doesn't exist.");
        }
    }
    @Override
    public void deleteIngredient(Long id) {
            ingredientRepository.deleteById(id);
    }
}
