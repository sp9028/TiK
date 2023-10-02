package ltpo.Seznami;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SeznamiUVTest {

    private SeznamiUV uv;

    public SeznamiUVTest() {
    }

    @Before
    public void setUp() {
        uv = new SeznamiUV();
        uv.addImpl("sk", new Sklad<String>());
        uv.addImpl("pv", new PrioritetnaVrsta<String>());
        uv.addImpl("bst", new Bst<String>());
    }

    @Test
    public void testUseSklad() {
        assertEquals("OK", uv.processInput("use sk"));
        doTests();
        testSklad(true);
    }

    @Test
    public void testUsePrioritetnaVrsta() {
        assertEquals("OK", uv.processInput("use pv"));
        doTests();
        testPrioritetnaVrsta(true);
    }

    @Test
    public void testUseBST() {
        assertEquals("OK", uv.processInput("use bst"));
        doTests();
        testBst(true);
    }

    @Test
    public void testUseAllMixed() {
        assertEquals("OK", uv.processInput("use sk"));
        testAddTestSequence();
        assertEquals("OK", uv.processInput("use pv"));
        testAddTestSequence();
        assertEquals("OK", uv.processInput("use bst"));
        testAddTestSequence();
        assertEquals("OK", uv.processInput("use pv"));
        testPrioritetnaVrsta(false);
        assertEquals("OK", uv.processInput("use bst"));
        testBst(false);
        assertEquals("OK", uv.processInput("use sk"));
        testSklad(false);
    }

    @Ignore
    @Test
    public void testSaveOnFullDisk() {

        uv.processInput("use sk");
        String str = "Long string to fill disk...";
        for (int i = 0; i < 1000; i++) {
            uv.processInput("add " + str);
        }
        assertEquals("Error: IO error No space left on device", uv.processInput("save x"));
    }

    @Test
    public void testSaveError() {
        uv.addImpl("skMock", new SkladMock());
        uv.processInput("use skMock");
        assertEquals("Error: IO error No space left on device", uv.processInput("save x"));
    }

    @Test
    public void testSaveErrorEasyMock() throws IOException {

        Sklad mock = EasyMock.createMock(Sklad.class);
        mock.save(EasyMock.anyObject(OutputStream.class));
        EasyMock.expectLastCall().andThrow(new IOException("No space left on device")).atLeastOnce();
        replay(mock);

        uv.addImpl("skEasyMock", mock);
        uv.processInput("use skEasyMock");

        assertEquals("Error: IO error No space left on device", uv.processInput("save x"));

        verify(mock);
    }


    // *****************
    // POMOZNE METODE
    // *****************

    public void doTests() {
        reset();
        testAdd();
        reset();
        testAddNothing();
        reset();
        testRemoveFirst();
        reset();
        testRemoveFirstNothing();
        reset();
        testGetFirst();
        reset();
        testGetFirstNothing();
        reset();
        testResetOnEmpty();
        reset();
        testResetOnFull();
        reset();
        testSizeOnEmpty();
        reset();
        testSizeOne();
        reset();
        testSizeTwo();
        reset();
        testDepthOnEmpty();
        reset();
        testDepthOne();
        reset();
        testDepthTwo();
        reset();
        testIsEmptyEmpty();
        reset();
        testIsEmptyNotEmpty();
        reset();
        testResetOnEmpty();
        reset();
        testResetOnFull();
        reset();
        //testExists(); // to do
        reset();
        //testRemove();  // to do
        reset();
        //testToList();  // to do
        //reset();
    }

    public void reset() {
        uv.processInput("reset");
    }

    public void testAdd() {
        assertEquals("OK", uv.processInput("add Test1"));
        assertEquals("OK", uv.processInput("add Test2"));
    }

    public void testAddNothing() {
        assertEquals("Error: please specify a string", uv.processInput("add"));
    }

    public void testRemoveFirst() {
        assertEquals("OK", uv.processInput("add Test"));
        assertEquals("Test", uv.processInput("remove_first"));
    }

    public void testRemoveFirstNothing() {
        assertEquals("Error: data structure is empty", uv.processInput("remove_first"));
        assertEquals("Error: please specify a string", uv.processInput("add"));
        assertEquals("Error: data structure is empty", uv.processInput("remove_first"));
    }

    public void testGetFirst() {
        assertEquals("OK", uv.processInput("add Test"));
        assertEquals("Test", uv.processInput("get_first"));
    }

    public void testGetFirstNothing() {
        assertEquals("Error: data structure is empty", uv.processInput("get_first"));
        assertEquals("Error: please specify a string", uv.processInput("add"));
        assertEquals("Error: data structure is empty", uv.processInput("get_first"));
    }

    public void testSizeOnEmpty() {
        assertEquals("0", uv.processInput("size"));
    }

    public void testSizeOne() {
        assertEquals("OK", uv.processInput("add Test"));
        assertEquals("1", uv.processInput("size"));
    }

    public void testSizeTwo() {
        assertEquals("OK", uv.processInput("add Test1"));
        assertEquals("OK", uv.processInput("add Test2"));
        assertEquals("2", uv.processInput("size"));
    }

    public void testDepthOnEmpty() {
        assertEquals("0", uv.processInput("depth"));
    }

    public void testDepthOne() {
        assertEquals("OK", uv.processInput("add Test"));
        assertEquals("1", uv.processInput("depth"));
    }

    public void testDepthTwo() {
        assertEquals("OK", uv.processInput("add Test1"));
        assertEquals("OK", uv.processInput("add Test2"));
        assertEquals("2", uv.processInput("depth"));
    }

    public void testIsEmptyEmpty() {
        assertEquals("Data structure is empty.", uv.processInput("is_empty"));
        assertEquals("Error: please specify a string", uv.processInput("add"));
        assertEquals("Data structure is empty.", uv.processInput("is_empty"));
    }

    public void testIsEmptyNotEmpty() {
        assertEquals("OK", uv.processInput("add Test1"));
        assertEquals("OK", uv.processInput("add Test2"));
        assertEquals("OK", uv.processInput("add Test3"));
        assertEquals("Data structure is not empty.", uv.processInput("is_empty"));
    }

    public void testResetOnEmpty() {
        assertEquals("OK", uv.processInput("reset"));
    }

    public void testResetOnFull() {
        assertEquals("OK", uv.processInput("add Test"));
        assertEquals("OK", uv.processInput("reset"));
        assertEquals("Error: data structure is empty", uv.processInput("remove_first"));
        assertEquals("0", uv.processInput("size"));
    }

    // TO DO
    public void testExists() {
        fail("To funkcijo morate implementirati!");
    }

    // TO DO
    public void testRemove() {
        fail("To funkcijo morate implementirati!");
    }

    // TO DO
    public void testToList() {
        fail("To funkcijo morate implementirati!");
    }

    public void testAddTestSequence() {
        assertEquals("OK", uv.processInput("add Test4"));
        assertEquals("OK", uv.processInput("add Test2"));
        assertEquals("OK", uv.processInput("add Test3"));
        assertEquals("OK", uv.processInput("add Test1"));
        assertEquals("OK", uv.processInput("add Test5"));
    }

    public void testSklad(boolean add) {
        if (add) {
            testAddTestSequence();
        }
        assertEquals("Test5", uv.processInput("remove_first"));
        assertEquals("Test1", uv.processInput("remove_first"));
        assertEquals("Test3", uv.processInput("remove_first"));
        assertEquals("Test2", uv.processInput("remove_first"));
        assertEquals("Test4", uv.processInput("remove_first"));
    }

    public void testPrioritetnaVrsta(boolean add) {
        if (add) {
            testAddTestSequence();
        }
        assertEquals("Test5", uv.processInput("remove_first"));
        assertEquals("Test4", uv.processInput("remove_first"));
        assertEquals("Test3", uv.processInput("remove_first"));
        assertEquals("Test2", uv.processInput("remove_first"));
        assertEquals("Test1", uv.processInput("remove_first"));
    }

    public void testBst(boolean add) {
        if (add) {
            testAddTestSequence();
        }
        assertEquals("Test4", uv.processInput("remove_first"));
        assertEquals("Test5", uv.processInput("remove_first"));
        assertEquals("Test2", uv.processInput("remove_first"));
        assertEquals("Test3", uv.processInput("remove_first"));
        assertEquals("Test1", uv.processInput("remove_first"));
    }

}