/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * ArrayList
 * *Kesken
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

    public TaulukkoLista(int kapasiteetti) {
        this.kapasiteetti = kapasiteetti;
        this.koko = 0;
        this.alkiot = new Object[kapasiteetti];
    }

    private void kasvataKokoa() {
        kapasiteetti *= 2;
        Arrays.copyOf(alkiot, kapasiteetti);
    }

    @Override
    public int size() {
        return koko;
    }

    @Override
    public boolean isEmpty() {
        return koko == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < koko; i++) {
            if (alkiot[i].equals(o)) {
                return true;
            }

        }
        return false;
    }

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
        };
    }

    @Override
    public Object[] toArray() {
        Object[] palautettava = new Object[koko];

        for (int i = 0; i < koko; i++) {
            palautettava[i] = alkiot[i];
        }
        return palautettava;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

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
    }

    @Override
    public Object get(int index) {
        if (index < 0 || index > koko) {
            throw new IllegalStateException("Indeksi yli rajojen.");
        }
        return alkiot[index];
    }

    @Override
    public Object set(int index, Object element) {
        if (index < 0 || index > koko) {
            throw new IllegalStateException("Indeksi yli rajojen.");
        }
        Object edellinen = alkiot[index];
        alkiot[index] = element;
        return edellinen;
    }

    @Override
    public void add(int index, Object element) {
        if (index < 0 || index > koko) {
            throw new IllegalStateException("Indeksi yli rajojen.");
        }
        if (index == 0) {

        } else {

        }
    }

    @Override
    public Object remove(int index) {
        if (index < 0 || index > koko) {
            throw new IllegalStateException("Indeksi yli rajojen.");
        }
        Object edellinen = alkiot[index];
        alkiot[index] = null; //siftaa vasemmat kiinni
        return edellinen;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < koko; i++) {
            if (alkiot[i].equals(o)) {
                return i;
            }

        }
        return -1;
    }

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

}
