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
public class DataValidation {

    public boolean isValid;
    
    private ArrayList messages;
    
    public DataValidation()
    {
        clear();
    }
    
    public void clear()
    {
        isValid = true;
        messages = new ArrayList();        
    }
    
    public void addMessage(String Message)
    {
        messages.add(Message);
    }
    
    public void addSeparator()
    {
        if (messages.size() > 0) messages.add("-------------------");
    }
    
    public String[] getMessages()
    {
        if (messages == null || messages.size() <= 0) return null;
        String[] ret = new String[messages.size()];
        for (int n = 0; n < messages.size(); n++) ret[n] = (String) messages.get(n);
        return ret;
    }
    
}
