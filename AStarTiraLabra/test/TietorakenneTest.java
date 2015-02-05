
import logiikka.tietorakenteet.TaulukkoLista;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Omiin tietorakenteisiin liittyvät testit
 *
 * @author kride
 */
public class TietorakenneTest {

    TaulukkoLista omaLista;

    @Before
    public void setUp() {
        omaLista = new TaulukkoLista();
    }

    @Test
    public void lisaysToimiiPienillä() {
        omaLista.add(5);
        omaLista.add(5);
        omaLista.add(5);
        assertEquals(3, omaLista.size());
    }

    @Test
    public void lisaysToimiiSuurilla() {
        for (int i = 0; i < 1000; i++) {
            omaLista.add(i);

        }
        assertEquals(1000, omaLista.size());
    }

    @Test(timeout = 1000)
    public void lisaysNopeahkoa() {
        for (int i = 0; i < 1000000; i++) {
            omaLista.add(i);

        }
        assertEquals(1000000, omaLista.size());
    }

    @Test(timeout = 1000)
    public void taulukonPalautusOnnistuu() {
        for (int i = 0; i < 1000000; i++) {
            omaLista.add(i);
        }

        Object[] a = omaLista.toArray();
        assertEquals(1000000, a.length);

    }

    @Test
    public void arvonAsettaminenOnnistuu() {
        omaLista.add(5);
        omaLista.set(0, 2);
        assertEquals(2, omaLista.get(0));
    }

    @Test
    public void taulukonTyhjennysOnnistuu() {
        omaLista.add(5);
        omaLista.clear();
        assertEquals(0, omaLista.size());
    }

}
