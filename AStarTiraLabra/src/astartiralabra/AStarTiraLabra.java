package astartiralabra;

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
        char[][] kartta = new char[][]{
            {'.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', 'B'},
            {'.', '.', '.', 'X', 'X', 'X', 'X', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', 'X', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', 'X', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', 'X', 'X', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', 'X', '.', '.', '.', '.', '.'},
            {'A', '.', '.', 'X', 'X', 'X', 'X', '.', '.', '.', '.', '.'},};

        AStar aStar = new AStar(kartta);
        aStar.suoritaReitinHaku();

    }

}
