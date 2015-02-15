package logiikka;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import kayttoliittyma.Piirtaja;
import util.Piste;

/**
 * A*-algoritmi. Analysoi ja luo verkon jonkan jälkeen ajattaessa etsii
 * pienimmän polun A ja B pisteiden välille käyttäen Dijkstran algoritmin ideaa
 * johon on lisätty heurestiikka eli Manhattan etäisyys * TieBraker.
 *
 * @author kride
 * @see io.Tulostaja
 * @see logiikka.Ymparistomuuttuja
 * @see logiikka.Analysoija
 * @see logiikka.Heurestiikka
 * @see logiikka.Solmu
 */
public class AStar {

    private int[][] RGBKartta, karttaArvoina;
    private char[][] merkkiKartta;
    private long[] etaisyysArviotAlkuun;
    private int[] polku;
    private boolean[] lopullisetPituudet;
    private PriorityQueue<Solmu> openSet;
    private ArrayList<Integer>[] verkko;
    private int kartanLeveys, kartanKorkeus, lahtoY, lahtoX, maaliY, maaliX;
    private Analysoija analysoija;
    private Queue<Integer> analysoidut, polunKoordinaatit;
    private boolean keskeyta;
    private int solmuNyt;

    // Piirto
    Piirtaja karttaPiirtaja;

    /**
     * @param merkkiKartta Kartta char-merkkeinä
     */
    public AStar(char[][] merkkiKartta) {
        this();
        this.merkkiKartta = merkkiKartta;
        this.karttaArvoina = analysoija.analysoiKarttaArvoiksiMerkeista(merkkiKartta, this);
        this.kartanKorkeus = merkkiKartta.length;
        this.kartanLeveys = merkkiKartta[0].length;
        this.etaisyysArviotAlkuun = new long[kartanKorkeus * kartanLeveys];
        this.polku = new int[kartanKorkeus * kartanLeveys];
        this.lopullisetPituudet = new boolean[kartanLeveys * kartanKorkeus];
        luoVerkkoChar();
        alustaEtaisyydetAarettomiksi();

    }

    /**
     * @param karttaPiirtaja Luokka jonka luodaan Ikkuna-luokassa ja tuodaan
     * A*:n käyttöön
     * @param karttaRGB Kartta, joka on muokattu RGB-väreihin ts. kuva
     */
    public AStar(int[][] karttaRGB, Piirtaja karttaPiirtaja) {
        this();
        this.karttaPiirtaja = karttaPiirtaja;
        this.RGBKartta = karttaRGB;
        this.karttaArvoina = analysoija.analysoiKarttaArvoiksiVareista(karttaRGB, this);
        this.kartanKorkeus = RGBKartta.length;
        this.kartanLeveys = RGBKartta[0].length;
        this.etaisyysArviotAlkuun = new long[kartanKorkeus * kartanLeveys];
        this.polku = new int[kartanKorkeus * kartanLeveys];
        this.lopullisetPituudet = new boolean[kartanLeveys * kartanKorkeus];
        luoVerkkoRBG();
        alustaEtaisyydetAarettomiksi();
    }

    private AStar() {
        alustaAloitusKoordinaatit();
        this.analysoidut = new ArrayDeque<>();
        this.analysoija = new Analysoija();
        this.openSet = new PriorityQueue<>();
        this.polunKoordinaatit = new ArrayDeque<>();
        keskeyta = false;
        solmuNyt = Analysoija.muutaPitkaksi(lahtoY, lahtoX, kartanLeveys);
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

        if (onkoaAustamaton()) {
            throw new IllegalStateException("Maali tai lähtö alustamaton.");
        }

        lisaaAloitusSolmu();

        while (!openSet.isEmpty()) {

            Solmu pienin = openSet.poll();
            int tunnus = pienin.tunnus;
            int pieninY = Analysoija.getRivi(tunnus, kartanLeveys);
            int pieninX = Analysoija.getSarake(tunnus, kartanLeveys);
            merkkaaKarttaan(Color.MAGENTA, tunnus);
            solmuNyt = tunnus;
            lopullisetPituudet[tunnus] = true;

            if ((pieninY == maaliY && pieninX == maaliX) || keskeyta) {
                break;
            }

            for (int i = 0; i < verkko[tunnus].size(); i++) {

                int vierusSolmu = verkko[tunnus].get(i);
                int vierusSolmunY = Analysoija.getRivi(vierusSolmu, kartanLeveys);
                int vierusSolmunX = Analysoija.getSarake(vierusSolmu, kartanLeveys);
                Piste vSP = new Piste(vierusSolmunX, vierusSolmunY);
                Piste mSP = new Piste(maaliX, maaliY);

                if (lopullisetPituudet[vierusSolmu]) {
                    continue;
                }

                long vanhaEtaisyys = etaisyysArviotAlkuun[vierusSolmu];
                long uusiEtaisyys = pienin.etaisyysAlusta + karttaArvoina[vierusSolmunY][vierusSolmunX];

                if (uusiEtaisyys < vanhaEtaisyys) {
                    analysoidut.add(vierusSolmu);
                    etaisyysArviotAlkuun[vierusSolmu] = uusiEtaisyys;
                    double prioriteetti = uusiEtaisyys + Heurestiikka.laskeHeurestinenArvo(vSP, mSP)
                            + Heurestiikka.lisaaTiebraker(
                                    vSP,
                                    new Piste(maaliX, maaliY),
                                    new Piste(lahtoX, lahtoY));
                    polku[vierusSolmu] = tunnus;
                    openSet.add(new Solmu(prioriteetti, vierusSolmu, uusiEtaisyys));
                    merkkaaKarttaan(Color.DARK_GRAY, vierusSolmu);
                }
            }

        }

        merkkaaKaydytKarttaan('*');

    }

    private void lisaaAloitusSolmu() {
        int aloitus = Analysoija.muutaPitkaksi(lahtoY, lahtoX, kartanLeveys);
        etaisyysArviotAlkuun[aloitus] = 0;
        openSet.add(new Solmu(0, aloitus, 0));
    }

    private boolean onkoaAustamaton() {
        return lahtoX == -1 || lahtoY == -1 || maaliX == -1 || maaliY == -1;
    }

    /**
     * @return viimeksi analysoidun solmun
     */
    public int getNykyinenSolmu() {
        return solmuNyt;
    }

    /**
     * Keskeyttää A* algoritmin suorituksen
     */
    public void keskeyta() {
        keskeyta = true;
    }

    public void naytaPolku() {
        if (karttaPiirtaja != null) {
            luoPolku(getMaali());
            for (int solmu : getPolku()) {
                karttaPiirtaja.piirraKarttaan(Color.RED, solmu);
            }
        }
    }

    /**
     * Asettaa A*-algoritmin lähdön koordinaatin
     *
     * @param Ax x-koordinaatti
     * @param Ay y-koordinaatti
     */
    public void asetaLahto(int Ax, int Ay) {
        this.lahtoX = Ax;
        this.lahtoY = Ay;
    }

    /**
     * Asettaa A*-algoritmin maalin koordinaatin
     *
     * @param Bx x-koordinaatti
     * @param By y-koordinaatti
     */
    public void asetaMaali(int Bx, int By) {
        this.maaliX = Bx;
        this.maaliY = By;
    }

    /**
     * Ilmoittaa A*-algoritmille, että maali on vaihtunut TO-DO: MT-AA*
     */
    public void ilmoitaMaalinMuutoksesta() {
        openSet.clear();
        this.lopullisetPituudet = new boolean[kartanLeveys * kartanKorkeus];
        lisaaAloitusSolmu();
        alustaEtaisyydetAarettomiksi();
    }

    /**
     * Palauttaa maalikoordinaatin
     *
     * @return Piste-olio (x,y) muotoisena
     */
    public Piste getMaaliPisteena() {
        return new Piste(maaliX, maaliY);
    }

    /**
     * Palauttaa lahtokoordinaatin
     *
     * @return Piste-olio (x,y) muotoisena
     */
    public Piste getLahtoPisteena() {
        return new Piste(lahtoX, lahtoY);
    }

    /**
     * Palauttaa maalikoordinaatin pitkässä muodossa
     *
     * @return Maalikoordinaatti
     */
    public int getMaali() {
        return Analysoija.muutaPitkaksi(maaliY, maaliX, kartanLeveys);
    }

    /**
     * Palauttaa lahtokoordinaatin pitkässä muodossa
     *
     * @return Lähtökoordinaatti
     */
    public int getLahto() {
        return Analysoija.muutaPitkaksi(lahtoY, lahtoX, kartanLeveys);
    }

    /**
     * Palauttaa kaikki analysoidut solmut jonossa, jotta ne voidaan myöhemmin
     * piirtää järjestyksessä
     *
     * @return Jono analysoituja koordinaatteja
     */
    public Queue<Integer> getAnalysoidut() {
        return this.analysoidut;
    }

    /**
     * Palauttaa solmun kaikki naapurit
     *
     * @param solmu Haluttu solmu
     * @return Lista naapureiden tunnuksista
     */
    public ArrayList<Integer> getSolmunNaapurit(int solmu) {
        return verkko[solmu];
    }

    /**
     * Palauttaa g-arvon solmulle.
     *
     * @param tunnus Haluttu solmu
     * @return Pituus lähdöstä haluttuun solmuun.
     */
    public long getPituusAlkuun(int tunnus) {
        return etaisyysArviotAlkuun[tunnus];
    }

    /**
     * Palauttaa polun koordinaatit jonossa, jotta ne voidaan piirtää myöhemmin
     *
     * @return Polun koordinaatit jonossa
     */
    public Queue<Integer> getPolku() {
        return polunKoordinaatit;
    }

    public char[][] getMerkkiKartta() {
        return merkkiKartta;
    }

    public int[][] getRGBKartta() {
        return RGBKartta;
    }

    /**
     * Alustaa A* uudelleen, jotta sitä voitaisiin käyttää, vaikka kartta
     * muuttuu.
     */
    public void resetoiAlgoritmi() {
        this.polku = new int[kartanKorkeus * kartanLeveys];
        this.lopullisetPituudet = new boolean[kartanLeveys * kartanKorkeus];
        this.openSet.clear();
        this.analysoidut.clear();
        this.polunKoordinaatit.clear();
        alustaEtaisyydetAarettomiksi();
        keskeyta = false;
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
     * Luodaan verkko niin, että jokaiseen viereiseen solmuun kartassa on
     * yhteys. Käytännössä tämä tarkoittaa, että myös esteisiin on yhteys, mutta
     * INF-muuttuja estää suuruudellaan sen läpikäynnin.
     */
    private void luoVerkkoRBG() {
        verkko = new ArrayList[kartanKorkeus * kartanLeveys];

        for (int i = 0; i < verkko.length; i++) {
            verkko[i] = new ArrayList<>();
        }

        int[][] suunnat = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1},
        {1, 1}, {-1, -1}, {1, -1}, {-1, 1}};

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

    /**
     * Luodaan verkko niin, että jokaiseen viereiseen solmuun kartassa on
     * yhteys. Käytännössä tämä tarkoittaa, että myös esteisiin on yhteys, mutta
     * INF-muuttuja estää suuruudellaan sen läpikäynnin.
     */
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
                    verkko[tarkasteltava].add(naapuri); //aiheuttaa hitautta

                }

            }
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
     * Luo polun ArrayListaan
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

    /**
     * Luo polun jonoon
     *
     * @param s Solmun tunnus yksiulotteisena johon polku halutaan
     */
    public void luoPolku(int s) {
        if (polku[s] != Ymparistomuuttuja.INF.getArvo()) {
            luoPolku(polku[s]);
        }
        polunKoordinaatit.add(s);
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

    private void merkkaaKarttaan(Color vari, int koordinaatti) {
        if (karttaPiirtaja != null) {
            karttaPiirtaja.piirraKarttaan(vari, koordinaatti);
        }
    }

}
