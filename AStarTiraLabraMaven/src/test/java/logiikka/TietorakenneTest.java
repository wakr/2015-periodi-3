package logiikka;

import java.util.Arrays;
import logiikka.tietorakenteet.Jono;
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
    Jono<Integer> jono;

    @Before
    public void setUp() {
        omaLista = new TaulukkoLista();
        minKeko = new MinimiKeko<>();
        jono = new Jono<>();
    }

    // ArrayList
    @Test
    public void listaanLisaysToimiiPienilla() {
        omaLista.add(5);
        omaLista.add(5);
        omaLista.add(5);
        assertEquals(3, omaLista.size());
    }

    @Test
    public void listaanLisaysToimiiSuurilla() {
        for (int i = 0; i < 1000; i++) {
            omaLista.add(i);

        }
        assertEquals(1000, omaLista.size());
    }

    @Test(timeout = 1000)
    public void listaanLisaysNopeahkoa() {
        for (int i = 0; i < 1000000; i++) {
            omaLista.add(i);

        }
        assertEquals(1000000, omaLista.size());
    }

    @Test(timeout = 1000)
    public void listaantaulukonPalautusOnnistuu() {
        for (int i = 0; i < 1000000; i++) {
            omaLista.add(i);
        }

        Object[] a = omaLista.toArray();
        assertEquals(1000000, a.length);

    }

    @Test
    public void listanArvonAsettaminenOnnistuu() {
        omaLista.add(5);
        omaLista.set(0, 2);
        assertEquals(2, omaLista.get(0));
    }

    @Test
    public void listanTaulukonTyhjennysOnnistuu() {
        omaLista.add(5);
        omaLista.clear();
        assertEquals(0, omaLista.size());
    }

    @Test
    public void listanTaulukkoTyhjanaAlussa() {
        assertEquals(true, omaLista.isEmpty());
    }

    @Test
    public void listastaLoytaaEtsityn() {
        omaLista.add(10);
        assertEquals(true, omaLista.contains(10));
    }

    @Test
    public void listaeiLoydaTurhaa() {
        omaLista.add(10);
        assertEquals(false, omaLista.contains(122));
    }

    @Test
    public void listanToArrayToimiiObjekteille() {
        omaLista.add(5);
        omaLista.add(2);
        Object[] o = omaLista.toArray();
        assertEquals(2, o.length);
    }

    @Test
    public void listaanLisaysKohtaanToimii() {
        omaLista.add(5);
        omaLista.add(2);
        omaLista.add(0, 1);
        assertEquals(1, omaLista.get(0));
    }

    @Test
    public void listastaPistoKohdastaToimiiJaAlkioPalautetaan() {
        omaLista.add(5);
        int saatu = (int) omaLista.remove(0);
        assertEquals(5, saatu);
    }

    @Test
    public void listanIndeksiPalautuuOikeinObjektille() {
        omaLista.add(5);
        omaLista.add(2);
        omaLista.add(1);
        assertEquals(2, omaLista.indexOf(1));
    }

    @Test
    public void listaanObjektinLisaysOnnistuu() {
        omaLista.add(new Solmu(1, 1, 1));
        Solmu s = (Solmu) omaLista.get(0);
        assertEquals(1, s.etaisyysAlusta);
    }

    @Test
    public void listanIndeksiPalautuuViimeisestaOikein() {
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

    @Test(timeout = 1000)
    public void minimikekoonLisaysNopeahkoa() {
        for (int i = 0; i < 1000000; i++) {
            minKeko.add(i);

        }
        for (int i = 0; i < 1000000; i++) {
            minKeko.poll();

        }
        assertEquals(0, minKeko.size());
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

    @Test
    public void minimiKekoTukeeComparableOlioita() {
        MinimiKeko<Solmu> olioKeko = new MinimiKeko<>();
        Solmu pienempi = new Solmu(1, 2, 1);
        Solmu suurempi = new Solmu(5, 3, 1);
        Solmu keski = new Solmu(3, 4, 1);

        olioKeko.add(suurempi);
        olioKeko.add(pienempi);
        olioKeko.add(keski);

        assertEquals(pienempi.tunnus, olioKeko.peek().tunnus);

    }

    // Jono
    @Test
    public void jonoAlussaTyhja() {
        assertTrue(jono.isEmpty());
    }

    @Test
    public void jonoonLisatessaYksiPeekOnLisatty() {
        jono.add(5);
        int l = jono.peek();
        jono.poll();
        assertEquals(5, l);
        assertEquals(true, jono.isEmpty());
    }

    @Test
    public void jonostaPollatessaLukuOnLisatty() {
        jono.add(2);
        assertEquals(2, (int) jono.poll());
    }

    @Test
    public void jononKokoOikein() {
        for (int i = 0; i < 10; i++) {
            jono.add(i);
        }
        assertTrue(jono.size() == 10);
    }

    @Test
    public void jononClearTyhjentaa() {
        for (int i = 0; i < 10; i++) {
            jono.add(i);
        }
        jono.clear();
        assertTrue(jono.isEmpty());
    }

    @Test
    public void jononIteroiminenPollaamisellaTyhjentaaSen() {
        for (int i = 0; i < 10; i++) {
            jono.add(i);
        }
        for (int i = 0; i < 10; i++) {
            jono.poll();
        }
        assertTrue(jono.isEmpty());
    }

    @Test
    public void jononPollaus() {
        int summa = 0;
        for (int i = 0; i < 5; i++) {
            jono.add(i);
        }

        for (int t : jono) {
            summa += t;
        }

        assertTrue(summa == 10);
    }

}
