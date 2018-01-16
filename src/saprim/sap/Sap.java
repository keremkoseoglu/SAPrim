/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.sap;

/**
 *
 * @author Kerem
 */

import com.sap.mw.jco.*;  
import com.sap.mw.jco.JCO.*;
import java.util.*;
import saprim.*;
import saprim.integration.*;

public class Sap {

    private JCO.Client client;
    private IRepository repository;      
    
    public enum DATE_SOURCE { PRIMAVERA_DATE, REGULAR_DATE };
    
    private static final int MAX_VORNR_LENGTH = 4;
    
    public Sap()
    {
    }
    
    public void connect()
    {
        client = JCO.createClient
                (Main.config.sapClient,
                Main.config.sapUser,
                Main.config.sapPass,
                Main.config.sapLang,
                Main.config.sapIP,
                Main.config.sapSystem
                );

        client.connect(); 
        repository = JCO.createRepository("rep", client);
    }    
    
    public boolean getConnected()
    {
        return client.isAlive();
    }
    
    public void disconnect()
    {
        client.disconnect();
    }    
    
    public saprim.integration.Project[] getProjectList(boolean FilterCrowdedProjects)
    {
        saprim.integration.Project[] ret;
        boolean b = true;
        
        IFunctionTemplate ftemp = repository.getFunctionTemplate("ZSAPRIM_GET_PROJECT_LIST");
        JCO.Function function = ftemp.getFunction();  
        
        if (FilterCrowdedProjects) {
            function.getImportParameterList().setValue("X", "I_FILTER_CROWDED_PROJECTS");
        }
        
        client.execute(function);
        JCO.Table t = function.getTableParameterList().getTable("E_LIST");   
        ret = new saprim.integration.Project[t.getNumRows()];
        
        if (t.getNumRows() > 0)
        {
            t.firstRow();
            while (b)
            {
                ret[t.getRow()] = new saprim.integration.Project();
                for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("PROJE")) ret[t.getRow()].proje = field.getString();
                    if (field.getName().equals("PSPID")) ret[t.getRow()].pspid = field.getString();
                    if (field.getName().equals("POST1")) ret[t.getRow()].post1 = field.getString();
                    if (field.getName().equals("WBSCO")) ret[t.getRow()].wbsco = field.getInt();
                }
                b = t.nextRow();
            }    
        }
        
        return ret;        
    }
    
    public saprim.integration.Project getProjectDetails(saprim.integration.Project P)
    {
        saprim.integration.Project ret = new saprim.integration.Project();
        boolean b = true;
        
        ret.post1 = P.post1;
        ret.proje = P.proje;
        ret.pspid = P.pspid;
        
        IFunctionTemplate ftemp = repository.getFunctionTemplate("ZSAPRIM_GET_PROJECT_DETAILS");
        JCO.Function function = ftemp.getFunction();     
        function.getImportParameterList().setValue(ret.proje, "I_PROJE");
        
        client.execute(function);
        
        // Project details
        JCO.Structure s = function.getExportParameterList().getStructure("E_PROJECT");
        ret.astna = s.getString("ASTNA");
        ret.astnr = s.getString("ASTNR");
        ret.plfaz = s.getString("PLFAZ");
        ret.plsez = s.getString("PLSEZ");
        //ret.post1 = s.getString("POST1");
        ret.profl = s.getString("PROFL");
        //ret.proje = s.getString("PROJE");
        //ret.pspid = s.getString("PSPID");
        ret.vbukr = s.getString("VBUKR");
        ret.verna = s.getString("VERNA");
        ret.vernr = s.getString("VERNR");
        ret.vgsbr = s.getString("VGSBR");
        ret.werks = s.getString("WERKS");
        //ret.odmul = s.getDouble("ODMUL");
        
        // WBS
        JCO.Table t = function.getTableParameterList().getTable("E_WBS");  
        ret.wbss = new saprim.integration.WBS[t.getNumRows()];
        if (t.getNumRows() > 0)
        {
            t.firstRow();
            b = true;
            while (b)
            {
                ret.wbss[t.getRow()] = new saprim.integration.WBS();
                for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("PSPNR")) ret.wbss[t.getRow()].pspnr = field.getString();
                    if (field.getName().equals("POSID")) ret.wbss[t.getRow()].posid = field.getString();
                    if (field.getName().equals("POST1")) ret.wbss[t.getRow()].post1 = field.getString();
                    if (field.getName().equals("UP_PSPNR")) ret.wbss[t.getRow()].up_pspnr = field.getString();
                    if (field.getName().equals("UP_POSID")) ret.wbss[t.getRow()].up_posid = field.getString();
                    if (field.getName().equals("PSTRT")) ret.wbss[t.getRow()].pstrt = field.getString();
                    if (field.getName().equals("PENDE")) ret.wbss[t.getRow()].pende = field.getString();
                    if (field.getName().equals("PDAUR")) ret.wbss[t.getRow()].pdaur = field.getString();
                    if (field.getName().equals("PEINH")) ret.wbss[t.getRow()].peinh = field.getString();
                    if (field.getName().equals("ESTRT")) ret.wbss[t.getRow()].estrt = field.getString();
                    if (field.getName().equals("EENDE")) ret.wbss[t.getRow()].eende = field.getString();
                    if (field.getName().equals("EDAUR")) ret.wbss[t.getRow()].edaur = field.getString();
                    if (field.getName().equals("EEINH")) ret.wbss[t.getRow()].eeinh = field.getString();
                    if (field.getName().equals("ISTRT")) ret.wbss[t.getRow()].istrt = field.getString();
                    if (field.getName().equals("IENDE")) ret.wbss[t.getRow()].iende = field.getString();
                    if (field.getName().equals("IDAUR")) ret.wbss[t.getRow()].idaur = field.getString();
                    if (field.getName().equals("IEINH")) ret.wbss[t.getRow()].ieinh = field.getString();                    
                    if (field.getName().equals("PRART")) ret.wbss[t.getRow()].prart = field.getString();
                    if (field.getName().equals("PRATX")) ret.wbss[t.getRow()].pratx = field.getString();                                        
                }
                b = t.nextRow();
            }    
        }
        ret.wbsco = ret.wbss.length;
        
        // Network
        t = function.getTableParameterList().getTable("E_NETWORK");  
        ret.networks = new saprim.integration.Network[t.getNumRows()];
        if (t.getNumRows() > 0)
        {
            t.firstRow();
            b = true;
            while (b)
            {
                ret.networks[t.getRow()] = new saprim.integration.Network();
                for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("AUFNR")) ret.networks[t.getRow()].aufnr = field.getString();
                    if (field.getName().equals("KTEXT")) ret.networks[t.getRow()].ktext = field.getString();
                    if (field.getName().equals("PSPEL")) ret.networks[t.getRow()].pspel = field.getString();
                    if (field.getName().equals("AUFPL")) ret.networks[t.getRow()].aufpl = field.getString();
                }
                b = t.nextRow();
            }    
        }        
        
        // Operation
        t = function.getTableParameterList().getTable("E_OPERATION");   
        ret.operations = new saprim.integration.Operation[t.getNumRows()];
        if (t.getNumRows() > 0)
        {
            t.firstRow();
            b = true;
            while (b)
            {
                ret.operations[t.getRow()] = new saprim.integration.Operation();
                for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("AUFPL")) ret.operations[t.getRow()].aufpl = field.getString();
                    if (field.getName().equals("APLZL")) ret.operations[t.getRow()].aplzl = field.getString();
                    if (field.getName().equals("LTXA1")) ret.operations[t.getRow()].ltxa1 = field.getString();
                    if (field.getName().equals("ARBEI")) ret.operations[t.getRow()].arbei = field.getFloat();
                    if (field.getName().equals("ARBEH")) ret.operations[t.getRow()].arbeh = field.getString();
                    if (field.getName().equals("ISMNW")) ret.operations[t.getRow()].ismnw = field.getFloat();
                    if (field.getName().equals("UMREN")) ret.operations[t.getRow()].umren = Integer.parseInt(field.getString());
                    if (field.getName().equals("UMREZ")) ret.operations[t.getRow()].umrez = Integer.parseInt(field.getString());
                    if (field.getName().equals("VORNR")) ret.operations[t.getRow()].vornr = field.getString();
                    if (field.getName().equals("DAUNO")) ret.operations[t.getRow()].dauno = field.getString();
                    if (field.getName().equals("DAUNE")) ret.operations[t.getRow()].daune = field.getString();
                    if (field.getName().equals("KALID")) ret.operations[t.getRow()].kalid = field.getString();
                    if (field.getName().equals("PRCAL")) ret.operations[t.getRow()].prcal = field.getString();
                    if (field.getName().equals("EINSA")) ret.operations[t.getRow()].einsa = field.getString();
                    if (field.getName().equals("NTANF")) ret.operations[t.getRow()].ntanf = field.getString();
                    if (field.getName().equals("EINSE")) ret.operations[t.getRow()].einse = field.getString();
                    if (field.getName().equals("NTEND")) ret.operations[t.getRow()].ntend = field.getString();
                    if (field.getName().equals("FRSP")) ret.operations[t.getRow()].frsp = field.getString();                    
                    if (field.getName().equals("PSPNR")) ret.operations[t.getRow()].pspnr = field.getString(); 
                    if (field.getName().equals("POSID")) ret.operations[t.getRow()].posid = field.getString();
                    
                    if (field.getName().equals("APRAT")) 
                    {
                        double aprat1 = field.getDouble();
                        double aprat2 = 100;
                        ret.operations[t.getRow()].aprat = aprat1 / aprat2;
                    }
                    
                    if (field.getName().equals("STRTD")) ret.operations[t.getRow()].started = field.getString().equals("X");
                    if (field.getName().equals("ENDED")) ret.operations[t.getRow()].ended = field.getString().equals("X");
                    if (field.getName().equals("ISAVD")) ret.operations[t.getRow()].isavd = field.getString();
                    if (field.getName().equals("IEAVD")) ret.operations[t.getRow()].ieavd = field.getString();
                    
                }
                b = t.nextRow();
            }    
        }     
        
        // Operation Relationships
        t = function.getTableParameterList().getTable("E_OPERATION_R");  
        ret.operationRelationships = new saprim.integration.OperationRelationship[t.getNumRows()];
        if (t.getNumRows() > 0)
        {
            t.firstRow();
            b = true;
            while (b)
            {
                ret.operationRelationships[t.getRow()] = new saprim.integration.OperationRelationship();
                for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("AUFPL_VOR")) ret.operationRelationships[t.getRow()].aufpl_vor = field.getString();
                    if (field.getName().equals("APLZL_VOR")) ret.operationRelationships[t.getRow()].aplzl_vor = field.getString();
                    if (field.getName().equals("AUFPL_NCH")) ret.operationRelationships[t.getRow()].aufpl_nch = field.getString();
                    if (field.getName().equals("APLZL_NCH")) ret.operationRelationships[t.getRow()].aplzl_nch = field.getString();
                    if (field.getName().equals("AOBAR")) ret.operationRelationships[t.getRow()].aobar = field.getString();
                }
                b = t.nextRow();
            }    
        }          
        
        // Confirmation
        t = function.getTableParameterList().getTable("E_CONFIRMATION");   
        ret.confirmations = new saprim.integration.Confirmation[t.getNumRows()];
        if (t.getNumRows() > 0)
        {
            t.firstRow();
            b = true;
            while (b)
            {
                ret.confirmations[t.getRow()] = new saprim.integration.Confirmation();
                for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("RUECK")) ret.confirmations[t.getRow()].rueck = field.getString();
                    if (field.getName().equals("RMZHL")) ret.confirmations[t.getRow()].rmzhl = field.getString();
                    if (field.getName().equals("ISMNW")) ret.confirmations[t.getRow()].ismnw = field.getFloat();
                    if (field.getName().equals("ISMNE")) ret.confirmations[t.getRow()].ismne = field.getString();
                    if (field.getName().equals("AUFPL")) ret.confirmations[t.getRow()].aufpl = field.getString();
                    if (field.getName().equals("APLZL")) ret.confirmations[t.getRow()].aplzl = field.getString();
                    if (field.getName().equals("AUFNR")) ret.confirmations[t.getRow()].aufnr = field.getString();
                    if (field.getName().equals("VORNR")) ret.confirmations[t.getRow()].vornr = field.getString();
                }
                b = t.nextRow();
            }    
        }         
        
        // Milestone
        t = function.getTableParameterList().getTable("E_MILESTONE");
        ret.milestones = new saprim.integration.Milestone[t.getNumRows()];
        if (t.getNumRows() > 0)
        {
            t.firstRow();
            b = true;
            while (b)
            {
                ret.milestones[t.getRow()] = new saprim.integration.Milestone();
                for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("MLST_ZAEHL")) ret.milestones[t.getRow()].mlst_zaehl = field.getString();
                    if (field.getName().equals("ZAEHL")) ret.milestones[t.getRow()].zaehl = field.getString();
                    if (field.getName().equals("AUTYP")) ret.milestones[t.getRow()].autyp = field.getString();
                    if (field.getName().equals("AUFPL")) ret.milestones[t.getRow()].aufpl = field.getString();
                    if (field.getName().equals("APLZL")) ret.milestones[t.getRow()].aplzl = field.getString();
                    if (field.getName().equals("KTEXT")) ret.milestones[t.getRow()].ktext = field.getString();
                }
                b = t.nextRow();
            }    
        }         
        
        // Resources
        t = function.getTableParameterList().getTable("E_RESOURCE");  
        ret.resources = new saprim.integration.Resource[t.getNumRows()];
        if (t.getNumRows() > 0)
        {
            t.firstRow();
            b = true;
            while (b)
            {
                ret.resources[t.getRow()] = new saprim.integration.Resource();
                for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("LARNT")) ret.resources[t.getRow()].larnt = field.getString();
                    if (field.getName().equals("ARBID")) ret.resources[t.getRow()].arbid = field.getString();
                    if (field.getName().equals("KTEXT")) ret.resources[t.getRow()].ktext = field.getString();
                    if (field.getName().equals("VERWE")) ret.resources[t.getRow()].verwe = field.getString();
                    if (field.getName().equals("PRIRE")) ret.resources[t.getRow()].prire = field.getString();
                }
                b = t.nextRow();
            }    
        }     
        
        // Material Resources
        t = function.getTableParameterList().getTable("E_MATERIAL");  
        ret.materialResources = new saprim.integration.MaterialResource[t.getNumRows()];
        if (t.getNumRows() > 0)
        {
            t.firstRow();
            b = true;
            while (b)
            {
                ret.materialResources[t.getRow()] = new saprim.integration.MaterialResource();
                for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("AUFNR")) ret.materialResources[t.getRow()].aufnr = field.getString();
                    if (field.getName().equals("AUFPL")) ret.materialResources[t.getRow()].aufpl = field.getString();
                    if (field.getName().equals("APLZL")) ret.materialResources[t.getRow()].aplzl = field.getString();
                    if (field.getName().equals("MATNR")) ret.materialResources[t.getRow()].matnr = field.getString();
                    if (field.getName().equals("MAKTX")) ret.materialResources[t.getRow()].maktx = field.getString();
                    if (field.getName().equals("MENGE")) ret.materialResources[t.getRow()].menge = field.getDouble();
                    if (field.getName().equals("MEINS")) ret.materialResources[t.getRow()].meins = field.getString();
                }
                b = t.nextRow();
            }    
        }           
        
        // That's it!
        return ret;          
    }      
    
    public MasterData getMasterData()
    {
        JCO.Table t;
        MasterData ret = new MasterData();
        boolean b = true;
        
        IFunctionTemplate ftemp = repository.getFunctionTemplate("ZSAPRIM_GET_MASTER_DATA");
        JCO.Function function = ftemp.getFunction();     
        client.execute(function);        
        
        // Project Profile
        t = function.getTableParameterList().getTable("E_PROFL"); 
        ret.projectProfiles = new ProjectProfile[t.getNumRows()];
        if (t.getNumRows() > 0)
        {
            t.firstRow();
            b = true;
            while (b)
            {
                ret.projectProfiles[t.getRow()] = new ProjectProfile();
                for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("PROFIDPROJ")) ret.projectProfiles[t.getRow()].profidproj = field.getString();
                    if (field.getName().equals("PROFI_TXT")) ret.projectProfiles[t.getRow()].profi_txt = field.getString();
                }
                b = t.nextRow();
            }    
        }     
        
        // Company Code
        t = function.getTableParameterList().getTable("E_BUKRS"); 
        ret.companyCodes = new CompanyCode[t.getNumRows()];
        if (t.getNumRows() > 0)
        {
            t.firstRow();
            b = true;
            while (b)
            {
                ret.companyCodes[t.getRow()] = new CompanyCode();
                for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("BUKRS")) ret.companyCodes[t.getRow()].bukrs = field.getString();
                    if (field.getName().equals("BUTXT")) ret.companyCodes[t.getRow()].butxt = field.getString();
                }
                b = t.nextRow();
            }    
        }      
        
        // Business Area
        t = function.getTableParameterList().getTable("E_GSBER"); 
        ret.businessAreas = new BusinessArea[t.getNumRows()];
        if (t.getNumRows() > 0)
        {
            t.firstRow();
            b = true;
            while (b)
            {
                ret.businessAreas[t.getRow()] = new BusinessArea();
                for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("GSBER")) ret.businessAreas[t.getRow()].gsber = field.getString();
                    if (field.getName().equals("GTEXT")) ret.businessAreas[t.getRow()].gtext = field.getString();
                }
                b = t.nextRow();
            }    
        }     
        
        // Plant
        t = function.getTableParameterList().getTable("E_WERKS"); 
        ret.plants = new Plant[t.getNumRows()];
        if (t.getNumRows() > 0)
        {
            t.firstRow();
            b = true;
            while (b)
            {
                ret.plants[t.getRow()] = new Plant();
                for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("WERKS")) ret.plants[t.getRow()].werks = field.getString();
                    if (field.getName().equals("NAME1")) ret.plants[t.getRow()].name1 = field.getString();
                    if (field.getName().equals("BUKRS")) ret.plants[t.getRow()].bukrs = field.getString();
                }
                b = t.nextRow();
            }    
        }       
        
        // Project Manager
        t = function.getTableParameterList().getTable("E_VERNR"); 
        ret.projectManagers = new ProjectManager[t.getNumRows()];
        if (t.getNumRows() > 0)
        {
            t.firstRow();
            b = true;
            while (b)
            {
                ret.projectManagers[t.getRow()] = new ProjectManager();
                for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("VERNR")) ret.projectManagers[t.getRow()].vernr = field.getString();
                    if (field.getName().equals("VERNA")) ret.projectManagers[t.getRow()].verna = field.getString();
                }
                b = t.nextRow();
            }    
        }    
        
        // Requesters
        t = function.getTableParameterList().getTable("E_ASTNR"); 
        ret.requesters = new Requester[t.getNumRows()];
        if (t.getNumRows() > 0)
        {
            t.firstRow();
            b = true;
            while (b)
            {
                ret.requesters[t.getRow()] = new Requester();
                for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("ASTNR")) ret.requesters[t.getRow()].astnr = field.getString();
                    if (field.getName().equals("ASTNA")) ret.requesters[t.getRow()].astna = field.getString();
                }
                b = t.nextRow();
            }    
        }       
        
        // Places
        t = function.getTableParameterList().getTable("E_STAND"); 
        ret.places = new Place[t.getNumRows()];
        if (t.getNumRows() > 0)
        {
            t.firstRow();
            b = true;
            while (b)
            {
                ret.places[t.getRow()] = new Place();
                for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("STAND")) ret.places[t.getRow()].stand = field.getString();
                    if (field.getName().equals("KTEXT")) ret.places[t.getRow()].ktext = field.getString();
                }
                b = t.nextRow();
            }    
        }     
        
        // Profit centers
        t = function.getTableParameterList().getTable("E_PRCTR"); 
        ret.profitCenters = new ProfitCenter[t.getNumRows()];
        if (t.getNumRows() > 0)
        {
            t.firstRow();
            b = true;
            while (b)
            {
                ret.profitCenters[t.getRow()] = new ProfitCenter();
                for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("PRCTR")) ret.profitCenters[t.getRow()].prctr = field.getString();
                    if (field.getName().equals("KTEXT")) ret.profitCenters[t.getRow()].ktext = field.getString();
                }
                b = t.nextRow();
            }    
        }          
        
        // Project types
        t = function.getTableParameterList().getTable("E_PRART"); 
        ret.projectTypes = new ProjectType[t.getNumRows()];
        if (t.getNumRows() > 0)
        {
            t.firstRow();
            b = true;
            while (b)
            {
                ret.projectTypes[t.getRow()] = new ProjectType();
                for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("PRART")) ret.projectTypes[t.getRow()].prart = field.getString();
                    if (field.getName().equals("PRATX")) ret.projectTypes[t.getRow()].pratx = field.getString();
                }
                b = t.nextRow();
            }    
        }   
        
        // MRP Controllers
        t = function.getTableParameterList().getTable("E_DISPO"); 
        ret.mrpControllers = new MRPController[t.getNumRows()];
        if (t.getNumRows() > 0)
        {
            t.firstRow();
            b = true;
            while (b)
            {
                ret.mrpControllers[t.getRow()] = new MRPController();
                for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("WERKS")) ret.mrpControllers[t.getRow()].werks = field.getString();
                    if (field.getName().equals("DISPO")) ret.mrpControllers[t.getRow()].dispo = field.getString();
                    if (field.getName().equals("DSNAM")) ret.mrpControllers[t.getRow()].dsnam = field.getString();
                }
                b = t.nextRow();
            }    
        }   
        
        // Work Centers
        t = function.getTableParameterList().getTable("E_ARBPL"); 
        ret.workCenters = new WorkCenter[t.getNumRows()];
        if (t.getNumRows() > 0)
        {
            t.firstRow();
            b = true;
            while (b)
            {
                ret.workCenters[t.getRow()] = new WorkCenter();
                for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("WERKS")) ret.workCenters[t.getRow()].werks = field.getString();
                    if (field.getName().equals("ARBPL")) ret.workCenters[t.getRow()].arbpl = field.getString();
                    if (field.getName().equals("KTEXT")) ret.workCenters[t.getRow()].ktext = field.getString();
                }
                b = t.nextRow();
            }    
        }       
        
        // Distribution Keys
        t = function.getTableParameterList().getTable("E_VERTL"); 
        ret.distributionKeys = new DistributionKey[t.getNumRows()];
        if (t.getNumRows() > 0)
        {
            t.firstRow();
            b = true;
            while (b)
            {
                ret.distributionKeys[t.getRow()] = new DistributionKey();
                for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("VERTL")) ret.distributionKeys[t.getRow()].vertl = field.getString();
                    if (field.getName().equals("KTEXT")) ret.distributionKeys[t.getRow()].ktext = field.getString();
                }
                b = t.nextRow();
            }    
        }          
        
        // Return
        return ret;
    }
    
    public void createProject(saprim.integration.Project P, boolean UseExisting)
    {
        JCO.Table t;
        JCO.Structure s;
        
        // Preparation
        IFunctionTemplate ftemp = repository.getFunctionTemplate("ZSAPRIM_CREATE_PROJECT");
        JCO.Function function = ftemp.getFunction();    
        
        function.getImportParameterList().setValue(UseExisting ? "X" : "", "I_USEEX");
        
        // s
        s = com.sap.mw.jco.JCO.createStructure(repository.getStructureDefinition("ZSAPRIM_S_PROJECT"));
        s.setValue(P.astna, "ASTNA");
        s.setValue(P.astnr, "ASTNR");
        s.setValue(Sap.parseDate(P.plfaz), "PLFAZ");
        s.setValue(Sap.parseDate(P.plsez), "PLSEZ");
        s.setValue(P.post1, "POST1");
        s.setValue(P.prctr, "PRCTR");
        s.setValue(P.profl, "PROFL");
        s.setValue(P.pspid, "PSPID");
        s.setValue(P.stand, "STAND");
        s.setValue(P.vbukr, "VBUKR");
        s.setValue(P.verna, "VERNA");
        s.setValue(P.vernr, "VERNR");
        s.setValue(P.vgsbr, "VGSBR");
        s.setValue(P.werks, "WERKS");      
        s.setValue(P.dispo, "DISPO");      
        s.setValue(P.arbpl, "ARBPL");
        s.setValue(P.vertl, "VERTL");
        function.getImportParameterList().setValue(s, "I_PROJECT");
        
        // I_WBS
        if (P.wbss != null)
        {        
            t = com.sap.mw.jco.JCO.createTable(repository.getTableDefinition("ZSAPRIM_S_WBS"));
            for (int n = 0; n < P.wbss.length; n++)
            {
                t.appendRow();
                t.setValue(P.wbss[n].posid.toUpperCase(), "POSID");
                t.setValue(P.wbss[n].post1, "POST1");
                t.setValue(P.wbss[n].pspnr, "PSPNR");
                t.setValue(P.wbss[n].up_posid == null ? null : P.wbss[n].up_posid.toUpperCase(), "UP_POSID");
                t.setValue(P.wbss[n].up_pspnr, "UP_PSPNR");
                t.setValue(Sap.parseDate(P.wbss[n].pstrt), "PSTRT");
                t.setValue(Sap.parseDate(P.wbss[n].pende), "PENDE");
                t.setValue(P.wbss[n].pdaur, "PDAUR");
                t.setValue(P.wbss[n].peinh, "PEINH");
                t.setValue(Sap.parseDate(P.wbss[n].estrt), "ESTRT");
                t.setValue(Sap.parseDate(P.wbss[n].eende), "EENDE");
                t.setValue(P.wbss[n].edaur, "EDAUR");
                t.setValue(P.wbss[n].eeinh, "EEINH");
                t.setValue(Sap.parseDate(P.wbss[n].istrt), "ISTRT");
                t.setValue(Sap.parseDate(P.wbss[n].iende), "IENDE");
                t.setValue(P.wbss[n].idaur, "IDAUR");
                t.setValue(P.wbss[n].ieinh, "IEINH");                
                t.setValue(P.wbss[n].prart, "PRART");
                t.setValue(P.wbss[n].pratx, "PRATX");      
            }
            function.getTableParameterList().setValue(t, "I_WBS");
        }
        
        // I_NETWORK
        if (P.networks != null)
        {
            t = com.sap.mw.jco.JCO.createTable(repository.getTableDefinition("ZSAPRIM_S_NETWORK"));
            for (int n = 0; n < P.networks.length; n++)
            {
                t.appendRow();
                t.setValue(P.networks[n].aufnr, "AUFNR");
                t.setValue(P.networks[n].aufnt, "AUFNT");
                t.setValue(P.networks[n].aufpl, "AUFPL");
                t.setValue(P.networks[n].gltrp, "GLTRP");
                t.setValue(P.networks[n].gstrp, "GSTRP");
                t.setValue(P.networks[n].ktext, "KTEXT");
                t.setValue(P.networks[n].posid == null ? null : P.networks[n].posid.toUpperCase(), "POSID");
                t.setValue(P.networks[n].pspel, "PSPEL");
                t.setValue(P.networks[n].pspid, "PSPID");
            }  
            function.getTableParameterList().setValue(t, "I_NETWORK");
        }
        
        // I_OPERATION
        if (P.operations != null)
        {
            t = com.sap.mw.jco.JCO.createTable(repository.getTableDefinition("ZSAPRIM_S_OPERATION"));
            for (int n = 0; n < P.operations.length; n++)
            {
                t.appendRow();
                t.setValue(P.operations[n].aplzl, "APLZL");
                t.setValue(P.operations[n].arbeh, "ARBEH");
                t.setValue(P.operations[n].arbei, "ARBEI");
                t.setValue(P.operations[n].aufnt, "AUFNT");
                t.setValue(P.operations[n].aufpl, "AUFPL");
                t.setValue(P.operations[n].ismnw, "ISMNW");
                t.setValue(P.operations[n].ltxa1, "LTXA1");
                t.setValue(P.operations[n].umren, "UMREN");
                t.setValue(P.operations[n].umrez, "UMREZ");
                t.setValue(P.operations[n].vornr, "VORNR");
                t.setValue(P.operations[n].dauno, "DAUNO");
                t.setValue(P.operations[n].daune, "DAUNE");
                t.setValue(P.operations[n].kalid, "KALID");
                t.setValue(P.operations[n].prcal, "PRCAL");
                t.setValue(P.operations[n].einsa, "EINSA");
                t.setValue(P.operations[n].ntanf, "NTANF");
                t.setValue(P.operations[n].einse, "EINSE");
                t.setValue(P.operations[n].ntend, "NTEND");
                t.setValue(P.operations[n].frsp, "FRSP");                
                t.setValue(P.operations[n].posid.toUpperCase(), "POSID");   
                t.setValue(P.operations[n].pspnr, "PSPNR"); 
                t.setValue(P.operations[n].vornr, "VORNR"); 
            }     
            function.getTableParameterList().setValue(t, "I_OPERATION");
        }
        
        // I_MILESTONE
        if (P.milestones != null)
        {
            t = com.sap.mw.jco.JCO.createTable(repository.getTableDefinition("ZSAPRIM_S_MILESTONE"));
            for (int n = 0; n < P.milestones.length; n++)
            {
                t.appendRow();
                t.setValue(P.milestones[n].aplzl, "APLZL");
                t.setValue(P.milestones[n].aufnr, "AUFNR");
                t.setValue(P.milestones[n].aufnt, "AUFNT");
                t.setValue(P.milestones[n].aufpl, "AUFPL");
                t.setValue(P.milestones[n].autyp, "AUTYP");
                t.setValue(P.milestones[n].ktext, "KTEXT");
                t.setValue(P.milestones[n].mlst_zaehl, "MST_ZAEHL");
                t.setValue(P.milestones[n].posid.toUpperCase(), "POSID");
                t.setValue(P.milestones[n].vornr, "VORNR");
                t.setValue(P.milestones[n].zaehl, "ZAEHL");
            }   
            function.getTableParameterList().setValue(t, "I_MILESTONE");
        }
        
        // I_RELATIONSHIP
        if (P.operationRelationships != null)
        {
            t = com.sap.mw.jco.JCO.createTable(repository.getTableDefinition("ZSAPRIM_S_OPERATION_RELAT"));
            for (int n = 0; n < P.operationRelationships.length; n++)
            {
                t.appendRow();
                t.setValue(P.operationRelationships[n].aobar, "AOBAR");
                t.setValue(P.operationRelationships[n].vornr_nch, "VORNR_NCH");
                t.setValue(P.operationRelationships[n].vornr_vor, "VORNR_VOR");
            }   
            function.getTableParameterList().setValue(t, "I_RELATIONSHIP");
        }        
        
        // Execute
        client.execute(function);           
    }
    
    public void transferConfirmations(saprim.integration.Confirmation[] Confirmations)
    {
        if (Confirmations == null) return;
        if (Confirmations.length <= 0) return;
        
        JCO.Table t;
        JCO.Structure s;
        
        // Preparation
        IFunctionTemplate ftemp = repository.getFunctionTemplate("ZSAPRIM_SET_CONFIRMATION");
        JCO.Function function = ftemp.getFunction();    
        
        // Confirmations
        t = com.sap.mw.jco.JCO.createTable(repository.getTableDefinition("ZSAPRIM_S_ICONFIRMATION"));
        for (int n = 0; n < Confirmations.length; n++)
        {
            t.appendRow();
            t.setValue(Confirmations[n].pspid, "PSPID");
            t.setValue(Confirmations[n].vornr, "VORNR");
            t.setValue(Confirmations[n].perce, "PERCE");
            t.setValue(Confirmations[n].isdd, "ISDD");
        }   
        function.getTableParameterList().setValue(t, "I_CONFIRMATION");  
        
        // Execute
        client.execute(function);
    }
    
    public saprim.integration.DataValidation validateData(Confirmation[] Confirmations)
    {
        DataValidation dv = new DataValidation();
        dv.isValid = true;
        
        // VORNR uzunlukları
        for (int n = 0; n < Confirmations.length; n++)
        {
            if (Confirmations[n].vornr.length() > MAX_VORNR_LENGTH)
            {
                dv.isValid = false;
                dv.addSeparator();
                dv.addMessage("Length of operation ID " + Confirmations[n].vornr + " would be too large; shouldnt exceed " + String.valueOf(MAX_VORNR_LENGTH) + " characters.");
            }
        }
        
        return dv;
    }    
    
    public saprim.integration.DataValidation validateData(saprim.integration.Project P)
    {
        DataValidation dv = new DataValidation();
        
        // Operasyon VORNR uzunlukları
        for (int n = 0; n < P.operations.length; n++)
        {
            if (P.operations[n].vornr.length() > MAX_VORNR_LENGTH)
            {
                dv.isValid = false;
                dv.addSeparator();
                dv.addMessage("Length of operation ID " + P.operations[n].vornr + " would be too large; shouldnt exceed " + String.valueOf(MAX_VORNR_LENGTH) + " characters.");
                dv.addMessage("Corresponding operation: " + P.operations[n].toString());                
            }
        }
        
        // Bütün WBS kodları benzersiz olmalı
        for (int n = 0; n < P.wbss.length; n++)
        {
            for (int m = n + 1; m < P.wbss.length; m++)
            {
                if (P.wbss[n].posid.equals(P.wbss[m].posid))
                {
                    dv.isValid = false;
                    dv.addSeparator();
                    dv.addMessage("Multiple WBS's have the same ID: " + P.wbss[n].posid);
                    dv.addMessage(P.wbss[n].toString());
                    dv.addMessage(P.wbss[m].toString());
                }
            }
        }
        
        return dv;
    }
    
    public static Date parseDate(String SapDate)
    {
        Date ret;
        int year, month, day;
        
        if (SapDate == null) return null;
        if (SapDate.length() == 0) return null;
        if (SapDate.equals("0000-00-00")) return null;
        
        if (SapDate.indexOf("-") >= 0)
        {
            return saprim.primavera.Primavera.parseDate(SapDate, true);
        }
        else
        {
            year = Integer.parseInt(SapDate.substring(0, 4));
            month = Integer.parseInt(SapDate.substring(4, 6));
            day = Integer.parseInt(SapDate.substring(6, 8));
        }
        
        ret = new Date(year - 1900, month - 1, day);
        return ret;
    }
    
    public static String parseDate(Date D)
    {
        return parseDate(D, DATE_SOURCE.REGULAR_DATE);
    }
    
    public static String parseDate(Date D, DATE_SOURCE DS)
    {
        int year = D.getYear() + 1900;
        int month = DS == DATE_SOURCE.REGULAR_DATE ? D.getMonth() - 1 : D.getMonth();
        int day = D.getDate();
        
        return String.valueOf(year) + formatMonth(month) + formatDay(day);
    }    
    
    public static String parseUserDate(String Date)
    {
        int pos1 = Date.indexOf(".", 0);
        int pos2 = Date.indexOf(".", pos1 + 1);
        
        String day = Date.substring(0, pos1);
        String month = Date.substring(pos1 + 1, pos2);
        String year = Date.substring(pos2 + 1, Date.length());
        
        return year + month + day;
    }
    
    public static String formatDay(int Day)
    {
        return formatMonth(Day);
    }
    
    public static String formatMonth(int Month)
    {
        return Month < 10 ? "0" + String.valueOf(Month) : String.valueOf(Month);
    }
    
    public static String parseRelationshipType(com.primavera.integration.client.bo.enm.RelationshipType R)
    {   
        if (R == null) return "";
        if (R == com.primavera.integration.client.bo.enm.RelationshipType.FINISH_TO_FINISH) return "EF";
        if (R == com.primavera.integration.client.bo.enm.RelationshipType.FINISH_TO_START) return "NF";
        if (R == com.primavera.integration.client.bo.enm.RelationshipType.START_TO_FINISH) return "SF";
        if (R == com.primavera.integration.client.bo.enm.RelationshipType.START_TO_START) return "AF";
        return "";
    }   
    
    public static String pack(String S)
    {
        if (S == null) return "";
        
        String ret = "";
        
        ret = S;
        
        while (ret.length() > 0 && ret.substring(0, 1).equals("0")) ret = ret.substring(1, ret.length());
        return ret;
    }
    
}
