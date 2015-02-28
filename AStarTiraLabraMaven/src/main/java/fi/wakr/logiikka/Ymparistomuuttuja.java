package fi.wakr.logiikka;

/**
 * Sisältää hyödylliset arvot, jotka muun muassa määrittelevät äärettömyyden
 * verkkorakenteessa, sekä heurestiikkan käyttämän parametrin <i>D</i>.
 * Kumpiakin arvoja voidaan säädellä riippuen halutusta säätötoiminnallisuudesta
 * esimerkiksi reitinhaussa tai verkon dimensiosta.
 *
 * @author Kristian Wahlroos
 * @see fi.wakr.logiikka.reitinhaku.AStar
 * @see fi.wakr.logiikka.Heurestiikka
 */
public enum Ymparistomuuttuja {

    /**
     * Äärettömyys
     */
    INF(999999999),
    /**
     * A*-algoritmin säätöparametri
     */
    D(2),
    /**
     * Keskimääräinen kustannus liikkuessa
     */
    D2(2),
    /**
     * "Maksuttoman" ruudun paino
     */
    NormaaliRuutu(1),
    /**
     * Vesiesteen paino
     */
    VesiRuutu(5);

    private final int arvo;

    private Ymparistomuuttuja(int arvo) {
        this.arvo = arvo;
    }

    public int getArvo() {
        return this.arvo;
    }
}
