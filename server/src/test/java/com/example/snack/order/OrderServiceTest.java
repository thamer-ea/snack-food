package com.example.snack.order;

import com.example.snack.ingredient.Ingredient;
import com.example.snack.ingredient.IngredientService;
import com.example.snack.order.model.CustomSnackOrder;
import com.example.snack.order.model.Order;
import com.example.snack.order.model.SnackOrder;
import com.example.snack.promotion.PromotionGeneric;
import com.example.snack.promotion.PromotionMatch;
import com.example.snack.promotion.PromotionService;
import com.example.snack.snack.Snack;
import com.example.snack.snack.SnackIngredient;
import com.example.snack.snack.SnackService;
import com.example.snack.test.FailFast;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.doReturn;

@RunWith(PowerMockRunner.class)
@PrepareForTest({IngredientService.class})
@Category(FailFast.class)
public class OrderServiceTest {

    @Mock
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    private OrderService orderServiceSpy;

    private IngredientService ingredientService;
    private IngredientService ingredientServiceSpy;
    private SnackService snackService;
    private SnackService snackServiceSpy;
    private PromotionService promotionService;
    private PromotionService promotionServiceSpy;


    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        snackService = new SnackService();
        snackServiceSpy = PowerMockito.spy(snackService);
        ingredientService = new IngredientService();
        ingredientServiceSpy = PowerMockito.spy(ingredientService);
        promotionService = new PromotionService();
        promotionServiceSpy = PowerMockito.spy(promotionService);
        orderService = new OrderService(ingredientServiceSpy, snackServiceSpy, promotionServiceSpy, orderRepository);
        orderServiceSpy = PowerMockito.spy(orderService);
    }

    @Test
    public void shouldCalculateFinalPrice() throws Exception {
        Order order = new Order();
        List<SnackOrder> snackOrderList = new ArrayList<>();
        SnackOrder snackOrder1 = new SnackOrder(null, 1, 5.1);
        SnackOrder snackOrder2 = new SnackOrder(null, 2, 4.0);
        snackOrderList.add(snackOrder1);
        snackOrderList.add(snackOrder2);
        order.setSnacks(snackOrderList);
        List<CustomSnackOrder> customSnackOrderList = new ArrayList<>();
        CustomSnackOrder customSnackOrder1 = new CustomSnackOrder(null, 2, 3.3);
        CustomSnackOrder customSnackOrder2 = new CustomSnackOrder(null, 1, 7);
        customSnackOrderList.add(customSnackOrder1);
        customSnackOrderList.add(customSnackOrder2);
        order.setCustomSnacks(customSnackOrderList);
        Method method = OrderService.class.getDeclaredMethod("calculateFinalPrice", Order.class);
        method.setAccessible(true);
        method.invoke(orderServiceSpy, order);
        double finalPrice = (snackOrder1.getPrice() * snackOrder1.getQuantity()) +
                (snackOrder2.getPrice() * snackOrder2.getQuantity()) +
                (customSnackOrder1.getPrice() * customSnackOrder1.getQuantity()) +
                (customSnackOrder2.getPrice() * customSnackOrder2.getQuantity());
        assertEquals(order.getTotalPrice(), finalPrice, 0.0);
    }

    @Test
    public void shouldCalculatePromotions() throws Exception {
        Order order = new Order();
        Ingredient ingredient1 = new Ingredient("1","ingredient1", 1);
        Ingredient ingredient2 = new Ingredient("2","ingredient2", 2);

        List<SnackOrder> snackOrderList = new ArrayList<>();
        Set<SnackIngredient> snackIngredients1 = new HashSet<>();
        snackIngredients1.add(new SnackIngredient(ingredient1, 1));
        Snack snack1 = new Snack("snack1", snackIngredients1);
        Set<SnackIngredient> snackIngredients2 = new HashSet<>();
        snackIngredients2.add(new SnackIngredient(ingredient1, 1));
        snackIngredients2.add(new SnackIngredient(ingredient2, 1));
        Snack snack2 = new Snack("snack2", snackIngredients2);
        SnackOrder snackOrder1 = new SnackOrder(snack1, 1, 5.1);
        SnackOrder snackOrder2 = new SnackOrder(snack2, 2, 4.0);
        snackOrderList.add(snackOrder1);
        snackOrderList.add(snackOrder2);
        order.setSnacks(snackOrderList);
        List<CustomSnackOrder> customSnackOrderList = new ArrayList<>();
        CustomSnackOrder customSnackOrder1 = new CustomSnackOrder(snackIngredients1, 2, 3.3);
        CustomSnackOrder customSnackOrder2 = new CustomSnackOrder(snackIngredients2, 1, 7);
        customSnackOrderList.add(customSnackOrder1);
        customSnackOrderList.add(customSnackOrder2);
        order.setCustomSnacks(customSnackOrderList);

        List<PromotionGeneric> promotions = new ArrayList<>();
        Set<Ingredient> hasIngredient = new HashSet<>();
        Set<Ingredient> hasNoIngredient = new HashSet<>();
        hasIngredient.add(ingredient1);
        hasNoIngredient.add(ingredient2);
        double discount = 0.1;
        PromotionMatch promotionMatch = new PromotionMatch(hasIngredient, hasNoIngredient, discount);
        PromotionGeneric promotionGeneric = new PromotionGeneric("test", promotionMatch);
        promotions.add(promotionGeneric);
        doReturn(promotions).when(promotionServiceSpy, "getAllPromotion");


        Method method = OrderService.class.getDeclaredMethod("calculatePromotions", Order.class);
        method.setAccessible(true);
        method.invoke(orderServiceSpy, order);
        double finalPrice = (snackOrder1.getPrice() * snackOrder1.getQuantity() * (1 - discount)) +
                (snackOrder2.getPrice() * snackOrder2.getQuantity()) +
                (customSnackOrder1.getPrice() * customSnackOrder1.getQuantity() * (1 - discount)) +
                (customSnackOrder2.getPrice() * customSnackOrder2.getQuantity());
        assertEquals(order.getSnacks().get(0).getPrice(), ingredient1.getPrice() * (1 - discount), 0.0);
        assertEquals(order.getSnacks().get(1).getPrice(), ingredient1.getPrice() + ingredient2.getPrice(), 0.0);
        assertEquals(order.getCustomSnacks().get(0).getPrice(), ingredient1.getPrice() * (1 - discount), 0.0);
        assertEquals(order.getCustomSnacks().get(1).getPrice(), ingredient1.getPrice() + ingredient2.getPrice(), 0.0);
    }

}
