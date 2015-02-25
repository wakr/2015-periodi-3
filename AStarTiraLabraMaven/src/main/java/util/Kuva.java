package util;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * Kuvan esittämistä ja muokkaamista varten oleva luokka. Kuva vastaa
 * käytännössä aina karttaa ohjelmassa.
 *
 *
 * @author Kristian Wahlroos
 */
public class Kuva {

    private final Image kuva;
    private final BufferedImage bufferoituKuva;
    private int[][] rgb;

    /**
     * @param bufKuva Kuva bufferoituna
     * @param skaalattuKorkeus skaalatun kuvan korkeus
     * @param skaalattuLeveys skaalatun kuvan leveys
     */
    public Kuva(BufferedImage bufKuva, int skaalattuKorkeus, int skaalattuLeveys) {
        this.bufferoituKuva = bufKuva;
        this.kuva = bufferoituKuva.getScaledInstance(skaalattuLeveys, skaalattuKorkeus, Image.SCALE_SMOOTH);
    }

    public Image getKuva() {
        return kuva;
    }

    public BufferedImage getBufferoituKuva() {
        return bufferoituKuva;
    }

    public int getKorkeus() {
        return bufferoituKuva.getHeight();
    }

    public int getLeveys() {
        return bufferoituKuva.getWidth();
    }

    public int[][] getRGPArvot() {
        return rgb;
    }

    /**
     * Muuttaa kuvan RPG-esitykseen käyttäen hyödykseen tietoa RGB-arvojen
     * tallennuksista. Tästä syystä bitshift-operaatiot.
     *
     * @param image kartta kuvana
     */
    public void konvertoi2DTaulukkoonRPGArvoina(BufferedImage image) {

        final byte[] pikselit = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int leveys = image.getWidth();
        final int korkeus = image.getHeight();

        int[][] tulos = new int[korkeus][leveys];

        final int pikselinPituus = 3;
        for (int pikseli = 0, rivi = 0, sarake = 0; pikseli < pikselit.length; pikseli += pikselinPituus) {
            int argb = 0;
            argb += -16777216; // 255 alpha
            argb += ((int) pikselit[pikseli] & 0xff); // blue
            argb += (((int) pikselit[pikseli + 1] & 0xff) << 8); // green
            argb += (((int) pikselit[pikseli + 2] & 0xff) << 16); // red
            tulos[rivi][sarake] = argb;
            sarake++;
            if (sarake == leveys) {
                sarake = 0;
                rivi++;
            }
        }

        this.rgb = tulos;
    }

}
