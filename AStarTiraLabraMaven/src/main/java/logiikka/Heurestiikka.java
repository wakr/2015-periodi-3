package logiikka;

import util.Piste;

/**
 * Laskee Manhattan etäisyyden kahden solmun välille. Manhattan etäisyyteen
 * lisätään suhdeluku tulona, jolloin etäisyyden suhdetta voidaan säätää.
 * Käytännössä D = 0 tarkoittaa, että A*-algoritmin pitäisi toimia kuten
 * Dijkstra ja jos D on suurempi kuin 0, niin A*-algoritmin käyttäytyminen
 * reitinhaussa muuttuu.
 *
 * @author kristianw
 * @see logiikka.AStar
 * @see logiikka.Ymparistomuuttuja
 */
public class Heurestiikka {

    /**
     * Laskee Manhattan-etäisyyden koordinaattiin
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
     * Tiebraker A* varten
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
