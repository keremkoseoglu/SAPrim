/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.primavera;

/**
 *
 * @author Kerem
 */

import com.primavera.integration.client.bo.BusinessObjectException;
import com.primavera.integration.client.bo.object.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EPSTreeWrapper {

    private EPS eps;
    
    public EPSTreeWrapper(EPS E)
    {
        eps = E;
    }
    
    public @Override String toString()
    {
        try {
            return eps.getId();
        } catch (BusinessObjectException ex) {
            Logger.getLogger(EPSTreeWrapper.class.getName()).log(Level.SEVERE, null, ex);
            return "(ERROR)";
        }
    }
    
    public EPS getEPS() { return eps; }
    
}
