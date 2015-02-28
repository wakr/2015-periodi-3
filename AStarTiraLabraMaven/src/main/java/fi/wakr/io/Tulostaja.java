package fi.wakr.io;

import java.util.Arrays;

/**
 * Hoitaa kartan tulostamisen kolmessa eri muodossa: graafisena sisältäen
 * merkkejä, arvotauluna tai etäisyyksien mukaan
 *
 * @author Kristian Wahlroos
 * @see fi.wakr.logiikka.reitinhaku.Reitinhakija
 */
public class Tulostaja {

    /**
     * Tulostaa kartan arvoina
     *
     * @param kartta Kartta kaksiulotteisessa taulukossa
     */
    public static void tulostaKartta(int[][] kartta) {
        for (int[] rivi : kartta) {
            System.out.println(Arrays.toString(rivi));
        }
    }

    /**
     * Tulostaa kartan merkkein
     *
     * @param kartta Kartta sisältäen merkkejä
     */
    public static void tulostaKartta(char[][] kartta) {
        for (char[] rivi : kartta) {
            System.out.println(Arrays.toString(rivi));
        }
    }

    /**
     * Tulostaa kartan etaisyydet
     *
     * @param etaisyys Solmujen etäisyydet lähtösolmusta
     * @param leveys Kartan leveys
     */
    public static void tulostaEtaisydet(long[] etaisyys, int leveys) {

        for (int i = 0; i < etaisyys.length; i++) {
            if (i % leveys == 0) {
                System.out.println("");
            }
            System.out.format("%-10d", etaisyys[i]);
        }
    }

   

}
