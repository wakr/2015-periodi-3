package fi.wakr.logiikka.tietorakenteet;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Queue-tietorakenne.
 *
 *
 * @param <T> Jonon alkeistyyppi
 */
public class Jono<T> implements Iterable<T> {

    private class Linkki {

        private T alkio;
        private Linkki seuraava;
    }

    private int alkioidenMaara;
    private Linkki ensimmainen, viimeinen;

    public Jono() {
        ensimmainen = null;
        viimeinen = null;
    }

    /**
     * Palauttaa jonon ensimmäisen alkion
     *
     * @return T Alkio tyyppiä T
     */
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Tyhjä jono.");
        }
        return ensimmainen.alkio;
    }

    /**
     * Lisää alkion jonon kärkeen
     *
     * @param t Tyyppiä T oleva alkio
     */
    public void add(T t) {
        Linkki x = new Linkki();
        x.alkio = t;
        if (isEmpty()) {
            ensimmainen = x;
            viimeinen = x;
        } else {
            viimeinen.seuraava = x;
            viimeinen = x;
        }
        alkioidenMaara++;
    }

    /**
     * Palauttaa ja poistaa keon ensimmäisen alkion
     *
     * @return T Alkio tyyppiä T
     */
    public T poll() {
        if (isEmpty()) {
            throw new RuntimeException("Queue underflow");
        }
        T t = ensimmainen.alkio;
        ensimmainen = ensimmainen.seuraava;
        alkioidenMaara--;
        if (isEmpty()) {
            viimeinen = null;
        }
        return t;
    }

    /**
     * Iteraattori Jonolle, jonka avulla voidaan käydä jonon alkiot for-each
     * -komennolla
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private Linkki nykyinen = ensimmainen;

            @Override
            public boolean hasNext() {
                return nykyinen != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T t = nykyinen.alkio;
                nykyinen = nykyinen.seuraava;
                return t;
            }

            // Ei toteutettu, koska ei tarvita algoritmissa.
            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }

    /**
     * Palauttaa alkioiden määrän
     *
     * @return alkioiden määrä
     */
    public int size() {
        return alkioidenMaara;
    }

    /**
     * Kertoo onko jono tyhjä
     *
     * @return true - jos jono on tyhjä, false - muuten
     */
    public boolean isEmpty() {
        return ensimmainen == null;
    }

    /**
     * Tyhjentää jonon kaikista alkioista
     */
    public void clear() {
        alkioidenMaara = 0;
        ensimmainen = null;
        viimeinen = null;
    }

}
