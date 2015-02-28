package fi.wakr.logiikka.tietorakenteet;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * PriorityQueue-tietorakenne
 *
 *
 * @author Kristian Wahlroos
 * @param <T> Taulukon tyyppi
 */
public class MinimiKeko<T extends Comparable<T>> implements Iterable<T> {

    private static final int DEF_KAPASITEETTI = 100;
    protected T[] alkiot;
    protected int koko;

    public MinimiKeko() {
        alkiot = (T[]) new Comparable[DEF_KAPASITEETTI];
        koko = 0;
    }

    /**
     * Lisää arvon minimikekoon
     *
     * @param arvo Lisättävä arvo, joka on tyyppiä T
     */
    public void add(T arvo) {
        if (koko >= alkiot.length - 1) {
            alkiot = this.laajenna();
        }

        koko++;
        int index = koko;
        alkiot[index] = arvo;

        korjaaYlos();
    }

    /**
     * Tyhjentää minikeon
     */
    public void clear() {
        alkiot = (T[]) new Comparable[DEF_KAPASITEETTI];
        koko = 0;
    }

    /**
     * Iteraattori, jonka avulla for each -komento on muuan muassa käytettävissä
     */
    @Override
    public Iterator<T> iterator() {
        return new ListIterator<T>() {

            int pointteri = 0, aloitus = 0;

            @Override
            public boolean hasNext() {
                return pointteri < koko;
            }

            @Override
            public T next() {
                if (hasNext()) {
                    pointteri++;
                    return alkiot[pointteri];
                }
                throw new NoSuchElementException();
            }

            @Override
            public boolean hasPrevious() {
                return pointteri > 0;
            }

            @Override
            public T previous() {
                if (hasPrevious()) {
                    pointteri--;
                    return alkiot[pointteri];
                }
                throw new NoSuchElementException();
            }

            @Override
            public int nextIndex() {
                return pointteri + 1;
            }

            @Override
            public int previousIndex() {
                return pointteri - 1;
            }

            @Override
            public void remove() {
                alkiot[pointteri] = null;
            }

            @Override
            public void set(T e) {
                alkiot[pointteri] = e;
            }

            @Override
            public void add(T e) {
            }
        };
    }

    // heapify ylös
    protected void korjaaYlos() {
        int indeksi = this.koko;

        while (onVanhempi(indeksi)
                && (vanhempi(indeksi).compareTo(alkiot[indeksi]) > 0)) {

            vaihdaPaittain(indeksi, vanhemmanIndeksi(indeksi));
            indeksi = vanhemmanIndeksi(indeksi);
        }
    }

    // heapify alas
    protected void korjaaAlas() {
        int index = 1;

        while (onVasenLapsi(index)) {
            int pienempiLapsi = vasenIndeksi(index);

            if (onOikeaLapsi(index)
                    && alkiot[vasenIndeksi(index)].compareTo(alkiot[oikeaIndeksi(index)]) > 0) {
                pienempiLapsi = oikeaIndeksi(index);
            }

            if (alkiot[index].compareTo(alkiot[pienempiLapsi]) > 0) {
                vaihdaPaittain(index, pienempiLapsi);
            } else {
                break;
            }

            index = pienempiLapsi;
        }
    }

    /**
     * Palauttaa tiedon siitä, onko keko tyhjä
     *
     * @return boolean-arvo, jossa true tarkoittaa, että keko on tyhjä.
     */
    public boolean isEmpty() {
        return koko == 0;
    }

    /**
     * @return Keon alkioiden määrä.
     */
    public int size() {
        return koko;
    }

    /**
     * Ottaa ensimmäisen ja palauttaa sen.
     *
     * @return T Tyyppiä T oleva alkio taulukossa
     */
    public T poll() {
        T result = peek();

        alkiot[1] = alkiot[size()];
        alkiot[size()] = null;
        koko--;

        korjaaAlas();

        return result;
    }

    /**
     * Palauttaa ensimmäisen alkion taulukosta
     *
     * @return T Tyyppiä T oleva alkio
     */
    public T peek() {
        if (this.isEmpty()) {
            throw new IllegalStateException();
        }

        return alkiot[1];
    }

    private boolean onVanhempi(int i) {
        return i > 1;
    }

    private int vasenIndeksi(int i) {
        return i * 2;
    }

    private int oikeaIndeksi(int i) {
        return i * 2 + 1;
    }

    private boolean onVasenLapsi(int i) {
        return vasenIndeksi(i) <= koko;
    }

    private boolean onOikeaLapsi(int i) {
        return oikeaIndeksi(i) <= koko;
    }

    private T vanhempi(int i) {
        return alkiot[vanhemmanIndeksi(i)];
    }

    private int vanhemmanIndeksi(int i) {
        return i / 2;
    }

    private T[] laajenna() {
        return Arrays.copyOf(alkiot, alkiot.length * 2);
    }

    protected void vaihdaPaittain(int index1, int index2) {
        T tmp = alkiot[index1];
        alkiot[index1] = alkiot[index2];
        alkiot[index2] = tmp;
    }

    @Override
    public String toString() {
        return Arrays.toString(alkiot);
    }

}
