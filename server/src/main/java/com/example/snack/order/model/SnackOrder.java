package com.example.snack.order.model;

import com.example.snack.snack.Snack;
import lombok.Data;

@Data
public class SnackOrder {

    private Snack snack;
    private Integer quantity;
    private double price;

    public SnackOrder(Snack snack, Integer quantity) {
        this.snack = snack;
        this.quantity = quantity;
    }

    public SnackOrder(Snack snack, Integer quantity, double price) {
        this.snack = snack;
        this.quantity = quantity;
        this.price = price;
    }
}
