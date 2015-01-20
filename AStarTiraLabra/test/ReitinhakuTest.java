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
 *
 * @author kride
 */
public class ReitinhakuTest {

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
    public void etaisyysAlustaHaluttuMiniKartassa() {
        aStar = new AStar(pieninKartta);
        aStar.suoritaReitinHaku();
        System.out.println(aStar.getEtaisyysAlusta(2));
    }

}
