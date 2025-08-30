package praktikum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BurgerTest {

    @Mock
    private Bun mockedBun;

    private Burger burger;
    private Ingredient hotSauce;
    private Ingredient sourCream;
    private Ingredient chiliSauce;
    private Ingredient cutlet;
    private Ingredient sausage;

    @BeforeEach
    void setUp() {
        //Инициализируем объекты
        burger = new Burger();
        hotSauce= new Ingredient(IngredientType.SAUCE, "hot sauce", 100.0f);
        sourCream = new Ingredient(IngredientType.SAUCE, "sour cream", 200.0f);
        chiliSauce = new Ingredient(IngredientType.SAUCE, "chili sauce", 300.0f);
        cutlet = new Ingredient(IngredientType.FILLING, "cutlet", 100.0f);
        sausage = new Ingredient(IngredientType.FILLING, "sausage", 200.0f);
    }

    @Test
    void testSetBuns() {
        burger.setBuns(mockedBun);
        when(mockedBun.getPrice()).thenReturn(100.0f);
        when(mockedBun.getName()).thenReturn("black bun");

        assertNotNull(burger.bun, "Булочка должна быть установлена");
        assertEquals(mockedBun, burger.bun, "Установленная булочка должна совпадать");
        assertEquals("black bun", burger.bun.getName(), "Название булочки должно быть correct");
        assertEquals(100.0f, burger.bun.getPrice(), "Цена булочки должна быть correct");
    }

    @Test
    void testaddIngredient() {
        burger.addIngredient(hotSauce);
        burger.addIngredient(cutlet);

        assertEquals(2, burger.ingredients.size(), "Должно быть 2 ингридиента");
        assertEquals(hotSauce, burger.ingredients.get(0), "Первый ингридиент - соус");
        assertEquals(cutlet, burger.ingredients.get(1), "Второй ингридиент - котлета");
    }

    @Test
    void testRemoveIngredient() {
        burger.addIngredient(hotSauce);   // инд = 0
        burger.addIngredient(sausage);     // инд = 1
        burger.addIngredient(sourCream);  // инд = 2

        int initialSize = burger.ingredients.size();
        assertEquals(3, initialSize, "Изначально должно быть 3 ингредиента");

        //Удаляем котлету
        burger.removeIngredient(1); //

        assertEquals(2, burger.ingredients.size(), "Должно остаться 2 ингредиента");
        assertEquals(hotSauce, burger.ingredients.get(0), "Первый ингредиент должен остаться");
        assertEquals(sourCream, burger.ingredients.get(1), "Третий ингредиент должен стать вторым");
        assertFalse(burger.ingredients.contains(sausage), "Удаленного ингредиента нет");
    }

    @Test
    void testRemoveIngredientInvalidIndex() {
        burger.addIngredient(hotSauce);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            burger.removeIngredient(5);}, "Должно выдать исключение при несуществующем индексе");
    }

    @ParameterizedTest
    @MethodSource("provideBurgerPositionsTestCases")
    void testMoveIngredientDifferentPositions (int fromIndex, int toIndex, String description) {
        burger.addIngredient(hotSauce);   // инд = 0
        burger.addIngredient(sausage);     // инд = 1
        burger.addIngredient(cutlet);     // инд = 2

        Ingredient movedIngredient = burger.ingredients.get(fromIndex);
        burger.moveIngredient(fromIndex, toIndex);

        assertEquals(movedIngredient, burger.ingredients.get(toIndex));
    }

    private static Stream<Arguments> provideBurgerPositionsTestCases() {
        return Stream.of(
                Arguments.of(0, 1, "Хот соус с позиции 0 на позицию 1"),
                Arguments.of(1, 0, "Сосиска с позиции 1 на позицию 0"),
                Arguments.of(0, 2, "Хот соус с позиции 0 на позицию 2"),
                Arguments.of(2, 0, "Котлета с позиции 2 на позицию 0")
        );
    }


    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void testRemoveIngredientDifferentPositions(int indexToRemove) {
        burger.addIngredient(chiliSauce);
        burger.addIngredient(sourCream);
        burger.addIngredient(cutlet);

        int initialSize = burger.ingredients.size();
        Ingredient removedIngredient = burger.ingredients.get(indexToRemove);

        burger.removeIngredient(indexToRemove);

        assertEquals(initialSize - 1, burger.ingredients.size());
        assertFalse(burger.ingredients.contains(removedIngredient));
    }
}