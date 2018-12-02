package com.example.snack.snack;

import com.example.snack.ingredient.Ingredient;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.doReturn;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SnackService.class})
@Category(FailFast.class)
public class SnackServiceTest {

    @Mock
    private SnackService snackService;

    private SnackService snackServiceSpy;

    @Mock
    private SnackRepository snackRepository;

    private Set<SnackIngredient> ingredients = new HashSet<>();

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        snackService = new SnackService();
        snackServiceSpy = PowerMockito.spy(snackService);

        SnackIngredient snackIngredient = new SnackIngredient(new Ingredient(UUID.randomUUID().toString(), "food", 18.5), 1);
        ingredients.add(snackIngredient);
    }

    @Test
    public void shouldFindAllSnacks() throws Exception {
        List<Snack> snackList = new ArrayList<>();

        snackList.add(new Snack("XBacon", ingredients));
        snackList.add(new Snack("XBurger", ingredients));

        PageRequest pageRequest = new PageRequest(1, 10);
        Page<Snack> page = new PageImpl<>(snackList);

        doReturn(page).when(snackServiceSpy, "getAllSnacks", pageRequest);
        assertEquals(snackServiceSpy.getAllSnacks(pageRequest), page);
    }

    @Test
    public void shouldNotFindAllSnacks() throws Exception {
        PageRequest pageRequest = new PageRequest(1, 10);
        doReturn(null).when(snackServiceSpy, "getAllSnacks", pageRequest);
        assertNull(snackServiceSpy.getAllSnacks(pageRequest));
    }

    @Test
    public void shouldNotExistSnack_whenWithoutIdInvalid() throws Exception {
        doReturn(java.util.Optional.empty()).when(snackServiceSpy, "getSnack", "invalid");
        assertFalse(snackServiceSpy.getSnack("invalid").isPresent());
    }

    @Test
    public void shouldExistSnack_whenWithIdValid() throws Exception {
        doReturn(java.util.Optional.of(new Snack("X", ingredients))).when(snackServiceSpy, "getSnack", "valid");
        assertTrue(snackServiceSpy.getSnack("valid").isPresent());
    }

    @Test
    public void shouldCalculatePrice() {
        Set<SnackIngredient> ingredients = new HashSet<>();
        ingredients.add(new SnackIngredient(new Ingredient("Bacon", 5.0), 2));
        ingredients.add(new SnackIngredient(new Ingredient("Burger", 10.3), 1));
        Snack snack = new Snack("SNACK", ingredients);

        assertEquals(snack.getPrice(), (5 * 2) + (10.3 * 1));
    }
}
