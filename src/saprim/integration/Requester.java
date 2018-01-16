/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.integration;

/**
 *
 * @author Kerem
 */
public class Requester {

    public String astnr, astna;
    
    public Requester()
    {
        
    }
    
    public @Override String toString() { return saprim.sap.Sap.pack(astnr) + " - " + astna; }
    
}
