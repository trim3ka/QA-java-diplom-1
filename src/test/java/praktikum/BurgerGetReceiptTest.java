package praktikum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BurgerGetReceiptTest {

    @Mock
    private Bun bun;

    @Mock
    private Ingredient sauce;

    @Mock
    private Ingredient filling;

    private Burger burger;

    @BeforeEach
    void setUp() {
        burger = new Burger();

        // Настраиваем мок булочки
        when(bun.getPrice()).thenReturn(100.0f);
        when(bun.getName()).thenReturn("black bun");

        burger.setBuns(bun);
    }

    @Test
    void testGetReceiptWithBunOnly() {
        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("(==== black bun ====)"));
        assertTrue(receipt.contains("Price: 200"));
        assertFalse(receipt.contains("= ="));
    }

    @Test
    void testGetReceiptWithTwoIngredients() {
        when(sauce.getType()).thenReturn(IngredientType.SAUCE);
        when(sauce.getName()).thenReturn("sour cream");
        when(sauce.getPrice()).thenReturn(200.0f);

        when(filling.getType()).thenReturn(IngredientType.FILLING);
        when(filling.getName()).thenReturn("dinosaur");
        when(filling.getPrice()).thenReturn(200.0f);

        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("(==== black bun ====)"));
        assertTrue(receipt.contains("= sauce sour cream ="));
        assertTrue(receipt.contains("= filling dinosaur ="));
        assertTrue(receipt.contains("Price: 600"));
    }

    @Test
    void testGetReceiptEmptyBurger() {
        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("(==== black bun ====)"));
        assertFalse(receipt.contains("= null ="));
        assertTrue(receipt.contains("Price: 200"));
    }

    @Test
    void testGetReceiptIngredientsOrder() {
        when(sauce.getType()).thenReturn(IngredientType.SAUCE);
        when(sauce.getName()).thenReturn("sour cream");
        when(sauce.getPrice()).thenReturn(200.0f);

        when(filling.getType()).thenReturn(IngredientType.FILLING);
        when(filling.getName()).thenReturn("dinosaur");
        when(filling.getPrice()).thenReturn(200.0f);

        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        String receipt = burger.getReceipt();

        int sauceIndex = receipt.indexOf("= sauce sour cream =");
        int fillingIndex = receipt.indexOf("= filling dinosaur =");
        assertTrue(sauceIndex < fillingIndex, "Соус должен быть перед начинкой в чеке");
    }
}