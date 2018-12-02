package com.example.snack.ingredients;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Ingredient {

    @Id
    private String id;

    private String name;

    private double price;

    public Ingredient(String name, double price) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Name cannot be null or blank.");
        }
        if (price < 0) {
            throw new ValueException("Price cannot be negative.");
        }
        this.name = name;
        this.price = price;
    }

    public Ingredient(String id, String name, double price) {
        this(name, price);
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("Id cannot be null or blank.");
        }
        this.id = id;
    }
}
