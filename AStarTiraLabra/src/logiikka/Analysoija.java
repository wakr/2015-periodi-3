package logiikka;

import extra.Ymparistomuuttuja;
import java.util.Arrays;

/**
 * A*-algoritmin tarvitsemat erilaiset analysointi-funktiot.
 *
 * @author Kristian Wahlroos
 * @see logiikka.AStar
 */
public class Analysoija {

    private AStar aStar;
    private final int[] blk = new int[]{0, 0, 0};
    private final int[] white = new int[]{255, 255, 255};
    private final int[] red = new int[]{255, 0, 0};
    private final int[] green = new int[]{0, 255, 0};

    /**
     * Analysoi annetun kartan jokaisen arvon palauttaen uuden kartan, joka
     * sisältää arvot kaksiulotteisessa taulukossa.
     *
     * @param kartta Annettava kartta
     * @param aStar {@link logiikka.AStar} luokan ilmentymä jonka avulla
     * asetetaan maali ja lähtö
     * @return Palauttaa kaksiuloitteisen taulukon, jossa analysoidut arvot
     */
    public int[][] analysoiKarttaArvoiksi(char[][] kartta, AStar aStar) {
        this.aStar = aStar;
        int[][] arvoTaulu = new int[kartta.length][kartta[0].length];
        for (int i = 0; i < kartta.length; i++) {
            char[] kartta1 = kartta[i];
            for (int j = 0; j < kartta1.length; j++) {
                char l = kartta1[j];
                arvoTaulu[i][j] = analysoiMerkki(l, i, j);
            }
        }
        return arvoTaulu;
    }

    public int[][] analysoiKarttaArvoiksiVareista(int[][] karttaRGB, AStar aStar) {
        this.aStar = aStar;
        int[][] arvoTaulu = new int[karttaRGB.length][karttaRGB[0].length];
        for (int i = 0; i < karttaRGB.length; i++) {
            int[] kartta1 = karttaRGB[i];
            for (int j = 0; j < kartta1.length; j++) {
                int l = kartta1[j];
                arvoTaulu[i][j] = analysoiVari(l, i, j);
            }
        }
        return arvoTaulu;
    }

    /**
     * Analysoi kartan yksittäisen merkin palauttaen sitä vastaavan painon
     * solmun luontia varten
     *
     * @param tarkasteltavana Saatu merkki
     * @param y y-indeksi
     * @param x x-indeksi
     * @return Solmun paino
     */
    private int analysoiMerkki(char tarkasteltavana, int y, int x) {
        if (tarkasteltavana == '.') {
            return 1;
        }
        if (tarkasteltavana == ',') {
            return 5;
        }
        if (tarkasteltavana == 'A') {
            aStar.asetaLahto(x, y);
            return 1;
        }
        if (tarkasteltavana == 'B') {
            aStar.asetaMaali(x, y);
            return 1;
        } else {
            return Ymparistomuuttuja.INF.getArvo();
        }
    }

    private int analysoiVari(int RGBVari, int y, int x) {
        int[] RGB = new int[]{getRed(RGBVari), getGreen(RGBVari), getBlue(RGBVari)};

        
        if (RGB[0] == red[0] && RGB[1] == red[1] && RGB[2] == red[2]) { // maali
            aStar.asetaMaali(x, y);
            return 1;
        }
        if (RGB[0] == white[0] && RGB[1] == white[1] && RGB[2] == white[2]) {
            return 1;
        }
        if (RGB[0] == green[0] && RGB[1] == green[1] && RGB[2] == green[2]) { //lahto
            aStar.asetaLahto(x, y);
            return 1;
        } else {
            return Ymparistomuuttuja.INF.getArvo();
        }
    }

    private int getRed(int rgb) {
        return (rgb >> 16) & 0x000000FF;
    }

    private int getGreen(int rgb) {
        return (rgb >> 8) & 0x000000FF;
    }

    private int getBlue(int rgb) {
        return (rgb) & 0x000000FF;
    }

    /**
     * Koska taulua käsitellään yksiulotteisena A*-algoritmissa sen selkeyden
     * vuoksi, niin koordinaatit on pystyttävä muuttamaan yksiulotteisesta
     * esityksestä kaksiulotteiseen. Seuraavat kaksi metodia hoitavat kyseisen
     * operaation.
     *
     * @param pidennettyKoordinaatti Koordinaatti yksiulotteisessa muodossa
     * @param leveys Kartan leveys
     * @return Yksiulotteisen koordinaatin sitä vastaava rivi eli y-koordinaatti
     */
    public static int getRivi(int pidennettyKoordinaatti, int leveys) {
        return pidennettyKoordinaatti / leveys;
    }

    /**
     * Jatkoa edelliselle yksiulotteisen koordinaatin muuttamiselle. Kyseessä
     * tässä vain x-koordinaatin haku.
     *
     * @param pidennettyKoordinaatti Koordinaatti yksiulotteisessa muodossa
     * @param leveys Kartan leveys
     * @return Yksiulotteisen koordinaatin sitä vastaava sarake eli
     * x-koordinaatti
     */
    public static int getSarake(int pidennettyKoordinaatti, int leveys) {
        return pidennettyKoordinaatti % leveys;
    }

    /**
     * Käänteinen operaatio koordinaateille, eli kaksiulotteisen koordinaatin
     * muutos yksiulotteiseksi.
     *
     * @param y y-koordinaatti
     * @param x x-koordinaatti
     * @param leveys Kartan leveys
     * @return Koordinaatti yksiulotteisessa esityksessä
     */
    public static int muutaPitkaksi(int y, int x, int leveys) {
        return (y * leveys) + x;
    }

}
