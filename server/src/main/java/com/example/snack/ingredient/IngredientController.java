package com.example.snack.ingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/${api.version}")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping(value = "/ingredients", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllIngredients(@PageableDefault(size = 1000) Pageable pageable) {
        Page<Ingredient> ingredients = ingredientService.getAllIngredients(pageable);
        if (ingredients.hasContent()) {
            return ResponseEntity.ok(ingredients.getContent());
        }
        return ResponseEntity.noContent().build();
    }
}
