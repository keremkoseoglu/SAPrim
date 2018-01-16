/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.integration;

import saprim.sap.*;
import saprim.integration.CompanyCode;
import saprim.integration.BusinessArea;

/**
 *
 * @author Kerem
 */
public class MasterData {

    public BusinessArea[] businessAreas;
    public CompanyCode[] companyCodes;
    public DistributionKey[] distributionKeys;
    public Plant[] plants;
    public Place[] places;
    public ProfitCenter[] profitCenters;
    public ProjectManager[] projectManagers;    
    public ProjectProfile[] projectProfiles;
    public Requester[] requesters;
    public ProjectType[] projectTypes;
    public MRPController[] mrpControllers;
    public WorkCenter[] workCenters;
    
    public MasterData()
    {
    }
    
}
