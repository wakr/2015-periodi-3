package fi.wakr.logiikka;


import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * Solmu-apuluokkaan sisältyvät testit.
 * 
 */
public class SolmuTest {

    @Test
    public void SolmuAinaYksilollinen() {
        Solmu s1 = new Solmu(1, 1, 0);
        Solmu s2 = new Solmu(1, 2, 1);

        assertTrue(s1.hashCode() != s2.hashCode());
    }

    @Test
    public void SolmutSamatJosSamaTunnus() {
        Solmu s1 = new Solmu(1, 1, 0);
        Solmu s2 = new Solmu(1, 1, 1);
        Solmu s3 = new Solmu(1, 2, 1);

        assertTrue(s1.equals(s2));
        assertTrue(!s1.equals(null));
        assertTrue(!s1.equals(s3));
        assertTrue(!s1.equals(1));
    }
    
    public void SolmunTulostusOikein(){
        Solmu s1 = new Solmu(1, 1, 0);
        String haluttu = "Paino: 1 tunnus: 1";
        assertEquals(s1.toString(), haluttu);
    }

}
