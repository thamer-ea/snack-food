package com.example.snack.promotion;

import com.example.snack.ingredient.Ingredient;
import com.example.snack.snack.Snack;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PromotionMatch.class})
@Category(FailFast.class)
public class PromotionMatchTest {

    private PromotionMatch promotionMatch;
    private Set<Ingredient> hasIngredient = new HashSet<>();
    private Set<Ingredient> hasNoIngredient = new HashSet<>();
    private double discount = 0.1;
    private Ingredient ingredient1 = new Ingredient("1","ingredient1", 1);
    private Ingredient ingredient2 = new Ingredient("2","ingredient2", 2);
    private Ingredient ingredient3 = new Ingredient("3","ingredient3", 3);

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);

        hasIngredient.add(ingredient1);
        hasNoIngredient.add(ingredient2);
        promotionMatch = new PromotionMatch(hasIngredient, hasNoIngredient, discount);
    }

    @Test
    public void shouldGetPromotion() {
        Set<SnackIngredient> ingredients = new HashSet<>();
        ingredients.add(new SnackIngredient(ingredient1, 2));
        PromotionResult promotionResult = promotionMatch.calculatePrice(ingredients);
        assertTrue(promotionResult.isCompatible());
        assertEquals(promotionResult.getPrice(), (ingredient1.getPrice() * 2) * (1 - discount), 0.0);
    }

    @Test
    public void shouldGetPromotion_withMoreIngredients() {
        Set<SnackIngredient> ingredients = new HashSet<>();
        ingredients.add(new SnackIngredient(ingredient1, 2));
        ingredients.add(new SnackIngredient(ingredient3, 1));
        PromotionResult promotionResult = promotionMatch.calculatePrice(ingredients);
        assertTrue(promotionResult.isCompatible());
        assertEquals(promotionResult.getPrice(), (ingredient1.getPrice() * 2 + ingredient3.getPrice() * 1) * (1 - discount), 0.0);
    }

    @Test
    public void shouldNotGetPromotion() {
        Set<SnackIngredient> ingredients = new HashSet<>();
        ingredients.add(new SnackIngredient(ingredient3, 2));
        PromotionResult promotionResult = promotionMatch.calculatePrice(ingredients);
        assertFalse(promotionResult.isCompatible());
    }

    @Test
    public void shouldNotGetPromotion_withNotPermittedIngredient() {
        Set<SnackIngredient> ingredients = new HashSet<>();
        ingredients.add(new SnackIngredient(ingredient2, 2));
        PromotionResult promotionResult = promotionMatch.calculatePrice(ingredients);
        assertFalse(promotionResult.isCompatible());
    }

    @Test
    public void shouldNotGetPromotion_withBothIngredients() {
        Set<SnackIngredient> ingredients = new HashSet<>();
        ingredients.add(new SnackIngredient(ingredient1, 1));
        ingredients.add(new SnackIngredient(ingredient2, 2));
        PromotionResult promotionResult = promotionMatch.calculatePrice(ingredients);
        assertFalse(promotionResult.isCompatible());
    }
}
