package logiikka;

import extra.Ymparistomuuttuja;
import io.Tulostaja;
import util.Solmu;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 *
 * @author kride
 */
public class AStar {

    private char[][] kartta;
    private int[][] karttaArvoina;
    private int[] etaisyysArviotAlkuun, polku;
    private boolean[] lopullisetPituudet;
    private PriorityQueue<Solmu> minKeko;
    private ArrayList<Integer>[] verkko;
    private int kartanLeveys, kartanKorkeus, lahtoY, lahtoX, maaliY, maaliX;
    private Analysoija analysoija;

    public AStar(char[][] kartta) {
        this.kartta = kartta;
        alustaAloitusKoordinaatit();
        this.analysoija = new Analysoija();
        this.karttaArvoina = analysoija.analysoiKarttaArvoiksi(kartta, this);
        this.kartanKorkeus = kartta.length;
        this.kartanLeveys = kartta[0].length;
        this.etaisyysArviotAlkuun = new int[kartanKorkeus * kartanLeveys];
        this.polku = new int[kartanKorkeus * kartanLeveys];
        this.lopullisetPituudet = new boolean[kartanLeveys * kartanKorkeus];
        this.minKeko = new PriorityQueue<>();
        luoVerkko();
        alustaEtaisyydetAarettomiksi();
    }

    private void alustaAloitusKoordinaatit() {
        this.lahtoX = -1;
        this.lahtoY = -1;
        this.maaliX = -1;
        this.maaliY = -1;
    }

    public void suoritaReitinHaku() {

        int aloitus = Analysoija.muutaPitkaksi(lahtoY, lahtoX, kartanLeveys);
        etaisyysArviotAlkuun[aloitus] = 0;

        minKeko.add(new Solmu(0, aloitus));

        while (!minKeko.isEmpty()) {

            System.out.println("");
            Tulostaja.tulostaKartta(kartta);
            System.out.println("");
          // Tulostaja.tulostaEtaisydet(etaisyysArviotAlkuun, kartanLeveys);
            System.out.println("");
            Solmu pienin = minKeko.poll();
            int tunnus = pienin.tunnus;
            int tY = Analysoija.getRivi(tunnus, kartanLeveys);
            int tX = Analysoija.getSarake(tunnus, kartanLeveys);

            kartta[tY][tX] = '*';

            if (lopullisetPituudet[tunnus]) {
                continue;
            }
            lopullisetPituudet[tunnus] = true;

            if (tY == maaliY && tX == maaliX) {
                System.out.println("Maali!");
                break;
            }

            for (int i = 0; i < verkko[tunnus].size(); i++) {
                int toinenSolmu = verkko[tunnus].get(i);
                int toinenY = Analysoija.getRivi(toinenSolmu, kartanLeveys);
                int toinenX = Analysoija.getSarake(toinenSolmu, kartanLeveys);

                int vanhaEtaisyys = etaisyysArviotAlkuun[toinenSolmu];
                int uusiEtaisyys = pienin.paino + karttaArvoina[toinenY][toinenX]
                         + Heurestiikka.laskeHeurestinenArvo(toinenX, toinenY, maaliX, maaliY);
                
                if (uusiEtaisyys < vanhaEtaisyys) {
                    etaisyysArviotAlkuun[toinenSolmu] = uusiEtaisyys;
                    polku[toinenSolmu] = tunnus;
                    minKeko.add(new Solmu(uusiEtaisyys, toinenSolmu));
                }
            }

        }
        
        polku(Analysoija.muutaPitkaksi(maaliY, maaliX, kartanLeveys));
        Tulostaja.tulostaKartta(kartta);

    }

    public void asetaLahto(int Ax, int Ay) {
        this.lahtoX = Ax;
        this.lahtoY = Ay;
    }

    public void asetaMaali(int Bx, int By) {
        this.maaliX = Bx;
        this.maaliY = By;
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
                //int l = rivi[j];

                for (int[] pari : suunnat) {
                    int y = pari[0];
                    int x = pari[1];
                    int uusiY = i + y;
                    int uusiX = j + x;
                    if (uusiY < 0 || uusiX < 0 || uusiY >= kartanKorkeus || uusiX >= kartanLeveys) {
                        continue;
                    }
                    int tarkasteltava = (i * kartanLeveys) + j;
                    int naapuri = (uusiY * kartanLeveys) + uusiX;
                    verkko[tarkasteltava].add(naapuri);
                    
                }

            }
        }
    }

    public ArrayList<Integer> getSolmunNaapurit(int solmu) {
        return verkko[solmu];
    }

  

    private void alustaEtaisyydetAarettomiksi() {
        for (int i = 0; i < etaisyysArviotAlkuun.length; i++) {
            etaisyysArviotAlkuun[i] = Ymparistomuuttuja.INF.getArvo();
        }
    }

    private void polku(int s) {
        if (polku[s] != 0) {
            polku(polku[s]);
        }

        kartta[Analysoija.getRivi(s, kartanLeveys)][Analysoija.getSarake(s, kartanLeveys)] = '©';
    }

}
