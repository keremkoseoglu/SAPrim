/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.integration;

/**
 *
 * @author Kerem
 */
public class OperationRelationship {

    // SAP -> Primavera alanları
    public String aufpl_vor;            
    public String aplzl_vor;
    public String aufpl_nch;            
    public String aplzl_nch;
    
    // Primavera -> SAP alanları
    public String vornr_vor;
    public String vornr_nch;
    
    // Ortak alanlar
    public String aobar;
    
    public OperationRelationship()
    {
        
    }
    
}
