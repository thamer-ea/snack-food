package com.example.snack.promotion;

import com.example.snack.ingredient.Ingredient;
import com.example.snack.snack.SnackIngredient;
import lombok.Data;

import java.util.Set;

@Data
public class PromotionMatch implements Promotion {

    private Set<Ingredient> hasIngredients;
    private Set<Ingredient> hasNoIngredients;
    private double discount;

    public PromotionMatch(Set<Ingredient> hasIngredients, Set<Ingredient> hasNoIngredients, double discount) {
        this.hasIngredients = hasIngredients;
        this.hasNoIngredients = hasNoIngredients;
        this.discount = discount;
    }

    @Override
    public PromotionResult calculatePrice(Set<SnackIngredient> snackIngredients) {
        int hasIngredientsCount = 0;
        boolean hasIngredientsFlag = false;
        for (Ingredient i : hasIngredients) {
            for (SnackIngredient si : snackIngredients) {
                if (si.getIngredient().getId().equals(i.getId()) && si.getQuantity() >= 0) {
                    hasIngredientsCount += 1;
                }
            }
        }
        if (hasIngredientsCount == hasIngredients.size()) {
            hasIngredientsFlag = true;
        }

        boolean hasNoIngredientsFlag = true;
        for (Ingredient i : hasNoIngredients) {
            for (SnackIngredient si : snackIngredients) {
                if (si.getIngredient().getId().equals(i.getId()) && si.getQuantity() >= 0) {
                    hasNoIngredientsFlag = false;
                }
            }
        }

        if (hasIngredientsFlag && hasNoIngredientsFlag) {
            double price = snackIngredients.stream().mapToDouble(si -> si.getQuantity() * si.getIngredient().getPrice()).sum();
            price = price - (price * discount);
            return new PromotionResult(true, price);
        }
        return new PromotionResult(false);
    }
}
