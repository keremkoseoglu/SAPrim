/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.integration;

/**
 *
 * @author Kerem
 */
public class ProjectType {

    public String prart; // Proje türü
    public String pratx; // Proje türü tanımı
    
    public ProjectType() { }
    
    public @Override String toString() {
        return saprim.sap.Sap.pack(prart) + " - " + pratx;
    }
    
}
