package praktikum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BunTest {

    @Mock
    private Database database;

    private Bun blackBun;
    private Bun whiteBun;
    private Bun redBun;

    @BeforeEach
    void setUp() {
        // Инициализация объектов перед КАЖДЫМ тестом
        blackBun = new Bun("black bun", 100);
        whiteBun = new Bun("white bun", 200);
        redBun = new Bun("red bun", 300);
    }

    @Test
    void testBunNames() {
        assertEquals("black bun", blackBun.getName());
        assertEquals("white bun", whiteBun.getName());
        assertEquals("red bun", redBun.getName());
    }
    @Test
    void testBunPrices() {
        assertEquals(100, blackBun.getPrice());
        assertEquals(200, whiteBun.getPrice());
        assertEquals(300, redBun.getPrice());
    }

}