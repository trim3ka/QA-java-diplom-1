package praktikum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BunTest {

    private Bun blackBun;
    private Bun whiteBun;
    private Bun redBun;

    @BeforeEach
    void setUp() {
        // Инициализация объектов перед тестом
        blackBun = new Bun("black bun", 100.0f);
        whiteBun = new Bun("white bun", 200.0f);
        redBun = new Bun("red bun", 300.0f);
    }

    @Test
    void testBunNames() {
        assertEquals("black bun", blackBun.getName());
        assertEquals("white bun", whiteBun.getName());
        assertEquals("red bun", redBun.getName());
    }
    @Test
    void testBunPrices() {
        assertEquals(100.0f, blackBun.getPrice());
        assertEquals(200.0f, whiteBun.getPrice());
        assertEquals(300.0f, redBun.getPrice());
    }
}