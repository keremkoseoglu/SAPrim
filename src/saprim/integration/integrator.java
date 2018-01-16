/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.integration;

/**
 *
 * @author Kerem
 */
public interface integrator {

    public void setSapConnected();
    public void setSapProjectSelected(Project P, boolean NewProject);
    public void setSapMasterDataSelected(BusinessArea BA, CompanyCode C, Plant P, ProjectProfile PP, ProjectManager PM, Requester Req, Place Pla, ProfitCenter PC, ProjectType PT, MRPController MC, WorkCenter WC, DistributionKey DK);
    public void setSapConfirmationDatesSelected(String Budat, String Iedd);
    public void setPrimaveraConnected();
    public void setPrimaveraProjectSelected(com.primavera.integration.client.bo.object.Project P);
    public void setPrimaveraEPSSelected(com.primavera.integration.client.bo.object.EPS E);
    
    public void doTransfer() throws Exception;
    
}
