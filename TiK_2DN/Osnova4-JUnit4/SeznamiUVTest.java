package ltpo.Seznami;
import org.easymock.EasyMock;
import org.junit.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

public class SeznamiUVTest {
    private SeznamiUV uv;

    public SeznamiUVTest() {
    }

    @Before
    public void setUp() {
        uv = new SeznamiUV();
    }

    @Test
    public void testNoCommand() {
        assertEquals(">> Napačen ukaz", uv.processInput(""));
    }

    protected String getEmsoString() {
        String SALTCHARS = "1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 13) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    protected String getImePriimekString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 7) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    protected String getKategorijaString() {
        String SALTCHARS = "ABCDEFG";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 1) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    private String[] randomData(int seed) {
        Random leto = new Random();
        String[] data = new String[5];
        data[0] = getEmsoString();
        data[1] = getImePriimekString();
        data[2] = getImePriimekString();;
        data[3] = getKategorijaString();
        data[4] = String.valueOf(leto.nextInt(1970, 2001));
        return data;
    }

    private String[] addOseba(int seed) {
        String[] data = randomData(seed);

        assertEquals("dodaj> EMŠO: ", uv.processInput("dodaj"));
        assertEquals("dodaj> IME: ", uv.processInput(data[0]));
        assertEquals("dodaj> PRIIMEK: ", uv.processInput(data[1]));
        assertEquals("dodaj> KATEGORIJA: ", uv.processInput(data[2]));
        assertEquals("dodaj> LETO PRIDOBITVE: ", uv.processInput(data[3]));
        assertEquals(">> V redu", uv.processInput(data[4]));

        return data;
    }

    private void reset(){
        uv.processInput("izprazni");
        uv.processInput("d");
    }

    @Test
    public void testAddOseba() {
        addOseba(5);
    }

    @Test
    public void testOseba() {
        addOseba(5);
        assertEquals(">> Število oseb: 1", uv.processInput("preštej"));
    }

    private void count(int i) {
        assertEquals(">> Število oseb: " + i, uv.processInput("preštej"));
    }

    @Test
    public void testAddFive(){
        for (int i = 0; i < 5; i++) {
            addOseba(i);
        }
        count(5);
    }

    @Test
    public  void testAddMore(){
        for (int i = 0; i < 100; i++) {
            addOseba(i);
        }
        count(100);
    }

    @Test
    public void testAddInvalidArgument() {
        assertEquals(">> Napačen argument", uv.processInput("dodaj argument"));

        count(0);
    }

    @Test
    public void addOsebaNotrandom(){
        assertEquals("dodaj> EMŠO: ", uv.processInput("dodaj"));
        assertEquals("dodaj> IME: ", uv.processInput("1231231231234"));
        assertEquals("dodaj> PRIIMEK: ", uv.processInput("Janez"));
        assertEquals("dodaj> KATEGORIJA: ", uv.processInput("Novak"));
        assertEquals("dodaj> LETO PRIDOBITVE: ", uv.processInput("B"));
        assertEquals(">> V redu", uv.processInput("2002"));

    }

    @Test
    public void testDuplicate(){
        addOsebaNotrandom();
        assertEquals("dodaj> EMŠO: ", uv.processInput("dodaj"));
        assertEquals("dodaj> IME: ", uv.processInput("1231231231234"));
        assertEquals("dodaj> PRIIMEK: ", uv.processInput("Janez"));
        assertEquals("dodaj> KATEGORIJA: ", uv.processInput("Novak"));
        assertEquals("dodaj> LETO PRIDOBITVE: ", uv.processInput("B"));
        assertEquals(">> Oseba že obstaja", uv.processInput("2002"));
    }

    @Test
    public void testAddInvalidData(){
        assertEquals("dodaj> EMŠO: ", uv.processInput("dodaj"));
        assertEquals("dodaj> IME: ", uv.processInput("123123123123"));
        assertEquals("dodaj> PRIIMEK: ", uv.processInput("Janez"));
        assertEquals("dodaj> KATEGORIJA: ", uv.processInput("Novak"));
        assertEquals("dodaj> LETO PRIDOBITVE: ", uv.processInput("B"));
        assertEquals(">> Napačni podatki", uv.processInput("2002"));
    }

    @Test
    public void testWrongArgument(){
        assertEquals(">> Napačen argument", uv.processInput("preštej 123"));
        assertEquals(">> Napačen argument", uv.processInput("dodaj 123"));
        assertEquals(">> Napačen argument", uv.processInput("izprazni 123"));
        assertEquals(">> Napačen argument", uv.processInput("izpiši 123"));
    }

    @Test
    public void testReset(){
        testAddFive();
        count(5);
        assertEquals("izprazni> Ste prepričani? (d|n): ", uv.processInput("izprazni"));
        assertEquals(">> Ukaz je bil preklican", uv.processInput("n"));
        assertEquals("izprazni> Ste prepričani? (d|n): ", uv.processInput("izprazni"));
        assertEquals(">> Napačen ukaz", uv.processInput("test"));
        assertEquals("izprazni> Ste prepričani? (d|n): ", uv.processInput("izprazni"));
        assertEquals(">> V redu", uv.processInput("d"));
        count(0);

    }

    @Test
    public void testRemoveByEmso(){
        addOsebaNotrandom();
        assertEquals(">> V redu", uv.processInput("odstrani 1231231231234"));
        count(0);
        addOsebaNotrandom();
        assertEquals(">> Oseba ne obstaja", uv.processInput("odstrani 0980980981234"));
        count(1);
        assertEquals(">> Napačni podatki", uv.processInput("odstrani 123"));
        count(1);
    }

    @Test
    public void testRemoveByName(){
        addOsebaNotrandom();
        count(1);
        assertEquals("odstrani> IME: ", uv.processInput("odstrani"));
        assertEquals("odstrani> PRIIMEK: ", uv.processInput("Janez"));
        assertEquals(">> V redu", uv.processInput("Novak"));
        count(0);
        addOsebaNotrandom();
        assertEquals("odstrani> IME: ", uv.processInput("odstrani"));
        assertEquals("odstrani> PRIIMEK: ", uv.processInput("Janez"));
        assertEquals(">> Oseba ne obstaja", uv.processInput("Anton"));
        assertEquals("odstrani> IME: ", uv.processInput("odstrani"));
        assertEquals("odstrani> PRIIMEK: ", uv.processInput(" "));
        assertEquals(">> Napačni podatki", uv.processInput(" "));
        assertEquals("odstrani> IME: ", uv.processInput("odstrani"));
        assertEquals("odstrani> PRIIMEK: ", uv.processInput("Micka"));
        assertEquals(">> Oseba ne obstaja", uv.processInput("Kovaceva"));
    }

    @Test
    public void testSearchByEmso(){
        addOsebaNotrandom();
        assertEquals(">> Napačen ukaz", uv.processInput("poišči1"));
        assertEquals(">> Napačni podatki", uv.processInput("poišči 123"));
        assertEquals(">> Oseba ne obstaja", uv.processInput("poišči 0980980981234"));
        assertEquals(">> 1231231231234 | Novak, Janez | B | 2002", uv.processInput("poišči 1231231231234"));
    }

    @Test
    public void testSearchByName(){
        addOsebaNotrandom();
        assertEquals("poišči> IME: ", uv.processInput("poišči"));
        assertEquals("poišči> PRIIMEK: ", uv.processInput("Janez"));
        assertEquals(">> 1231231231234 | Novak, Janez | B | 2002", uv.processInput("Novak"));
        assertEquals("poišči> IME: ", uv.processInput("poišči"));
        assertEquals("poišči> PRIIMEK: ", uv.processInput("Janez"));
        assertEquals(">> Napačni podatki", uv.processInput(" "));
        assertEquals("poišči> IME: ", uv.processInput("poišči"));
        assertEquals("poišči> PRIIMEK: ", uv.processInput("Janez"));
        assertEquals(">> Oseba ne obstaja", uv.processInput("Meglic"));
        assertEquals("poišči> IME: ", uv.processInput("poišči"));
        assertEquals("poišči> PRIIMEK: ", uv.processInput("Micka"));
        assertEquals(">> Oseba ne obstaja", uv.processInput("Antonic"));
    }

    @Test
    public void testResetMany(){
        testAddMore();
        count(100);
        assertEquals("izprazni> Ste prepričani? (d|n): ", uv.processInput("izprazni"));
        assertEquals(">> V redu", uv.processInput("d"));
        count(0);
    }

    @Test
    public void testPrint(){
        addOsebaNotrandom();
        assertEquals(">> Število oseb: 1\n\t1231231231234 | Novak, Janez | B | 2002", uv.processInput("izpiši"));
    }

    @Test
    public void testSave(){
        testAddMore();
        assertEquals(">> V redu", uv.processInput("shrani datoteka.bin"));
    }

    @Test
    public void testRestore(){
        reset();
        addOsebaNotrandom();
        assertEquals(">> V redu", uv.processInput("shrani datoteka.bin"));
        reset();
        assertEquals(">> V redu", uv.processInput("povrni datoteka.bin"));
        count(1);
    }

    @Test
    public void countEasyMock() throws IOException {

        Bst<Oseba> mock = EasyMock.createMock(Bst.class);
        mock.size();
        EasyMock.expectLastCall().andReturn(0).atLeastOnce();
        replay(mock);

        uv.setMockObj(mock);

        assertEquals(">> Število oseb: 0", uv.processInput("preštej"));

        verify(mock);
    }


    @Test
    public void testSaveErrorEasyMock() throws IOException {

        Bst<Oseba> mock = EasyMock.createMock(Bst.class);
        mock.save(EasyMock.anyObject(OutputStream.class));
        EasyMock.expectLastCall().andThrow(new IOException("No space left on device")).atLeastOnce();
        replay(mock);

        uv.setMockObj(mock);

        assertEquals(">> I/O Napaka: No space left on device", uv.processInput("shrani x"));

        verify(mock);
    }

    @Test
    public void testRestoreErrorEasyMock() throws IOException, ClassNotFoundException {
        Bst<Oseba> mock = EasyMock.createMock(Bst.class);
        mock.restore(EasyMock.anyObject(InputStream.class));
        EasyMock.expectLastCall().andThrow(new IOException("filename (No such file or directory)")).anyTimes();
        replay(mock);

        uv.setMockObj(mock);

        assertEquals(">> I/O Napaka: filename (The system cannot find the file specified)", uv.processInput("povrni filename"));

        verify(mock);
    }



}
