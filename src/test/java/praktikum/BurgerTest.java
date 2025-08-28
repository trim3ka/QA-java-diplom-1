package praktikum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
    private Ingredient dinosaur;
    private Ingredient sausage;

    @BeforeEach
    void setUp() {
        //Инициализируем объекты
        burger = new Burger();
        hotSauce= new Ingredient(IngredientType.SAUCE, "hot sauce", 100.0f);
        sourCream = new Ingredient(IngredientType.SAUCE, "sour cream", 200.0f);
        chiliSauce = new Ingredient(IngredientType.SAUCE, "chili sauce", 300.0f);
        cutlet = new Ingredient(IngredientType.FILLING, "cutlet", 100.0f);
        dinosaur = new Ingredient(IngredientType.FILLING, "dinosaur", 200.0f);
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

        burger.setBuns(mockedBun);
        burger.addIngredient(hotSauce);
        burger.addIngredient(cutlet);

        assertEquals(2, burger.ingredients.size(), "Должно быть 2 ингридиента");
        assertEquals(hotSauce, burger.ingredients.get(0), "Первый ингридиент - соус");
        assertEquals(cutlet, burger.ingredients.get(1), "Второй ингридиент - котлета");
    }

    @Test
    void testRemoveIngredient() {
        burger.setBuns(mockedBun);
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
    @CsvSource({
            "0, 1", // первый на 2е место
            "1, 0", // второй на 1е место
            "0, 2", // первый на последнее место
            "2, 0"  // последний на 1е место
    })
    void testMoveIngredientDifferentPositions(int fromIndex, int toIndex) {
        burger.addIngredient(hotSauce);
        burger.addIngredient(sourCream);
        burger.addIngredient(cutlet);

        Ingredient movedIngredient = burger.ingredients.get(fromIndex);
        burger.moveIngredient(fromIndex, toIndex);

        assertEquals(movedIngredient, burger.ingredients.get(toIndex));
    }


    @ParameterizedTest
    @CsvSource({
            "black bun, 100.0, sour cream, 200.0, sausage, 200.0, 600.0",
            "white bun, 200.0, hot sauce, 100.0, cutlet, 100.0, 600.0",
            "red bun, 300.0, chili sauce, 300.0, dinosaur, 200.0, 1100.0",
            "black bun, 100.0, chili sauce, 300.0, cutlet, 100.0, 600.0"
    })
    void testGetPriceWithDifferentIngredients(String bunName, float bunPrice,
                                              String ing1Name, float ing1Price,
                                              String ing2Name, float ing2Price,
                                              float expectedPrice) {

        Bun bun = new Bun(bunName, bunPrice);
        Ingredient ing1 = new Ingredient(IngredientType.SAUCE, ing1Name, ing1Price);
        Ingredient ing2 = new Ingredient(IngredientType.FILLING, ing2Name, ing2Price);

        burger.setBuns(bun);
        burger.addIngredient(ing1);
        burger.addIngredient(ing2);

        assertEquals(expectedPrice, burger.getPrice(), 0.001f);
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


    @Test
    void testGetReceiptWithBunOnly() {
        burger.setBuns(mockedBun);
        when(mockedBun.getPrice()).thenReturn(100.0f);
        when(mockedBun.getName()).thenReturn("black bun");

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("(==== black bun ====)"));
        assertTrue(receipt.contains("Price: 200"));
        assertFalse(receipt.contains("= ="));
    }

    @Test
    void testGetReceiptWithTwoIngredients() {
        burger.setBuns(mockedBun);
        burger.addIngredient(sourCream);
        burger.addIngredient(dinosaur);
        when(mockedBun.getPrice()).thenReturn(100.0f);
        when(mockedBun.getName()).thenReturn("black bun");

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("(==== black bun ====)"));
        assertTrue(receipt.contains("= sauce sour cream ="));
        assertTrue(receipt.contains("= filling dinosaur ="));
        assertTrue(receipt.contains("Price: 600"));
    }
}