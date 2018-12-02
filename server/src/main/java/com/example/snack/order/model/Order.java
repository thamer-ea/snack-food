package com.example.snack.order.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Order {

    private String id;
    private String customer;
    private List<SnackOrder> snacks;
    private List<CustomSnackOrder> customSnacks;
    private double totalPrice;

}
