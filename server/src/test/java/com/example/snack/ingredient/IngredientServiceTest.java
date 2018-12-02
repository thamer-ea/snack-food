package com.example.snack.ingredient;

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

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNull;
import static org.powermock.api.mockito.PowerMockito.doReturn;

@RunWith(PowerMockRunner.class)
@PrepareForTest({IngredientService.class})
@Category(FailFast.class)
public class IngredientServiceTest {

    @Mock
    private IngredientService ingredientService;

    @Mock
    private IngredientRepository ingredientRepository;

    private IngredientService ingredientServiceSpy;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientService();
        ingredientServiceSpy = PowerMockito.spy(ingredientService);
    }

    @Test
    public void shouldGetAllIngredients() throws Exception {
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(new Ingredient("1", "Bacon", 1.0));
        ingredientList.add(new Ingredient("2", "Burger", 3.0));

        PageRequest pageRequest = new PageRequest(1, 10);
        Page<Ingredient> page = new PageImpl<>(ingredientList);

        doReturn(page).when(ingredientServiceSpy, "getAllIngredients", pageRequest);
        assertEquals(ingredientServiceSpy.getAllIngredients(pageRequest), page);
    }

    @Test
    public void shouldNotFindAllIngredients() throws Exception {
        PageRequest pageRequest = new PageRequest(1, 10);
        doReturn(null).when(ingredientServiceSpy, "getAllIngredients", pageRequest);
        assertNull(ingredientServiceSpy.getAllIngredients(pageRequest));
    }

    @Test
    public void shouldNotFindIngredient_whenWithoutId() throws Exception {
        doReturn(null).when(ingredientServiceSpy, "getIngredient", "");
        assertNull(ingredientServiceSpy.getIngredient(""));
    }

    @Test
    public void shouldFindIngredient_whenWithId() throws Exception {
        Ingredient ingredient = new Ingredient("1", "Bacon", 1.0);
        doReturn(java.util.Optional.ofNullable(ingredient)).when(ingredientServiceSpy, "getIngredient", "1");
        assertEquals(ingredientServiceSpy.getIngredient("1").get(), ingredient);
    }
}
