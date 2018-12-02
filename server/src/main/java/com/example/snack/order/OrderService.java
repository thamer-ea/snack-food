package com.example.snack.order;

import com.example.snack.SnackApplication;
import com.example.snack.exception.MessageException;
import com.example.snack.ingredient.Ingredient;
import com.example.snack.ingredient.IngredientService;
import com.example.snack.order.model.*;
import com.example.snack.snack.Snack;
import com.example.snack.snack.SnackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(SnackApplication.class);
    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private SnackService snackService;
    @Autowired
    private OrderRepository orderRepository;

    private static void addIngredient(List<IngredientOrder> ingredientOrderList, IngredientOrder ingredientOrder) {
        for (IngredientOrder io : ingredientOrderList) {
            if (io.getIngredient().getId().equals(ingredientOrder.getIngredient().getId())) {
                io.setQuantity(io.getQuantity() + ingredientOrder.getQuantity());
                return;
            }
        }
        ingredientOrderList.add(ingredientOrder);
    }

    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Order createOrder(OrderData orderData) {
        Order order = new Order();
        order.setCustomer(orderData.getCustomer());

        List<SnackOrder> snackOrderList = new ArrayList();
        orderData.getSnacks().forEach(snackOrderData -> {
            Optional<Snack> snack = snackService.getSnack(snackOrderData.getId());
            if (snack.isPresent()) {
                snackOrderList.add(new SnackOrder(snack.get(), snackOrderData.getQuantity()));
            } else {
                throw new MessageException("Snack " + snackOrderData.getId() + " not found.", HttpStatus.BAD_REQUEST);
            }
        });
        if (!snackOrderList.isEmpty()) {
            order.setSnacks(snackOrderList);
        }

        List<CustomSnackOrder> customSnackOrderList = new ArrayList();
        orderData.getCustomSnacks().forEach(customSnackOrderData -> {
            List<IngredientOrder> ingredientOrderList = new ArrayList();
            customSnackOrderData.getIngredients().forEach(ingredientOrderData -> {
                Optional<Ingredient> ingredient = ingredientService.getIngredient(ingredientOrderData.getId());
                if (ingredient.isPresent()) {
                    addIngredient(ingredientOrderList, new IngredientOrder(ingredient.get(), ingredientOrderData.getQuantity()));
                } else {
                    throw new MessageException("Ingredient " + ingredientOrderData.getId() + " not found.", HttpStatus.BAD_REQUEST);
                }
            });
            customSnackOrderList.add(new CustomSnackOrder(ingredientOrderList, customSnackOrderData.getQuantity()));
        });
        if (!customSnackOrderList.isEmpty()) {
            order.setCustomSnacks(customSnackOrderList);
        }

        //TODO calculate price
        return orderRepository.save(order);
    }


}
