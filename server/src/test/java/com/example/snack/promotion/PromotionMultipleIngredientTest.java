package com.example.snack.promotion;

import com.example.snack.ingredient.Ingredient;
import com.example.snack.snack.SnackIngredient;
import com.example.snack.test.FailFast;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PromotionMultipleIngredient.class})
@Category(FailFast.class)
public class PromotionMultipleIngredientTest {

    private PromotionMultipleIngredient promotionMultipleIngredient;
    private Ingredient ingredient1 = new Ingredient("1","ingredient1", 1);
    private Ingredient ingredient2 = new Ingredient("2","ingredient2", 2);

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        promotionMultipleIngredient = new PromotionMultipleIngredient(ingredient1, 3, 2);
    }

    @Test
    public void shouldGetPromotion_simple() {
        Set<SnackIngredient> ingredients = new HashSet<>();
        ingredients.add(new SnackIngredient(ingredient1, 3));
        PromotionResult promotionResult = promotionMultipleIngredient.calculatePrice(ingredients);
        assertTrue(promotionResult.isCompatible());
        assertEquals(promotionResult.getPrice(), (ingredient1.getPrice() * 2), 0.0);
    }

    @Test
    public void shouldGetPromotion_complex() {
        Set<SnackIngredient> ingredients = new HashSet<>();
        ingredients.add(new SnackIngredient(ingredient1, 7));
        PromotionResult promotionResult = promotionMultipleIngredient.calculatePrice(ingredients);
        assertTrue(promotionResult.isCompatible());
        assertEquals(promotionResult.getPrice(), (ingredient1.getPrice() * 5), 0.0);
    }

    @Test
    public void shouldGetPromotion_withMoreIngredients() {
        Set<SnackIngredient> ingredients = new HashSet<>();
        ingredients.add(new SnackIngredient(ingredient1, 3));
        ingredients.add(new SnackIngredient(ingredient2, 1));
        PromotionResult promotionResult = promotionMultipleIngredient.calculatePrice(ingredients);
        assertTrue(promotionResult.isCompatible());
        assertEquals(promotionResult.getPrice(), (ingredient1.getPrice() * 2) + (ingredient2.getPrice() * 1), 0.0);
    }

    @Test
    public void shouldNotGetPromotion() {
        Set<SnackIngredient> ingredients = new HashSet<>();
        ingredients.add(new SnackIngredient(ingredient1, 2));
        PromotionResult promotionResult = promotionMultipleIngredient.calculatePrice(ingredients);
        assertFalse(promotionResult.isCompatible());
    }

    @Test
    public void shouldNotGetPromotion_withAnotherIngredient() {
        Set<SnackIngredient> ingredients = new HashSet<>();
        ingredients.add(new SnackIngredient(ingredient2, 3));
        PromotionResult promotionResult = promotionMultipleIngredient.calculatePrice(ingredients);
        assertFalse(promotionResult.isCompatible());
    }

    @Test
    public void shouldNotGetPromotion_withBothIngredients() {
        Set<SnackIngredient> ingredients = new HashSet<>();
        ingredients.add(new SnackIngredient(ingredient1, 1));
        ingredients.add(new SnackIngredient(ingredient2, 3));
        PromotionResult promotionResult = promotionMultipleIngredient.calculatePrice(ingredients);
        assertFalse(promotionResult.isCompatible());
    }
}
