package com.example.zapbites.Ingredient;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ingredient")
@Validated
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    @PreAuthorize("hasRole('BUSINESS')") //this is not secured properly as it is not going to be used in production
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        List<Ingredient> ingredients = ingredientService.getAllIngredients();
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@businessOwnerEvaluator.checkForOwnerByIngredientId(#id)")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable Long id) {
        Optional<Ingredient> optionalIngredient = ingredientService.getIngredientById(id);
        return optionalIngredient.map(o -> new ResponseEntity<>(o, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createIngredient(@Valid @RequestBody Ingredient ingredient) {
            Ingredient createdingredient = ingredientService.createIngredient(ingredient);
            return new ResponseEntity<>(createdingredient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@businessOwnerEvaluator.checkForOwnerByIngredient(#ingredient)")
    public ResponseEntity<Ingredient> updateIngredient(@RequestBody Ingredient ingredient) {
            Ingredient updatedIngredient = ingredientService.updateIngredient(ingredient);
            return new ResponseEntity<>(updatedIngredient, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@businessOwnerEvaluator.checkForOwnerByIngredientId(#id)")
    public ResponseEntity<Void> deleteIngredientById(@PathVariable Long id) {
        ingredientService.deleteIngredient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
