/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.integration;

/**
 *
 * @author Kerem
 */
public class ProjectManager {

    public String vernr, verna;
    
    public ProjectManager()
    {
        
    }
    
    public @Override String toString() { return saprim.sap.Sap.pack(vernr) + " - " + verna; }
    
}
