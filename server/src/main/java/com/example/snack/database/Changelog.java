package com.example.snack.database;

import com.example.snack.ingredients.Ingredient;
import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;

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
}
