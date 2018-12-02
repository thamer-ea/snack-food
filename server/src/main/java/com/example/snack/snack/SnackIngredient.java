package com.example.snack.snack;

import com.example.snack.ingredient.Ingredient;

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

    public Ingredient getIngredient() {
        return ingredient;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
