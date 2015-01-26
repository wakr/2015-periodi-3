package logiikka;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * A*-algoritmi. Analysoi ja luo verkon jonkan jälkeen ajattaessa etsii
 * pienimmän polun A ja B pisteiden välille käyttäen Dijkstran ideaa johon on
 * lisättä heurestiikka eli Manhattan etäisyys * TieBraker.
 *
 * @author kride
 * @see io.Tulostaja
 * @see logiikka.Ymparistomuuttuja
 * @see logiikka.Analysoija
 * @see logiikka.Heurestiikka
 * @see util.Solmu
 */
public class AStar {

    //private char[][] RGBKartta;
    private int[][] RGBKartta;
    private char[][] merkkiKartta;
    private int[][] karttaArvoina;
    private long[] etaisyysArviotAlkuun;
    private int[] polku;
    private boolean[] lopullisetPituudet;
    private PriorityQueue<Solmu> minKeko;
    private ArrayList<Integer>[] verkko;
    private int kartanLeveys, kartanKorkeus, lahtoY, lahtoX, maaliY, maaliX;
    private Analysoija analysoija;
    private HashSet<Integer> analysoidut;

    public AStar(char[][] merkkiKartta) {
        this.merkkiKartta = merkkiKartta;
        alustaAloitusKoordinaatit();
        this.analysoija = new Analysoija();
        this.karttaArvoina = analysoija.analysoiKarttaArvoiksiMerkeista(merkkiKartta, this);
        this.kartanKorkeus = merkkiKartta.length;
        this.kartanLeveys = merkkiKartta[0].length;
        this.etaisyysArviotAlkuun = new long[kartanKorkeus * kartanLeveys];
        this.polku = new int[kartanKorkeus * kartanLeveys];
        this.lopullisetPituudet = new boolean[kartanLeveys * kartanKorkeus];
        this.minKeko = new PriorityQueue<>();
        luoVerkkoChar();
        alustaEtaisyydetAarettomiksi();
        this.analysoidut = new HashSet<>();
    }

    public AStar(int[][] karttaRGB) {
        this.RGBKartta = karttaRGB;
        this.analysoidut = new HashSet<>();
        alustaAloitusKoordinaatit();
        this.analysoija = new Analysoija();
        this.karttaArvoina = analysoija.analysoiKarttaArvoiksiVareista(karttaRGB, this);
        this.kartanKorkeus = RGBKartta.length;
        this.kartanLeveys = RGBKartta[0].length;
        this.etaisyysArviotAlkuun = new long[kartanKorkeus * kartanLeveys];
        this.polku = new int[kartanKorkeus * kartanLeveys];
        this.lopullisetPituudet = new boolean[kartanLeveys * kartanKorkeus];
        this.minKeko = new PriorityQueue<>();
        luoVerkkoRBG();
        alustaEtaisyydetAarettomiksi();

    }

    private void alustaAloitusKoordinaatit() {
        this.lahtoX = -1;
        this.lahtoY = -1;
        this.maaliX = -1;
        this.maaliY = -1;
    }

    /**
     * Aloittaa laittamalla lähtö solmun kekoon jonka jälkeen edetään
     * naapureihin ja analysoidaan ne heurestiikan nojalla, kunnes keko on tyhjä
     * tai ollaan löydetty maalissa sijaitseva solmu B. Metodi myös pitää yllä
     * lyhintä polkua sekä kirjoittaa karttaan sijaintia.
     */
    public void suoritaReitinHaku() {

        int aloitus = Analysoija.muutaPitkaksi(lahtoY, lahtoX, kartanLeveys);
        etaisyysArviotAlkuun[aloitus] = 0;

        minKeko.add(new Solmu(0, aloitus, 0));


        while (!minKeko.isEmpty()) {

            Solmu pienin = minKeko.poll();
            int tunnus = pienin.tunnus;
            int tY = Analysoija.getRivi(tunnus, kartanLeveys);
            int tX = Analysoija.getSarake(tunnus, kartanLeveys);


            lopullisetPituudet[tunnus] = true;

            if (tY == maaliY && tX == maaliX) {
                break;
            }

            for (int i = 0; i < verkko[tunnus].size(); i++) {

                int toinenSolmu = verkko[tunnus].get(i);
                int toinenY = Analysoija.getRivi(toinenSolmu, kartanLeveys);
                int toinenX = Analysoija.getSarake(toinenSolmu, kartanLeveys);

                if (lopullisetPituudet[toinenSolmu]) {
                    continue;
                }

                long vanhaEtaisyys = etaisyysArviotAlkuun[toinenSolmu];
                long uusiEtaisyys = pienin.etaisyysAlusta + karttaArvoina[toinenY][toinenX];

                if (uusiEtaisyys < vanhaEtaisyys) {
                    analysoidut.add(toinenSolmu);
                    etaisyysArviotAlkuun[toinenSolmu] = uusiEtaisyys;
                    double prioriteetti = uusiEtaisyys + Heurestiikka.laskeHeurestinenArvo(toinenX, toinenY, maaliX, maaliY)
                            + Heurestiikka.addCross(toinenX, toinenY, maaliX, maaliY, lahtoX, lahtoY);

                    polku[toinenSolmu] = tunnus;
                    minKeko.add(new Solmu(prioriteetti, toinenSolmu, uusiEtaisyys));
                }
            }

        }

        merkkaaKaydytKarttaan('*');
        
    }

    private void merkkaaKaydytKarttaan(char merkkaaja) {
        if (merkkiKartta != null) {
            for (int koordinaatti : analysoidut) {
                int kX = Analysoija.getSarake(koordinaatti, kartanLeveys);
                int kY = Analysoija.getRivi(koordinaatti, kartanLeveys);
                merkkiKartta[kY][kX] = merkkaaja;
            }
        }
    }

    public void asetaLahto(int Ax, int Ay) {
        this.lahtoX = Ax;
        this.lahtoY = Ay;
    }

    public void asetaMaali(int Bx, int By) {
        this.maaliX = Bx;
        this.maaliY = By;
    }

    public int getMaali() {
        return Analysoija.muutaPitkaksi(maaliY, maaliX, kartanLeveys);
    }

    public int getLahto() {
        return Analysoija.muutaPitkaksi(lahtoY, lahtoX, kartanLeveys);
    }

    public HashSet<Integer> getAnalysoidut() {
        return this.analysoidut;
    }

    /**
     * Luodaan verkko niin, että jokaiseen viereiseen solmuun kartassa on
     * yhteys. Käytännössä tämä tarkoittaa, että myös esteisiin on yhteys, mutta
     * INF-muuttuja estää suuruudellaan sen läpikäynnin.
     */
    private void luoVerkkoRBG() {
        verkko = new ArrayList[kartanKorkeus * kartanLeveys];

        for (int i = 0; i < verkko.length; i++) {
            verkko[i] = new ArrayList<>();
        }

        int[][] suunnat = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

        for (int i = 0; i < RGBKartta.length; i++) {
            int[] rivi = karttaArvoina[i];
            for (int j = 0; j < rivi.length; j++) {

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

    private void luoVerkkoChar() {
        verkko = new ArrayList[kartanKorkeus * kartanLeveys];

        for (int i = 0; i < verkko.length; i++) {
            verkko[i] = new ArrayList<>();
        }

        int[][] suunnat = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

        for (int i = 0; i < kartanKorkeus; i++) {
            int[] rivi = karttaArvoina[i];
            for (int j = 0; j < rivi.length; j++) {

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

    public char[][] getMerkkiKartta() {
        return merkkiKartta;
    }

    public int[][] getRGBKartta() {
        return RGBKartta;
    }

    private void alustaEtaisyydetAarettomiksi() {
        for (int i = 0; i < etaisyysArviotAlkuun.length; i++) {
            etaisyysArviotAlkuun[i] = Ymparistomuuttuja.INF.getArvo();
        }

        for (int i = 0; i < polku.length; i++) {
            polku[i] = Ymparistomuuttuja.INF.getArvo();

        }
    }

    /**
     * Luo lyhyimmän polun karttaan.
     *
     * @param s Solmu johon halutaan polku
     */
    public void polku(int s) {
        if (polku[s] != Ymparistomuuttuja.INF.getArvo()) {
            polku(polku[s]);
        }

        merkkiKartta[Analysoija.getRivi(s, kartanLeveys)][Analysoija.getSarake(s, kartanLeveys)] = '©';
    }

    /**
     *
     * @param s Solmun tunnus yksiulotteisena johon polku halutaan
     * @param p ArrayList johon polku halutaan asettaa
     * @return Lyhyin polku solmuun s
     */
    public ArrayList<Integer> polku(int s, ArrayList<Integer> p) {
        if (polku[s] != Ymparistomuuttuja.INF.getArvo()) {
            polku(polku[s], p);

        }
        p.add(s);
        return p;
    }

}
