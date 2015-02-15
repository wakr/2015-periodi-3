package logiikka;

import io.Tulostaja;
import java.awt.FileDialog;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import kayttoliittyma.Piirtaja;
import logiikka.AStar;
import logiikka.Analysoija;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import util.Kuva;

/**
 * Reitinhakuihin liittyvät testit
 *
 * @author kride
 */
public class AStarTest {

    static char[][] pieninKartta, pieninKarttaEsteella, pieniKartta, vapaaPieniKartta,
            esteKartta, esteKartta2, leveaKartta, miniKartta, serpettiiniKentta, jatti;

    @Before
    public void alustaKartat() {
        pieninKartta = new char[][]{
            {'A', '.'},
            {'.', 'B'}};
        pieninKarttaEsteella = new char[][]{
            {'A', 'X'},
            {'.', 'B'}};
        pieniKartta = new char[][]{
            {'A', 'X', 'X', 'X', 'B'},
            {'.', 'X', 'X', 'X', '.'},
            {'.', 'X', 'X', 'X', '.'},
            {'.', '.', '.', '.', '.'}};
        vapaaPieniKartta = new char[][]{
            {'A', 'X', 'X', 'X', 'B'},
            {'.', '.', '.', '.', '.'},
            {'.', 'X', 'X', 'X', '.'},
            {'.', '.', '.', '.', '.'}};
        esteKartta = new char[][]{
            {'A', 'X', 'X', 'X', 'B'},
            {'.', '.', 'X', '.', '.'},
            {'.', 'X', '.', 'X', '.'},
            {'.', '.', 'X', 'X', '.'},
            {'.', '.', 'X', '.', '.'},
            {'.', '.', 'X', '.', '.'},
            {'.', '.', '.', '.', '.'}};
        esteKartta2 = new char[][]{
            {'A', '.', '.', '.', '.'},
            {'.', '.', '.', 'X', '.'},
            {'.', '.', '.', 'X', '.'},
            {'.', '.', 'X', 'X', '.'},
            {'.', '.', 'X', 'B', '.'},
            {'.', '.', 'X', '.', '.'},
            {'.', '.', '.', '.', '.'}};
        leveaKartta = new char[][]{
            {'.', 'X', '.', '.', '.', '.', '.', '.', '.', '.', 'B'},
            {'A', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'}};
        miniKartta = new char[][]{
            {'A', 'X', 'B'},
            {'.', '.', '.'}};
        serpettiiniKentta = new char[][]{
            {'.', 'X', '.', '.', '.', 'X', '.', '.', '.', 'X', 'B'},
            {'A', '.', '.', 'X', '.', '.', '.', 'X', '.', '.', '.'}};

        jatti = new char[100][1000];
        for (int i = 0; i < jatti.length; i++) {
            for (int j = 0; j < jatti[0].length; j++) {
                jatti[i][j] = '.';

            }

        }
        jatti[0][0] = 'A';
        jatti[jatti.length - 1][jatti[0].length - 1] = 'B';

    }

    private AStar aStar;
    private Kuva karttaKuvana;
    private BufferedImage alkuPerainenKuva;
    BufferedImage kuva;

    @Before
    public void setUp() throws IOException {
        File f = new File("src/main/java/util/karttakuvat/testiKartat/pieniTesti.bmp");
        kuva = ImageIO.read(f);
        alkuPerainenKuva = ImageIO.read(f);
        karttaKuvana = new Kuva(kuva, kuva.getHeight(), kuva.getWidth());
        karttaKuvana.konvertoi2DTaulukkoonRPGArvoina(karttaKuvana.getBufferoituKuva());
    }

    @Test(timeout = 1000)
    public void miniKarttaPolkuOikein() {
        aStar = new AStar(miniKartta);
        aStar.suoritaReitinHaku();
        List<Integer> haluttu = Arrays.asList(0, 3, 4, 5, 2);
        assertEquals(haluttu.toString(), aStar.polku(2, new ArrayList<Integer>()).toString());
    }

    @Test(timeout = 1000)
    public void miniKarttaEsteellaPolkuOikein() {
        aStar = new AStar(pieninKarttaEsteella);
        aStar.suoritaReitinHaku();
        List<Integer> haluttu = Arrays.asList(0, 2, 3);
        assertEquals(haluttu.toString(), aStar.polku(3, new ArrayList<Integer>()).toString());
    }

    @Test(timeout = 1000)
    public void serpenttiiniPolkuOikein() {
        aStar = new AStar(serpettiiniKentta);
        aStar.suoritaReitinHaku();
        aStar.polku(10);
        List<Integer> haluttu = Arrays.asList(11, 12, 13, 2, 3, 4, 15, 16, 17, 6, 7, 8, 19, 20, 21, 10);
        assertEquals(haluttu.toString(), aStar.polku(10, new ArrayList<Integer>()).toString());
    }

    @Test(timeout = 1000)
    public void jattiKartassaHakuNOpeaa() {

        aStar = new AStar(jatti);
        aStar.suoritaReitinHaku();
        assertEquals((jatti[0].length + jatti.length) - 2, aStar.getPituusAlkuun(aStar.getMaali()));
    }

    @Test(timeout = 1000)
    public void maaliLoydetaanHaunJalkeenPieniKartasta() {
        aStar = new AStar(pieniKartta);
        aStar.suoritaReitinHaku();
        aStar.polku(aStar.getMaali());
        List<Integer> haluttu = Arrays.asList(aStar.getLahto(), 5, 10, 15, 16, 17, 18, 19, 14, 9, 4);
        assertEquals(haluttu.toString(), aStar.polku(aStar.getMaali(), new ArrayList<Integer>()).toString());
    }

    @Test(timeout = 1000)
    public void lyhinPolkuLoydetaan() {
        aStar = new AStar(vapaaPieniKartta);
        aStar.suoritaReitinHaku();
        int lyhyimmanPituus = 7;
        ArrayList<Integer> lyhinPolku = aStar.polku(aStar.getMaali(), new ArrayList<Integer>());
        assertEquals(lyhyimmanPituus, lyhinPolku.size());
    }

    @Test(timeout = 1000)
    public void lyhinPolkuLoydetaan2() {
        aStar = new AStar(esteKartta);
        aStar.suoritaReitinHaku();
        int lyhyimmanPituus = 17;
        ArrayList<Integer> lyhinPolku = aStar.polku(aStar.getMaali(), new ArrayList<Integer>());
        assertEquals(lyhyimmanPituus, lyhinPolku.size());
    }

    @Test(timeout = 1000)
    public void lyhinPolkuLoydetaan3() {
        aStar = new AStar(esteKartta2);
        aStar.suoritaReitinHaku();
        int lyhyimmanPituus = 10;
        ArrayList<Integer> lyhinPolku = aStar.polku(aStar.getMaali(), new ArrayList<Integer>());
        assertEquals(lyhyimmanPituus, lyhinPolku.size());
    }

    @Test
    public void etaisyydetAlkuunOvatOikeatPienella() {
        aStar = new AStar(pieninKartta);
        aStar.suoritaReitinHaku();
        assertEquals(2, aStar.getPituusAlkuun(aStar.getMaali()));
    }

    @Test
    public void etaisyydetAlkuunOvatOikeatEsteella() {
        aStar = new AStar(esteKartta);
        aStar.suoritaReitinHaku();
        assertEquals(16, aStar.getPituusAlkuun(aStar.getMaali()));
    }

    @Test
    public void etaisyydetAlkuunOvatOikeatSerpettiiniKentalla() {
        aStar = new AStar(serpettiiniKentta);
        aStar.suoritaReitinHaku();
        assertEquals(15, aStar.getPituusAlkuun(aStar.getMaali()));
    }

    /**
     * RBG-kuvien testit
     */
    @Test(timeout = 1000)
    public void kuvastaLoydetaanLyhinPolku() throws IOException {

        aStar = new AStar(karttaKuvana.getRGPArvot(), null);
        aStar.suoritaReitinHaku();
        assertEquals(7, aStar.getPituusAlkuun(aStar.getMaali()));
    }

}
