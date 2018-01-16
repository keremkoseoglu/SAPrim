/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.integration;

/**
 *
 * @author Kerem
 */
public class Resource {

    public String larnt;    // Aktivite türü
    public String arbid;    // Fiili iş yeri tanıtıcısı
    public String ktext;    // İş yeri türü tanımı
    public String verwe;    // İşyeri türü
    public String prire;    // Primavera Kaynağı
    
    public Resource() {
        
    }
    
    public @Override String toString() {
        return saprim.sap.Sap.pack(arbid) + ":" + saprim.sap.Sap.pack(larnt) + " - " + ktext;
    }
    
}
