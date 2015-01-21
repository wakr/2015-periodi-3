/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author kride
 */
public class Solmu implements Comparable<Solmu> {

    public double paino;
    public int tunnus;
    public int etaisyysAlusta;

    public Solmu(double paino, int tunnus, int etaisyysAlusta) {
        this.paino = paino;
        this.tunnus = tunnus;
        this.etaisyysAlusta = etaisyysAlusta;
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
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.tunnus;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Solmu other = (Solmu) obj;
        if (this.tunnus != other.tunnus) {
            return false;
        }
        return true;
    }

    
    
    @Override
    public String toString() {
        return "Paino: " + paino + " tunnus: " + tunnus;
     }
    
    

}
