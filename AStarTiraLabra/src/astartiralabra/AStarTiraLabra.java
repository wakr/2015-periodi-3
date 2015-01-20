package astartiralabra;

import io.Tulostaja;
import util.Solmu;
import java.util.ArrayList;
import java.util.Arrays;
import logiikka.AStar;

;

/**
 *
 * @author kristianw
 */
public class AStarTiraLabra {

    public static void main(String[] args) {
        //        char[][] kartta = new char[][]{
        //            {'.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', 'B'},
        //            {'.', '.', '.', 'X', 'X', 'X', 'X', '.', '.', '.', '.', '.'},
        //            {'.', '.', '.', '.', '.', '.', 'X', '.', '.', '.', '.', '.'},
        //            {'.', '.', '.', '.', '.', '.', 'X', '.', '.', '.', '.', '.'},
        //            {'.', '.', '.', '.', '.', '.', 'X', 'X', '.', '.', '.', '.'},
        //            {'.', '.', '.', '.', '.', '.', 'X', '.', '.', '.', '.', '.'},
        //            {'A', '.', '.', 'X', 'X', 'X', 'X', '.', '.', '.', '.', '.'},};
        char[][] miniKartta = new char[][]{
            {'A', 'X', 'B'},
            {'.', '.', '.'}};
        AStar aStar = new AStar(miniKartta);

        aStar.suoritaReitinHaku();
        aStar.polku(2);
        Tulostaja.tulostaKartta(aStar.getKartta());
        System.out.println(aStar.polku(2, new ArrayList<Integer>()));
    }

}
