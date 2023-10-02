package ltpo.Seznami;

import org.junit.*;
import static org.junit.Assert.*;

public class SkladTest {

    static Sklad<String> instance;

    public SkladTest() {
    }

    private void addOne() {
        instance.push("Test1");
    }

    private void addFive() {
        instance.push("Test4");
        instance.push("Test1");
        instance.push("Test3");
        instance.push("Test5");
        instance.push("Test2");
    }

    @BeforeClass
    public static void setUpOnce() {
        instance = new Sklad<>();
    }

    @Before
    public void setUp() {
        while (!instance.isEmpty()) {
            instance.pop();
        }
    }

    @Test
    public void testAsListEmpty(){
        assertEquals("[]", instance.asList().toString());
    }

    @Test
    public void testAsListOne(){
        addOne();
        assertEquals("[Test1]", instance.asList().toString());
    }

    @Test
    public void testAsListMany(){
        addFive();
        assertEquals("[Test2, Test5, Test3, Test1, Test4]", instance.asList().toString());
    }

    @Test
    public void testDepth(){
        addFive();
        assertEquals(5, instance.depth());
    }

    @Test
    public void testPush() {
        String a = "Test";
        instance.push(a);
    }

    @Test
    public void testAdd() {
        String a = "Test";
        instance.add(a);
    }

    @Test
    public void testPop() {
        String a = "Test";
        instance.push(a);
        String b = instance.pop();
        assertEquals("Test", b);
    }

    @Test
    public void testPop1() {
        String a = "Test";
        instance.push(a);
        String b = instance.removeFirst();
        assertEquals("Test", b);
    }

    @Test
    public void testWithTwoElements() {
        String a = "Prvi element";
        String b = "Drugi element";
        instance.push(a);
        instance.push(b);
        assertEquals(b, instance.pop());
        assertEquals(a, instance.pop());
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void testPopOnEmptyStack() {
        String a = instance.pop();
    }

    @Test
    public void testIsEmptyOnEmpty() {
        assertTrue(instance.isEmpty());
    }

    @Test
    public void testIsEmptyOnFull() {
        instance.push("Test");
        assertFalse(instance.isEmpty());
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void testPeekOnEmptyStack() {
        String a = instance.peek();
    }

    @Test
    public void testPeekOnFullStack() {
        String a = "Vrednost 1";
        String b = "Vrednost 2";
        instance.push(a);
        instance.push(b);
        String c = instance.peek();
        assertEquals(c, b);
    }

    @Test
    public void testPeekSame() {
        instance.push("Test1");
        instance.push("Test2");
        instance.push("Test3");
        assertEquals("Test3", instance.peek());
        assertEquals("Test3", instance.pop());
        assertEquals("Test2", instance.pop());
        assertEquals("Test1", instance.pop());
        assertTrue(instance.isEmpty());
    }

    @Test
    public void testPeekSame1() {
        instance.push("Test1");
        instance.push("Test2");
        instance.push("Test3");
        assertEquals("Test3", instance.getFirst());
        assertEquals("Test3", instance.pop());
        assertEquals("Test2", instance.pop());
        assertEquals("Test1", instance.pop());
        assertTrue(instance.isEmpty());
    }

    @Test
    public void testCountEmpty() {
        assertEquals(0, instance.size());
    }

    @Test(timeout = 100)
    public void testCountNonEmpty() {
        instance.push("Vrednost 1");
        instance.push("Vrednost 2");
        instance.push("Vrednost 3");
        assertEquals(3, instance.size());
    }

    @Test
    public void testCountSame() {
        instance.push("Test1");
        instance.push("Test2");
        instance.push("Test3");
        assertEquals(3, instance.size());
        assertEquals("Test3", instance.pop());
        assertEquals("Test2", instance.pop());
        assertEquals("Test1", instance.pop());
        assertTrue(instance.isEmpty());
    }

    @Test
    public void testTopTrue() {
        instance.push("Test");
        assertTrue(instance.isTop("Test"));
    }

    @Test
    public void testTopFalse() {
        instance.push("Test1");
        assertFalse(instance.isTop("Test"));
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void testTopEmpty() {
        assertFalse(instance.isTop("Test"));
    }

    @Test
    public void testTopSame() {
        instance.push("Test1");
        instance.push("Test2");
        instance.push("Test3");
        assertTrue(instance.isTop("Test3"));
        assertEquals("Test3", instance.pop());
        assertEquals("Test2", instance.pop());
        assertEquals("Test1", instance.pop());
        assertTrue(instance.isEmpty());
    }

    @Test
    public void testSearchEmpty() {
        assertEquals(-1, instance.search("Test"));
    }

    @Test(timeout = 100)
    public void testSearchFoundTop() {
        instance.push("Vrednost 1");
        instance.push("Vrednost 2");
        instance.push("Vrednost 3");
        assertEquals(0, instance.search("Vrednost 3"));
    }

    @Test(timeout = 100)
    public void testSearchFoundNonTop() {
        instance.push("Vrednost 1");
        instance.push("Vrednost 2");
        instance.push("Vrednost 3");
        assertEquals(2, instance.search("Vrednost 1"));
    }

    @Test(timeout = 100)
    public void testSearchNotFound() {
        instance.push("Vrednost 1");
        instance.push("Vrednost 2");
        instance.push("Vrednost 3");
        assertEquals(-1, instance.search("Vrednost"));
    }

    @Test
    public void testSearchSame() {
        instance.push("Test1");
        instance.push("Test2");
        instance.push("Test3");
        assertEquals(1, instance.search("Test2"));
        assertEquals("Test3", instance.pop());
        assertEquals("Test2", instance.pop());
        assertEquals("Test1", instance.pop());
        assertTrue(instance.isEmpty());
    }

    // Testi za add, removeFirst, getFirst, size in depth
    // so izpuščeni, ker gre le za klice že testiranih metod!

    // TO DO
    // Testi za remove() in exists() ...
    @Test
    public void testRemoveFirst() {
        String str = "Test1";
        instance.push(str);
        assertEquals(str, instance.remove(str));
    }

    @Test
    public void testRemove() {
        String str = "Test1";
        instance.push("Test2");
        instance.push(str);
        assertEquals(str, instance.remove(str));
        assertFalse(instance.exists(str));
    }

    @Test
    public void testRemoveMany() {
        instance.push("Test1");
        instance.push("Test2");
        instance.push("Test3");
        instance.push("Test4");
        assertEquals("Test2", instance.remove("Test2"));
        assertFalse(instance.exists("Test2"));
    }

    @Test(expected =  java.util.NoSuchElementException.class)
    public void testRemoveEmpty() {
        instance.remove("Test1");
    }

    @Test(expected =  java.util.NoSuchElementException.class)
    public void testRemoveNonExistent() {
        instance.push("Test2");
        instance.remove("Test1");
    }

    @Test
    public void testRemoveSame() {
        String str = "Test1";
        instance.push("Test2");
        instance.push("Test3");
        instance.push(str);
        instance.remove(str);
        assertEquals("Test3", instance.pop());
        assertEquals("Test2", instance.pop());
    }

    @Test
    public void exists() {
        String str = "Test1";
        instance.push(str);
        instance.push("Test2");
        instance.push("Test3");
        assertTrue(instance.exists(str));
    }

    @Test
    public void existsNot() {
        instance.push("Test2");
        instance.push("Test3");
        assertFalse(instance.exists("Test1"));
    }

    @Test
    public void existsEmpty() {
        assertFalse(instance.exists("Test1"));
    }

    @Test
    public void testExistsSame() {

        instance.push("Test1");
        instance.push("Test2");
        instance.push("Test3");

        instance.exists("Test1");
        instance.exists("Test3");

        assertEquals("Test3", instance.pop());
        assertEquals("Test2", instance.pop());
        assertEquals("Test1", instance.pop());
    }

}
