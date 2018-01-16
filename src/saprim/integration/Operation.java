/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.integration;

/**
 * Operation class
 * @author Kerem
 */
public class Operation {

    public String aufpl;            // Siparişteki işlemlere ilişkin plan no.
    public String aplzl;            // Genel sipariş sayacı
    public String ltxa1;            // İşlem kısa metni
    public float arbei;             // İşlem kapsamındaki iş
    public String arbeh;            // İş birimi
    public float ismnw;             // Fiili iş
    public int umren;               // Plan ve işlem ölçü birimi dönüştürülmesi için bölen
    public int umrez;               // Plan ve işlem ölçü birimi dönüştürülmesi için bölünen
    public String vornr;            // İşlem numarası
    public String aufnt;            // Geçici Sipariş Numarası
    public String dauno;            // Normal işlem süresi
    public String daune;            // Normal süre birimi
    public String kalid;            // Fabrika takvimi
    public String prcal;            // Primavera Takvimi
    public String einsa;            // İşlem başlangıcı için tarihi sınırlaması
    public String ntanf;            // İşlem için başlangıç (planlanan) sınırlandırması
    public String einse;            // İşlem bitişi için tarihi sınırlaması
    public String ntend;            // İşlem için bitiş (planlanan) sınırlaması
    public String frsp;             // En erken olası işlem / en geç olası işlem  
    public String posid;            // Proje yapı planı öğesi (PYP öğesi) CHAR24
    public String pspnr;            // Proje yapı planı öğesi NUMC8
    public double aprat;            // Tamamlanma oranı
    
    public String isavd;            // Fiili işlem başlangıcı (tarih)
    public String ieavd;            // İşlemin fiili bitişi (tarih)
    public boolean started;         // Başladı mı başlamadı mı (sadece S2P)
    public boolean ended;           // Bitti mi mi bitmedi mi (sadece S2P)
    
    public com.primavera.common.value.ObjectId objectId;
    
    public Operation()
    {
        
    }
    
    public @Override String toString()
    {
        return saprim.sap.Sap.pack(posid) + " - " + saprim.sap.Sap.pack(aufpl) + ":" + saprim.sap.Sap.pack(aplzl) + ":" + saprim.sap.Sap.pack(vornr) + " - " + ltxa1;
    }
    
    public String getIdForPrimavera()
    {
        // Dikkat! Aşağıdaki kodda bir değişiklik yaparsanız, 
        // getVornrForSap içerisinde de değişiklik yapmak gerekecektir.
        
        // return posid + "-" + vornr;
        return vornr;
    }
    
    public String getVornrForSap(com.primavera.integration.client.bo.object.Activity A) throws Exception
    {
        if (posid == null || posid.length() <= 0) throw new Exception("POSID not assigned yet");
        
        String id = A.getId();
        return getVornrForSap(id, posid);
    }
    
    public static String getVornrForSap(String ActivityId, String Posid) 
    {
        /*
        // MPPJAJ.01.01.01-1001 formatında (kendi yarattığımız) bir işlem ise, 1001'i ayıklayıp döndüreceğiz.
        // 1001 formatında sadece işlem numarası varsa, doğrudan döndürebiliriz
        if (ActivityId.length() > Posid.length() && ActivityId.substring(0, Posid.length()).equals(Posid))
        {
            return ActivityId.substring(Posid.length(), ActivityId.length());
        }
        else
        {
            return ActivityId;
        }      */
        
        return ActivityId;
    }
    
}
