/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.integration;

/**
 *
 * @author Kerem
 */

import saprim.*;
import saprim.primavera.*;
import saprim.sap.*;
import com.primavera.integration.client.bo.object.*;

public class p2sProjectDefinition implements integrator {

    private EPS eps;
    private com.primavera.integration.client.bo.object.Project project;
    private saprim.integration.Project iproject;
    private boolean useExistingProject;
    
    private BusinessArea businessArea;
    private CompanyCode companyCode;
    private Plant plant;
    private ProjectProfile projectProfile;    
    private ProjectManager projectManager;
    private Requester requester;
    private Place place;
    private ProfitCenter profitCenter;
    private ProjectType projectType;
    private MRPController mrpController;
    private WorkCenter workCenter;
    private DistributionKey distributionKey;
    
    public p2sProjectDefinition()
    {
        FrmPrimaveraLogin f = new FrmPrimaveraLogin(this);
        f.setVisible(true);
    }
    
    public void setSapConnected() {
        FrmSapSelectProject f = new FrmSapSelectProject(this, true);
        f.setVisible(true);
    }

    public void setSapProjectSelected(Project P, boolean NewProject) {
        iproject = P == null ? new saprim.integration.Project() : P;
        useExistingProject = !NewProject;
        
        if (P != null) P = Main.sap.getProjectDetails(P);
        
        FrmSapMasterData f = new FrmSapMasterData(this, P);
        f.setVisible(true);
    }

    public void setSapMasterDataSelected(BusinessArea BA, CompanyCode C, Plant P, ProjectProfile PP, ProjectManager PM, Requester Req, Place Pla, ProfitCenter PC, ProjectType PT, MRPController MC, WorkCenter WC, DistributionKey DK) {
        businessArea = BA;
        companyCode = C;
        plant = P;
        projectProfile = PP;
        projectManager = PM;
        requester = Req;
        place = Pla;
        profitCenter = PC;
        projectType = PT;
        mrpController = MC;
        workCenter = WC;
        distributionKey = DK;
        
        FrmProgress f = new saprim.FrmProgress(this);
        f.setVisible(true);
    }
    
    public void setSapConfirmationDatesSelected(String Budat, String Iedd) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    

    public void setPrimaveraConnected() {
        FrmPrimaveraSelectProject f = new FrmPrimaveraSelectProject(this, FrmPrimaveraSelectProject.TARGET_OBJECT.PROJECT);
        f.setVisible(true);
    }

    public void setPrimaveraProjectSelected(com.primavera.integration.client.bo.object.Project P) {
        project = P;
        FrmSapLogin f = new FrmSapLogin(this);
        f.setVisible(true);
    }

    public void setPrimaveraEPSSelected(EPS E) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void doTransfer() throws Exception {
        // Proje alanları
        try { iproject.plfaz = Sap.parseDate(Primavera.parseDate(project.getAnticipatedStartDate())); } catch(Exception ex) { }
        try { iproject.plsez = Sap.parseDate(Primavera.parseDate(project.getAnticipatedFinishDate())); } catch(Exception ex) { }
        try { iproject.post1 = saprim.config.Util.correctTurkishCharacters(project.getName()); } catch(Exception ex) { }
        if (!useExistingProject) {
            try { iproject.pspid = saprim.config.Util.correctTurkishCharacters(project.getId().toUpperCase()); } catch(Exception ex) { }
        }
        iproject.profl = projectProfile.profidproj;
        iproject.vbukr = companyCode.bukrs;
        iproject.vgsbr = businessArea.gsber;
        iproject.werks = plant.werks;
        iproject.vernr = projectManager.vernr;
        iproject.astnr = requester.astnr;
        if (profitCenter != null) iproject.prctr = profitCenter.prctr;
        if (place != null) iproject.stand = place.stand;
        iproject.dispo = mrpController.dispo;
        iproject.arbpl = workCenter.arbpl;
        iproject.vertl = distributionKey.vertl;
        
        // WBS'ler
        com.primavera.integration.client.bo.object.WBS[] wbss = Main.primavera.getWBSList(project.getId());
        if (wbss != null)
        {
            iproject.wbss = new saprim.integration.WBS[wbss.length];
            for (int n = 0; n < wbss.length; n++)
            {
                iproject.wbss[n] = new saprim.integration.WBS();
                iproject.wbss[n].posid = saprim.config.Util.correctTurkishCharacters(wbss[n].getCode().toUpperCase());
                iproject.wbss[n].post1 = saprim.config.Util.correctTurkishCharacters(wbss[n].getName());
                try { iproject.wbss[n].eende = Sap.parseDate(Primavera.parseDate(wbss[n].getAnticipatedFinishDate())); } catch(Exception ex) {}
                try { iproject.wbss[n].estrt = Sap.parseDate(Primavera.parseDate(wbss[n].getAnticipatedStartDate())); } catch(Exception ex) {}
                try { iproject.wbss[n].iende = Sap.parseDate(Primavera.parseDate(wbss[n].getFinishDate())); } catch(Exception ex) {}
                try { iproject.wbss[n].istrt = Sap.parseDate(Primavera.parseDate(wbss[n].getStartDate())); } catch(Exception ex) {}
                try { iproject.wbss[n].pende = Sap.parseDate(Primavera.parseDate(wbss[n].getForecastFinishDate())); } catch(Exception ex) {}
                try { iproject.wbss[n].pstrt = Sap.parseDate(Primavera.parseDate(wbss[n].getForecastStartDate())); } catch(Exception ex) {}
                iproject.wbss[n].prart = projectType.prart;
                iproject.wbss[n].pratx = projectType.pratx;
                
                for (int m = 0; m < wbss.length; m++)
                {
                    if (wbss[n].getParentObjectId().equals(wbss[m].getObjectId())) 
                    {
                        iproject.wbss[n].up_posid = wbss[m].getCode().toUpperCase();
                    }
                }
            }
        }
        iproject.wbsco = iproject.wbss.length;
        
        // Ağ plan - sadece bir tane yaratılacak
        iproject.networks = new saprim.integration.Network[1];
        iproject.networks[0] = new saprim.integration.Network();
        iproject.networks[0].aufnt = "1";
        iproject.networks[0].ktext = iproject.post1;
        iproject.networks[0].posid = iproject.proje;
        
        // İşlemler
        com.primavera.integration.client.bo.object.Activity[] act = Main.primavera.getActivityList(project.getObjectId().toString());
        iproject.operations = new saprim.integration.Operation[act.length];
        if (iproject.operations != null) {
            for (int n = 0; n < act.length; n++) {
                iproject.operations[n] = new saprim.integration.Operation();
                iproject.operations[n].prcal = act[n].getCalendarName();
                iproject.operations[n].posid = saprim.config.Util.correctTurkishCharacters(act[n].getWBSCode());
                iproject.operations[n].ltxa1 = saprim.config.Util.correctTurkishCharacters(act[n].getName());
                iproject.operations[n].dauno = act[n].getPlannedDuration().toString();
                iproject.operations[n].vornr = iproject.operations[n].getVornrForSap(act[n]);
                
                if (act[n].getPrimaryConstraintType() == com.primavera.integration.client.bo.enm.ConstraintType.MANDATORY_START) {
                    iproject.operations[n].einsa = "1";
                    iproject.operations[n].ntanf = saprim.sap.Sap.parseDate(act[n].getPrimaryConstraintDate());
                } 
                else if (act[n].getPrimaryConstraintType() == com.primavera.integration.client.bo.enm.ConstraintType.START_ON_OR_AFTER) {
                    iproject.operations[n].einsa = "2";
                    iproject.operations[n].ntanf = saprim.sap.Sap.parseDate(act[n].getPrimaryConstraintDate());
                }     
                else if (act[n].getPrimaryConstraintType() == com.primavera.integration.client.bo.enm.ConstraintType.START_ON_OR_BEFORE) {
                    iproject.operations[n].einsa = "3";
                    iproject.operations[n].ntanf = saprim.sap.Sap.parseDate(act[n].getPrimaryConstraintDate());
                }         
                else if (act[n].getPrimaryConstraintType() == com.primavera.integration.client.bo.enm.ConstraintType.MANDATORY_FINISH) {
                    iproject.operations[n].einse = "1";
                    iproject.operations[n].ntend = saprim.sap.Sap.parseDate(act[n].getPrimaryConstraintDate());
                } 
                else if (act[n].getPrimaryConstraintType() == com.primavera.integration.client.bo.enm.ConstraintType.FINISH_ON_OR_AFTER) {
                    iproject.operations[n].einse = "2";
                    iproject.operations[n].ntend = saprim.sap.Sap.parseDate(act[n].getPrimaryConstraintDate());
                }
                else if (act[n].getPrimaryConstraintType() == com.primavera.integration.client.bo.enm.ConstraintType.FINISH_ON_OR_BEFORE) {
                    iproject.operations[n].einse = "3";
                    iproject.operations[n].ntend = saprim.sap.Sap.parseDate(act[n].getPrimaryConstraintDate());
                } 
                else if (act[n].getPrimaryConstraintType() == com.primavera.integration.client.bo.enm.ConstraintType.START_ON_OR_AFTER) {
                    iproject.operations[n].frsp = "1";
                }         
                else if (act[n].getPrimaryConstraintType() == com.primavera.integration.client.bo.enm.ConstraintType.AS_LATE_AS_POSSIBLE) {
                    iproject.operations[n].frsp = "2";
                    iproject.operations[n].ntend = saprim.sap.Sap.parseDate(act[n].getPrimaryConstraintDate());
                }                    
            }
        }
        
        // İşlemler arası bağlantılar
        com.primavera.integration.client.bo.object.Relationship[] rel = Main.primavera.getRelationshipList(project.getObjectId().toString()); 
        if (rel != null)
        {
            iproject.operationRelationships = new saprim.integration.OperationRelationship[rel.length];
            for (int n = 0; n < rel.length; n++)
            {
                iproject.operationRelationships[n] = new saprim.integration.OperationRelationship();
                
                Activity a1 = rel[n].loadPredecessorActivity(new String[]{ "Id", "WBSCode" });
                Activity a2 = rel[n].loadSuccessorActivity(new String[]{ "Id", "WBSCode" });
                
                iproject.operationRelationships[n].vornr_vor = saprim.integration.Operation.getVornrForSap(rel[n].getPredecessorActivityId(), a1.getWBSCode());
                iproject.operationRelationships[n].vornr_nch = saprim.integration.Operation.getVornrForSap(rel[n].getSuccessorActivityId(), a2.getWBSCode());
                iproject.operationRelationships[n].aobar = Sap.parseRelationshipType(rel[n].getType());
            }
        }
        
        
        // Validasyon
        saprim.integration.DataValidation dv = Main.sap.validateData(iproject);
        if (!dv.isValid)
        {
            FrmMessage f = new FrmMessage();
            f.setText(dv.getMessages());
            f.setVisible(true);
            return;
        }        
        
        // Execute
        Main.sap.createProject(iproject, useExistingProject);
    }  


}
