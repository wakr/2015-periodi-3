package logiikka;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import kayttoliittyma.Piirtaja;
import util.Piste;

/**
 * A*-algoritmi. Analysoi ja luo verkon jonkan jälkeen ajattaessa etsii
 * pienimmän polun A ja B pisteiden välille käyttäen Dijkstran algoritmin ideaa
 * johon on lisätty heurestiikka eli Manhattan etäisyys * TieBraker.
 *
 * @author Kristian Wahlroos
 * @see io.Tulostaja
 * @see logiikka.Ymparistomuuttuja
 * @see logiikka.Analysoija
 * @see logiikka.Heurestiikka
 * @see logiikka.Solmu
 */
public class MTAA extends Reitinhakija {

    //MTAA
    private double[] hValues;
    private int[] search;
    private int counter;

    /**
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
     * @param karttaPiirtaja Luokka jonka luodaan Ikkuna-luokassa ja tuodaan
     * A*:n käyttöön
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
        this.hValues = new double[kartanKorkeus * kartanLeveys];
        this.search = new int[kartanLeveys * kartanLeveys];

        counter = 0;
        luoVerkkoRBG();
        alustaEtaisyydetAarettomiksi();
        alustaHeurestiikka();
    }

    private void alustaHeurestiikka() { // alustetaan h(s) jokaiselle s c- S
        for (int i = 0; i < hValues.length; i++) {
            int nytY = Analysoija.getRivi(i, kartanLeveys);
            int nytX = Analysoija.getSarake(i, kartanLeveys);
            Piste nyt = new Piste(nytX, nytY);

            double hS = Heurestiikka.laskeHeurestinenArvo(nyt, new Piste(maaliX, maaliY));
            hS += Heurestiikka.lisaaTiebraker(
                    nyt,
                    new Piste(maaliX, maaliY),
                    new Piste(lahtoX, lahtoY));
            hValues[i] = hS;
        }

    }

    /**
     * Aloittaa laittamalla lähtö solmun kekoon jonka jälkeen edetään
     * naapureihin ja analysoidaan ne heurestiikan nojalla, kunnes keko on tyhjä
     * tai ollaan löydetty maalissa sijaitseva solmu B. Metodi myös pitää yllä
     * lyhintä polkua sekä kirjoittaa karttaan sijaintia.
     */
    @Override
    public void suoritaReitinHaku() {

        if (onkoaAustamaton()) {
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

        updateH(); // updates all expanded nodes
        keskeyta = false;

    }

    private void tutkiPienimmanNaapurit(int tunnus, Solmu pienin) {
        for (int i = 0; i < verkko[tunnus].size(); i++) {

            int vierusSolmu = verkko[tunnus].get(i);
            initializeState(vierusSolmu);
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
            double prioriteetti = uusiEtaisyys + hValues[vierusSolmu];
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
        counter++;
        initializeState(Analysoija.muutaPitkaksi(lahtoY, lahtoX, kartanLeveys)); // IS(s_start)
        initializeState(Analysoija.muutaPitkaksi(maaliY, maaliX, kartanLeveys)); //IS(s_target)
        softReset();
    }

    private void initializeState(int s) {
        if (search[s] != counter && search[s] != 0) {
            etaisyysArviotAlkuun[s] = Ymparistomuuttuja.INF.getArvo();
        }
        search[s] = counter;
    }

    public void updateH() {

        int maali = Analysoija.muutaPitkaksi(maaliY, maaliX, kartanLeveys);
        for (int i = 0; i < lopullisetPituudet.length; i++) {
            if (lopullisetPituudet[i]) { // for each s c- Closed
                hValues[i] = etaisyysArviotAlkuun[maali] - etaisyysArviotAlkuun[i]; //h(s) = g(s_target) - g(s)
            }
        }
    }

    private void lisaaAloitusSolmu() {
        int aloitus = Analysoija.muutaPitkaksi(lahtoY, lahtoX, kartanLeveys);
        etaisyysArviotAlkuun[aloitus] = 0;
        openSet.add(new Solmu(etaisyysArviotAlkuun[aloitus] + hValues[aloitus], aloitus, 0));
    }

    /**
     * Ilmoittaa A*-algoritmille, että maali on vaihtunut TO-DO: MT-AA*
     */
    @Override
    public void ilmoitaMaalinMuutoksesta() {
        keskeyta = true;
        int maaliNyt = Analysoija.muutaPitkaksi(maaliY, lahtoX, kartanLeveys);
        double vanha = hValues[maaliNyt];
        for (int i = 0; i < hValues.length; i++) {

            int iX = Analysoija.getSarake(i, kartanLeveys);
            int iY = Analysoija.getRivi(i, kartanLeveys);
            Piste s = new Piste(iX, iY);

            double hS = Heurestiikka.laskeHeurestinenArvo(s, new Piste(maaliX, maaliY));
            hS += Heurestiikka.lisaaTiebraker(
                    s,
                    new Piste(maaliX, maaliY),
                    new Piste(lahtoX, lahtoY));

            //hValues[i] = Math.max(hS, hValues[i] - vanha);
            hValues[i] = hS;
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
