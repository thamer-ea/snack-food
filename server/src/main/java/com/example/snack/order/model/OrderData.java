package com.example.snack.order.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class OrderData {

    @NotEmpty(message = "Customer is required.")
    String customer;

    @Valid
    List<SnackOrderData> snacks;

    @Valid
    List<CustomSnackOrderData> customSnacks;
}
