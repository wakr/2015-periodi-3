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
 * @see logiikka.AStar;
 */
public class Analysoija {

    private AStar aStar;

    /**
     *
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
     *
     */
    private int analysoiMerkki(char tarkasteltavana, int i, int j) {
        if (tarkasteltavana == '.') {
            return 1;
        }
        if (tarkasteltavana == ',') {
            return 5;
        }
        if (tarkasteltavana == 'A') {
            aStar.asetaLahto(j, i);
            return 1;
        }
        if (tarkasteltavana == 'B') {
            aStar.asetaMaali(j, i);
            return 1;
        } else {
            return Ymparistomuuttuja.INF.getArvo();
        }
    }

    /**
     *
     */
    public static int getRivi(int pidennettyKoordinaatti, int leveys) {
        return pidennettyKoordinaatti / leveys;
    }

    /**
     *
     */
    public static int getSarake(int pidennettyKoordinaatti, int leveys) {
        return pidennettyKoordinaatti % leveys;
    }

    /**
     *
     */
    public static int muutaPitkaksi(int y, int x, int leveys) {
        return (y * leveys) + x;
    }

}
