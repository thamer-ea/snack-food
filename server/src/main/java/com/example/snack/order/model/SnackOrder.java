package com.example.snack.order.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class SnackOrder {

    @NotEmpty(message = "Snack ID is required.")
    private String id;
    @Positive(message = "Snack quantity must be greater than 0.")
    @NotNull(message = "Snack quantity is required.")
    private Integer quantity;

    public SnackOrder(String id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }
}
