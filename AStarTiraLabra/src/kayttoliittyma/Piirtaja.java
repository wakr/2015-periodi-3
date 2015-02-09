/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import javax.swing.Timer;
import logiikka.Analysoija;
import util.Kuva;
import util.Piste;

/**
 *
 * @author kride
 */
public class Piirtaja {

    private JLabel piirrettava;
    private Kuva karttaKuvana;
    private BufferedImage alkuperainenKuva;
    Graphics2D g;

    public Piirtaja(JLabel piirrettava, Kuva karttaKuvana, BufferedImage alkuperainenKuva) {
        this.piirrettava = piirrettava;
        this.karttaKuvana = karttaKuvana;
        this.alkuperainenKuva = alkuperainenKuva;
        g = (Graphics2D) piirrettava.getGraphics();
    }

    public void piirraKarttaan(Color vari, int kayty) {

        g.setColor(vari);

        int x = Analysoija.getSarake(kayty, karttaKuvana.getLeveys());
        int y = Analysoija.getRivi(kayty, karttaKuvana.getLeveys());
        Piste muutetutKoordinaatit = muutaKoordinaatitLyhyesta(y, x);

        g.drawRect(muutetutKoordinaatit.getX(), muutetutKoordinaatit.getY(), 1, 1);

        try {
            Thread.sleep(5);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

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
