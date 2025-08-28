package praktikum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class IngredientTest {

    @Mock
    IngredientType mockIngredientType;

    @Test
    public void testGetPrice() {
        Ingredient ingredient = new Ingredient(mockIngredientType, "hot sauce", 100.0f);

        assertEquals(100.0f, ingredient.getPrice(), "Цена ингредиента совпадает");
    }

    @Test
    public void testGetName() {
        Ingredient ingredient = new Ingredient(mockIngredientType, "chili sauce", 150.0f);

        assertEquals("chili sauce", ingredient.getName(), "Название ингредиента совпадает");
    }

    @Test
    public void testGetType() {
        Ingredient ingredient = new Ingredient(mockIngredientType, "sour cream", 80.0f);

        assertEquals(mockIngredientType, ingredient.getType());
    }
}