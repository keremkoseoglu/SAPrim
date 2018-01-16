/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.integration;

/**
 *
 * @author Kerem
 */
public class Milestone {

    public String mlst_zaehl;       // Özgün kontrol noktası numarası
    public String zaehl;            // Dahili sayaç
    public String autyp;            // Sipariş tipi
    public String aufpl;            // Siparişteki işlemlere ilişkin plan no.
    public String aplzl;            // Genel sipariş sayacı
    public String ktext;            // Tanım
    public String aufnr;            // Ağ plan numarası
    public String aufnt;            // Geçici Sipariş Numarası
    public String vornr;            // Ağ ve standart ağ işlem no.
    public String posid;            // Proje yapı planı öğesi (PYP öğesi)
    
    public Milestone()
    {
        
    }
    
    public @Override String toString()
    {
        return saprim.sap.Sap.pack(mlst_zaehl) + ":" + saprim.sap.Sap.pack(zaehl) + " - " + ktext;
    }
    
}
