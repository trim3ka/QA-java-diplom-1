package praktikum;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BurgerGetPriceTest {
    private Burger burger;

    @ParameterizedTest
    @MethodSource("provideBunOnlyTestCases")
    void testGetPriceWithBunOnly(String bunName, float bunPrice, float expectedPrice) {
        Bun bun = new Bun(bunName, bunPrice);

        burger = new Burger();
        burger.setBuns(bun);

        assertEquals(expectedPrice, burger.getPrice(), 0.001f);
    }

    private static Stream<Arguments> provideBunOnlyTestCases() {
        return Stream.of(
                Arguments.of("black bun", 100.0f, 200.0f),  // 100 * 2
                Arguments.of("white bun", 200.0f, 400.0f),  // 200 * 2
                Arguments.of("red bun", 300.0f, 600.0f),    // 300 * 2
                Arguments.of("free bun", 0.0f, 0.0f)        // 0 * 2
        );
    }

    @ParameterizedTest
    @MethodSource("provideBurgerPriceTestCases")
    void testGetPriceWithDifferentIngredients(String bunName, float bunPrice,
                                              String ing1Name, float ing1Price,
                                              String ing2Name, float ing2Price,
                                              float expectedPrice) {


        Bun bun = new Bun(bunName, bunPrice);
        Ingredient ing1 = new Ingredient(IngredientType.SAUCE, ing1Name, ing1Price);
        Ingredient ing2 = new Ingredient(IngredientType.FILLING, ing2Name, ing2Price);

        burger = new Burger();
        burger.setBuns(bun);
        burger.addIngredient(ing1);
        burger.addIngredient(ing2);

        assertEquals(expectedPrice, burger.getPrice(), 0.001f);
    }

    private static Stream<Arguments> provideBurgerPriceTestCases() {
        return Stream.of(
                Arguments.of("black bun", 100.0f, "sour cream", 200.0f, "sausage", 200.0f, 600.0f),
                Arguments.of("white bun", 200.0f, "hot sauce", 100.0f, "cutlet", 100.0f, 600.0f),
                Arguments.of("red bun", 300.0f, "chili sauce", 300.0f, "dinosaur", 200.0f, 1100.0f),
                Arguments.of("black bun", 100.0f, "chili sauce", 300.0f, "cutlet", 100.0f, 600.0f)
        );
    }
}
