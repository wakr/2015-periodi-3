package fi.wakr.logiikka.reitinhaku;

import fi.wakr.logiikka.reitinhaku.MTAA;
import fi.wakr.logiikka.reitinhaku.Reitinhakija;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import fi.wakr.logiikka.tietorakenteet.TaulukkoLista;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import fi.wakr.util.Kuva;

/**
 * Reitinhakuihin liittyvät testit
 *
 * @author kride
 */
public class MTAATest {

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

    private Reitinhakija mtaa;
    private Kuva karttaKuvana;
    private BufferedImage alkuPerainenKuva;
    BufferedImage kuva;

    @Before
    public void setUp() throws IOException {
        File f = new File("src/main/java/fi/wakr/util/karttakuvat/testiKartat/pieniTesti.bmp");
        kuva = ImageIO.read(f);
        alkuPerainenKuva = ImageIO.read(f);
        karttaKuvana = new Kuva(kuva, kuva.getHeight(), kuva.getWidth());
        karttaKuvana.konvertoi2DTaulukkoonRPGArvoina(karttaKuvana.getBufferoituKuva());
    }

    @Test(timeout = 1000)
    public void miniKarttaPolkuOikein() {
        mtaa = new MTAA(miniKartta);
        mtaa.suoritaReitinHaku();
        List<Integer> haluttu = Arrays.asList(0, 3, 4, 5, 2);
        assertEquals(haluttu.toString(), mtaa.polku(2, new TaulukkoLista<Integer>()).toString());
    }

    @Test(timeout = 1000)
    public void miniKarttaEsteellaPolkuOikein() {
        mtaa = new MTAA(pieninKarttaEsteella);
        mtaa.suoritaReitinHaku();
        List<Integer> haluttu = Arrays.asList(0, 2, 3);
        assertEquals(haluttu.toString(), mtaa.polku(3, new TaulukkoLista<Integer>()).toString());
    }

    @Test(timeout = 1000)
    public void serpenttiiniPolkuOikein() {
        mtaa = new MTAA(serpettiiniKentta);
        mtaa.suoritaReitinHaku();
        mtaa.polku(10);
        List<Integer> haluttu = Arrays.asList(11, 12, 13, 2, 3, 4, 15, 16, 17, 6, 7, 8, 19, 20, 21, 10);
        assertEquals(haluttu.toString(), mtaa.polku(10, new TaulukkoLista<Integer>()).toString());
    }

    @Test(timeout = 1000)
    public void jattiKartassaHakuNOpeaa() {

        mtaa = new MTAA(jatti);
        mtaa.suoritaReitinHaku();
        assertEquals((jatti[0].length + jatti.length) - 2, mtaa.getPituusAlkuun(mtaa.getMaali()));
    }

    @Test(timeout = 1000)
    public void maaliLoydetaanHaunJalkeenPieniKartasta() {
        mtaa = new MTAA(pieniKartta);
        mtaa.suoritaReitinHaku();
        mtaa.polku(mtaa.getMaali());
        List<Integer> haluttu = Arrays.asList(mtaa.getLahto(), 5, 10, 15, 16, 17, 18, 19, 14, 9, 4);
        assertEquals(haluttu.toString(), mtaa.polku(mtaa.getMaali(), new TaulukkoLista<Integer>()).toString());
    }

    @Test(timeout = 1000)
    public void lyhinPolkuLoydetaan() {
        mtaa = new MTAA(vapaaPieniKartta);
        mtaa.suoritaReitinHaku();
        int lyhyimmanPituus = 7;
        TaulukkoLista<Integer> lyhinPolku = mtaa.polku(mtaa.getMaali(), new TaulukkoLista<Integer>());
        assertEquals(lyhyimmanPituus, lyhinPolku.size());
    }

    @Test(timeout = 1000)
    public void lyhinPolkuLoydetaan2() {
        mtaa = new MTAA(esteKartta);
        mtaa.suoritaReitinHaku();
        int lyhyimmanPituus = 17;
        TaulukkoLista<Integer> lyhinPolku = mtaa.polku(mtaa.getMaali(), new TaulukkoLista<Integer>());
        assertEquals(lyhyimmanPituus, lyhinPolku.size());
    }

    @Test(timeout = 1000)
    public void lyhinPolkuLoydetaan3() {
        mtaa = new MTAA(esteKartta2);
        mtaa.suoritaReitinHaku();
        int lyhyimmanPituus = 10;
        TaulukkoLista<Integer> lyhinPolku = mtaa.polku(mtaa.getMaali(), new TaulukkoLista<Integer>());
        assertEquals(lyhyimmanPituus, lyhinPolku.size());
    }

    @Test
    public void etaisyydetAlkuunOvatOikeatPienella() {
        mtaa = new MTAA(pieninKartta);
        mtaa.suoritaReitinHaku();
        assertEquals(2, mtaa.getPituusAlkuun(mtaa.getMaali()));
    }

    @Test
    public void etaisyydetAlkuunOvatOikeatEsteella() {
        mtaa = new MTAA(esteKartta);
        mtaa.suoritaReitinHaku();
        assertEquals(16, mtaa.getPituusAlkuun(mtaa.getMaali()));
    }

    @Test
    public void etaisyydetAlkuunOvatOikeatSerpettiiniKentalla() {
        mtaa = new MTAA(serpettiiniKentta);
        mtaa.suoritaReitinHaku();
        assertEquals(15, mtaa.getPituusAlkuun(mtaa.getMaali()));
    }

    /**
     * RBG-kuvien testit
     *
     * @throws java.io.IOException
     */
    @Test(timeout = 1000)
    public void kuvastaLoydetaanLyhinPolku() throws IOException {

        mtaa = new MTAA(karttaKuvana.getRGPArvot(), null);
        mtaa.suoritaReitinHaku();
        assertEquals(7, mtaa.getPituusAlkuun(mtaa.getMaali()));
    }

}
