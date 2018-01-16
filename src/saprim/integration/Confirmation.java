/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.integration;

/**
 *
 * @author Kerem
 */
public class Confirmation {

    // SAP -> Primavera alanları
    public String rueck;
    public String rmzhl;
    public float ismnw;
    public String ismne;
    public String aufpl;
    public String aplzl;
    public String aufnr;
    
    // Primavera -> SAP alanları
    public String pspid;
    public float perce;
    public String budat;
    public String isdd;
    public String iedd;
    
    // Ortak alanlar
    public String vornr;
    
    public Confirmation()
    {
        
    }
    
}
