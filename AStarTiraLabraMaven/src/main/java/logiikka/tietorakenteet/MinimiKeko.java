package logiikka.tietorakenteet;

import java.io.Serializable;
import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

/**
 * PriorityQueue *Kesken*
 *
 *
 * @author kride
 */
public class MinimiKeko<T> extends AbstractQueue<T>
        implements Serializable {

    private static final int DEF_KAPASITEETTI = 100;
    protected T[] alkiot;
    protected int koko;

    public MinimiKeko() {
        alkiot = (T[]) new Comparable[DEF_KAPASITEETTI];
        koko = 0;
    }

    @Override
    public boolean add(T e) {
        return super.add(e); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Palauttaa tiedon siitä, onko keko tyhjä
     *
     * @return boolean-arvo, jossa true tarkoittaa, että keko on tyhjä.
     */
    @Override
    public boolean isEmpty() {
        return koko == 0;
    }

    /**
     * @return Keon alkioiden määrä.
     */
    @Override
    public int size() {
        return koko;
    }

    @Override
    public boolean offer(T e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T poll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T peek() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    @Override
    public String toString() {
        return Arrays.toString(alkiot);
    }

}
