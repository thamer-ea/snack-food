package com.example.snack.snack;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Document
public class Snack {

    @Id
    String id;

    String name;

    Set<SnackIngredient> ingredients;

    double price;

    public Snack(String name, Set<SnackIngredient> ingredients) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Name cannot be null or blank.");
        }
        if (ingredients.isEmpty()) {
            throw new IllegalArgumentException("Required at least one ingredient.");
        }
        this.name = name;
        this.ingredients = ingredients;
        this.price = ingredients.stream().mapToDouble(si -> si.getQuantity() * si.getIngredient().getPrice()).sum();
    }
}
