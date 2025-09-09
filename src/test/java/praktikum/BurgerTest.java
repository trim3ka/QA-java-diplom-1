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
    private Bun bun;

    @Mock
    private Ingredient ingredient1;

    @Mock
    private Ingredient ingredient2;

    @Mock
    private Ingredient ingredient3;

    private Burger burger;

    @BeforeEach
    void setUp() {
        burger = new Burger();
    }

    @Test
    void testSetBuns() {
        when(bun.getPrice()).thenReturn(100.0f);
        when(bun.getName()).thenReturn("black bun");
        burger.setBuns(bun);

        assertNotNull(burger.bun, "Булочка должна быть установлена");
        assertEquals(bun, burger.bun, "Установленная булочка должна совпадать");
        assertEquals("black bun", burger.bun.getName(), "Название булочки должно быть correct");
        assertEquals(100.0f, burger.bun.getPrice(), "Цена булочки должна быть correct");
    }

    @Test
    void testAddIngredient() {

        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient3);

        assertEquals(2, burger.ingredients.size(), "Должно быть 2 ингредиента");
        assertEquals(ingredient1, burger.ingredients.get(0), "Первый ингредиент - соус");
        assertEquals(ingredient3, burger.ingredients.get(1), "Второй ингредиент - котлета");
    }

    @Test
    void testRemoveIngredient() {
        burger.addIngredient(ingredient1);   // инд = 0
        burger.addIngredient(ingredient2);   // инд = 1
        burger.addIngredient(ingredient3);   // инд = 2

        int initialSize = burger.ingredients.size();
        assertEquals(3, initialSize, "Изначально должно быть 3 ингредиента");

        // Удаляем второй ингредиент
        burger.removeIngredient(1);

        assertEquals(2, burger.ingredients.size(), "Должно остаться 2 ингредиента");
        assertEquals(ingredient1, burger.ingredients.get(0), "Первый ингредиент должен остаться");
        assertEquals(ingredient3, burger.ingredients.get(1), "Третий ингредиент должен стать вторым");
        assertFalse(burger.ingredients.contains(ingredient2), "Удаленного ингредиента нет");
    }

    @Test
    void testRemoveIngredientInvalidIndex() {
        burger.addIngredient(ingredient1);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            burger.removeIngredient(5);}, "Должно выдать исключение при несуществующем индексе");
    }

    @ParameterizedTest
    @MethodSource("provideBurgerPositionsTestCases")
    void testMoveIngredientDifferentPositions(int fromIndex, int toIndex, String description) {
        burger.addIngredient(ingredient1);   // инд = 0
        burger.addIngredient(ingredient2);   // инд = 1
        burger.addIngredient(ingredient3);   // инд = 2

        Ingredient movedIngredient = burger.ingredients.get(fromIndex);
        burger.moveIngredient(fromIndex, toIndex);

        assertEquals(movedIngredient, burger.ingredients.get(toIndex));
    }

    private static Stream<Arguments> provideBurgerPositionsTestCases() {
        return Stream.of(
                Arguments.of(0, 1, "Первый ингредиент с позиции 0 на позицию 1"),
                Arguments.of(1, 0, "Второй ингредиент с позиции 1 на позицию 0"),
                Arguments.of(0, 2, "Первый ингредиент с позиции 0 на позицию 2"),
                Arguments.of(2, 0, "Третий ингредиент с позиции 2 на позицию 0")
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void testRemoveIngredientDifferentPositions(int indexToRemove) {
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);
        burger.addIngredient(ingredient3);

        int initialSize = burger.ingredients.size();
        Ingredient removedIngredient = burger.ingredients.get(indexToRemove);

        burger.removeIngredient(indexToRemove);

        assertEquals(initialSize - 1, burger.ingredients.size());
        assertFalse(burger.ingredients.contains(removedIngredient));
    }
}