package com.example.zapbites.Ingredient;

import com.example.zapbites.Ingredient.Exceptions.DuplicateIngredientException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/ingredient")
@Validated
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;

    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable Long id) {
        Optional<Ingredient> optionalIngredient = ingredientService.getIngredientById(id);
        return optionalIngredient.map(o -> new ResponseEntity<>(o, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Object> createIngredient(@Valid @RequestBody Ingredient ingredient) {
        try {
            Ingredient createdingredient = ingredientService.createIngredient(ingredient);
            return new ResponseEntity<>(createdingredient, HttpStatus.CREATED);
        } catch (DuplicateIngredientException e) {
            return new ResponseEntity<>("This Ingredient already exists.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@RequestBody Ingredient ingredient) {
        try {
            Ingredient updatedIngredient = ingredientService.updateIngredient(ingredient);
            return new ResponseEntity<>(updatedIngredient, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredientById(@PathVariable Long id) {
        ingredientService.deleteIngredient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
