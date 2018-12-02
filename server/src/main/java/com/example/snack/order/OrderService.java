package com.example.snack.order;

import com.example.snack.SnackApplication;
import com.example.snack.exception.MessageException;
import com.example.snack.ingredient.Ingredient;
import com.example.snack.ingredient.IngredientService;
import com.example.snack.order.model.*;
import com.example.snack.promotion.PromotionGeneric;
import com.example.snack.promotion.PromotionResult;
import com.example.snack.promotion.PromotionService;
import com.example.snack.snack.Snack;
import com.example.snack.snack.SnackIngredient;
import com.example.snack.snack.SnackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(SnackApplication.class);
    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private SnackService snackService;
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private OrderRepository orderRepository;

    private static void addIngredient(Set<SnackIngredient> snackIngredientsSet, SnackIngredient snackIngredient) {
        for (SnackIngredient si : snackIngredientsSet) {
            if (si.getIngredient().getId().equals(snackIngredient.getIngredient().getId())) {
                si.setQuantity(si.getQuantity() + snackIngredient.getQuantity());
                return;
            }
        }
        snackIngredientsSet.add(snackIngredient);
    }

    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Order createOrder(OrderData orderData) {
        Order order = new Order();
        order.setCustomer(orderData.getCustomer());

        List<SnackOrder> snackOrderList = new ArrayList<>();
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

        List<CustomSnackOrder> customSnackOrderList = new ArrayList<>();
        orderData.getCustomSnacks().forEach(customSnackOrderData -> {
            Set<SnackIngredient> ingredientOrderSet = new HashSet<>();
            customSnackOrderData.getIngredients().forEach(ingredientOrderData -> {
                Optional<Ingredient> ingredient = ingredientService.getIngredient(ingredientOrderData.getId());
                if (ingredient.isPresent()) {
                    addIngredient(ingredientOrderSet, new SnackIngredient(ingredient.get(), ingredientOrderData.getQuantity()));
                } else {
                    throw new MessageException("Ingredient " + ingredientOrderData.getId() + " not found.", HttpStatus.BAD_REQUEST);
                }
            });
            customSnackOrderList.add(new CustomSnackOrder(ingredientOrderSet, customSnackOrderData.getQuantity()));
        });
        if (!customSnackOrderList.isEmpty()) {
            order.setCustomSnacks(customSnackOrderList);
        }

        calculatePromotions(order);
        calculateFinalPrice(order);
        return orderRepository.save(order);


    }

    private void calculateFinalPrice(Order order) {
        double finalPrice = 0;
        for (SnackOrder snackOrder : order.getSnacks()) {
            finalPrice += (snackOrder.getPrice() * snackOrder.getQuantity());
        }
        for (CustomSnackOrder customSnackOrder : order.getCustomSnacks()) {
            finalPrice += (customSnackOrder.getPrice() * customSnackOrder.getQuantity());
        }
        order.setTotalPrice(finalPrice);
    }

    private void calculatePromotions(Order order) {
        List<PromotionGeneric> promotions = promotionService.getAllPromotion();
        for (SnackOrder snackOrder : order.getSnacks()) {
            PromotionResult pResult = null;
            for (PromotionGeneric pg : promotions) {
                pResult = pg.getPromotion().calculatePrice(snackOrder.getSnack().getIngredients());
                if (pResult.isCompatible()) {
                    break;
                }
            }
            if (pResult != null && pResult.isCompatible()) {
                snackOrder.setPrice(pResult.getPrice());
            } else {
                snackOrder.setPrice(snackOrder.getSnack().getIngredients().stream().mapToDouble(si -> si.getQuantity() * si.getIngredient().getPrice()).sum());
            }
        }

        for (CustomSnackOrder customSnackOrder : order.getCustomSnacks()) {
            PromotionResult pResult = null;
            for (PromotionGeneric pg : promotions) {
                pResult = pg.getPromotion().calculatePrice(customSnackOrder.getIngredients());
                if (pResult.isCompatible()) {
                    break;
                }
            }
            if (pResult != null && pResult.isCompatible()) {
                customSnackOrder.setPrice(pResult.getPrice());
            } else {
                customSnackOrder.setPrice(customSnackOrder.getIngredients().stream().mapToDouble(si -> si.getQuantity() * si.getIngredient().getPrice()).sum());
            }
        }
    }
}
