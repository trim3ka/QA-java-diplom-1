package praktikum;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IngredientTest {

    @Test
    void testGetPrice() {
        Ingredient ingredient = new Ingredient(IngredientType.SAUCE, "hot sauce", 100.0f);
        assertEquals(100.0f, ingredient.getPrice(), "Цена ингредиента должна совпадать");
    }

    @Test
    void testGetName() {
        Ingredient ingredient = new Ingredient(IngredientType.SAUCE, "chili sauce", 150.0f);
        assertEquals("chili sauce", ingredient.getName(), "Название ингредиента должно совпадать");
    }

    @Test
    void testGetType() {
        Ingredient ingredient = new Ingredient(IngredientType.FILLING, "cutlet", 80.0f);
        assertEquals(IngredientType.FILLING, ingredient.getType(), "Тип ингредиента должен совпадать");
    }
}