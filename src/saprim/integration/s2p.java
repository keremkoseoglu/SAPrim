/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.integration;

import com.primavera.integration.client.bo.object.*;
import saprim.*;

/**
 *
 * @author Kerem
 */
public class s2p implements integrator {

    saprim.integration.Project sapProject;
    EPS eps;
    private saprim.FrmProgress frmProgress;
    
    public s2p()
    {
        FrmSapLogin f = new FrmSapLogin(this);
        f.setVisible(true);
    }
    
    public void setSapConnected()
    {
        FrmSapSelectProject f = new FrmSapSelectProject(this, false);
        f.setVisible(true);
    }

    public void setSapProjectSelected(saprim.integration.Project P, boolean NewProject) {
        sapProject = Main.sap.getProjectDetails(P);
        
        FrmPrimaveraLogin f = new FrmPrimaveraLogin(this);
        f.setVisible(true);
    }
    
    public void setSapMasterDataSelected(BusinessArea BA, CompanyCode C, Plant P, ProjectProfile PP, ProjectManager PM, Requester Req, Place Pla, ProfitCenter PC, ProjectType PT, MRPController MC, WorkCenter WC, DistributionKey DK)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void setSapConfirmationDatesSelected(String Budat, String Iedd) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    

    public void setPrimaveraConnected() {
        FrmPrimaveraSelectProject f = new FrmPrimaveraSelectProject(this, FrmPrimaveraSelectProject.TARGET_OBJECT.EPS);
        f.setVisible(true);
    }

    public void setPrimaveraProjectSelected(com.primavera.integration.client.bo.object.Project P) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void doTransfer() throws Exception {
        saprim.integration.DataValidation dv = Main.primavera.validateData(sapProject);
        if (!dv.isValid)
        {
            FrmMessage f = new FrmMessage();
            f.setText(dv.getMessages());
            f.setVisible(true);
            return;
        }
        
        Main.primavera.createProject(eps, sapProject);
    }

    public void setPrimaveraEPSSelected(EPS E) {
        eps = E;
        frmProgress = new FrmProgress(this);
        frmProgress.setVisible(true);
    }
    
}
