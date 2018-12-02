package com.example.snack.promotion;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class PromotionGeneric {

    @Id
    private String id;
    private String name;
    private Promotion promotion;

    public PromotionGeneric(String name, Promotion promotion) {
        this.name = name;
        this.promotion = promotion;
    }
}
