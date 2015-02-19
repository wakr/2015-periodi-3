package util;

/**
 * Luokka koordinaatiston pisteitä varten.
 *
 * @author kride
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
