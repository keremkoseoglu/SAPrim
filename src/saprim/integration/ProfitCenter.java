/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.integration;

/**
 *
 * @author Kerem
 */
public class ProfitCenter {

    public String prctr, ktext;
    
    public ProfitCenter() {}
    public @Override String toString() { return saprim.sap.Sap.pack(prctr) + " - " + ktext; }
    
}
