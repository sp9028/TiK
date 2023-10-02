package ltpo.Seznami;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Drevo23Test {
    private Drevo23<String> drevo23;

    @Before
    public void setUp() {
        drevo23 = new Drevo23<>();
    }

    private void addOne() {
        drevo23.add("Test");
    }

    private void addTwo() {
        drevo23.add("Test1");
        drevo23.add("Test2");
    }

    private void addMany() {
        drevo23.add("Test1");
        drevo23.add("Test2");
        drevo23.add("Test3");
        drevo23.add("Test4");
        drevo23.add("Test5");
    }

    private void containsOne(){
        assertTrue(drevo23.exists("Test"));
    }

    private void containsTwo() {
        assertTrue(drevo23.exists("Test1"));
        assertTrue(drevo23.exists("Test2"));
    }

    private void containsMany(){
        assertTrue(drevo23.exists("Test1"));
        assertTrue(drevo23.exists("Test2"));
        assertTrue(drevo23.exists("Test3"));
        assertTrue(drevo23.exists("Test4"));
        assertTrue(drevo23.exists("Test5"));

    }

    @Test
    public void testAddOne(){
        addOne();
        containsOne();
    }

    @Test
    public void testAddTwo(){
        addTwo();
        containsTwo();
    }

    @Test
    public void testAddMany(){
        addMany();
        containsMany();
    }

    @Test
    public void testAddSameElements() {
        addOne();
        addOne();
        containsOne();
    }

    @Test
    public void testExists(){
        addOne();
        assertTrue(drevo23.exists("Test"));
        assertFalse(drevo23.exists("kar nekaj"));
    }

    @Test
    public void testExistsTwo(){
        addTwo();
        assertTrue(drevo23.exists("Test2"));
        assertTrue(drevo23.exists("Test1"));
        assertFalse(drevo23.exists("element"));
    }

    @Test
    public void testExistsMany(){
        addMany();
        assertTrue(drevo23.exists("Test2"));
        assertTrue(drevo23.exists("Test1"));
        assertTrue(drevo23.exists("Test4"));
        assertTrue(drevo23.exists("Test3"));
        assertTrue(drevo23.exists("Test5"));
    }

    @Test
    public void testSizeEmpty(){
        assertEquals(0, drevo23.size());
    }

    @Test
    public void testSizeOne(){
        addOne();
        assertEquals(1, drevo23.size());
    }

    @Test
    public void testSizeTwo(){
        addTwo();
        assertEquals(2, drevo23.size());
    }

    @Test
    public void testSizeMany(){
        addMany();
        assertEquals(5, drevo23.size());
    }

    @Test
    public void testisEmpty(){
        assertTrue(drevo23.isEmpty());
    }

    @Test
    public void testDepthEmpty() {
        assertEquals(0, drevo23.depth());
    }

    @Test
    public void testDepth() {
        addOne();
        assertEquals(1, drevo23.depth());
    }

    @Test
    public void testDepthTwo() {
        addTwo();
        assertEquals(1, drevo23.depth());
    }

    @Test
    public void testDepthMany(){
        addMany();
        assertEquals(2, drevo23.depth());
    }

    @Test
    public void testFindMin(){
        addOne();
        assertEquals(drevo23.findMin(), "Test");
    }

    @Test
    public void testGetFirst(){
        addOne();
        assertEquals(drevo23.getFirst(), "Test");
    }

    @Test
    public void testGetFirst2(){
        addTwo();
        assertEquals(drevo23.getFirst(), "Test1");
    }

    @Test
    public void testGetFirst3(){
        addMany();
        assertEquals(drevo23.getFirst(), "Test1");
    }

    private void addTwoReversed() {
        drevo23.add("Test2");
        drevo23.add("Test1");
    }

    @Test
    public void testAddTwoReversed() {
        addTwoReversed();
        containsTwo();
    }

    @Test
    public void testAddManyReversed() {
        drevo23.add("l");
        drevo23.add("k");
        drevo23.add("j");
        drevo23.add("i");
        drevo23.add("h");
        drevo23.add("g");
        drevo23.add("f");
        drevo23.add("e");
        drevo23.add("d");
        drevo23.add("c");
        drevo23.add("b");
        drevo23.add("a");

        assertEquals("[e, c, a, b, d, g, f, h, i, k, j, l]",
                drevo23.asList().toString());
    }

    @Test
    public void testAddManyReversed1() {
        drevo23.add("a");
        drevo23.add("l");
        drevo23.add("b");
        drevo23.add("k");
        drevo23.add("j");
        drevo23.add("c");
        drevo23.add("d");
        drevo23.add("i");
        drevo23.add("e");
        drevo23.add("h");
        drevo23.add("f");
        drevo23.add("g");

        assertEquals("[d, b, a, c, f, e, g, h, i, k, j, l]",
                drevo23.asList().toString());
    }

    @Test
    public void testAddManyMore(){
        drevo23.add("6");
        drevo23.add("5");
        drevo23.add("4");
        drevo23.add("3");
        drevo23.add("1");
        drevo23.add("2");
        drevo23.add("9");
        drevo23.add("10");
        drevo23.add("12");
        drevo23.add("13");
        drevo23.add("15");
        drevo23.add("20");
        drevo23.add("122");
        drevo23.add("100");
        drevo23.add("16");

        assertEquals("[13, 10, 1, 100, 12, 122, 2, 15, 16, 20, 3, 5, 4, 6, 9]", drevo23.asList().toString());
    }

    @Test
    public void testRemove() {
        addOne();
        assertEquals("Test", drevo23.remove("Test"));
        assertEquals(0, drevo23.size());
    }

    @Test
    public void testRemoveTwo(){
        addTwo();
        assertEquals("Test1", drevo23.remove("Test1"));
        assertFalse(drevo23.exists("Test1"));
        assertEquals("Test2", drevo23.remove("Test2"));
        assertFalse(drevo23.exists("Test2"));
    }

    @Test
    public void testRemoveMany(){
        addMany();

        assertEquals("Test1", drevo23.remove("Test1"));
        assertFalse(drevo23.exists("Test1"));

        assertEquals("Test3", drevo23.remove("Test3"));
        assertFalse(drevo23.exists("Test3"));

        assertEquals("Test2", drevo23.remove("Test2"));
        assertFalse(drevo23.exists("Test2"));

        assertEquals("Test4", drevo23.remove("Test4"));
        assertFalse(drevo23.exists("Test4"));

        assertEquals("Test5", drevo23.remove("Test5"));
        assertFalse(drevo23.exists("Test5"));

        assertEquals(0, drevo23.size());
    }

    @Test
    public void testRemoveRight(){
        drevo23.add("a");
        drevo23.add("b");
        drevo23.add("c");
        drevo23.add("d");
        drevo23.add("e");
        drevo23.add("f");
        drevo23.add("g");
        drevo23.add("h");

        drevo23.remove("h");
        assertEquals("[d, b, a, c, f, e, g]", drevo23.asList().toString());
        drevo23.remove("g");
        assertEquals("[c, a, b, d, e, f]", drevo23.asList().toString());
        drevo23.remove("f");
        assertEquals("[c, a, b, d, e]", drevo23.asList().toString());
        drevo23.remove("e");
        assertEquals("[c, a, b, d]", drevo23.asList().toString());
        drevo23.remove("d");
        assertEquals("[b, a, c]", drevo23.asList().toString());
        drevo23.remove("b");
        assertEquals("[a, c]", drevo23.asList().toString());
        drevo23.remove("c");
        assertEquals("[a]", drevo23.asList().toString());
        drevo23.remove("a");
        assertEquals("[]", drevo23.asList().toString());

    }

    @Test
    public void testRemoveMiddle(){
        drevo23.add("1");
        drevo23.add("2");
        drevo23.add("3");
        drevo23.add("4");
        drevo23.add("5");
        drevo23.add("6");
        drevo23.add("7");
        drevo23.add("8");

        drevo23.remove("4");
        assertEquals("[2, 1, 3, 5, 6, 7, 8]", drevo23.asList().toString());
        drevo23.remove("5");
        assertEquals("[2, 1, 3, 6, 7, 8]", drevo23.asList().toString());
        drevo23.remove("6");
        assertEquals("[2, 1, 3, 7, 8]", drevo23.asList().toString());
        drevo23.remove("3");
        assertEquals("[2, 1, 7, 8]", drevo23.asList().toString());
        drevo23.remove("1");
        assertEquals("[7, 2, 8]", drevo23.asList().toString());
        drevo23.remove("2");
        assertEquals("[7, 8]", drevo23.asList().toString());
        drevo23.remove("7");
        assertEquals("[8]", drevo23.asList().toString());
        drevo23.remove("8");
        assertEquals("[]", drevo23.asList().toString());
    }

    @Test
    public void testRemoveFirst(){
        addOne();
        assertEquals("Test", drevo23.removeFirst());
    }

}
