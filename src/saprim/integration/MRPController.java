/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.integration;

/**
 *
 * @author Kerem
 */
public class MRPController {

    public String werks;    // Üretim yeri
    public String dispo;    // MİP sorumlusu
    public String dsnam;    // MİP sorumlusunun adı

    public MRPController() { }
    
    public @Override String toString() {
        return saprim.sap.Sap.pack(werks) + "::" + saprim.sap.Sap.pack(dispo) + " - " + dsnam;
    }
}
