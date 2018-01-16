/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.integration;

/**
 *
 * @author Kerem
 */
public class CompanyCode {

    public String bukrs, butxt;
    
    public CompanyCode()
    {
        
    }
    
    public @Override String toString()
    {
        return saprim.sap.Sap.pack(bukrs) + " - " + butxt;
    }
    
}
