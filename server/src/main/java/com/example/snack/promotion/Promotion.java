package com.example.snack.promotion;

import com.example.snack.snack.SnackIngredient;

import java.util.Set;

public interface Promotion {

    public PromotionResult calculatePrice(Set<SnackIngredient> snackIngredients);

}
