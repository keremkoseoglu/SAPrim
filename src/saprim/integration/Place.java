/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.integration;

/**
 *
 * @author Kerem
 */
public class Place {

    public String stand, ktext;
    
    public Place() {}
    public @Override String toString() { return saprim.sap.Sap.pack(stand) + " - " + ktext; }
    
}
