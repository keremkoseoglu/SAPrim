/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.integration;

/**
 *
 * @author Kerem
 */
public class BusinessArea {

    public String gsber, gtext;
    
    public BusinessArea() { }
    
    public @Override String toString() { return saprim.sap.Sap.pack(gsber) + " - " + gtext; }
    
}
