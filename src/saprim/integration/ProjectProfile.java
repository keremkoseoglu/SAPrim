/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.integration;

/**
 *
 * @author Kerem
 */
public class ProjectProfile {

    public String profidproj, profi_txt;
    
    public ProjectProfile()
    {
        
    }
    
    public @Override String toString()
    {
        return saprim.sap.Sap.pack(profidproj) + " - " + profi_txt;
    }
    
}
