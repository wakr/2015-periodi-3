

import extra.Ymparistomuuttuja;
import java.util.ArrayList;
import java.util.Arrays;
import logiikka.AStar;
import logiikka.Analysoija;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Verkon rakennukseen ja analysointiin liittyvät testit
 * @author kride
 */
public class VerkonRakennusTest {

    private AStar aStar;
    private Analysoija analysoija;

    @Test(timeout = 1000)
    public void testaaPienenKartanNaapuritLahdosta() {

        aStar = new AStar(ReitinhakuTest.pieniKartta);
        ArrayList<Integer> luvut = new ArrayList<>();
        luvut.add(1);
        luvut.add(ReitinhakuTest.pieniKartta[0].length);

        assertTrue(aStar.getSolmunNaapurit(0).containsAll(luvut));

    }

    @Test(timeout = 1000)
    public void testaaMiniKartanNaapuritLahdosta() {

        aStar = new AStar(ReitinhakuTest.miniKartta);
        ArrayList<Integer> luvut = new ArrayList<>();
        luvut.add(1);
        luvut.add(ReitinhakuTest.miniKartta[0].length);

        assertTrue(aStar.getSolmunNaapurit(0).containsAll(luvut));

    }

    @Test(timeout = 1000)
    public void testaaKeskiKartanNaapuritLahdosta() {

        aStar = new AStar(ReitinhakuTest.leveaKartta);
        ArrayList<Integer> luvut = new ArrayList<>();
        luvut.add(0);
        luvut.add(ReitinhakuTest.leveaKartta[0].length + 1);

        assertTrue(aStar.getSolmunNaapurit(11).containsAll(luvut));

    }

    @Test(timeout = 1000)
    public void miniKarttaAnalysoidaanOikein() {
        analysoija = new Analysoija();
        aStar = new AStar(ReitinhakuTest.miniKartta);
        int[][] haluttu = new int[][]{
            {1, Ymparistomuuttuja.INF.getArvo(), 1},
            {1, 1, 1}};

        int[][] analysoitu = analysoija.analysoiKarttaArvoiksi(ReitinhakuTest.miniKartta, aStar);
        assertEquals(Arrays.deepToString(haluttu), Arrays.deepToString(analysoitu));
    }

    @Test(timeout = 1000)
    public void pieninKarttaAnalysoidaanOikein() {
        analysoija = new Analysoija();
        aStar = new AStar(ReitinhakuTest.pieninKartta);
        int[][] haluttu = new int[][]{
            {1, 1},
            {1, 1}};

        int[][] analysoitu = analysoija.analysoiKarttaArvoiksi(ReitinhakuTest.pieninKartta, aStar);
        assertEquals(Arrays.deepToString(haluttu), Arrays.deepToString(analysoitu));
    }

    @Test(timeout = 1000)
    public void pieniKarttaAnalysoidaanOikein() {
        analysoija = new Analysoija();
        aStar = new AStar(ReitinhakuTest.pieniKartta);
        int nf = Ymparistomuuttuja.INF.getArvo();
        int[][] haluttu = new int[][]{
            {1, nf, nf, nf, 1},
            {1, nf, nf, nf, 1},
            {1, nf, nf, nf, 1},
            {1, 1, 1, 1, 1}};

        int[][] analysoitu = analysoija.analysoiKarttaArvoiksi(ReitinhakuTest.pieniKartta, aStar);
        assertEquals(Arrays.deepToString(haluttu), Arrays.deepToString(analysoitu));
    }

    @Test(timeout = 1000)
    public void leveaKarttaAnalysoidaanOikein() {
        analysoija = new Analysoija();
        aStar = new AStar(ReitinhakuTest.leveaKartta);
        int nf = Ymparistomuuttuja.INF.getArvo();
        int[][] haluttu = new int[][]{
            {1, nf, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};

        int[][] analysoitu = analysoija.analysoiKarttaArvoiksi(ReitinhakuTest.leveaKartta, aStar);
    }
}
