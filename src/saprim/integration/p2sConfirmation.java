/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.integration;

import saprim.*;
import com.primavera.integration.client.bo.object.EPS;
import com.primavera.integration.client.bo.object.Project;
import java.util.ArrayList;

/**
 *
 * @author Kerem
 */
public class p2sConfirmation implements integrator {

    private com.primavera.integration.client.bo.object.Project project;
    private String budat, iedd;
    
    public p2sConfirmation()
    {
        FrmPrimaveraLogin f = new FrmPrimaveraLogin(this);
        f.setVisible(true);        
    }
    
    public void setSapConnected() {
        FrmSapConfDates f = new saprim.FrmSapConfDates(this);
        f.setVisible(true);
    }

    public void setSapProjectSelected(saprim.integration.Project P, boolean NewProject) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setSapMasterDataSelected(BusinessArea BA, CompanyCode C, Plant P, ProjectProfile PP, ProjectManager PM, Requester Req, Place Pla, ProfitCenter PC, ProjectType PT, MRPController MC, WorkCenter WC, DistributionKey DK) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void setSapConfirmationDatesSelected(String Budat, String Iedd) {
        budat = Budat;
        iedd = Iedd;
        
        FrmProgress f = new saprim.FrmProgress(this);
        f.setVisible(true);
    }

    public void setPrimaveraConnected() {
        FrmPrimaveraSelectProject f = new FrmPrimaveraSelectProject(this, FrmPrimaveraSelectProject.TARGET_OBJECT.PROJECT);
        f.setVisible(true);
    }

    public void setPrimaveraProjectSelected(Project P) {
        project = P;
        FrmSapLogin f = new FrmSapLogin(this);
        f.setVisible(true);
    }

    public void setPrimaveraEPSSelected(EPS E) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void doTransfer() throws Exception {
        
        // İşlemleri al
        com.primavera.integration.client.bo.object.Activity[] activities = Main.primavera.getActivityList(project.getObjectId().toString());
        if (activities == null) return;
        if (activities.length <= 0) return;
        
        // Filtre: Başlamamış aktiviteler bizi ilgilendirmiyor
        ArrayList alTmp = new ArrayList();
        for (int n = 0; n < activities.length; n++)
        {
            if (activities[n].getStatus() == com.primavera.integration.client.bo.enm.ActivityStatus.IN_PROGRESS
                    ||
                activities[n].getStatus() == com.primavera.integration.client.bo.enm.ActivityStatus.COMPLETED
                ) alTmp.add(activities[n]);
        }
        if (alTmp.size() == 0) return;
        
        activities = new com.primavera.integration.client.bo.object.Activity[alTmp.size()];
        for (int n = 0; n < alTmp.size(); n++)
        {
            activities[n] = (com.primavera.integration.client.bo.object.Activity) alTmp.get(n);
        }
         
        // Aktarım
        Confirmation[] c = new Confirmation[activities.length];
        for (int n = 0; n < activities.length; n++)
        {            
            c[n] = new Confirmation();
            c[n].pspid = project.getId();
            c[n].vornr = saprim.integration.Operation.getVornrForSap(activities[n].getId(), activities[n].getWBSCode());
            c[n].perce = activities[n].getPercentComplete().floatValue();
            
            c[n].budat = saprim.sap.Sap.parseUserDate(budat);
            c[n].iedd = saprim.sap.Sap.parseUserDate(iedd);
            c[n].isdd = saprim.sap.Sap.parseDate(saprim.primavera.Primavera.parseDate(activities[n].getStartDate()), saprim.sap.Sap.DATE_SOURCE.PRIMAVERA_DATE);                    
        }
        
        // Validasyon
        saprim.integration.DataValidation dv = Main.sap.validateData(c);
        if (!dv.isValid)
        {
            FrmMessage f = new FrmMessage();
            f.setText(dv.getMessages());
            f.setVisible(true);
            return;
        }        
        
        // Execute
        Main.sap.transferConfirmations(c);
    }

}
