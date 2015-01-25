/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import io.Tulostaja;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import logiikka.AStar;
import logiikka.Analysoija;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Reitinhakuihin liittyvät testit
 * @author kride
 */
public class AStarTest {

    static char[][] pieninKartta = new char[][]{
        {'A', '.'},
        {'.', 'B'}};
    static char[][] pieninKarttaEsteella = new char[][]{
        {'A', 'X'},
        {'.', 'B'}};
    static char[][] pieniKartta = new char[][]{
        {'A', 'X', 'X', 'X', 'B'},
        {'.', 'X', 'X', 'X', '.'},
        {'.', 'X', 'X', 'X', '.'},
        {'.', '.', '.', '.', '.'}};
    static char[][] vapaaPieniKartta = new char[][]{
        {'A', 'X', 'X', 'X', 'B'},
        {'.', '.', '.', '.', '.'},
        {'.', 'X', 'X', 'X', '.'},
        {'.', '.', '.', '.', '.'}};
    static char[][] esteKartta = new char[][]{
        {'A', 'X', 'X', 'X', 'B'},
        {'.', '.', 'X', '.', '.'},
        {'.', 'X', '.', 'X', '.'},
        {'.', '.', 'X', 'X', '.'},
        {'.', '.', 'X', '.', '.'},
        {'.', '.', 'X', '.', '.'},
        {'.', '.', '.', '.', '.'}};
    static char[][] esteKartta2 = new char[][]{
        {'A', '.', '.', '.', '.'},
        {'.', '.', '.', 'X', '.'},
        {'.', '.', '.', 'X', '.'},
        {'.', '.', 'X', 'X', '.'},
        {'.', '.', 'X', 'B', '.'},
        {'.', '.', 'X', '.', '.'},
        {'.', '.', '.', '.', '.'}};
    static char[][] leveaKartta = new char[][]{
        {'.', 'X', '.', '.', '.', '.', '.', '.', '.', '.', 'B'},
        {'A', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'}};
    static char[][] miniKartta = new char[][]{
        {'A', 'X', 'B'},
        {'.', '.', '.'}};
    static char[][] serpettiiniKentta = new char[][]{
        {'.', 'X', '.', '.', '.', 'X', '.', '.', '.', 'X', 'B'},
        {'A', '.', '.', 'X', '.', '.', '.', 'X', '.', '.', '.'}};

    private AStar aStar;

    @Test(timeout = 1000)
    public void miniKarttaPolkuOikein() {
        aStar = new AStar(miniKartta);
        aStar.suoritaReitinHaku();
        List<Integer> haluttu = Arrays.asList(0, 3, 4, 5, 2);
        assertEquals(haluttu.toString(), aStar.polku(2, new ArrayList<Integer>()).toString());
    }

    @Test(timeout = 1000)
    public void miniKarttaEsteellaPolkuOikein() {
        aStar = new AStar(pieninKarttaEsteella);
        aStar.suoritaReitinHaku();
        List<Integer> haluttu = Arrays.asList(0, 2, 3);
        assertEquals(haluttu.toString(), aStar.polku(3, new ArrayList<Integer>()).toString());
    }

    @Test(timeout = 1000)
    public void serpenttiiniPolkuOikein() {
        aStar = new AStar(serpettiiniKentta);
        aStar.suoritaReitinHaku();
        aStar.polku(10);
        List<Integer> haluttu = Arrays.asList(11, 12, 13, 2, 3, 4, 15, 16, 17, 6, 7, 8, 19, 20, 21, 10);
        assertEquals(haluttu.toString(), aStar.polku(10, new ArrayList<Integer>()).toString());
    }

    @Test(timeout = 1000)
    public void maaliLoydetaanHaunJalkeenPieniKartasta() {
        aStar = new AStar(pieniKartta);
        aStar.suoritaReitinHaku();
        aStar.polku(aStar.getMaali());
        List<Integer> haluttu = Arrays.asList(aStar.getLahto(), 5, 10, 15, 16, 17, 18, 19, 14, 9, 4);
        assertEquals(haluttu.toString(), aStar.polku(aStar.getMaali(), new ArrayList<Integer>()).toString());
    }

    @Test(timeout = 1000)
    public void lyhinPolkuLoydetaan() {
        aStar = new AStar(vapaaPieniKartta);
        aStar.suoritaReitinHaku();
        int lyhyimmanPituus = 7;
        ArrayList<Integer> lyhinPolku = aStar.polku(aStar.getMaali(), new ArrayList<Integer>());
        assertEquals(lyhyimmanPituus, lyhinPolku.size());
    }

    @Test(timeout = 1000)
    public void lyhinPolkuLoydetaan2() {
        aStar = new AStar(esteKartta);
        aStar.suoritaReitinHaku();
        int lyhyimmanPituus = 17;
        ArrayList<Integer> lyhinPolku = aStar.polku(aStar.getMaali(), new ArrayList<Integer>());
        assertEquals(lyhyimmanPituus, lyhinPolku.size());
    }

    @Test(timeout = 1000)
    public void lyhinPolkuLoydetaan3() {
        aStar = new AStar(esteKartta2);
        aStar.suoritaReitinHaku();
        int lyhyimmanPituus = 10;
        ArrayList<Integer> lyhinPolku = aStar.polku(aStar.getMaali(), new ArrayList<Integer>());
        assertEquals(lyhyimmanPituus, lyhinPolku.size());
    }

}
