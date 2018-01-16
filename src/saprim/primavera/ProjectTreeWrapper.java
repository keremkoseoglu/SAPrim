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

public class ProjectTreeWrapper {

    private Project project;
    
    public ProjectTreeWrapper(Project P)
    {
        project = P;
    }
    
    public @Override String toString()
    {
        try {
            return project.getId();
        } catch (BusinessObjectException ex) {
            Logger.getLogger(ProjectTreeWrapper.class.getName()).log(Level.SEVERE, null, ex);
            return "(ERROR)";
        }
    }    
    
    public Project getProject()
    {
        return project;
    }
    
}
