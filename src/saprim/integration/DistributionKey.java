/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.integration;

/**
 *
 * @author Kerem
 */
public class DistributionKey {

    public String vertl, ktext;
    
    public DistributionKey() {}
    
    public @Override String toString()
    {
        return saprim.sap.Sap.pack(vertl) + " - " + ktext;
    }
    
}
