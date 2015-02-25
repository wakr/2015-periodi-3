package logiikka;

import io.Tulostaja;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import logiikka.tietorakenteet.TaulukkoLista;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import util.Kuva;

/**
 * Reitinhakuihin liittyvät testit
 *
 * @author kride
 */
public class DijkstraTest {

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

        char[][] kartta = new char[][]{
            {'.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', 'B'},
            {'.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', '.', 'X', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', '.', 'X', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', '.', 'X', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', '.', 'X', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', '.', 'X', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', '.', 'X', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', '.', 'X', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', '.', 'X', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', '.', 'X', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'},
            {'A', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'}};

        jatti = new char[100][1000];
        for (int i = 0; i < jatti.length; i++) {
            for (int j = 0; j < jatti[0].length; j++) {
                jatti[i][j] = '.';

            }

        }
        jatti[0][0] = 'A';
        jatti[jatti.length - 1][jatti[0].length - 1] = 'B';

    }

    private Reitinhakija dijkstra;
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
        dijkstra = new Dijkstra(miniKartta);
        dijkstra.suoritaReitinHaku();
        List<Integer> haluttu = Arrays.asList(0, 3, 4, 5, 2);
        assertEquals(haluttu.toString(), dijkstra.polku(2, new TaulukkoLista<Integer>()).toString());
    }

    @Test(timeout = 1000)
    public void miniKarttaEsteellaPolkuOikein() {
        dijkstra = new Dijkstra(pieninKarttaEsteella);
        dijkstra.suoritaReitinHaku();
        List<Integer> haluttu = Arrays.asList(0, 2, 3);
        assertEquals(haluttu.toString(), dijkstra.polku(3, new TaulukkoLista<Integer>()).toString());
    }

    @Test(timeout = 1000)
    public void serpenttiiniPolkuOikein() {
        dijkstra = new Dijkstra(serpettiiniKentta);
        dijkstra.suoritaReitinHaku();
        dijkstra.polku(10);
        List<Integer> haluttu = Arrays.asList(11, 12, 13, 2, 3, 4, 15, 16, 17, 6, 7, 8, 19, 20, 21, 10);
        assertEquals(haluttu.toString(), dijkstra.polku(10, new TaulukkoLista<Integer>()).toString());
    }

    @Test(timeout = 1000)
    public void jattiKartassaHakuNOpeaa() {

        dijkstra = new Dijkstra(jatti);
        dijkstra.suoritaReitinHaku();
        assertEquals((jatti[0].length + jatti.length) - 2, dijkstra.getPituusAlkuun(dijkstra.getMaali()));
    }

    @Test(timeout = 1000)
    public void maaliLoydetaanHaunJalkeenPieniKartasta() {
        dijkstra = new Dijkstra(pieniKartta);
        dijkstra.suoritaReitinHaku();
        dijkstra.polku(dijkstra.getMaali());
        List<Integer> haluttu = Arrays.asList(dijkstra.getLahto(), 5, 10, 15, 16, 17, 18, 19, 14, 9, 4);
        assertEquals(haluttu.toString(), dijkstra.polku(dijkstra.getMaali(), new TaulukkoLista<Integer>()).toString());
    }

    @Test(timeout = 1000)
    public void lyhinPolkuLoydetaan() {
        dijkstra = new Dijkstra(vapaaPieniKartta);
        dijkstra.suoritaReitinHaku();
        int lyhyimmanPituus = 7;
        TaulukkoLista<Integer> lyhinPolku = dijkstra.polku(dijkstra.getMaali(), new TaulukkoLista<Integer>());
        assertEquals(lyhyimmanPituus, lyhinPolku.size());
    }

    @Test(timeout = 1000)
    public void lyhinPolkuLoydetaan2() {
        dijkstra = new Dijkstra(esteKartta);
        dijkstra.suoritaReitinHaku();
        int lyhyimmanPituus = 17;
        TaulukkoLista<Integer> lyhinPolku = dijkstra.polku(dijkstra.getMaali(), new TaulukkoLista<Integer>());
        assertEquals(lyhyimmanPituus, lyhinPolku.size());
    }

    @Test(timeout = 1000)
    public void lyhinPolkuLoydetaan3() {
        dijkstra = new Dijkstra(esteKartta2);
        dijkstra.suoritaReitinHaku();
        int lyhyimmanPituus = 10;
        TaulukkoLista<Integer> lyhinPolku = dijkstra.polku(dijkstra.getMaali(), new TaulukkoLista<Integer>());
        assertEquals(lyhyimmanPituus, lyhinPolku.size());
    }

    @Test
    public void etaisyydetAlkuunOvatOikeatPienella() {
        dijkstra = new Dijkstra(pieninKartta);
        dijkstra.suoritaReitinHaku();
        assertEquals(2, dijkstra.getPituusAlkuun(dijkstra.getMaali()));
    }

    @Test
    public void etaisyydetAlkuunOvatOikeatEsteella() {
        dijkstra = new Dijkstra(esteKartta);
        dijkstra.suoritaReitinHaku();
        assertEquals(16, dijkstra.getPituusAlkuun(dijkstra.getMaali()));
    }

    @Test
    public void etaisyydetAlkuunOvatOikeatSerpettiiniKentalla() {
        dijkstra = new Dijkstra(serpettiiniKentta);
        dijkstra.suoritaReitinHaku();
        assertEquals(15, dijkstra.getPituusAlkuun(dijkstra.getMaali()));
    }

    /**
     * RBG-kuvien testit
     *
     * @throws java.io.IOException
     */
    @Test(timeout = 1000)
    public void kuvastaLoydetaanLyhinPolku() throws IOException {

        dijkstra = new Dijkstra(karttaKuvana.getRGPArvot(), null);
        dijkstra.suoritaReitinHaku();
        assertEquals(7, dijkstra.getPituusAlkuun(dijkstra.getMaali()));
    }

}
