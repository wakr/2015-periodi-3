package astartiralabra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 *
 * @author kristianw
 */
public class AStarTiraLabra {

    final static int INF = 99999;

    static int Ax = -1, Ay = -1, Bx = -1, By = -1;
    static PriorityQueue<Solmu> minKeko = new PriorityQueue<>();
    static ArrayList<Integer>[] verkko;
    static ArrayList<Integer>[] paino;
    static int N;
    static int[] etaisyys;
    static int[][] karttaArvoina;
    static char[][] kartta;
    static int[] p;

    public static void main(String[] args) {

        // x y
//        kartta = new char[][]{
//            {'O', 'A', 'O', 'X', 'B'},
//            {'O', 'O', 'O', 'X', 'O'},
//            {'O', 'O', 'O', 'X', 'O'},
//            {'O', 'O', 'X', 'O', 'O'},
//            {'O', 'O', 'O', 'O', 'O'},};
//         kartta = new char[][]{
//            {'.', 'A', '.', 'X', '.', '.', '.'},
//            {'.', '.', '.', 'X', '.', 'X', '.'},
//            {'.', '.', '.', 'X', '.', 'X', '.'},
//            {'.', '.', 'X', '.', '.', 'X', '.'},
//            {'.', '.', '.', '.', 'X', 'X', '.'},
//            {'.', '.', 'X', '.', 'X', 'X', 'B'},
//            {'.', '.', '.', '.', 'X', 'X', '.'},
//         };
        kartta = new char[][]{
            {'.', '.', '.', '.', '.', '.', '.', '.','.','.'},
            {'.', '.', 'X', 'X', 'X', 'X', 'X', 'X','.','B'},
            {'.', '.', '.', '.', '.', '.', '.', 'X','.','.'},
            {'.', '.', '.', '.', '.', '.', '.', 'X','.','.'},
            {'.', '.', '.', '.', '.', '.', '.', 'X','.','.'},
            {'.', '.', '.', '.', '.', '.', '.', 'X','.','.'},
            {'.', '.', '.', '.', '.', '.', '.', 'X','.','.'},
            {'.', 'A', 'X', 'X', 'X', 'X', 'X', 'X','.','.'},
            {'.', '.', '.', '.', '.', '.', '.', '.','.','.'},
            {'.', '.', '.', '.', '.', '.', '.', '.','.','.'},
        
        };

        N = kartta.length;
        etaisyys = new int[N * N];
        karttaArvoina = analysoiKartta(kartta);
        verkko = new ArrayList[kartta.length * kartta[0].length];
        paino = new ArrayList[kartta.length * kartta[0].length];
        //tulostaKartta(karttaArvoina);
        luoVerkko(karttaArvoina);
        p = new int[N * N];

        for (int i = 0; i < etaisyys.length; i++) {
            etaisyys[i] = INF;
        }

        suoritaDijkstra();

    }

    private static void suoritaDijkstra() {
        int aloitus = muutaPitkaksi(Ay, Ax);

        etaisyys[aloitus] = 0;

        minKeko.add(new Solmu(0, aloitus));

        while (!minKeko.isEmpty()) {

            tulostaKartta(kartta);
            System.out.println("");
            Solmu pienin = minKeko.poll();
            int tunnus = pienin.tunnus;
            int tY = getRivi(tunnus);
            int tX = getSarake(tunnus);

            kartta[tY][tX] = '*';

            if (tY == By && tX == Bx) {
                System.out.println("Maali!");
                break;
            }

            for (int i = 0; i < verkko[tunnus].size(); i++) {
                int toinenSolmu = verkko[tunnus].get(i);
                int toinenY = getRivi(toinenSolmu);
                int toinenX = getSarake(toinenSolmu);
                int vanhaEtaisyys = etaisyys[toinenSolmu];
                int uusiEtaisyys = pienin.paino + karttaArvoina[toinenY][toinenX];

                if (uusiEtaisyys < vanhaEtaisyys) {
                    etaisyys[toinenSolmu] = uusiEtaisyys;
             
                    p[toinenSolmu] = tunnus;
                    int prioriteetti = uusiEtaisyys + heuristiikka(toinenSolmu);
                    minKeko.add(new Solmu(uusiEtaisyys, toinenSolmu));

                }

            }

        }


        polku((By * N) + Bx);
        tulostaKartta(kartta);
//        int rivi = 0;
//        for (int i = 0; i < N * N; i++) {
//            rivi++;
//            if(rivi == N){
//                System.out.println("");
//                rivi=0;
//            }
//            System.out.print(etaisyys[i] + " ");
//            
//        }
        
    }

    // Manhattan
    private static int heuristiikka(int solmu) {

        int D = 0;
        int dx = Math.abs(getSarake(solmu) - Bx);
        int dy = Math.abs(getRivi(solmu) - By);

        return D * (dx + dy);
    }

    private static void polku(int s) {
        if (p[s] != 0) {
            polku(p[s]);
        }

        kartta[getRivi(s)][getSarake(s)] = '©';
    }

    private static int getRivi(int pidennettyKoordinaatti) {
        return pidennettyKoordinaatti / N;
    }

    private static int getSarake(int pidennettyKoordinaatti) {
        return pidennettyKoordinaatti % N;
    }

    private static int muutaPitkaksi(int y, int x) {
        return (y * N) + x;
    }

    private static int[][] analysoiKartta(char[][] kartta) {
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
            Ax = j;
            Ay = i;
            return 1;
        }
        if (l == 'B') {
            Bx = j;
            By = i;
            return 1;
        } else {
            return INF; // should be infinity
        }
    }

    private static void tulostaKartta(int[][] kartta) {
        for (int[] rivi : kartta) {
            System.out.println(Arrays.toString(rivi));
        }
    }

    private static void tulostaKartta(char[][] kartta) {
        for (char[] rivi : kartta) {
            System.out.println(Arrays.toString(rivi));
        }
    }

    private static void luoVerkko(int[][] kartta) {
        for (int i = 0; i < verkko.length; i++) {
            verkko[i] = new ArrayList<>();
            paino[i] = new ArrayList<>();
        }

        int[][] suunnat = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

        for (int i = 0; i < kartta.length; i++) {
            int[] rivi = kartta[i];
            for (int j = 0; j < rivi.length; j++) {
                int l = rivi[j];
                // System.out.println(i + " " + j);
                for (int[] pari : suunnat) {
                    int y = pari[0];
                    int x = pari[1];
                    int uusiY = i + y;
                    int uusiX = j + x;
                    if (uusiY < 0 || uusiX < 0 || uusiY >= N || uusiX >= N) {
                        continue;
                    }

                    verkko[(i * N) + j].add((uusiY * N) + uusiX);

                }
                //System.out.println("");
                // System.out.println("");
            }
        }
    }

}
