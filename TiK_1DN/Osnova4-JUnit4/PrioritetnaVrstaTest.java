package ltpo.Seznami;

import org.junit.*;
import static org.junit.Assert.*;


public class PrioritetnaVrstaTest {

    private PrioritetnaVrsta<String> pv;

    public PrioritetnaVrstaTest() {}

    private void addOne() {
        pv.add("Test1");
    }

    private void addFive() {
        pv.add("Test3");
        pv.add("Test1");
        pv.add("Test5");
        pv.add("Test4");
        pv.add("Test2");
    }

    @Test
    public void testAsListEmpty(){
        assertEquals("[]", pv.asList().toString());
    }

    @Test
    public void testAsListOne(){
        addOne();
        assertEquals("[Test1]", pv.asList().toString());
    }

    @Test
    public void testAsListMany(){
        addFive();
        assertEquals("[Test5, Test4, Test3, Test1, Test2]", pv.asList().toString());
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        pv = new PrioritetnaVrsta<>(10);
    }

    @After
    public void tearDown() {
    }


    /** Test metod razreda <PrioritetnaVrsta> */

    // testi dodajanja

    @Test
    public void testAddOne() {
        pv.add("Test");
    }

    @Test
    public void testAddMultiple() {
        pv.add("Test1");
        pv.add("Test2");
    }

    //@Ignore("To be implemented later...")
    @Test
    public void testAddOverflow() {
        pv = new PrioritetnaVrsta<>(2);
        pv.add("Test1");
        pv.add("Test2");
        pv.add("Test3");
        pv.add("Test4");
    }

    // testi brisanja

    @Test(expected=java.util.NoSuchElementException.class)
    public void testRemoveFirstEmpty() {
        pv.removeFirst();
    }

    @Test
    public void testRemoveFirstOne() {
        pv.add("Test");
        assertEquals("Test", pv.removeFirst());
        assertFalse(pv.exists("Test"));
    }

    @Test
    public void testRemoveFirstMultiple() {
        pv.add("Test1");
        pv.add("Test5");
        pv.add("Test2");
        pv.add("Test4");
        pv.add("Test3");
        assertEquals("Test5", pv.removeFirst());
        assertFalse(pv.exists("Test5"));
        assertEquals("Test4", pv.removeFirst());
        assertFalse(pv.exists("Test4"));
        assertEquals("Test3", pv.removeFirst());
        assertFalse(pv.exists("Test3"));
        assertEquals("Test2", pv.removeFirst());
        assertFalse(pv.exists("Test2"));
        assertEquals("Test1", pv.removeFirst());
        assertFalse(pv.exists("Test1"));
    }

    // metoda get

    @Test(expected=java.util.NoSuchElementException.class)
    public void testGetFirstEmpty() {
        pv.getFirst();
    }

    @Test
    public void testGetFirstOne() {
        pv.add("Test");
        assertEquals("Test", pv.getFirst());
    }

    @Test
    public void testGetFirstMultiple() {
        pv.add("Test1");
        assertEquals("Test1", pv.getFirst());
        pv.add("Test3");
        pv.add("Test2");
        assertEquals("Test3", pv.getFirst());
        assertEquals("Test3", pv.getFirst());
    }

    // testiranje metode za globino

    @Test
    public void testDepthEmpty() {
        assertEquals(0, pv.depth());
    }

    @Test
    public void testDepthOne() {
        pv.add("Test1");
        assertEquals(1, pv.depth());
    }

    @Test
    public void testDepthMultiple() {
        pv.add("Test1");
        assertEquals(1, pv.depth());
        pv.add("Test5");
        assertEquals(2, pv.depth());
        pv.add("Test2");
        assertEquals(2, pv.depth());
        pv.add("Test4");
        assertEquals(3, pv.depth());
        pv.add("Test3");
        assertEquals(3, pv.depth());
        pv.add("Test6");
        assertEquals(3, pv.depth());
        pv.add("Test8");
        assertEquals(3, pv.depth());
        pv.add("Test7");
        assertEquals(4, pv.depth());
    }

    // test metode size

    @Test
    public void testSizeEmpty() {
        assertEquals(0, pv.size());
    }

    @Test
    public void testSizeOne() {
        pv.add("Test");
        assertEquals(1, pv.size());
    }

    @Test
    public void testSizeMultiple() {
        assertEquals(0, pv.size());
        pv.add("Test");
        assertEquals(1, pv.size());
        pv.add("Test1");
        assertEquals(2, pv.size());
        pv.add("Test2");
        assertEquals(3, pv.size());
    }

    // test metode isEmpty

    @Test
    public void testIsEmptyEmpty() {
        assertTrue(pv.isEmpty());
    }

    @Test
    public void testIsEmptyOne() {
        pv.add("Test");
        assertFalse(pv.isEmpty());
    }

    @Test
    public void testIsEmptyMultiple() {
        pv.add("Test");
        pv.add("Test1");
        pv.add("Test2");
        assertFalse(pv.isEmpty());
    }

    // TO DO
    // Testi za remove in exists

    @Test
    public void testRemoveMin() {
        String str = "1";
        pv.add(str);
        assertEquals(str, pv.remove(str));
    }

    @Test
    public void testRemoveTwo() {
        pv.add("1");
        pv.add("2");
        assertEquals("1", pv.remove("1"));
        assertFalse(pv.exists("1"));
        assertEquals("2", pv.remove("2"));
        assertFalse(pv.exists("2"));
    }

    @Test
    public void testRemoveMultiple() {
        pv.add("Test1");
        pv.add("Test5");
        pv.add("Test2");
        pv.add("Test4");
        pv.add("Test3");
        assertEquals("Test3", pv.remove("Test3"));
        assertFalse(pv.exists("Test3"));
        assertEquals("Test4", pv.remove("Test4"));
        assertFalse(pv.exists("Test4"));
        assertEquals("Test5", pv.remove("Test5"));
        assertFalse(pv.exists("Test5"));
        assertEquals("Test2", pv.remove("Test2"));
        assertFalse(pv.exists("Test2"));
        assertEquals("Test1", pv.remove("Test1"));
        assertFalse(pv.exists("Test1"));
    }

    @Test(expected =  java.util.NoSuchElementException.class)
    public void testRemoveEmpty() {
        pv.remove("Test1");
    }

    @Test(expected =  java.util.NoSuchElementException.class)
    public void testRemoveNonExistent() {
        pv.add("Test2");
        pv.remove("Test1");
    }

    @Test
    public void testRemoveSame() {
        pv.add("Test1");
        pv.add("Test2");
        pv.add("Test4");
        pv.add("Test3");

        pv.remove("Test4");

        assertEquals(3, pv.size());
        assertEquals("Test3", pv.getFirst());

    }

    @Test
    public void exists() {
        String str = "Test1";
        pv.add(str);
        pv.add("Test2");
        pv.add("Test3");
        assertTrue(pv.exists(str));
    }

    @Test
    public void existsNot() {
        pv.add("Test2");
        pv.add("Test3");
        assertFalse(pv.exists("Test1"));
    }

    @Test
    public void existsEmpty() {
        assertFalse(pv.exists("Test1"));
    }

    @Test
    public void testExistsSame() {

        pv.add("Test1");
        pv.add("Test2");
        pv.add("Test3");

        pv.exists("Test1");
        pv.exists("Test3");

        assertEquals("Test3", pv.removeFirst());
        assertEquals("Test2", pv.removeFirst());
        assertEquals("Test1", pv.removeFirst());
    }
}
