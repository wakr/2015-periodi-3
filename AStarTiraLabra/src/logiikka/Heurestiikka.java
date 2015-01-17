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
public class Heurestiikka {

    public static int laskeHeurestinenArvo(int Sx, int Sy, int Bx, int By) {

        int dx = Math.abs(Sx - Bx);
        int dy = Math.abs(Sy - By);

        return Ymparistomuuttuja.D.getArvo() * (dx + dy);
    }

}
