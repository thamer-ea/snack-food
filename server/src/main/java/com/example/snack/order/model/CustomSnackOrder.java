package com.example.snack.order.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Set;

@Data
public class CustomSnackOrder {

    @Valid
    private Set<IngredientOrder> ingredients;

    @Positive(message = "Custom snack quantity must be greater than 0.")
    @NotNull(message = "Custom snack quantity is required.")
    private Integer quantity;

    public CustomSnackOrder(Set<IngredientOrder> ingredients, Integer quantity) {
        this.ingredients = ingredients;
        this.quantity = quantity;
    }
}
