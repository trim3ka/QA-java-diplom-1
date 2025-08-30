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
    private Bun mockedBun;
    private Burger burger;
    private Ingredient sourCream;
    private Ingredient dinosaur;

    @BeforeEach
    void setUp() {
        //Инициализируем объекты
        burger = new Burger();
        sourCream = new Ingredient(IngredientType.SAUCE, "sour cream", 200.0f);
        dinosaur = new Ingredient(IngredientType.FILLING, "dinosaur", 200.0f);

        when(mockedBun.getPrice()).thenReturn(100.0f);
        when(mockedBun.getName()).thenReturn("black bun");
        burger.setBuns(mockedBun);
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
        burger.addIngredient(sourCream);
        burger.addIngredient(dinosaur);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("(==== black bun ====)"));
        assertTrue(receipt.contains("= sauce sour cream ="));
        assertTrue(receipt.contains("= filling dinosaur ="));
        assertTrue(receipt.contains("Price: 600"));
    }

    @Test
    void testGetReceiptEmptyBurger() {
        //Бургер без ингредиентов

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("(==== black bun ====)"));
        assertFalse(receipt.contains("= null =")); // Не должно быть ингредиентов
        assertTrue(receipt.contains("Price: 200"));
    }

    @Test
    void testGetReceiptIngredientsOrder() {
        burger.addIngredient(sourCream);    // Первый
        burger.addIngredient(dinosaur);     // Второй

        String receipt = burger.getReceipt();

        int sauceIndex = receipt.indexOf("= sauce sour cream =");
        int fillingIndex = receipt.indexOf("= filling dinosaur =");
        assertTrue(sauceIndex < fillingIndex, "Соус должен быть перед начинкой в чеке");
    }
}
