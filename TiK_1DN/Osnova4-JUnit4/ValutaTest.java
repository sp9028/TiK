package ltpo.Seznami;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.easymock.EasyMock.*;

public class ValutaTest {

    private Valuta testObject;
    private Valuta expected;

    @Test
    public void testVEvre() throws IOException {
        MenjalnoRazmerje mock = createMock(MenjalnoRazmerje.class);
        expect(mock.getRazmerje("USD", "EUR")).andReturn(1.5);
        replay(mock);
        Valuta actual = testObject.vEvre(mock);
        Assertions.assertEquals(expected, actual);
        verify(mock);
    }
    @Test
    public void testServerMenjalnoRazmerjeUnavailable() throws IOException {
        MenjalnoRazmerje mock = createMock(MenjalnoRazmerje.class);
        expect(mock.getRazmerje("USD", "EUR")).andThrow(new IOException());
        replay(mock);
        Valuta actual = testObject.vEvre(mock);
        Assertions.assertNull(actual);
    }
}
