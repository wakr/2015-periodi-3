package fi.wakr.logiikka.reitinhaku;

import java.awt.Color;
import fi.wakr.kayttoliittyma.Piirtaja;
import fi.wakr.logiikka.Analysoija;
import fi.wakr.logiikka.Solmu;
import fi.wakr.util.Piste;

/**
 * Dijkstra-algoritmi.
 *
 * @author Kristian Wahlroos
 * @see fi.wakr.io.Tulostaja
 * @see fi.wakr.logiikka.Ymparistomuuttuja
 * @see fi.wakr.logiikka.Analysoija
 * @see fi.wakr.logiikka.Heurestiikka
 * @see fi.wakr.logiikka.Solmu
 * @see fi.wakr.logiikka.reitinhaku.Reitinhakija
 */
public class Dijkstra extends Reitinhakija {

    /**
     * Luo kartan char-merkeistä.
     *
     * @param merkkiKartta Kartta char-merkkeinä
     */
    public Dijkstra(char[][] merkkiKartta) {
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
     * @param karttaPiirtaja Luokka joka luodaan Ikkuna-luokassa ja tuodaan
     * käyttöön piirtämistä varten
     * @param karttaRGB Kartta, joka on muokattu RGB-väreihin ts. kuva
     */
    public Dijkstra(int[][] karttaRGB, Piirtaja karttaPiirtaja) {
        super();
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

    /**
     * Aloittaa laittamalla lähtösolmun kekoon jonka jälkeen edetään naapureihin
     * ja analysoidaan ne heurestiikan nojalla, kunnes keko on tyhjä tai ollaan
     * löydetty maalissa sijaitseva solmu B.
     */
    @Override
    public void suoritaReitinHaku() {

        if (onkoaAlustamaton()) {
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
                keskeyta = false;
                break;
            }

            tutkiPienimmanNaapurit(tunnus, pienin);

        }

        merkkaaKaydytKarttaan('*');

    }

    @Override
    public void tutkiPienimmanNaapurit(int tunnus, Solmu pienin) {

        // otetaan naapurit
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
                double prioriteetti = uusiEtaisyys;
                polku[vierusSolmu] = tunnus;
                openSet.add(new Solmu(prioriteetti, vierusSolmu, uusiEtaisyys));
                merkkaaKarttaan(Color.DARK_GRAY, vierusSolmu);
            }
        }
    }

    private void lisaaAloitusSolmu() {
        int aloitus = Analysoija.muutaPitkaksi(lahtoY, lahtoX, kartanLeveys);
        etaisyysArviotAlkuun[aloitus] = 0;
        openSet.add(new Solmu(0, aloitus, 0));
    }

    @Override
    public void ilmoitaMaalinMuutoksesta() {
        openSet.clear();
        this.lopullisetPituudet = new boolean[kartanLeveys * kartanKorkeus];
        lisaaAloitusSolmu();
        alustaEtaisyydetAarettomiksi();
    }

}
