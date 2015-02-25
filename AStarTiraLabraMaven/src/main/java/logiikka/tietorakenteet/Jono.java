package logiikka.tietorakenteet;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * *Kesken*
 *
 *
 * @param <T>
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

    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Tyhjä jono.");
        }
        return ensimmainen.alkio;
    }

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
        };
    }

    public int size() {
        return alkioidenMaara;
    }

    public boolean isEmpty() {
        return ensimmainen == null;
    }

    public void clear() {
        alkioidenMaara = 0;
        ensimmainen = null;
        viimeinen = null;
    }

}
