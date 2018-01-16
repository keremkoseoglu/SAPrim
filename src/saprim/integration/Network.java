/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.integration;

import java.util.*;

/**
 *
 * @author Kerem
 */
public class Network {

    public String aufnr;            // Sipariş numarası
    public String aufnt;            // Geçici Sipariş Numarası
    public String ktext;            // Kısa metin
    public String pspel;            // Proje yapı planı öğesi (PYP öğesi)
    public String aufpl;            // Siparişteki işlemlere ilişkin plan no.
    public String gstrp;            // Planlanan başlangıç termini
    public String gltrp;            // Planlanan bitiş termini
    public String pspid;            // Proje tanımı
    public String posid;            // Proje yapı planı öğesi (PYP öğesi)
    
    public Network()
    {
        
    }
    
    public @Override String toString()
    {
        return saprim.sap.Sap.pack(aufnr) + " - " + ktext;
    }
    
}
