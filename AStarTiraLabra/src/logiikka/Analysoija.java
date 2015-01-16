/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logiikka;

import extra.Ymparistomuuttuja;

/**
 *
 * @author kristianw
 */
public class Analysoija {
    
    public int[][] analysoiKarttaArvoiksi(char[][] kartta){
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
    
    private static int analysoiMerkki(char l, int i, int j) {
        if (l == '.') {
            return 1;
        }
        if (l == ',') {
            return 5;
        }
        if (l == 'A') {
           // Ax = j;
           // Ay = i;
            return 1;
        }
        if (l == 'B') {
           // Bx = j;
           // By = i;
            return 1;
        } else {
            return Ymparistomuuttuja.INF.getArvo(); // should be infinity
        }
    }
    
}
