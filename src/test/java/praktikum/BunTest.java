package praktikum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BunTest {

    private Bun blackBun;
    private Bun whiteBun;
    private Bun freeBun;

    @BeforeEach
    void setUp() {
        // Инициализация объектов перед тестом
        blackBun = new Bun("black bun", 100.0f);
        whiteBun = new Bun("white bun", 200.0f);
        freeBun = new Bun("free bun", 0.0f);
    }

    @Test
    void testBunNames() {
        assertEquals("white bun", whiteBun.getName());
    }
    @Test
    void testBunPrice() {
        assertEquals(100.0f, blackBun.getPrice());
    }

    @Test
    void testFreeBunPrice() {
        assertEquals(0.0f, freeBun.getPrice());
    }
}