package logiikka;

import logiikka.Ymparistomuuttuja;
import java.util.ArrayList;
import java.util.Arrays;
import logiikka.AStar;
import logiikka.Analysoija;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * Verkon rakennukseen ja analysointiin liittyvät testit
 *
 * @author kride
 */
public class VerkonRakennusTest {

    private Reitinhakija aStar;
    private Analysoija analysoija;

    @Before
    public void alustaKartat() {
        AStarTest.pieninKartta = new char[][]{
            {'A', '.'},
            {'.', 'B'}};
        AStarTest.pieninKarttaEsteella = new char[][]{
            {'A', 'X'},
            {'.', 'B'}};
        AStarTest.pieniKartta = new char[][]{
            {'A', 'X', 'X', 'X', 'B'},
            {'.', 'X', 'X', 'X', '.'},
            {'.', 'X', 'X', 'X', '.'},
            {'.', '.', '.', '.', '.'}};
        AStarTest.vapaaPieniKartta = new char[][]{
            {'A', 'X', 'X', 'X', 'B'},
            {'.', '.', '.', '.', '.'},
            {'.', 'X', 'X', 'X', '.'},
            {'.', '.', '.', '.', '.'}};
        AStarTest.esteKartta = new char[][]{
            {'A', 'X', 'X', 'X', 'B'},
            {'.', '.', 'X', '.', '.'},
            {'.', 'X', '.', 'X', '.'},
            {'.', '.', 'X', 'X', '.'},
            {'.', '.', 'X', '.', '.'},
            {'.', '.', 'X', '.', '.'},
            {'.', '.', '.', '.', '.'}};
        AStarTest.esteKartta2 = new char[][]{
            {'A', '.', '.', '.', '.'},
            {'.', '.', '.', 'X', '.'},
            {'.', '.', '.', 'X', '.'},
            {'.', '.', 'X', 'X', '.'},
            {'.', '.', 'X', 'B', '.'},
            {'.', '.', 'X', '.', '.'},
            {'.', '.', '.', '.', '.'}};
        AStarTest.leveaKartta = new char[][]{
            {'.', 'X', '.', '.', '.', '.', '.', '.', '.', '.', 'B'},
            {'A', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'}};
        AStarTest.miniKartta = new char[][]{
            {'A', 'X', 'B'},
            {'.', '.', '.'}};
        AStarTest.serpettiiniKentta = new char[][]{
            {'.', 'X', '.', '.', '.', 'X', '.', '.', '.', 'X', 'B'},
            {'A', '.', '.', 'X', '.', '.', '.', 'X', '.', '.', '.'}};

    }

    @Test(timeout = 1000)
    public void testaaPienenKartanNaapuritLahdosta() {

        aStar = new AStar(AStarTest.pieniKartta);
        ArrayList<Integer> luvut = new ArrayList<>();
        luvut.add(1);
        luvut.add(AStarTest.pieniKartta[0].length);

        assertTrue(aStar.getSolmunNaapurit(0).containsAll(luvut));

    }

    @Test(timeout = 1000)
    public void testaaMiniKartanNaapuritLahdosta() {

        aStar = new AStar(AStarTest.miniKartta);
        ArrayList<Integer> luvut = new ArrayList<>();
        luvut.add(1);
        luvut.add(AStarTest.miniKartta[0].length);

        assertTrue(aStar.getSolmunNaapurit(0).containsAll(luvut));

    }

    @Test(timeout = 1000)
    public void testaaKeskiKartanNaapuritLahdosta() {

        aStar = new AStar(AStarTest.leveaKartta);
        ArrayList<Integer> luvut = new ArrayList<>();
        luvut.add(0);
        luvut.add(AStarTest.leveaKartta[0].length + 1);

        assertTrue(aStar.getSolmunNaapurit(11).containsAll(luvut));

    }

    @Test(timeout = 1000)
    public void miniKarttaAnalysoidaanOikein() {
        analysoija = new Analysoija();
        aStar = new AStar(AStarTest.miniKartta);
        int[][] haluttu = new int[][]{
            {1, Ymparistomuuttuja.INF.getArvo(), 1},
            {1, 1, 1}};

        int[][] analysoitu = analysoija.analysoiKarttaArvoiksiMerkeista(AStarTest.miniKartta, aStar);
        assertEquals(Arrays.deepToString(haluttu), Arrays.deepToString(analysoitu));
    }

    @Test(timeout = 1000)
    public void pieninKarttaAnalysoidaanOikein() {
        analysoija = new Analysoija();
        aStar = new AStar(AStarTest.pieninKartta);
        int[][] haluttu = new int[][]{
            {1, 1},
            {1, 1}};

        int[][] analysoitu = analysoija.analysoiKarttaArvoiksiMerkeista(AStarTest.pieninKartta, aStar);
        assertEquals(Arrays.deepToString(haluttu), Arrays.deepToString(analysoitu));
    }

    @Test(timeout = 1000)
    public void pieniKarttaAnalysoidaanOikein() {
        analysoija = new Analysoija();
        aStar = new AStar(AStarTest.pieniKartta);
        int nf = Ymparistomuuttuja.INF.getArvo();
        int[][] haluttu = new int[][]{
            {1, nf, nf, nf, 1},
            {1, nf, nf, nf, 1},
            {1, nf, nf, nf, 1},
            {1, 1, 1, 1, 1}};

        int[][] analysoitu = analysoija.analysoiKarttaArvoiksiMerkeista(AStarTest.pieniKartta, aStar);
        assertEquals(Arrays.deepToString(haluttu), Arrays.deepToString(analysoitu));
    }

    @Test(timeout = 1000)
    public void leveaKarttaAnalysoidaanOikein() {
        analysoija = new Analysoija();
        aStar = new AStar(AStarTest.leveaKartta);
        int nf = Ymparistomuuttuja.INF.getArvo();
        int[][] haluttu = new int[][]{
            {1, nf, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};

        int[][] analysoitu = analysoija.analysoiKarttaArvoiksiMerkeista(AStarTest.leveaKartta, aStar);
        assertEquals(Arrays.deepToString(haluttu), Arrays.deepToString(analysoitu));
    }
}
