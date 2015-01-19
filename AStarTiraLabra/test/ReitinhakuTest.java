/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import logiikka.AStar;
import logiikka.Analysoija;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kride
 */
public class ReitinhakuTest {

    AStar aStar;

    @Test(timeout = 1000)
    public void testaaPienenKartanNaapuritLahdosta() {
        char[][] pieniKartta1 = new char[][]{
            {'A', 'X', 'X', 'X', 'B'},
            {'.', 'X', 'X', 'X', '.'},
            {'.', 'X', 'X', 'X', '.'},
            {'.', '.', '.', '.', '.'}
        };
        aStar = new AStar(pieniKartta1);
        ArrayList<Integer> luvut = new ArrayList<>();
        luvut.add(1);
        luvut.add(pieniKartta1[0].length);

        assertTrue(aStar.getSolmunNaapurit(0).containsAll(luvut));

    }

    @Test(timeout = 1000)
    public void testaaMiniKartanNaapuritLahdosta() {
        char[][] pieniKartta1 = new char[][]{
            {'A', 'X', 'B'},
            {'.', '.', '.'},};
        aStar = new AStar(pieniKartta1);
        ArrayList<Integer> luvut = new ArrayList<>();
        luvut.add(1);
        luvut.add(pieniKartta1[0].length);

        assertTrue(aStar.getSolmunNaapurit(0).containsAll(luvut));

    }

    @Test(timeout = 1000)
    public void testaaKeskiKartanNaapuritLahdosta() {
        char[][] pieniKartta1 = new char[][]{
            {'.', 'X', '.', '.', '.', '.', '.', '.', '.', '.', 'B'},
            {'A', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'},};
        aStar = new AStar(pieniKartta1);
        ArrayList<Integer> luvut = new ArrayList<>();
        luvut.add(0);
        luvut.add(pieniKartta1[0].length + 1);

        assertTrue(aStar.getSolmunNaapurit(11).containsAll(luvut));

    }

    @Test(timeout = 1000)
    public void konvertteriToimiiLyhyeksi() {
        char[][] pieniKartta1 = new char[][]{
            {'A', 'X', 'B'},
            {'.', '.', '.'},};
        int koordinaattiPitkana = 2;

        int saatuY = Analysoija.getRivi(koordinaattiPitkana, pieniKartta1[0].length);
        int saatuX = Analysoija.getSarake(koordinaattiPitkana, pieniKartta1[0].length);

        assertArrayEquals(new int[]{0, 2}, new int[]{saatuY, saatuX});
    }

    @Test(timeout = 1000)
    public void konvertteriToimiiLyhyeksi2() {
        char[][] pieniKartta1 = new char[][]{
            {'A', 'X', 'B'},
            {'.', '.', '.'},};
        int koordinaattiPitkana = 0;

        int saatuY = Analysoija.getRivi(koordinaattiPitkana, pieniKartta1[0].length);
        int saatuX = Analysoija.getSarake(koordinaattiPitkana, pieniKartta1[0].length);

        assertArrayEquals(new int[]{0, 0}, new int[]{saatuY, saatuX});
    }

    @Test(timeout = 1000)
    public void konvertteriToimiiLyhyeksi3() {
        char[][] pieniKartta1 = new char[][]{
            {'A', 'X', 'B'},
            {'.', '.', '.'},};
        int koordinaattiPitkana = 4;

        int saatuY = Analysoija.getRivi(koordinaattiPitkana, pieniKartta1[0].length);
        int saatuX = Analysoija.getSarake(koordinaattiPitkana, pieniKartta1[0].length);

        assertArrayEquals(new int[]{1, 1}, new int[]{saatuY, saatuX});
    }

    @Test(timeout = 1000)
    public void pitkaksiMuutosToimii() {
        char[][] pieniKartta1 = new char[][]{
            {'A', 'X', 'B'},
            {'.', '.', '.'},};
        
        int haluttu = 1;
        assertEquals(haluttu, Analysoija.muutaPitkaksi(0, 1, pieniKartta1[0].length));
    }
    
     @Test(timeout = 1000)
    public void pitkaksiMuutosToimii2() {
        char[][] pieniKartta1 = new char[][]{
            {'A', 'X', 'B'},
            {'.', '.', '.'},
            {'.', '.', '.'},};
        
        int haluttu = 4;
        assertEquals(haluttu, Analysoija.muutaPitkaksi(1, 1, pieniKartta1[0].length));
    }

}
