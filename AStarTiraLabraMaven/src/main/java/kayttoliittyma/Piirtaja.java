package kayttoliittyma;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import logiikka.Analysoija;
import util.Kuva;
import util.Piste;

/**
 * Huolehtii piirto-operaatiosta kokonaan. Piirtää kuviin haluttuun kohtiin
 * pisteet.
 *
 * @author kride
 */
public class Piirtaja {

    private final JLabel piirrettava;
    private final Kuva karttaKuvana;
    private final BufferedImage alkuperainenKuva;
    private final Graphics2D g;

    /**
     * @param piirrettava Kuva, johon piirretään
     * @param karttaKuvana Kuva, joka on venytetty
     * @param alkuperainenKuva Kuva, joka on talletettu alkuperäisenä
     */
    public Piirtaja(JLabel piirrettava, Kuva karttaKuvana, BufferedImage alkuperainenKuva) {
        this.piirrettava = piirrettava;
        this.karttaKuvana = karttaKuvana;
        this.alkuperainenKuva = alkuperainenKuva;
        g = (Graphics2D) piirrettava.getGraphics();
    }

    /**
     * Piirtää kuvaan halutulla värillä olevan pisteen
     *
     * @param vari Haluttu väri
     * @param kayty Koordinaatti pitkässä muodossa
     * 
     */
    public void piirraKarttaanHitaasti(Color vari, int kayty) {

        g.setColor(vari);

        int x = Analysoija.getSarake(kayty, karttaKuvana.getLeveys());
        int y = Analysoija.getRivi(kayty, karttaKuvana.getLeveys());
        Piste muutetutKoordinaatit = muutaKoordinaatitLyhyesta(y, x);

        g.drawRect(muutetutKoordinaatit.getX(), muutetutKoordinaatit.getY(), 1, 1);

        nuku(5);
    }
    
    /**
     * Piirtää polun karttaan heti eli ei pidä viivettä toisin kuin piirraKarttaanHitaasti-metodi
     * @param vari Haluttu väri jolla piirretään
     * @param kayty Koordinaatti pitkassa muodossa, joka tullaan piirtämään
     */

    public void piirraKarttaanNopeasti(Color vari, int kayty) {

        g.setColor(vari);

        int x = Analysoija.getSarake(kayty, karttaKuvana.getLeveys());
        int y = Analysoija.getRivi(kayty, karttaKuvana.getLeveys());
        Piste muutetutKoordinaatit = muutaKoordinaatitLyhyesta(y, x);

        g.drawRect(muutetutKoordinaatit.getX(), muutetutKoordinaatit.getY(), 1, 1);

        nuku(0);
    }

    private void nuku(int millisekuntia) {
        try {
            Thread.sleep(millisekuntia);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Muuttaa koordinaatin kartan x ja y muodosta vastaamaan graafisen kartan pistettä
     * @param y y-koordinaatti
     * @param x x-koordinaatti
     * @return Piste-olio, joka sisältää uudet pisteet
     */
    
    public Piste muutaKoordinaatitLyhyesta(int y, int x) {

        double korkeusKerroin = (double) piirrettava.getHeight() / (double) alkuperainenKuva.getHeight();
        double leveysKerroin = (double) piirrettava.getWidth() / (double) alkuperainenKuva.getWidth();

        double offSetYD = y * korkeusKerroin;
        double offSetXD = x * leveysKerroin;

        int offSetY = (int) offSetYD;
        int offSetX = (int) offSetXD;

        return new Piste(offSetX, offSetY);

    }

}
