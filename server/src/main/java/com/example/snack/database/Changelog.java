package com.example.snack.database;

import com.example.snack.ingredient.Ingredient;
import com.example.snack.snack.Snack;
import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
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
        List<Ingredient> xBaconIngredients = ingredientList.stream()
                .filter(e -> (xBaconIngredientsNames.contains(e.getName())))
                .collect(Collectors.toList());
        mongoTemplate.save(new Snack("X-Bacon", xBaconIngredients));

        final List<String> xBurgerIngredientsNames = asList("Hambúrguer de carne", "Queijo");
        List<Ingredient> xBurgerIngredients = ingredientList.stream()
                .filter(e -> (xBaconIngredientsNames.contains(e.getName())))
                .collect(Collectors.toList());
        mongoTemplate.save(new Snack("X-Burger", xBurgerIngredients));

        final List<String> xEggIngredientsNames = asList("Ovo", "Hambúrguer de carne", "Queijo");
        List<Ingredient> xEggIngredients = ingredientList.stream()
                .filter(e -> (xEggIngredientsNames.contains(e.getName())))
                .collect(Collectors.toList());
        mongoTemplate.save(new Snack("X-Egg", xEggIngredients));

        final List<String> xEggBaconIngredientsNames = asList("Ovo", "Bacon", "Hambúrguer de carne", "Queijo");
        List<Ingredient> xEggBaconIngredients = ingredientList.stream()
                .filter(e -> (xEggBaconIngredientsNames.contains(e.getName())))
                .collect(Collectors.toList());
        mongoTemplate.save(new Snack("X-Egg Bacon", xEggBaconIngredients));
    }
}
