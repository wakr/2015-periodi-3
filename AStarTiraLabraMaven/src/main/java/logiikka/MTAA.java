package logiikka;

import java.awt.Color;
import kayttoliittyma.Piirtaja;
import util.Piste;

/**
 * Moving Target Adaptive A* -algoritmi.
 * http://www.cs.cmu.edu/~maxim/files/mtswithaa_aamas07.pdf Laajennos
 * A*-algoritmista, joka pitää kirjaa edellisistä hauista nopeuttaen seuraavaa
 * hakua. Pystyy toimimaan jopa 50% nopeammin, kun peräkkäiset A* haut. Toimii
 * myös liikkuviin maaleihin.
 *
 * @author Kristian Wahlroos
 * @see io.Tulostaja
 * @see logiikka.Ymparistomuuttuja
 * @see logiikka.Analysoija
 * @see logiikka.Heurestiikka
 * @see logiikka.Solmu
 * @see logiikka.Reitinhakija
 */
public class MTAA extends Reitinhakija {

    //MTAA
    private double[] heurestisetArvot;
    private int[] hakuMaarat;
    private int laskuri;

    /**
     * Luo kartan merkkikartasta.
     *
     * @param merkkiKartta Kartta char-merkkeinä
     */
    public MTAA(char[][] merkkiKartta) {
        super();
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
     * Luo kartan kuvasta.
     *
     * @param karttaPiirtaja Luokka jonka luodaan Ikkuna-luokassa ja tuodaan
     * MTAA*:n käyttöön
     * @param karttaRGB Kartta, joka on muokattu RGB-väreihin ts. kuva
     */
    public MTAA(int[][] karttaRGB, Piirtaja karttaPiirtaja) {
        super();
        this.karttaPiirtaja = karttaPiirtaja;
        this.RGBKartta = karttaRGB;
        this.karttaArvoina = analysoija.analysoiKarttaArvoiksiVareista(karttaRGB, this);
        this.kartanKorkeus = RGBKartta.length;
        this.kartanLeveys = RGBKartta[0].length;
        this.etaisyysArviotAlkuun = new long[kartanKorkeus * kartanLeveys];
        this.polku = new int[kartanKorkeus * kartanLeveys];
        this.lopullisetPituudet = new boolean[kartanLeveys * kartanKorkeus];
        this.heurestisetArvot = new double[kartanKorkeus * kartanLeveys];
        this.hakuMaarat = new int[kartanLeveys * kartanLeveys];

        laskuri = 0;
        luoVerkkoRBG();
        alustaEtaisyydetAarettomiksi();
        alustaHeurestiikka();
    }

    private void alustaHeurestiikka() {
        for (int i = 0; i < heurestisetArvot.length; i++) {
            int nytY = Analysoija.getRivi(i, kartanLeveys);
            int nytX = Analysoija.getSarake(i, kartanLeveys);
            Piste nyt = new Piste(nytX, nytY);

            double hS = Heurestiikka.laskeHeurestinenArvo(nyt, new Piste(maaliX, maaliY));
            hS += Heurestiikka.lisaaTiebraker(
                    nyt,
                    new Piste(maaliX, maaliY),
                    new Piste(lahtoX, lahtoY));
            heurestisetArvot[i] = hS;
        }

    }

    /**
     * Ero MTAA* ja tavallisen A* välillä on tässä metodissa. Reitinhaun idea on
     * sama, kuin A*-algoritmissa, mutta tässä pidetään kirjaa heurestisista
     * arvoista ja päivitetään niitä kaavalla h(s) = g(maali) - g(s).
     *
     */
    @Override
    public void suoritaReitinHaku() {

        if (onkoaAlustamaton()) {
            throw new IllegalStateException("Maali tai lähtö alustamaton.");
        }

        alustaKierros();
        lisaaAloitusSolmu();

        while (!openSet.isEmpty() || keskeyta) {

            Solmu pienin = openSet.poll();
            int tunnus = pienin.tunnus;
            int pieninY = Analysoija.getRivi(tunnus, kartanLeveys);
            int pieninX = Analysoija.getSarake(tunnus, kartanLeveys);
            merkkaaKarttaan(Color.MAGENTA, tunnus);
            solmuNyt = tunnus;

            if (onkoTutkittuJo(tunnus)) {
                continue;
            }

            if (maaliLoydettyTaiMaaliVaihtuu(pieninY, pieninX)) {
                break;
            }

            tutkiPienimmanNaapurit(tunnus, pienin);

        }

        updateH(); // päivittää kaikkien analysoitujen solmun h(s)-arvot.
        keskeyta = false;

    }

    /**
     * Tutkii halvimman solmun kaikki mahdolliset naapurit verkossa.
     *
     * @param tunnus Pienimmän solmun tunnus
     * @param pienin Pienin solmu
     */
    @Override
    public void tutkiPienimmanNaapurit(int tunnus, Solmu pienin) {
        for (int i = 0; i < verkko[tunnus].size(); i++) {

            int vierusSolmu = verkko[tunnus].get(i);
            alustaSolmuMTAA(vierusSolmu);
            int vierusSolmunY = Analysoija.getRivi(vierusSolmu, kartanLeveys);
            int vierusSolmunX = Analysoija.getSarake(vierusSolmu, kartanLeveys);

            long vanhaEtaisyys = etaisyysArviotAlkuun[vierusSolmu];
            long uusiEtaisyys = pienin.etaisyysAlusta + karttaArvoina[vierusSolmunY][vierusSolmunX];

            parempiEtaisyysArvioLoydetty(uusiEtaisyys, vanhaEtaisyys, vierusSolmu, tunnus);
        }
    }

    private void parempiEtaisyysArvioLoydetty(long uusiEtaisyys, long vanhaEtaisyys, int vierusSolmu, int tunnus) {
        if (uusiEtaisyys < vanhaEtaisyys) {
            analysoidut.add(vierusSolmu);
            etaisyysArviotAlkuun[vierusSolmu] = uusiEtaisyys;
            double prioriteetti = uusiEtaisyys + heurestisetArvot[vierusSolmu];
            polku[vierusSolmu] = tunnus;
            openSet.add(new Solmu(prioriteetti, vierusSolmu, uusiEtaisyys));
            merkkaaKarttaan(Color.DARK_GRAY, vierusSolmu);
        }
    }

    private boolean onkoTutkittuJo(int tunnus) {
        if (lopullisetPituudet[tunnus]) {
            return true;
        }
        lopullisetPituudet[tunnus] = true;
        return false;
    }

    private void alustaKierros() {
        laskuri++;
        alustaSolmuMTAA(Analysoija.muutaPitkaksi(lahtoY, lahtoX, kartanLeveys));
        alustaSolmuMTAA(Analysoija.muutaPitkaksi(maaliY, maaliX, kartanLeveys));
        softReset();
    }

    /**
     * Alustaa solmun hakujen välissä MTAA* mukaan. Tämän avulla turhista
     * solmuista päästään eroon.
     *
     * @param solmu Alustettava solmu
     */
    public void alustaSolmuMTAA(int solmu) {
        if (hakuMaarat[solmu] != laskuri && hakuMaarat[solmu] != 0) {
            etaisyysArviotAlkuun[solmu] = Ymparistomuuttuja.INF.getArvo();
        }
        hakuMaarat[solmu] = laskuri;
    }

    /**
     * Päivitetään kaikki solmut Closed Setissä.
     */
    public void updateH() {

        int maali = Analysoija.muutaPitkaksi(maaliY, maaliX, kartanLeveys);
        for (int i = 0; i < lopullisetPituudet.length; i++) {
            if (lopullisetPituudet[i]) { // for each s c- Closed
                heurestisetArvot[i] = etaisyysArviotAlkuun[maali] - etaisyysArviotAlkuun[i]; //h(s) = g(s_target) - g(s)
            }
        }
    }

    private void lisaaAloitusSolmu() {
        int aloitus = Analysoija.muutaPitkaksi(lahtoY, lahtoX, kartanLeveys);
        etaisyysArviotAlkuun[aloitus] = 0;
        openSet.add(new Solmu(etaisyysArviotAlkuun[aloitus] + heurestisetArvot[aloitus], aloitus, 0));
    }

    /**
     * Ilmoittaa MTAA*-algoritmille, että maali on vaihtunut, jolloin lasketaan
     * heurestinen arvio uuteen maaliin.
     */
    @Override
    public void ilmoitaMaalinMuutoksesta() {
        keskeyta = true;
        int maaliNyt = Analysoija.muutaPitkaksi(maaliY, lahtoX, kartanLeveys);
        double vanha = heurestisetArvot[maaliNyt];
        for (int i = 0; i < heurestisetArvot.length; i++) {

            int iX = Analysoija.getSarake(i, kartanLeveys);
            int iY = Analysoija.getRivi(i, kartanLeveys);
            Piste s = new Piste(iX, iY);

            double hS = Heurestiikka.laskeHeurestinenArvo(s, new Piste(maaliX, maaliY));
            hS += Heurestiikka.lisaaTiebraker(
                    s,
                    new Piste(maaliX, maaliY),
                    new Piste(lahtoX, lahtoY));

            //hValues[i] = Math.max(hS, hValues[i] - vanha); ei toimi pseudossa?
            heurestisetArvot[i] = hS;
        }

    }

    private void softReset() {
        openSet.clear();
        lopullisetPituudet = new boolean[kartanLeveys * kartanKorkeus]; //Open == Closed == empty
        this.analysoidut.clear();
        this.polunKoordinaatit.clear();
        alustaEtaisyydetAarettomiksi();
    }

}
