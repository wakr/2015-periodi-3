package logiikka;

import logiikka.tietorakenteet.MinimiKeko;
import logiikka.tietorakenteet.TaulukkoLista;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Omiin tietorakenteisiin liittyvät testit
 *
 * @author Kristian Wahlroos
 */
public class TietorakenneTest {

    TaulukkoLista omaLista;
    MinimiKeko<Integer> minKeko;

    @Before
    public void setUp() {
        omaLista = new TaulukkoLista();
        minKeko = new MinimiKeko<>();
    }

    // ArrayList
    @Test
    public void lisaysToimiiPienilla() {
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

    @Test
    public void taulukkoTyhjanaAlussa() {
        assertEquals(true, omaLista.isEmpty());
    }

    @Test
    public void loytaaEtsityn() {
        omaLista.add(10);
        assertEquals(true, omaLista.contains(10));
    }

    @Test
    public void eiLoydaTurhaa() {
        omaLista.add(10);
        assertEquals(false, omaLista.contains(122));
    }

    @Test
    public void toArrayToimiiObjekteille() {
        omaLista.add(5);
        omaLista.add(2);
        Object[] o = omaLista.toArray();
        assertEquals(2, o.length);
    }

    @Test
    public void lisaysKohtaanToimii() {
        omaLista.add(5);
        omaLista.add(2);
        omaLista.add(0, 1);
        assertEquals(1, omaLista.get(0));
    }

    @Test
    public void poistoKohdastaToimiiJaAlkioPalautetaan() {
        omaLista.add(5);
        int saatu = (int) omaLista.remove(0);
        assertEquals(5, saatu);
    }

    @Test
    public void indeksiPalautuuOikeinObjektille() {
        omaLista.add(5);
        omaLista.add(2);
        omaLista.add(1);
        assertEquals(2, omaLista.indexOf(1));
    }

    @Test
    public void indeksiPalautuuViimeisestäOikein() {
        omaLista.add(5);
        omaLista.add(2);
        omaLista.add(1);
        omaLista.add(5);
        omaLista.add(2);
        omaLista.add(1);
        omaLista.add(5);
        omaLista.add(2);
        omaLista.add(1);
        assertEquals(8, omaLista.lastIndexOf(1));
    }

    // Minimikeko
    @Test
    public void minimikekoonLisattavatAlkiotLoytyvatSielta() {
        minKeko.add(5);
        minKeko.add(2);
        minKeko.add(6);

        assertEquals(2, (int) minKeko.poll());
        assertEquals(5, (int) minKeko.poll());
        assertEquals(6, (int) minKeko.poll());
    }

    @Test
    public void minimiKeonAlkioidenMaaraOikea() {
        minKeko.add(5);
        minKeko.add(2);
        minKeko.add(6);

        assertEquals(3, minKeko.size());
    }

    @Test
    public void minimiKekoTyhjaAlussa() {
        assertEquals(true, minKeko.isEmpty());
    }

    @Test
    public void minimiKeonEkaLoydetaan() {
        minKeko.add(6);
        assertEquals(6, (int) minKeko.peek());
    }

    @Test
    public void minimiKekoVoidaanTyhjentaa() {
        minKeko.add(5);
        minKeko.add(2);
        minKeko.add(6);
        minKeko.clear();

        assertEquals(true, minKeko.isEmpty());
    }

    @Test
    public void minimiKekoVoidaanIteroida() {
        minKeko.add(6);
        minKeko.add(2);
        minKeko.add(5);

        int sum = 0;

        for (Integer minKeko1 : minKeko) {
            sum += minKeko1;
        }

        assertEquals(13, sum);

    }

}
