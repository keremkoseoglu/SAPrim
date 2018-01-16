/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.config;

/**
 *
 * @author Kerem
 */

import java.io.*;
import java.util.*;

public class Util {
    
    public Util() {
    }
    
    public static ArrayList readTextFile(String path) throws Exception
    {
        File aFile = new File(path);
        ArrayList contents = new ArrayList();
        BufferedReader input = null;
        
        input = new BufferedReader( new FileReader(aFile));
        String line = null;
        while (( line = input.readLine()) != null)
        {
            contents.add(line);
        }
        
        return contents;
    }     
    
    public static String correctTurkishCharacters(String S)
    {
        String ret = "";
        
        for (int n = 0; n < S.length(); n++)
        {
            String c = S.substring(n, n + 1);
            
            if (c.equals("ý")) { ret += "ı"; }
            else if (c.equals("ð")) { ret += "ğ"; }
            else if (c.equals("þ")) { ret += "ş"; }
            else if (c.equals("Ð")) { ret += "Ğ"; }
            else if (c.equals("Þ")) { ret += "Ş"; }
            else if (c.equals("Ý")) { ret += "İ"; }
            else { ret += c; }
        }
        
        return ret;
    }

}
