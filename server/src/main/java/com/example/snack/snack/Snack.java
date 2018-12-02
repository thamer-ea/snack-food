package com.example.snack.snack;

import com.example.snack.ingredient.Ingredient;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class Snack {

    @Id
    String id;

    String name;

    List<Ingredient> ingredients;

    public Snack(String name, List<Ingredient> ingredients) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Name cannot be null or blank.");
        }
        if (ingredients.isEmpty()) {
            throw new IllegalArgumentException("Required at least one ingredient.");
        }

        this.name = name;
        this.ingredients = ingredients;
    }
}
