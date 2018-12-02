package com.example.snack.snack;

import com.example.snack.ingredient.Ingredient;
import lombok.Data;

@Data
public class SnackIngredient {

    private Ingredient ingredient;
    private Integer quantity;

    public SnackIngredient(Ingredient ingredient, Integer quantity) {
        if (ingredient == null) {
            throw new IllegalArgumentException("Ingredient cannot be null.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.ingredient = ingredient;
        this.quantity = quantity;
    }

}
