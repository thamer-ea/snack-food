package com.example.snack.order.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class IngredientOrder {

    @NotEmpty(message = "Ingredient ID of custom snack is required.")
    private String id;

    @Positive(message = "Ingredient quantity of custom snack must be greater than 0.")
    @NotNull(message = "Ingredient quantity of custom snack is required.")
    private Integer quantity;

    public IngredientOrder(String id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }
}
