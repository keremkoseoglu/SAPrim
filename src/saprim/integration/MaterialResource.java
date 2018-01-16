/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.integration;

/**
 *
 * @author Kerem
 */
public class MaterialResource {

    public String aufnr;    // Sipariş numarası
    public String aufpl;    // Siparişteki işlemlere ilişkin plan no.
    public String aplzl;    // Dahili sayaç
    public String matnr;    // Malzeme numarası
    public String maktx;    // Malzeme kısa metni
    public double menge;    // Miktar
    public String meins;    // Temel ölçü birimi
    
    public MaterialResource() {
        
    }
    
    public @Override String toString() {
        return saprim.sap.Sap.pack(matnr) + " - " + maktx;
    }
    
}
