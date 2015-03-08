package fi.wakr.util;

/**
 * Luokka koordinaatiston pisteitä varten.
 *
 * @author Kristian Wahlroos
 */
public class Piste {

    private final int x, y;

    public Piste(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
