package com.example.snack.order.model;

import com.example.snack.ingredient.Ingredient;
import lombok.Data;

@Data
public class IngredientOrder {

    private Ingredient ingredient;
    private Integer quantity;

    public IngredientOrder(Ingredient ingredient, Integer quantity) {
        this.ingredient = ingredient;
        this.quantity = quantity;
    }
}
