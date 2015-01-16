/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astartiralabra;

/**
 *
 * @author kride
 */
public class Solmu implements Comparable<Solmu> {

    int paino;
    int tunnus;

    public Solmu(int paino, int tunnus) {
        this.paino = paino;
        this.tunnus = tunnus;
    }

    @Override
    public int compareTo(Solmu toinen) {
        if (paino < toinen.paino) {
            return -1;
        }
        if (paino > toinen.paino) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Paino: " + paino + " tunnus: " + tunnus;
     }
    
    

}
