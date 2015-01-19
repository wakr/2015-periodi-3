/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extra;

/**
 *
 * @author kristianw
 */
public enum Ymparistomuuttuja {

    INF(9999),
    D(0);
    

    private final int arvo;

    private Ymparistomuuttuja(int arvo) {
        this.arvo = arvo;
    }
    
     private Ymparistomuuttuja(char arvo) {
        this.arvo = arvo;
    }

    public int getArvo() {
        return this.arvo;
    }
}
