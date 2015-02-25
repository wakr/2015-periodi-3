package fi.wakr.util;

import fi.wakr.util.Kuva;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import fi.wakr.logiikka.Analysoija;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kride
 */
public class KuvaTest {

    private Kuva karttaKuvana;
    private BufferedImage alkuPerainenKuva;
    private BufferedImage kuva;
    private Analysoija analysoija;

    @Before
    public void setUp() throws IOException {
        File f = new File("src/main/java/fi/wakr/util/karttakuvat/testiKartat/pieniTesti.bmp");
        kuva = ImageIO.read(f);
        alkuPerainenKuva = ImageIO.read(f);
        karttaKuvana = new Kuva(kuva, kuva.getHeight(), kuva.getWidth());
        analysoija = new Analysoija();
    }

    @Test
    public void kuvanMitatOikein() {
        assertEquals(5, karttaKuvana.getKorkeus());
        assertEquals(5, karttaKuvana.getLeveys());
    }

    @Test
    public void kuvaLuodaanOIkein() {
        karttaKuvana.konvertoi2DTaulukkoonRPGArvoina(karttaKuvana.getBufferoituKuva());
        assertEquals(5, karttaKuvana.getRGPArvot().length);
    }

    @Test
    public void luodunKuvanRBGArvotOikein() {
        karttaKuvana.konvertoi2DTaulukkoonRPGArvoina(karttaKuvana.getBufferoituKuva());
        int[][] saatu = karttaKuvana.getRGPArvot();
        int[] ekaRivi = saatu[0];
        int[][] rgbRivit = new int[ekaRivi.length][3];
        for (int i = 0; i < ekaRivi.length; i++) {
            int arvo = ekaRivi[i];
            int p = analysoija.getRed(arvo),
                    v = analysoija.getGreen(arvo),
                    s = analysoija.getBlue(arvo);
            rgbRivit[i][0] = p;
            rgbRivit[i][1] = v;
            rgbRivit[i][2] = s;
        }

        int[][] haluttu = new int[][]{{255, 255, 255}, {255, 255, 255}, {0, 0, 0}, {255, 255, 255}, {255, 0, 0}};

        for (int i = 0; i < rgbRivit.length; i++) {
            int[] s = rgbRivit[i];
            int[] h = haluttu[i];
            assertArrayEquals(h, s);

        }
    }

}
