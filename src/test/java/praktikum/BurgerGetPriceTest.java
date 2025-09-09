package praktikum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BurgerGetPriceTest {
    private static final float PRICE_DELTA = 0.001f;

    private Burger burger;

    @Mock
    private Bun bun;
    @Mock
    private Ingredient sauce;
    @Mock
    private Ingredient filling;

    @BeforeEach
    void setUp() {
        burger = new Burger();
    }

    @ParameterizedTest
    @MethodSource("bunPrices")
    void testGetPriceWithBunOnly(float bunPrice, float expectedPrice) {
        when(bun.getPrice()).thenReturn(bunPrice);

        burger.setBuns(bun);

        assertEquals(expectedPrice, burger.getPrice(), PRICE_DELTA);
    }

    private static Stream<Arguments> bunPrices() {
        return Stream.of(
                Arguments.of(100.0f, 200.0f),
                Arguments.of(200.0f, 400.0f),
                Arguments.of(300.0f, 600.0f),
                Arguments.of(0.0f, 0.0f)
        );
    }

    @ParameterizedTest
    @MethodSource("burgerPrices")
    void testGetPriceWithDifferentIngredients(float bunPrice, float saucePrice,
                                              float fillingPrice, float expectedPrice) {
        when(bun.getPrice()).thenReturn(bunPrice);
        when(sauce.getPrice()).thenReturn(saucePrice);
        when(filling.getPrice()).thenReturn(fillingPrice);

        burger.setBuns(bun);
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        assertEquals(expectedPrice, burger.getPrice(), PRICE_DELTA);
    }

    private static Stream<Arguments> burgerPrices() {
        return Stream.of(
                Arguments.of(100.0f, 200.0f, 200.0f, 600.0f),
                Arguments.of(200.0f, 100.0f, 100.0f, 600.0f),
                Arguments.of(300.0f, 300.0f, 200.0f, 1100.0f),
                Arguments.of(100.0f, 300.0f, 100.0f, 600.0f)
        );
    }
}