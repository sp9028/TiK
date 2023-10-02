package ltpo.Seznami;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BstTest {

    private Bst<String> bst;

    public BstTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        bst = new Bst<>();
    }

    @After
    public void tearDown() {
    }

    private void addOne() {
        bst.add("Test1");
    }

    private void addFive() {
        bst.add("Test3");
        bst.add("Test1");
        bst.add("Test5");
        bst.add("Test4");
        bst.add("Test2");
    }

    @Test
    public void testAsListEmpty(){
        assertEquals("[]", bst.asList().toString());
    }

    @Test
    public void testAsListOne(){
        addOne();
        assertEquals("[Test1]", bst.asList().toString());
    }

    @Test
    public void testAsListMany(){
        addFive();
        assertEquals("[Test1, Test2, Test3, Test4, Test5]", bst.asList().toString());
    }


    @Test
    public void testAdd() {
        addOne();
        assertEquals(1, bst.size());
        assertEquals("Test1", bst.getFirst());
    }

    @Test
    public void testAddMany() {
        addFive();
        assertEquals(5, bst.size());
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testAddSameElements() {
        addFive();
        addFive();
    }

    @Test
    public void testRemoveFirst(){
        addOne();
        assertEquals("Test1", bst.removeFirst());
        assertFalse(bst.exists("Test1"));
    }

    @Test(expected =  java.util.NoSuchElementException.class)
    public void testRemoveFirstEmpty() {
        bst.removeFirst();
    }

    @Test
    public void testRemoveFirstMany(){
        addFive();
        int c = bst.size();
        assertEquals("Test3", bst.removeFirst());
        assertEquals(c-1, bst.size());
    }

    @Test
    public void testRemoveFirstSame() {
        addFive();
        assertEquals("Test3", bst.removeFirst());
        assertEquals("Test4", bst.getFirst());
    }

    @Test
    public void testGetFirstOne() {
        bst.add("Test");
        assertEquals("Test", bst.getFirst());
        assertEquals(1, bst.size());
        assertEquals(1, bst.depth());
    }

    @Test
    public void testGetFirstMultiple() {
        bst.add("Test2");
        assertEquals("Test2", bst.getFirst());
        assertEquals(1, bst.size());
        assertEquals(1, bst.depth());
        bst.add("Test3");
        bst.add("Test1");
        assertEquals("Test2", bst.getFirst());
        assertEquals("Test2", bst.getFirst());
        assertEquals(3, bst.size());
        assertEquals(2, bst.depth());
    }

    @Test
    public void testGetFirstSame() {
        addFive();
        bst.getFirst();
        assertTrue(bst.exists("Test4"));
        assertEquals(5, bst.size());
        assertEquals("Test3", bst.getFirst());
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void testGetFirstOnEmpty() {
        bst.getFirst();
    }

    @Test
    public void testSizeOnEmpty() {
        assertEquals(0, bst.size());
    }

    @Test
    public void testSize(){
        addOne();
        assertEquals(1, bst.size());
    }

    @Test
    public void testSizeMany(){
        addFive();
        assertEquals(5, bst.size());
    }

    @Test
    public void testDepthOnEmpty() {
        assertEquals(0, bst.depth());
    }

    @Test
    public void testDepth(){
        addOne();
        assertEquals(1, bst.depth());
    }

    @Test
    public void testDepthMany(){
        addFive();
        assertEquals(3, bst.depth());
    }

    @Test
    public void testDepthSame(){
        addFive();
        bst.depth();
        assertTrue(bst.exists("Test4"));
        assertEquals(5, bst.size());
        assertEquals("Test3", bst.getFirst());
    }

    @Test
    public void isEmpty(){
        assertTrue(bst.isEmpty());
    }

    @Test
    public void isEmptyOne(){
        addOne();
        assertFalse(bst.isEmpty());
    }

    @Test
    public void isEmptyMany(){
        addFive();
        assertFalse(bst.isEmpty());
    }

    @Test
    public void testExists(){
        addOne();
        assertTrue(bst.exists("Test1"));
    }

    @Test
    public void testExistsEmpty(){
        assertFalse(bst.exists("Test"));
    }

    @Test
    public void testExistsMany(){
        addFive();
        assertTrue(bst.exists("Test2"));
    }

    @Test
    public void testExistsNot(){
        addOne();
        assertFalse(bst.exists("Test"));
    }

    @Test
    public void testExistsManyNot(){
        addFive();
        assertFalse(bst.exists("Test"));
    }

    @Test
    public void testRemove(){
        addOne();
        assertEquals("Test1", bst.remove("Test1"));
        assertEquals(0, bst.size());
        assertFalse(bst.exists("Test1"));
    }

    @Test(expected =  java.util.NoSuchElementException.class)
    public void testRemoveNonExistent(){
        addOne();
        bst.remove("Test");
    }

    @Test(expected =  java.util.NoSuchElementException.class)
    public void testRemoveNonExistentMany(){
        addFive();
        bst.remove("Test");
    }

    @Test(expected =  java.util.NoSuchElementException.class)
    public void testRemoveEmpty(){
        bst.remove("Test1");
    }

    @Test
    public void testRemoveMany(){
        addFive();
        assertEquals("Test2", bst.remove("Test2"));
        assertFalse(bst.exists("Test2"));
        assertEquals("Test5", bst.remove("Test5"));
        assertFalse(bst.exists("Test5"));
        assertEquals("Test3", bst.remove("Test3"));
        assertFalse(bst.exists("Test3"));
        assertEquals("Test1", bst.remove("Test1"));
        assertFalse(bst.exists("Test1"));
        assertEquals("Test4", bst.remove("Test4"));
        assertFalse(bst.exists("Test4"));
        assertEquals(0, bst.size());
    }

    @Test
    public void testRemoveSame(){
        addFive();
        bst.remove("Test5");
        assertEquals(4, bst.size());
        assertEquals(3, bst.depth());
        assertTrue(bst.exists("Test1"));
        assertEquals("Test3", bst.getFirst());
    }

}
