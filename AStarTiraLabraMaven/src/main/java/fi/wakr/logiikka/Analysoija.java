package fi.wakr.logiikka;

import fi.wakr.logiikka.reitinhaku.Reitinhakija;

/**
 * Reitinhaku-algoritmien tarvitsemat erilaiset analysointi-funktiot.
 *
 * @author Kristian Wahlroos
 * @see fi.wakr.logiikka.reitinhaku.Reitinhakija
 */
public class Analysoija {

    private Reitinhakija hakija;

    // Kaikki mahdolliset väriarvot ja niiden RGB-arvot
    private final int[] white = new int[]{255, 255, 255};
    private final int[] red = new int[]{255, 0, 0};
    private final int[] green = new int[]{0, 255, 0};
    private final int[] blue = new int[]{0, 0, 255};

    /**
     * Analysoi annetun kartan jokaisen arvon palauttaen uuden kartan, joka
     * sisältää arvot kaksiulotteisessa taulukossa.
     *
     * @param kartta Annettava kartta
     * @param hakija {@link fi.wakr.logiikka.reitinhaku.Reitinhakija} luokan
     * ilmentymä jonka avulla asetetaan maali ja lähtö
     * @return Palauttaa kaksiuloitteisen taulukon, jossa analysoidut arvot
     */
    public int[][] analysoiKarttaArvoiksiMerkeista(char[][] kartta, Reitinhakija hakija) {
        this.hakija = hakija;
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

    /**
     * Analysoi annetun kartan jokaisen arvon palauttaen uuden kartan, joka
     * sisältää arvot kaksiulotteisessa taulukossa.
     *
     * @param karttaRGB Annettava kartta väreinä
     * @param hakija {@link fi.wakr.logiikka.reitinhaku.Reitinhakija} luokan
     * ilmentymä jonka avulla asetetaan maali ja lähtö
     * @return Palauttaa kaksiuloitteisen taulukon, jossa analysoidut arvot
     */
    public int[][] analysoiKarttaArvoiksiVareista(int[][] karttaRGB, Reitinhakija hakija) {
        this.hakija = hakija;
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
     * solmun luontia varten. Tiedottaa myös reitinhakijalle maalin ja lähdön.
     *
     * @param tarkasteltavana Saatu merkki
     * @param y y-indeksi
     * @param x x-indeksi
     * @return Solmun paino
     */
    private int analysoiMerkki(char tarkasteltavana, int y, int x) {
        if (tarkasteltavana == '.') {
            return Ymparistomuuttuja.NormaaliRuutu.getArvo();
        }
        if (tarkasteltavana == ',') {
            return Ymparistomuuttuja.VesiRuutu.getArvo();
        }
        if (tarkasteltavana == 'A') {
            hakija.asetaLahto(x, y);
            return Ymparistomuuttuja.NormaaliRuutu.getArvo();
        }
        if (tarkasteltavana == 'B') {
            hakija.asetaMaali(x, y);
            return Ymparistomuuttuja.NormaaliRuutu.getArvo();
        } else {
            return Ymparistomuuttuja.INF.getArvo();
        }
    }

    /**
     * Analysoi kartan/kuvan yksittäisen RPG-värin palauttaen sitä vastaavan
     * painon solmun luontia varten
     *
     * @param RGBVari Saatu väriarvo
     * @param y y-indeksi
     * @param x x-indeksi
     * @return Solmun paino
     */
    private int analysoiVari(int RGBVari, int y, int x) {
        int[] RGB = new int[]{getRed(RGBVari), getGreen(RGBVari), getBlue(RGBVari)};

        if (RGB[0] == red[0] && RGB[1] == red[1] && RGB[2] == red[2]) { // maali
            hakija.asetaMaali(x, y);
            return Ymparistomuuttuja.NormaaliRuutu.getArvo();
        }
        if (RGB[0] == white[0] && RGB[1] == white[1] && RGB[2] == white[2]) {
            return Ymparistomuuttuja.NormaaliRuutu.getArvo();
        }
        if (RGB[0] == green[0] && RGB[1] == green[1] && RGB[2] == green[2]) { //lahto
            hakija.asetaLahto(x, y);
            return Ymparistomuuttuja.NormaaliRuutu.getArvo();
        }
        if (RGB[0] == blue[0] && RGB[1] == blue[1] && RGB[2] == blue[2]) {
            return Ymparistomuuttuja.VesiRuutu.getArvo();
        } else {
            return Ymparistomuuttuja.INF.getArvo();
        }
    }

    /**
     * Analysoi värit palauttamalla niistä arvon välille 0...255
     *
     * @param rgb RGB-muodossa oleva väriarvo
     * @return RPG-arvo väliltä 0...255
     */
    public int getRed(int rgb) {
        return (rgb >> 16) & 0x000000FF;
    }

    public int getGreen(int rgb) {
        return (rgb >> 8) & 0x000000FF;
    }

    public int getBlue(int rgb) {
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
