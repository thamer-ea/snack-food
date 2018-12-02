package com.example.snack.order.model;

import lombok.Data;

import java.util.List;

@Data
public class CustomSnackOrder {

    private List<IngredientOrder> ingredients;
    private Integer quantity;
    private double price;

    public CustomSnackOrder(List<IngredientOrder> ingredients, Integer quantity) {
        this.ingredients = ingredients;
        this.quantity = quantity;
    }
}
