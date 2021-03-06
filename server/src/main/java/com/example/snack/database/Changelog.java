package com.example.snack.database;

import com.example.snack.ingredient.Ingredient;
import com.example.snack.promotion.Promotion;
import com.example.snack.promotion.PromotionGeneric;
import com.example.snack.promotion.PromotionMatch;
import com.example.snack.promotion.PromotionMultipleIngredient;
import com.example.snack.snack.Snack;
import com.example.snack.snack.SnackIngredient;
import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@ChangeLog
public class Changelog {

    @ChangeSet(order = "001", id = "addIngredients", author = "thamer")
    public void addIngredients(MongoTemplate mongoTemplate) {
        Ingredient ingredient = new Ingredient("Alface", 0.4);
        mongoTemplate.save(ingredient);

        ingredient = new Ingredient("Bacon", 2.0);
        mongoTemplate.save(ingredient);

        ingredient = new Ingredient("Hambúrguer de carne", 3.0);
        mongoTemplate.save(ingredient);

        ingredient = new Ingredient("Ovo", 0.8);
        mongoTemplate.save(ingredient);

        ingredient = new Ingredient("Queijo", 1.5);
        mongoTemplate.save(ingredient);
    }

    @ChangeSet(order = "002", id = "addSnacks", author = "thamer")
    public void addSnacks(MongoTemplate mongoTemplate) {
        List<Ingredient> ingredientList = mongoTemplate.findAll(Ingredient.class);

        final List<String> xBaconIngredientsNames = asList("Bacon", "Hambúrguer de carne", "Queijo");
        Set<SnackIngredient> xBaconIngredients = ingredientList.stream()
                .filter(ingredient -> (xBaconIngredientsNames.contains(ingredient.getName())))
                .map(ingredient -> new SnackIngredient(ingredient, 1))
                .collect(Collectors.toSet());
        mongoTemplate.save(new Snack("X-Bacon", xBaconIngredients));

        final List<String> xBurgerIngredientsNames = asList("Hambúrguer de carne", "Queijo");
        Set<SnackIngredient> xBurgerIngredients = ingredientList.stream()
                .filter(ingredient -> (xBurgerIngredientsNames.contains(ingredient.getName())))
                .map(ingredient -> new SnackIngredient(ingredient, 1))
                .collect(Collectors.toSet());
        mongoTemplate.save(new Snack("X-Burger", xBurgerIngredients));

        final List<String> xEggIngredientsNames = asList("Ovo", "Hambúrguer de carne", "Queijo");
        Set<SnackIngredient> xEggIngredients = ingredientList.stream()
                .filter(ingredient -> (xEggIngredientsNames.contains(ingredient.getName())))
                .map(ingredient -> new SnackIngredient(ingredient, 1))
                .collect(Collectors.toSet());
        mongoTemplate.save(new Snack("X-Egg", xEggIngredients));

        final List<String> xEggBaconIngredientsNames = asList("Ovo", "Bacon", "Hambúrguer de carne", "Queijo");
        Set<SnackIngredient> xEggBaconIngredients = ingredientList.stream()
                .filter(ingredient -> (xEggBaconIngredientsNames.contains(ingredient.getName())))
                .map(ingredient -> new SnackIngredient(ingredient, 1))
                .collect(Collectors.toSet());
        mongoTemplate.save(new Snack("X-Egg Bacon", xEggBaconIngredients));
    }

    @ChangeSet(order = "003", id = "addPromotions", author = "thamer")
    public void addPromotions(MongoTemplate mongoTemplate) {
        List<Ingredient> ingredientList = mongoTemplate.findAll(Ingredient.class);

        // Light
        Set<Ingredient> hasIngredient = new HashSet<>();
        Set<Ingredient> hasNoIngredient = new HashSet<>();
        for (Ingredient ingredient : ingredientList) {
            if (ingredient.getName().equals("Alface")) {
                hasIngredient.add(ingredient);
            }
            if (ingredient.getName().equals("Bacon")) {
                hasNoIngredient.add(ingredient);
            }
        }
        Promotion lightPromotion = new PromotionMatch(hasIngredient, hasNoIngredient, 0.1);
        PromotionGeneric light = new PromotionGeneric("Light", lightPromotion);
        mongoTemplate.save(light);

        // Muita carne
        Ingredient meat = null;
        for (Ingredient ingredient : ingredientList) {
            if (ingredient.getName().equals("Hambúrguer de carne")) {
                meat = ingredient;
            }
        }
        Promotion aLotOfMeatPromotion = new PromotionMultipleIngredient(meat, 3, 2);
        PromotionGeneric aLotOfMeat = new PromotionGeneric("Muita carne", aLotOfMeatPromotion);
        mongoTemplate.save(aLotOfMeat);

        // Muito queijo
        Ingredient cheese = null;
        for (Ingredient ingredient : ingredientList) {
            if (ingredient.getName().equals("Queijo")) {
                cheese = ingredient;
            }
        }
        Promotion aLotOfCheesePromotion = new PromotionMultipleIngredient(cheese, 3, 2);
        PromotionGeneric aLotOfCheese = new PromotionGeneric("Muito queijo", aLotOfCheesePromotion);
        mongoTemplate.save(aLotOfCheese);
    }
}
