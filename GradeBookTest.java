import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class GradeBookTest {

    private GradeBook g1;
    private GradeBook g2;

    private static final double DELTA = 0.0001;

    @BeforeEach
    public void setUp() {
        g1 = new GradeBook(5);
        g1.addScore(50.0);
        g1.addScore(75.0);

        g2 = new GradeBook(5);
        g2.addScore(90.0);
        g2.addScore(85.0);
        g2.addScore(70.0);
    }

    @AfterEach
    public void tearDown() {
        g1 = null;
        g2 = null;
    }

    @Test
    public void testAddScoreAndToString() {
        assertEquals("50.0 75.0 ", g1.toString());
        assertEquals("90.0 85.0 70.0 ", g2.toString());
    }

    @Test
    public void testGetScoreSize() {
        assertEquals(2, g1.getScoreSize());
        assertEquals(3, g2.getScoreSize());
    }

    @Test
    public void testSum() {
        assertEquals(125.0, g1.sum(), DELTA);
        assertEquals(245.0, g2.sum(), DELTA);
    }

    @Test
    public void testMinimum() {
        assertEquals(50.0, g1.minimum(), DELTA);
        assertEquals(70.0, g2.minimum(), DELTA);

        GradeBook empty = new GradeBook(5);
        assertEquals(0.0, empty.minimum(), DELTA);
    }

    @Test
    public void testFinalScore() {
        assertEquals(75.0, g1.finalScore(), DELTA);
        assertEquals(175.0, g2.finalScore(), DELTA);

        GradeBook empty = new GradeBook(5);
        assertEquals(0.0, empty.finalScore(), DELTA);

        GradeBook single = new GradeBook(5);
        single.addScore(100.0);
        assertEquals(100.0, single.finalScore(), DELTA);
    }
}