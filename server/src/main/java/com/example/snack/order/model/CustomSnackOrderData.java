package com.example.snack.order.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
public class CustomSnackOrderData {

    @Valid
    @NotEmpty(message = "At least one ingredient is required.")
    private List<IngredientOrderData> ingredients;

    @Positive(message = "Custom snack quantity must be greater than 0.")
    @NotNull(message = "Custom snack quantity is required.")
    private Integer quantity;

    public CustomSnackOrderData(List<IngredientOrderData> ingredients, Integer quantity) {
        this.ingredients = ingredients;
        this.quantity = quantity;
    }
}
