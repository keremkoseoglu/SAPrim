/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.config;

/**
 *
 * @author Kerem
 */

import java.util.*;

public class Configuration {

    private ArrayList fileCont;
    
    public String sapIP, sapClient, sapUser, sapPass, sapSystem, sapLang;
    public String primHome, primUser, primPass;
    public String primSqlUrl, primSqlUser, primSqlPass;
    
    public String
        defBusinessArea,
        defCompanyCode,
        defPlant,
        defProfile,
        defManager,
        defRequester,
        defProfitCenter,
        defProjectType,
        defMrpController,
        defWorkCenter,
        defDistributionKey;
    
    public Configuration() throws Exception 
    {   
        fileCont = Util.readTextFile("config.txt");
        if (fileCont.size() <= 0) throw new Exception("Konfigürasyon dosyasında bir hata var");
        
        sapIP = getValue("sapIP");
        sapClient = getValue("sapClient");
        sapSystem = getValue("sapSystem");
        sapLang = getValue("sapLang");  
        sapUser = getValue("sapUser");  
        sapPass = getValue("sapPass");  
        
        primHome = getValue("primHome");
        primUser = getValue("primUser");
        primPass = getValue("primPass");
        
        primSqlUrl = getValue("primSqlUrl");
        primSqlUser = getValue("primSqlUser");
        primSqlPass = getValue("primSqlPass");
        
        defBusinessArea = getValue("defBusinessArea");
        defCompanyCode = getValue("defCompanyCode");
        defPlant = getValue("defPlant");
        defProfile = getValue("defProfile");
        defManager = getValue("defManager");
        defRequester = getValue("defRequester");
        defProfitCenter = getValue("defProfitCenter");
        defProjectType = getValue("defProjectType");
        defMrpController = getValue("defMrpController");
        defWorkCenter = getValue("defWorkCenter");
        defDistributionKey = getValue("defDistributionKey");        
    }    
    
    private String getValue(String Key)
    {
        int eqPos = 0;
        String curKey = "";
        String ret = "";
        boolean eqFound = false;
        boolean keyFound = false;
        
        for (int n = 0; n < fileCont.size(); n++)
        {
            String s = (String) fileCont.get(n);
            eqFound = false;
            keyFound = false;
            curKey = "";
            
            for (int m = 0; m < s.length(); m++)
            {
                String letter = s.substring(m, m + 1);
                if (!eqFound)
                {
                    if (letter.equals("=")) eqFound = true; else curKey += letter;
                }
                else
                {
                    if (curKey.equals(Key)) 
                    {
                        keyFound = true;
                        ret += letter;
                    }
                }
            }
            
            if (keyFound) return ret;
        }
        
        return ret;
    }    
}
