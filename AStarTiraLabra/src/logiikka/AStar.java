/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logiikka;

import util.Solmu;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 *
 * @author kride
 */
public class AStar {

    private char[][] kartta;
    private int[][] karttaArvoina;
    private int[] etaisyysArviot, polku;
    private boolean[] lopullisetPituudet;
    private PriorityQueue<Solmu> minKeko;
    private ArrayList<Integer>[] verkko;
    private int kartanLeveys, kartanKorkeus, lahtoY, lahtoX, maaliY, maaliX;
    private Analysoija analysoija;

    public AStar(char[][] kartta) {
        this.kartta = kartta;
        this.analysoija = new Analysoija();
        this.karttaArvoina = analysoija.analysoiKarttaArvoiksi(kartta);
        this.kartanKorkeus = kartta.length;
        this.kartanLeveys = kartta[0].length;
        alustaAloitusKoordinaatit();
        this.etaisyysArviot = new int[kartanKorkeus * kartanLeveys];
        this.polku = new int[kartanKorkeus * kartanLeveys];
        this.lopullisetPituudet = new boolean[kartanLeveys * kartanKorkeus];
        this.minKeko = new PriorityQueue<>();
        luoVerkko();
    }

    private void alustaAloitusKoordinaatit() {
        this.lahtoX = -1;
        this.lahtoY = -1;
        this.maaliX = -1;
        this.maaliY = -1;
    }

    private void luoVerkko() {
        verkko = new ArrayList[kartanKorkeus * kartanLeveys];
        
        for (int i = 0; i < verkko.length; i++) {
            verkko[i] = new ArrayList<>();
        }

        int[][] suunnat = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

        for (int i = 0; i < kartta.length; i++) {
            int[] rivi = karttaArvoina[i];
            for (int j = 0; j < rivi.length; j++) {
                int l = rivi[j];
     
                for (int[] pari : suunnat) {
                    int y = pari[0];
                    int x = pari[1];
                    int uusiY = i + y;
                    int uusiX = j + x;
                    if (uusiY < 0 || uusiX < 0 || uusiY >= kartanKorkeus || uusiX >= kartanLeveys) {
                        continue;
                    }

                    verkko[(i * kartanKorkeus) + j].add((uusiY * kartanKorkeus) + uusiX);

                }
             
            }
        }
    }

}
