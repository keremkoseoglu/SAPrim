/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.integration;

/**
 *
 * @author Kerem
 */
public class WBS {

    public String pspnr;    // Proje yapı planı öğesi
    public String posid;    // Proje yapı planı öğesi (PYP öğesi)
    public String post1;    // PS: Kısa tanım (ilk metin satırı)
    public String up_pspnr; // Üst PYP öğesinin numarası
    public String up_posid; // Proje yapı planı öğesi (PYP öğesi)
    public String pstrt;    // PYP öğesi için planlanan başlangıç termini
    public String pende;    // PYP öğesi için planlanan bitiş termini
    public String pdaur;    // PYP öğesi için planlanan tarih süresi
    public String peinh;    // PYP öğesi planlanan termin süresi için birim
    public String estrt;    // PYP öğesinin tahmini başlangıç termini
    public String eende;    // PYP öğesine ilişkin tahmini bitiş termini
    public String edaur;    // PYP öğesine ilişkin tahmini termin süresi
    public String eeinh;    // PYP öğesi tahmini termin süresi için birim
    public String istrt;    // PYP öğesi ile ilgili fiili başlangıç termini
    public String iende;    // PYP öğesi ile ilgili fiili bitiş termini
    public String idaur;    // PYP öğesi ile ilgili fiili süre
    public String ieinh;    // PYP öğesi fiili termin süresi için birim
    public String prart;    // Proje türü
    public String pratx;    // Proje türü tanımı
    
    public Network[] networks;
    
    public WBS()
    {
        
    }
    
    public @Override String toString()
    {
        return posid + " - " + post1;
    }
    
    public String getIdForPrimavera()
    {
        return posid;
    }
    
}
