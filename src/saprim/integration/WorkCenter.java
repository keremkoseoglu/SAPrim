/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.integration;

/**
 *
 * @author Kerem
 */
public class WorkCenter {

    public String werks, arbpl, ktext;
    
    public WorkCenter()
    {
    }
    
    public @Override String toString()
    {
        return saprim.sap.Sap.pack(werks) + "::" + saprim.sap.Sap.pack(arbpl) + " - " + ktext;
    }
    
}
