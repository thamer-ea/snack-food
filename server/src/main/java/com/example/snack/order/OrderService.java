package com.example.snack.order;

import com.example.snack.SnackApplication;
import com.example.snack.ingredient.IngredientService;
import com.example.snack.snack.SnackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(SnackApplication.class);
    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private SnackService snackService;

}
