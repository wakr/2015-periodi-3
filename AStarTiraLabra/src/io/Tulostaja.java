/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import java.util.Arrays;

/**
 *
 * @author kristianw
 */
public class Tulostaja {

    public static void tulostaKartta(int[][] kartta) {
        for (int[] rivi : kartta) {
            System.out.println(Arrays.toString(rivi));
        }
    }

    public static void tulostaKartta(char[][] kartta) {
        for (char[] rivi : kartta) {
            System.out.println(Arrays.toString(rivi));
        }
    }

    public static void tulostaEtaisydet(int[] etaisyys, int leveys) {

        for (int i = 0; i < etaisyys.length; i++) {
            if (i % leveys == 0) {
                System.out.println("");
            }
            System.out.format("%-10d", etaisyys[i]);
        }
    }

}
