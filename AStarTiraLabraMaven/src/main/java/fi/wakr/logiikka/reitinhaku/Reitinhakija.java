package fi.wakr.logiikka.reitinhaku;

import java.awt.Color;
import fi.wakr.kayttoliittyma.Piirtaja;
import fi.wakr.logiikka.Analysoija;
import fi.wakr.logiikka.Solmu;
import fi.wakr.logiikka.Ymparistomuuttuja;
import fi.wakr.logiikka.tietorakenteet.Jono;
import fi.wakr.logiikka.tietorakenteet.MinimiKeko;
import fi.wakr.logiikka.tietorakenteet.TaulukkoLista;
import fi.wakr.util.Piste;

/**
 * Abstrakti luokka, joka määrittelee toiminnallisuuden reitinhaku-algoritmeille
 *
 * @author Kristian Wahlroos
 */
public abstract class Reitinhakija {

    // tarvittavat tietorakenteet 
    protected int[][] RGBKartta, karttaArvoina;
    protected char[][] merkkiKartta;
    protected long[] etaisyysArviotAlkuun;
    protected int[] polku;
    protected boolean[] lopullisetPituudet;
    protected MinimiKeko<Solmu> openSet;
    protected TaulukkoLista<Integer>[] verkko;
    protected int kartanLeveys, kartanKorkeus, lahtoY, lahtoX, maaliY, maaliX;
    protected Analysoija analysoija;
    protected Jono<Integer> analysoidut, polunKoordinaatit;
    protected boolean keskeyta;
    protected int solmuNyt;

    // Piirto
    protected Piirtaja karttaPiirtaja;

    /**
     * Suorittaa algoritmi kohtaisen reitinhaun verkossa
     */
    public abstract void suoritaReitinHaku();

    /**
     * Ilmottaa algoritmille, että maali on muuttunut ja vanhat
     * heurestiikka-arviot eivät välttämättä enää päde
     */
    public abstract void ilmoitaMaalinMuutoksesta();

    /**
     * Tutkii solmun, jolla on alhaisin f(s)-arvo naapurit
     *
     * @param tunnus Pienimmän solmun tunnus
     * @param pienin Pienin solmu
     */
    public abstract void tutkiPienimmanNaapurit(int tunnus, Solmu pienin);

    protected Reitinhakija() {
        alustaAloitusKoordinaatit();
        this.analysoidut = new Jono<>();
        this.analysoija = new Analysoija();
        this.openSet = new MinimiKeko<>();
        this.polunKoordinaatit = new Jono<>();
        keskeyta = false;
        solmuNyt = Analysoija.muutaPitkaksi(lahtoY, lahtoX, kartanLeveys);

    }

    protected boolean onkoaAlustamaton() {
        return lahtoX == -1 || lahtoY == -1 || maaliX == -1 || maaliY == -1;
    }

    /**
     * @return viimeksi analysoidun solmun tunnus
     */
    public int getNykyinenSolmu() {
        return solmuNyt;
    }

    /**
     * Piirtää lopullisen polun karttaan.
     */
    public void naytaPolku() {
        if (karttaPiirtaja != null) {
            luoPolku(getMaali());
            for (int solmu : getPolku()) {
                karttaPiirtaja.piirraKarttaanNopeasti(Color.RED, solmu);
            }
        }
    }

    /**
     * Keskeyttää reitinhaun suorituksen.
     */
    public void keskeyta() {
        keskeyta = true;

    }

    /**
     * Asettaa reitinhaun lähdön koordinaatin
     *
     * @param Ax x-koordinaatti
     * @param Ay y-koordinaatti
     */
    public void asetaLahto(int Ax, int Ay) {
        this.lahtoX = Ax;
        this.lahtoY = Ay;
    }

    /**
     * Asettaa reitinhaun maalin koordinaatin
     *
     * @param Bx x-koordinaatti
     * @param By y-koordinaatti
     */
    public void asetaMaali(int Bx, int By) {
        this.maaliX = Bx;
        this.maaliY = By;
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
    public Jono<Integer> getAnalysoidut() {
        return this.analysoidut;
    }

    /**
     * Palauttaa solmun kaikki naapurit
     *
     * @param solmu Haluttu solmu
     * @return Lista naapureiden tunnuksista
     */
    public TaulukkoLista<Integer> getSolmunNaapurit(int solmu) {
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
    public Jono<Integer> getPolku() {
        return polunKoordinaatit;
    }

    public char[][] getMerkkiKartta() {
        return merkkiKartta;
    }

    public int[][] getRGBKartta() {
        return RGBKartta;
    }

    protected boolean maaliLoydettyTaiMaaliVaihtuu(int pieninY, int pieninX) {
        if ((pieninY != maaliY || pieninX != maaliX) && !keskeyta) {
        } else {
            return true;
        }
        return false;
    }

    protected final void alustaAloitusKoordinaatit() {
        this.lahtoX = -1;
        this.lahtoY = -1;
        this.maaliX = -1;
        this.maaliY = -1;
    }

    protected void alustaEtaisyydetAarettomiksi() {
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
    public void luoVerkkoRBG() {
        verkko = new TaulukkoLista[kartanKorkeus * kartanLeveys];

        for (int i = 0; i < verkko.length; i++) {
            verkko[i] = new TaulukkoLista<>();
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
    public void luoVerkkoChar() {
        verkko = new TaulukkoLista[kartanKorkeus * kartanLeveys];

        for (int i = 0; i < verkko.length; i++) {
            verkko[i] = new TaulukkoLista<>();
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
    public TaulukkoLista<Integer> polku(int s, TaulukkoLista<Integer> p) {
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

    protected void merkkaaKaydytKarttaan(char merkkaaja) {
        if (merkkiKartta != null) {
            for (int koordinaatti : analysoidut) {
                int kX = Analysoija.getSarake(koordinaatti, kartanLeveys);
                int kY = Analysoija.getRivi(koordinaatti, kartanLeveys);
                merkkiKartta[kY][kX] = merkkaaja;
            }
        }
    }

    protected void merkkaaKarttaan(Color vari, int koordinaatti) {
        if (karttaPiirtaja != null) {
            karttaPiirtaja.piirraKarttaanHitaasti(vari, koordinaatti);
        }
    }

    /**
     * Alustaa reitinhaun uudelleen, jotta sitä voitaisiin käyttää, vaikka
     * kartta muuttuu.
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
}
