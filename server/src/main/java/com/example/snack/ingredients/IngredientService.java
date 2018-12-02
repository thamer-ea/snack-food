package com.example.snack.ingredients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    public Page<Ingredient> getAllIngredients(Pageable pageable) {
        return ingredientRepository.findAll(pageable);
    }
}
