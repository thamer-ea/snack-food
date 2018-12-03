package com.example.snack.order.model;

import com.example.snack.snack.SnackIngredient;
import lombok.Data;

import java.util.Set;

@Data
public class CustomSnackOrder {

    private Set<SnackIngredient> ingredients;
    private Integer quantity;
    private double price;

    public CustomSnackOrder(Set<SnackIngredient> ingredients, Integer quantity) {
        this.ingredients = ingredients;
        this.quantity = quantity;
    }

    public CustomSnackOrder(Set<SnackIngredient> ingredients, Integer quantity, double price) {
        this.ingredients = ingredients;
        this.quantity = quantity;
        this.price = price;
    }
}
