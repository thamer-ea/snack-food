package com.example.snack.promotion;

import lombok.Data;

@Data
public class PromotionResult {

    private boolean compatible;
    private double price;

    public PromotionResult(boolean compatible, double price) {
        this.compatible = compatible;
        this.price = price;
    }

    public PromotionResult(boolean compatible) {
        this.compatible = compatible;
    }
}
