/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logiikka;

import extra.Ymparistomuuttuja;

/**
 * A*-algoritmin tarvitsemat erilaiset analysointi-funktiot.
 *
 * @author Kristian Wahlroos
 * @see logiikka.AStar
 */
public class Analysoija {

    private AStar aStar;

    /**
     * Analysoi annetun kartan jokaisen arvon palauttaen uuden kartan, joka
     * sisältää arvot kaksiulotteisessa taulukossa.
     * @param  kartta Annettava kartta
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

    /**
     * Analysoi kartan yksittäisen merkin palauttaen sitä vastaavan painon 
     * solmun luontia varten
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

    /**
     * Koska taulua käsitellään yksiulotteisena A*-algoritmissa sen 
     * selkeyden vuoksi, niin koordinaatit on pystyttävä muuttamaan 
     * yksiulotteisesta esityksestä kaksiulotteiseen. Seuraavat kaksi metodia
     * hoitavat kyseisen operaation.
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
     * @return Yksiulotteisen koordinaatin sitä vastaava sarake eli x-koordinaatti
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
