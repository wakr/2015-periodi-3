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
 * @author Kristian Wahlroos
 * @see io.Tulostaja
 * @see logiikka.Ymparistomuuttuja
 * @see logiikka.Analysoija
 * @see logiikka.Heurestiikka
 * @see logiikka.Solmu
 */
public class AStar extends Reitinhakija {

    /**
     * @param merkkiKartta Kartta char-merkkeinä
     */
    public AStar(char[][] merkkiKartta) {
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
    public AStar(int[][] karttaRGB, Piirtaja karttaPiirtaja) {
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

    /**
     * Ilmoittaa A*-algoritmille, että maali on vaihtunut TO-DO: MT-AA*
     */
    @Override
    public void ilmoitaMaalinMuutoksesta() {
        openSet.clear();
        this.lopullisetPituudet = new boolean[kartanLeveys * kartanKorkeus];
        lisaaAloitusSolmu();
        alustaEtaisyydetAarettomiksi();
    }

}
