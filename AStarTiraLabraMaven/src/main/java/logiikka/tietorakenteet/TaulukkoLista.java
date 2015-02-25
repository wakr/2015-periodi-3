package logiikka.tietorakenteet;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * ArrayList-toteutus
 *
 *
 * @author Kristian Wahlroos
 * @param <T> Taulukon tyyppi
 */
public class TaulukkoLista<T> implements Iterable<T> {

    private T[] alkiot;
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
        this.alkiot = (T[]) new Comparable[kapasiteetti];
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
    public boolean add(T e) {

        if (koko == kapasiteetti) {
            kasvataKokoa();
        }

        alkiot[koko] = e;
        koko++;
        return true;
    }

    public void clear() {
        this.alkiot = (T[]) new Comparable[kapasiteetti];
        koko = 0;
    }

    /**
     * Palauttaa alkio kohdasta
     *
     * @param index kohta taulussa
     * @throws IllegalStateException Jos indeksi on isompi kuin taulu
     * @return Object kohdasta index
     */
    public T get(int index) {
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
    public T set(int index, T element) {
        if (index < 0 || index > koko) {
            throw new IllegalStateException("Indeksi yli rajojen.");
        }
        T edellinen = alkiot[index];
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
    public void add(int index, T element) {
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
    public T remove(int index) {
        if (index < 0 || index > koko) {
            throw new IllegalStateException("Indeksi yli rajojen.");
        }
        T edellinen = alkiot[index];
        alkiot[index] = null; //siftaa vasemmat kiinni
        return edellinen;
    }

    /**
     * Hakee halutun objektin indeksin taulusta
     *
     * @param o Haluttu objekti
     * @return Objektin indeksi
     */
    public int indexOf(T o) {
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
    public int lastIndexOf(T o) {
        for (int i = koko - 1; i >= 0; i--) {
            if (alkiot[i].equals(o)) {
                return i;
            }

        }
        return -1;
    }

    /**
     * Katsoo sisältyykö annettu Collection alkiohin
     *
     * @param c Annettu joukko
     * @return true - jos kaikki c:n alkiot ovat tämän olion alkioissa. Muuten
     * false.
     *
     */
    public boolean containsAll(Collection<?> c) {
        Iterator<?> e = c.iterator();
        while (e.hasNext()) {
            if (!contains(e.next())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {

        Iterator<T> it = iterator();
        if (!it.hasNext()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (;;) {
            T e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (!it.hasNext()) {
                return sb.append(']').toString();
            }
            sb.append(',').append(' ');
        }
    }

    @Override
    public Iterator<T> iterator() {
        Iterator<T> it = new Iterator<T>() {

            private int nykyinenIndeksi = 0;

            @Override
            public boolean hasNext() {
                return nykyinenIndeksi < koko && alkiot[nykyinenIndeksi] != null;
            }

            @Override
            public T next() {
                return alkiot[nykyinenIndeksi++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

}
