package logiikka;

/**
 * Laskee Manhattan etäisyyden kahden solmun välille. Manhattan etäisyyteen
 * lisätään suhdeluku tulona, jolloin etäisyyden suhdetta voidaan säätää.
 * Käytännössä D = 0 tarkoittaa, että A*-algoritmin pitäisi toimia kuten Dijkstra ja
 * jos D on suurempi kuin 0, niin A*-algoritmin käyttäytyminen reitinhaussa
 * muuttuu.
 *
 * @author kristianw
 * @see logiikka.AStar
 * @see logiikka.Ymparistomuuttuja
 */
public class Heurestiikka {

    /**
     * Laskee Manhattan-etäisyyden koordinaattiin
     *
     * @param aloitusX Aloittavan solmun x-koordinaatti
     * @param aloitusY Aloittavan solmun y-koordinaatti
     * @param maaliX Maalin x-koordinaatti
     * @param maaliY Maalin y-koordinaatti
     * @return Palauttaa Manhattan etäisyyden
     */
    public static int laskeHeurestinenArvo(int aloitusX, int aloitusY, int maaliX, int maaliY) {

        int dx = Math.abs(aloitusX - maaliX);
        int dy = Math.abs(aloitusY - maaliY);

        return Ymparistomuuttuja.D.getArvo() * (dy +dx);
        //return Ymparistomuuttuja.D.getArvo() * Math.max(dy, dx);
    }

    /**
     * Tiebraker A* varten
     *
     * @param aloitusX Nykyisen solmun x-koordinaatti
     * @param aloitusY Nykyisen solmun y-koordinaatti
     * @param maaliX Maalin x-koordinaatti
     * @param maaliY Maalin y-koordinaatti
     * @param lahtoX Lähdön x-koordinaatti
     * @param lahtoY Lähdön y-koordinaatti
     * @return Etäisyyden suhteen avulla
     */
    public static double addCross(int aloitusX, int aloitusY, int maaliX, int maaliY, int lahtoX, int lahtoY) {

        double dx1 = aloitusX - maaliX;
        double dy1 = aloitusY - maaliY;
        double dx2 = lahtoX - maaliX;
        double dy2 = lahtoY - maaliY;
        double cross = Math.abs(dx1 * dy2 - dx2 * dy1);
        return cross * 0.001;
    }

}
