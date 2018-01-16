/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.integration;

/**
 *
 * @author Kerem
 */
public class Project {

    public String proje; // Proje tanımı (dahili)
    public String pspid; // Proje tanımı
    public String post1; // PS: Kısa tanım (ilk metin satırı)
    public String plfaz; // Proje için planlanan başlangıç termini
    public String plsez; // Proje için planlanan bitiş termini
    public String profl; // Proje profili
    public String vbukr; // Proje şirket kodu
    public String vgsbr; // Proje iş alanı
    public String werks; // Üretim yeri
    public String vernr; // Sorumlu no.(proje yöneticisi)
    public String verna; // Sorumlu (proje yöneticisi)
    public String astnr; // Talepte bulunanın numarası
    public String astna; // Talepte bulunan
    public String prctr; // Kar merkezi
    public String stand; // Yer
    public String dispo; // MRP Controller
    public String arbpl; // Kaynak
    public String vertl; // Kapasite ihtiyaçlarının dağıtımı (üretim ve planlı sprş.)
    public int wbsco;    // WBS sayısı
    public double odmul; // İşlem Süre Çarpanı
    
    public WBS[] wbss;
    public Network[] networks;
    public Operation[] operations;
    public OperationRelationship[] operationRelationships;
    public Milestone[] milestones;
    public Confirmation[] confirmations;
    public Resource[] resources;
    public MaterialResource[] materialResources;
    
    public Project()
    {
    }
    
    public @Override String toString()
    {
        return pspid + " - " + post1;
    }
    
    public String getIdForPrimavera()
    {
        return pspid;
    }
    
}
