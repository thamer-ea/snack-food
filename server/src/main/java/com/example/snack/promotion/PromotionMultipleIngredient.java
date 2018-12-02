package com.example.snack.promotion;

import com.example.snack.ingredient.Ingredient;
import com.example.snack.snack.SnackIngredient;
import lombok.Data;

import java.util.Set;

@Data
public class PromotionMultipleIngredient implements Promotion {

    private Ingredient ingredient;
    private Integer forEachQuantity;
    private Integer finalPayment;

    public PromotionMultipleIngredient(Ingredient ingredient, Integer forEachQuantity, Integer finalPayment) {
        this.ingredient = ingredient;
        this.forEachQuantity = forEachQuantity;
        this.finalPayment = finalPayment;
    }

    @Override
    public PromotionResult calculatePrice(Set<SnackIngredient> snackIngredients) {
        for (SnackIngredient si : snackIngredients) {
            if (si.getIngredient().getId().equals(ingredient.getId()) && si.getQuantity() >= 0) {
                int quantityToPay = (si.getQuantity() % forEachQuantity) + ((si.getQuantity() / forEachQuantity) * finalPayment);
                if (quantityToPay == si.getQuantity()) {
                    break;
                }
                int currentQuantity = si.getQuantity();
                si.setQuantity(quantityToPay);
                double price = snackIngredients.stream().mapToDouble(i -> i.getQuantity() * i.getIngredient().getPrice()).sum();
                si.setQuantity(currentQuantity);
                return new PromotionResult(true, price);
            }
        }
        return new PromotionResult(false);
    }
}
