package logiikka.tietorakenteet;

import java.util.Arrays;

/**
 * ArrayList-toteutus
 *
 *
 * @author Kristian Wahlroos
 * @param <T> Taulukon tyyppi
 */
public class TaulukkoLista<T> {

    private Object[] alkiot;
    private int koko;
    private int kapasiteetti;
    private static final int OLETUS_KAPASITEETTI = 100;

    public TaulukkoLista() {
        this(OLETUS_KAPASITEETTI);
    }

    /**
     * @param kapasiteetti Taulukon maksimi tilavuus
     */
    public TaulukkoLista(int kapasiteetti) {
        this.kapasiteetti = kapasiteetti;
        this.koko = 0;
        this.alkiot = new Object[kapasiteetti];
    }

    private void kasvataKokoa() {
        kapasiteetti *= 2;
        alkiot = Arrays.copyOf(alkiot, kapasiteetti);
    }

    /**
     * Palauttaa taulukon koon.
     *
     * @return Listan alkioiden määrä
     */
    public int size() {
        return koko;
    }

    /**
     * Palauttaa onko taulukko tyhjä.
     *
     * @return True - jos taulukossa ei ole alkioita, False muuten
     */
    public boolean isEmpty() {
        return koko == 0;
    }

    /**
     * Etsii taulukosta, että sisältääkö se halutun arvon
     *
     * @param o Etsittävä objekti
     *
     * @return True - jos etsittävä löytyy. False muulloin.
     */
    public boolean contains(Object o) {
        for (int i = 0; i < koko; i++) {
            if (alkiot[i].equals(o)) {
                return true;
            }

        }
        return false;
    }

    /**
     * Muuttaa taulukon Object[]-tyyliseksi taulukoksi.
     *
     * @return Alkiot Object[]-taulukossa
     */
    public Object[] toArray() {
        Object[] palautettava = new Object[koko];

        for (int i = 0; i < koko; i++) {
            if (alkiot[i] == null) {
                continue;
            }
            palautettava[i] = alkiot[i];
        }
        return palautettava;
    }

    /**
     * Muuttaa palautettavan taulukon haluttuun muotoon.
     *
     *  @param a tyyppitaulukko
     *
     * @param <T> Tyyppi jossa taulukko halutaan
     * @param a
     * @return Taulukko samaatyyppia kuin annettu parametri
     */
    public <T> T[] toArray(T[] a) {
        Object[] palautettava = new Object[koko];

        for (int i = 0; i < koko; i++) {
            palautettava[i] = alkiot[i];
        }
        return (T[]) palautettava;

    }

    /**
     * Lisaa objektin taulukkoon.
     *
     * @param e Lisättävä objekti
     * @return True - jos objektin lisääminen onnistui.
     */
    public boolean add(Object e) {

        if (koko == kapasiteetti) {
            kasvataKokoa();
        }

        alkiot[koko] = e;
        koko++;
        return true;
    }

    public void clear() {
        alkiot = new Object[koko];
        koko = 0;
    }

    /**
     * Palauttaa alkio kohdasta
     *
     * @param index kohta taulussa
     * @throws IllegalStateException Jos indeksi on isompi kuin taulu
     * @return Object kohdasta index
     */
    public Object get(int index) {
        if (index < 0 || index > koko) {
            throw new IllegalStateException("Indeksi yli rajojen.");
        }
        return alkiot[index];
    }

    /**
     * Asettaa taulussa olevan objektin halutuksi ja palauttaa vanhan.
     *
     * @param index halutun indeksi
     * @param element korvaaja
     * @return korvattu
     * @throws IllegalStateException Jos indeksi on isompi kuin taulu
     */
    public Object set(int index, Object element) {
        if (index < 0 || index > koko) {
            throw new IllegalStateException("Indeksi yli rajojen.");
        }
        Object edellinen = alkiot[index];
        alkiot[index] = element;
        return edellinen;
    }

    /**
     * Lisää kohtaan i, muttei palauta sitä
     *
     * @param index haluttu kohta
     * @param element korvaaja
     * @throws IllegalStateException Jos indeksi on isompi kuin taulu
     */
    public void add(int index, Object element) {
        if (index < 0 || index > koko) {
            throw new IllegalStateException("Indeksi yli rajojen.");
        }

        alkiot[index] = element;

    }

    /**
     * Poistaa halutusta indeksistä ja palauttaa sen.
     *
     * @param index Haluttu indeksi
     * @throws IllegalStateException Jos indeksi on isompi kuin taulu
     * @return Palauttaa poistetun
     */
    public Object remove(int index) {
        if (index < 0 || index > koko) {
            throw new IllegalStateException("Indeksi yli rajojen.");
        }
        Object edellinen = alkiot[index];
        alkiot[index] = null; //siftaa vasemmat kiinni
        return edellinen;
    }

    /**
     * Hakee halutun objektin indeksin taulusta
     *
     * @param o Haluttu objekti
     * @return Objektin indeksi
     */
    public int indexOf(Object o) {
        for (int i = 0; i < koko; i++) {
            if (alkiot[i].equals(o)) {
                return i;
            }

        }
        return -1;
    }

    /**
     * Palauttaa viimeisen indeksin, jossa objekti esiintyy.
     *
     * @param o Etsittävä objekti
     * @return Esiintymän indeksi
     */
    public int lastIndexOf(Object o) {
        for (int i = koko - 1; i >= 0; i--) {
            if (alkiot[i].equals(o)) {
                return i;
            }

        }
        return -1;
    }

    @Override
    public String toString() {
        return Arrays.toString(alkiot);
    }

}
