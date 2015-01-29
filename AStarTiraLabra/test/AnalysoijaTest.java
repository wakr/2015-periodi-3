
import logiikka.Analysoija;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Koordinaatiston muutoksiin liittyvät testit
 *
 * @author kride
 */
public class AnalysoijaTest {

    @Test(timeout = 1000)
    public void konvertteriToimiiLyhyeksi() {
        char[][] pieniKartta1 = new char[][]{
            {'A', 'X', 'B'},
            {'.', '.', '.'},};
        int koordinaattiPitkana = 2;

        int saatuY = Analysoija.getRivi(koordinaattiPitkana, pieniKartta1[0].length);
        int saatuX = Analysoija.getSarake(koordinaattiPitkana, pieniKartta1[0].length);

        assertArrayEquals(new int[]{0, 2}, new int[]{saatuY, saatuX});
    }

    @Test(timeout = 1000)
    public void konvertteriToimiiLyhyeksi2() {
        char[][] pieniKartta1 = new char[][]{
            {'A', 'X', 'B'},
            {'.', '.', '.'},};
        int koordinaattiPitkana = 0;

        int saatuY = Analysoija.getRivi(koordinaattiPitkana, pieniKartta1[0].length);
        int saatuX = Analysoija.getSarake(koordinaattiPitkana, pieniKartta1[0].length);

        assertArrayEquals(new int[]{0, 0}, new int[]{saatuY, saatuX});
    }

    @Test(timeout = 1000)
    public void konvertteriToimiiLyhyeksi3() {
        char[][] pieniKartta1 = new char[][]{
            {'A', 'X', 'B'},
            {'.', '.', '.'},};
        int koordinaattiPitkana = 4;

        int saatuY = Analysoija.getRivi(koordinaattiPitkana, pieniKartta1[0].length);
        int saatuX = Analysoija.getSarake(koordinaattiPitkana, pieniKartta1[0].length);

        assertArrayEquals(new int[]{1, 1}, new int[]{saatuY, saatuX});
    }

    @Test(timeout = 1000)
    public void pitkaksiMuutosToimii() {
        char[][] pieniKartta1 = new char[][]{
            {'A', 'X', 'B'},
            {'.', '.', '.'},};

        int haluttu = 1;
        assertEquals(haluttu, Analysoija.muutaPitkaksi(0, 1, pieniKartta1[0].length));
    }

    @Test(timeout = 1000)
    public void pitkaksiMuutosToimii2() {
        char[][] pieniKartta1 = new char[][]{
            {'A', 'X', 'B'},
            {'.', '.', '.'},
            {'.', '.', '.'},};

        int haluttu = 4;
        assertEquals(haluttu, Analysoija.muutaPitkaksi(1, 1, pieniKartta1[0].length));
    }

    @Test(timeout = 1000)
    public void RGPKonvertteritoimiiPunaiseksi() {

        int haluttu = 255;
        Analysoija a = new Analysoija();
        assertEquals(255, a.getRed(-1));
    }

    @Test(timeout = 1000)
    public void RGPKonvertteritoimiiSiniseksi() {

        int haluttu = 255;
        Analysoija a = new Analysoija();
        assertEquals(255, a.getBlue(-1));
    }

    @Test(timeout = 1000)
    public void RGPKonvertteritoimiiVihreaksi() {

        int haluttu = 255;
        Analysoija a = new Analysoija();
        assertEquals(255, a.getGreen(-1));
    }
}
