package logiikka.tietorakenteet;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/**
 * ArrayList *Kesken*
 *
 *
 * @author kride
 */
public class TaulukkoLista implements List<Object>, RandomAccess, Cloneable, Serializable {

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
     * Palauttaa taulukon koon
     */
    @Override
    public int size() {
        return koko;
    }

    /**
     * Palauttaa onko taulukko tyhjä
     */
    @Override
    public boolean isEmpty() {
        return koko == 0;
    }

    /**
     * Etsii taulukosta
     *
     * @param o Etsittävä objekti
     */
    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < koko; i++) {
            if (alkiot[i].equals(o)) {
                return true;
            }

        }
        return false;
    }

    /**
     * Iteraattori taulukkoa varten
     *
     * @return Iteraattori
     */
    @Override
    public Iterator<Object> iterator() {
        return new Iterator<Object>() {

            @Override
            public boolean hasNext() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Object next() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }

    /**
     * @return Syötetty data
     */
    @Override
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
     *  @param a tyyppitaulukko
     *
     * @return Taulukko samaatyyppia kuin annettu parametri
     */
    @Override
    public <T> T[] toArray(T[] a) {
        Object[] palautettava = new Object[koko];

        for (int i = 0; i < koko; i++) {
            palautettava[i] = alkiot[i];
        }
        return (T[]) palautettava;
    }

    /**
     * Lisaa objektin taulukkoon
     *
     * @param e Lisättävä objekti
     */
    @Override
    public boolean add(Object e) {

        if (koko == kapasiteetti) {
            kasvataKokoa();
        }

        alkiot[koko] = e;
        koko++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAll(Collection<? extends Object> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAll(int index, Collection<? extends Object> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        alkiot = new Object[koko];
        koko = 0;
    }

    /**
     * Palauttaa alkio kohdasta
     *
     * @param index kohta taulussa
     * @throws IllegalStateException Jos indeksi on isompi kuin taulu
     */
    @Override
    public Object get(int index) {
        if (index < 0 || index > koko) {
            throw new IllegalStateException("Indeksi yli rajojen.");
        }
        return alkiot[index];
    }

    /**
     * Asettaa taulussa olevan objektin halutuksi ja palauttaa vanhan
     *
     * @param index halutun indeksi
     * @param element korvaaja
     * @return korvattu
     * @throws IllegalStateException Jos indeksi on isompi kuin taulu
     */
    @Override
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
    @Override
    public void add(int index, Object element) {
        if (index < 0 || index > koko) {
            throw new IllegalStateException("Indeksi yli rajojen.");
        }

        alkiot[index] = element;

    }

    /**
     * Poistaa halutusta indeksistä
     *
     * @param index Haluttu indeksi
     * @throws IllegalStateException Jos indeksi on isompi kuin taulu
     */
    @Override
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
     */
    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < koko; i++) {
            if (alkiot[i].equals(o)) {
                return i;
            }

        }
        return -1;
    }

    /**
     *
     */
    @Override
    public int lastIndexOf(Object o) {
        for (int i = koko - 1; i >= 0; i--) {
            if (alkiot[i].equals(o)) {
                return i;
            }

        }
        return -1;
    }

    @Override
    public ListIterator<Object> listIterator() {
        return new ListIterator<Object>() {

            @Override
            public boolean hasNext() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Object next() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean hasPrevious() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Object previous() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int nextIndex() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int previousIndex() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void set(Object e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void add(Object e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }

    @Override
    public ListIterator<Object> listIterator(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return Arrays.toString(alkiot);
    }

}
