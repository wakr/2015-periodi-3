package fi.wakr.logiikka;

import fi.wakr.util.Piste;

/**
 * Laskee Chebyshevin etäisyyden kahden solmun välille. Chebyshevin etäisyyteen
 * lisätään suhdeluku tulona, jolloin etäisyyden suhdetta voidaan säätää.
 * Käytännössä D = 0 tarkoittaa, että heurestiikka alentuu Dijkstraksi ja jos D
 * on suurempi kuin 0, niin heurestiikan käyttäytyminen reitinhaussa muuttuu.
 * Tämä voi tarkoittaa nopeampaa hakua, mutta joka ei löydä lyhintä reittiä, vaan
 * jonkin mahdollisen reitin.
 *
 * @author Kristian Wahlroos
 * @see fi.wakr.logiikka.reitinhaku.Reitinhakija
 * @see fi.wakr.logiikka.Ymparistomuuttuja
 */
public class Heurestiikka {

    /**
     * Laskee Chebyshevin-etäisyyden koordinaattiin
     *
     * @param aloitus Aloituksen koordinaatit
     * @param maali Maalin koordinaatit
     * @return Palauttaa Manhattan etäisyyden
     */
    public static int laskeHeurestinenArvo(Piste aloitus, Piste maali) {
        int dx = Math.abs(aloitus.getX() - maali.getX());
        int dy = Math.abs(aloitus.getY() - maali.getY());
        return Ymparistomuuttuja.D.getArvo() * (dx + dy)
                + (Ymparistomuuttuja.D2.getArvo() - 2 * Ymparistomuuttuja.D.getArvo())
                * Math.min(dx, dy);

    }

    /**
     * Tiebraker A* varten. Tämä on hyödyllistä, jos/kun minikeossa on monta 
     * heurestiikka arviota samoilla arvoilla.
     *
     * @param aloitus Aloituksen koordinaatti
     * @param maali Maalin koordinaatti
     * @param lahto Lahdon koordinaatti
     * @return Etäisyyden suhteen avulla
     */
    public static double lisaaTiebraker(Piste aloitus, Piste maali, Piste lahto) {
        double dx1 = aloitus.getX() - maali.getX();
        double dy1 = aloitus.getY() - maali.getY();
        double dx2 = lahto.getX() - maali.getX();
        double dy2 = lahto.getY() - maali.getY();
        double cross = Math.abs(dx1 * dy2 - dx2 * dy1);
        return cross * 0.0001;
    }

}
