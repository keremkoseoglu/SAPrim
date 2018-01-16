/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim;

import java.util.logging.Level;
import java.util.logging.Logger;
import saprim.config.*;
import saprim.primavera.*;
import saprim.sap.*;

/**
 *
 * @author Kerem
 */
public class Main {

    public static final String VERSION = "1.0.1";
    
    public static FrmMain frmMain;
    public static FrmAbout frmAbout;
    /*public static FrmSapLogin frmSapLogin;
    public static FrmSapSelectProject frmSapSelectProject;*/
    public static Configuration config;
    public static Sap sap;
    
    public static saprim.integration.s2p s2p;
    public static saprim.integration.p2sProjectDefinition p2s;
    public static saprim.integration.p2sConfirmation p2sc;
    
    public static Primavera primavera;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Config
        try {
            config = new Configuration();
        } 
        catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        // Form
        frmMain = new FrmMain();
        frmMain.setVisible(true);        
    }
    
    public static void messageBox(String Text)
    {
        FrmMessage f = new FrmMessage();
        f.setText(Text);
        f.setVisible(true);
    }
    
    public static void disconnectAll()
    {
        if (primavera != null && primavera.getConnected()) primavera.disconnect();
        if (sap != null && sap.getConnected()) sap.disconnect();
    }

}
