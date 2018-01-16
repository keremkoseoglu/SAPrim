/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.primavera;

/**
 *
 * @author Kerem
 */

import com.primavera.integration.client.Session;
import com.primavera.integration.client.EnterpriseLoadManager;
import com.primavera.integration.client.RMIURL;
import com.primavera.integration.common.DatabaseInstance;
import com.primavera.integration.client.bo.*;
import com.primavera.integration.client.bo.object.*;
import com.primavera.common.value.*;
import java.util.*;
import saprim.*;
import saprim.sap.Sap;

public class Primavera {

    private Session session;
    private DatabaseInstance[] dbInstances;  
    
    private static final int MAX_TASK_DEPENDENT_ACTIVITY_ID_LENGTH = 20;
    private static final int MAX_WBS_ID_LENGTH = 20;
    private static final int MAX_PROJECT_ID_LENGTH = 20;
    
    public Primavera()
    {
    }
    
    public void connect() throws Exception
    {   
        PrimaveraDB pdb = new PrimaveraDB();
        pdb.connect();
        pdb.deleteSessions(Main.config.primUser);
        pdb.disconnect();
        
        System.setProperty("primavera.bootstrap.home",  Main.config.primHome);
        dbInstances = Session.getDatabaseInstances(RMIURL.getRmiUrl( RMIURL.LOCAL_SERVICE ) );
        session = Session.login( RMIURL.getRmiUrl( RMIURL.LOCAL_SERVICE ), 
            dbInstances[0].getDatabaseId(), Main.config.primUser, Main.config.primPass);
    }
    
    public boolean getConnected()
    {
        return (session != null);
    }
    
    public void disconnect()
    {
        if (getConnected()) 
        {
            session.logout();
            session = null;
        }
    }
    
    public EPS[] getEPSList() throws Exception
    {
        EPS[] ret;
        
        EnterpriseLoadManager elm = session.getEnterpriseLoadManager();
        BOIterator<EPS> boi = elm.loadEPS( new String[]{ "Id", "ObjectId", "ParentObjectId" }, null, "Name asc" );

        ret = new EPS[boi.getCount()];
        int n = 0;
        while ( boi.hasNext() )
        {
            ret[n] = boi.next();
            n++;
        }
        
        return ret;
    }
    
    public Project[] getProjectList() throws Exception
    {
        Project [] ret;
        
        EnterpriseLoadManager elm = session.getEnterpriseLoadManager();
        BOIterator<Project> boi = elm.loadProjects( new String[]
        { 
            "AnticipatedStartDate",
            "AnticipatedFinishDate",
            "ForecastFinishDate",
            "ForecastStartDate", 
            "Id", 
            "Name", 
            "ObjectId", 
            "ParentEPSObjectId" 
        }, null, "Name asc" );

        /*
        com.primavera.integration.client.bo.object.Project pro = new com.primavera.integration.client.bo.object.Project(session);
        pro.setAnticipatedStartDate(new BeginDate(Sap.parseDate(P.plfaz)));
        pro.setAnticipatedFinishDate(new EndDate(Sap.parseDate(P.plsez)));        
        pro.setId(P.pspid);
        pro.setName(P.post1);
        pro.setObjectId(pro.create());
        pro.setParentEPSObjectId(E.getObjectId()); 
         */
        
        ret = new Project[boi.getCount()];
        int n = 0;
        while ( boi.hasNext() )
        {
            ret[n] = boi.next();
            n++;
        }
        
        return ret;        
    }
    
    public WBS[] getWBSList(String ProjectId) throws Exception
    {
        WBS[] ret;
        
        EnterpriseLoadManager elm = session.getEnterpriseLoadManager();
        BOIterator<WBS> boi = elm.loadWBS( new String[]
        { 
            "Code",
            "Name", 
            "ObjectId", 
            "ParentObjectId",
            "AnticipatedStartDate",
            "AnticipatedFinishDate",
            "StartDate",
            "FinishDate",
            "ForecastStartDate",
            "ForecastFinishDate"
        }, "ProjectId = '" + ProjectId + "'", "Name asc" );
        
        ret = new WBS[boi.getCount()];
        int n = 0;
        while ( boi.hasNext() )
        {
            ret[n] = boi.next();
            n++;
        }
        
        return ret;         
    }
    
    public Activity[] getActivityList(String ProjectObjectId) throws Exception {
        Activity[] ret;
        
        EnterpriseLoadManager elm = session.getEnterpriseLoadManager();
        BOIterator<Activity> boi = elm.loadActivities(new String[]
        { 
            "CalendarName",
            "ProjectObjectId", 
            "WBSCode", 
            "WBSObjectId", 
            "Id",
            "Name",
            "Type",
            "PercentComplete",
            "PlannedDuration",
            "PrimaryConstraintDate",
            "PrimaryConstraintType",
            "StartDate",
            "Status"
        }, "ProjectObjectId = '" + ProjectObjectId + "'", "Name asc" );
        
        ret = new Activity[boi.getCount()];
        int n = 0;
        while ( boi.hasNext() )
        {
            ret[n] = boi.next();
            n++;
        }        
        
        return ret;
    }
    
    public Relationship[] getRelationshipList(String ProjectObjectId) throws Exception
    {
        Relationship[] ret;
        
        EnterpriseLoadManager elm = session.getEnterpriseLoadManager();
        BOIterator<Relationship> boi = elm.loadRelationships(new String[]
        { 
            "PredecessorActivityId",
            "SuccessorActivityId", 
            "Type"
        }, "PredecessorProjectObjectId = '" + ProjectObjectId + "'", null );
        
        ret = new Relationship[boi.getCount()];
        int n = 0;
        while ( boi.hasNext() )
        {
            ret[n] = boi.next();
            n++;
        }        
        
        return ret;            
    }
    
    public void createProject(EPS E, saprim.integration.Project P) throws Exception
    {
        // Hazırlık
        //EnterpriseLoadManager elm = session.getEnterpriseLoadManager(); 
        
        // Proje
        com.primavera.integration.client.bo.object.Project pro = new com.primavera.integration.client.bo.object.Project(session);
        try { pro.setAnticipatedStartDate(new BeginDate(Sap.parseDate(P.plfaz))); }catch(Exception ex) {}
        try { pro.setAnticipatedFinishDate(new EndDate(Sap.parseDate(P.plsez)));  }catch(Exception ex) {}       
        pro.setId(P.getIdForPrimavera());
        pro.setName(P.post1);
        pro.setParentEPSObjectId(E.getObjectId()); 
        pro.setObjectId(pro.create());

        //Proje notları
        String note = "";
        
        for (int n = 0; n < P.wbss.length; n++) {
            if (P.wbss[n].up_posid == null || P.wbss[n].up_posid.trim().length() == 0) {
                note += "Project Type: " + P.wbss[n].pratx + "\r\n";
                n = P.wbss.length + 1;
            }
        }
        
        note += "Project Manager: " + P.verna + "\r\n";
        note += "Requested By: " + P.astna + "\r\n";
        createProjectNote(pro, note);
        
        // WBS'ler
        if (P.wbss != null) createWBSOfParent(P, "", null, pro.getObjectId());
        
        // İşlemler arası bağlantılar
        if (P.operationRelationships != null) createOperationRelationships(P);
        
        // Kaynaklar
        // createResources(pro, P);
    }
    
    private void createOperationRelationships(saprim.integration.Project P) throws Exception
    {
        for (int n = 0; n < P.operationRelationships.length; n++)
        {
            com.primavera.integration.client.bo.object.Relationship rs = new com.primavera.integration.client.bo.object.Relationship(session);
            rs.setType(parseRelationshipType(P.operationRelationships[n].aobar));
            
            for (int x = 0; x < P.operations.length; x++)
            {
                if (P.operations[x].aufpl.equals(P.operationRelationships[n].aufpl_vor) &&
                    P.operations[x].aplzl.equals(P.operationRelationships[n].aplzl_vor))
                    rs.setPredecessorActivityObjectId(P.operations[x].objectId);
                
                if (P.operations[x].aufpl.equals(P.operationRelationships[n].aufpl_nch) &&
                    P.operations[x].aplzl.equals(P.operationRelationships[n].aplzl_nch))
                    rs.setSuccessorActivityObjectId(P.operations[x].objectId);
            }
            
            rs.create();
        }
    }
    
    private void createWBSOfParent(saprim.integration.Project P, String UpPosid, ObjectId ParentObjectId, ObjectId ProjectObjectId) throws Exception
    {   
        for (int n = 0; n < P.wbss.length; n++)
        {
            if (
                    (P.wbss[n].up_posid == null && UpPosid == null)
                    ||
                    (P.wbss[n].up_posid != null && P.wbss[n].up_posid.equals(UpPosid))
               )
            {
                com.primavera.integration.client.bo.object.WBS wbs = new com.primavera.integration.client.bo.object.WBS(session);
                wbs.setCode(P.wbss[n].getIdForPrimavera());
                wbs.setName(P.wbss[n].post1);
                wbs.setProjectObjectId(ProjectObjectId);
                if (ParentObjectId != null) wbs.setParentObjectId(ParentObjectId);
                try { wbs.setAnticipatedStartDate(Primavera.parseDateToBeginDate(Sap.parseDate(P.wbss[n].estrt))); }catch(Exception ex) { ex.printStackTrace(); }
                try { wbs.setAnticipatedFinishDate(Primavera.parseDateToEndDate(Sap.parseDate(P.wbss[n].eende))); }catch(Exception ex) { ex.printStackTrace(); }
                
                // Create WBS itself
                ObjectId oi = wbs.create();
                wbs.setObjectId(oi);
                
                // Create task dependent activities of this WBS
                for (int y = 0; y < P.operations.length; y++) {
                    if (P.operations[y].pspnr.equals(P.wbss[n].pspnr)) {
                        com.primavera.common.value.ObjectId ooi = createTaskDependentActivity(wbs, P.wbss[n], P.operations[y]);
                        P.operations[y].objectId = ooi;
                    }
                }
                
                /*
                for (int x = 0; x < P.networks.length; x++) {
                    if (P.networks[x].pspel.equals(P.wbss[n].pspnr)) {
                        for (int y = 0; y < P.operations.length; y++) {
                            if (P.operations[y].aufpl.equals(P.networks[x].aufpl)) {
                                createTaskDependentActivity(wbs, P.wbss[n], P.operations[y]);
                            }   
                        }
                    }
                } */
                
                // Create childrien
                createWBSOfParent(P, P.wbss[n].posid, oi, ProjectObjectId);
            }
        }        
    }
    
    public void createProjectNote(com.primavera.integration.client.bo.object.Project P, String Note) throws Exception {
        com.primavera.integration.client.bo.object.ProjectNote pn = new com.primavera.integration.client.bo.object.ProjectNote(session);
        
        EnterpriseLoadManager elm = session.getEnterpriseLoadManager();
        BOIterator<NotebookTopic> boinbt = elm.loadNotebookTopics(new String[] {"ObjectId"}, null, null);   
        while (boinbt.hasNext())
        {
            pn.setNotebookTopicObjectId(boinbt.next().getObjectId());
        }         
        
        pn.setProjectObjectId(P.getObjectId());
        pn.setNote(Note);
        pn.create();
    }    
    
    public com.primavera.common.value.ObjectId createTaskDependentActivity(com.primavera.integration.client.bo.object.WBS W, saprim.integration.WBS SapWBS, saprim.integration.Operation O) throws Exception {
        com.primavera.integration.client.bo.object.Activity act = new com.primavera.integration.client.bo.object.Activity(session);
        
        /*EnterpriseLoadManager elm = session.getEnterpriseLoadManager();
        BOIterator<com.primavera.integration.client.bo.object.Calendar> boical = elm.loadCalendars(new String[] {"ObjectId", "Name"}, null, null);   
        while (boical.hasNext())
        {
            com.primavera.integration.client.bo.object.Calendar c = boical.next();
            if (c.getName().equals(O.prcal)) act.setCalendarObjectId(c.getObjectId());
        }     */
        act.setCalendarObjectId(getCalendarId(O.prcal));
        
        act.setProjectObjectId(W.getProjectObjectId());
        act.setWBSObjectId(W.getObjectId());
        act.setId(O.getIdForPrimavera());
        act.setName(O.ltxa1);
        act.setType(com.primavera.integration.client.bo.enm.ActivityType.TASK_DEPENDENT);
        act.setDurationType(com.primavera.integration.client.bo.enm.DurationType.FIXED_DURATION_AND_UNITS);
        act.setPercentCompleteType(com.primavera.integration.client.bo.enm.PercentCompleteType.PHYSICAL);
        act.setPercentComplete(new Percent(O.aprat));
        //act.setCalendarObjectId(com.primavera.integration.client.bo.enm.CalendarType.GLOBAL)
        double d = Double.valueOf(O.dauno);
        act.setPlannedDuration(new Duration(d));
        
        //act.setStatus(O.started ? com.primavera.integration.client.bo.enm.ActivityStatus.IN_PROGRESS : com.primavera.integration.client.bo.enm.ActivityStatus.NOT_STARTED);
        //act.setActualStartDate(parseDateToBeginDate(saprim.sap.Sap.parseDate("20090202")));
        //act.setStatus(com.primavera.integration.client.bo.enm.ActivityStatus.NOT_STARTED);
        //act.setPlannedStartDate(parseDateToBeginDate(saprim.sap.Sap.parseDate("20090201")));
        //        
        if (O.ended)
        {
            act.setStatus(com.primavera.integration.client.bo.enm.ActivityStatus.COMPLETED);
            act.setActualStartDate(parseDateToBeginDate(saprim.sap.Sap.parseDate(O.isavd)));
            act.setActualFinishDate(parseDateToEndDate(saprim.sap.Sap.parseDate(O.ieavd)));
        }
        else
        {
            if (O.started)
            {
                act.setStatus(com.primavera.integration.client.bo.enm.ActivityStatus.IN_PROGRESS);
                act.setActualStartDate(parseDateToBeginDate(saprim.sap.Sap.parseDate(O.isavd)));
            }
            else
            {
                act.setStatus(com.primavera.integration.client.bo.enm.ActivityStatus.NOT_STARTED);
            }            
        }
        
        if (O.einsa.equals("1")) {
            act.setPrimaryConstraintDate(saprim.sap.Sap.parseDate(O.ntanf));
            act.setPrimaryConstraintType(com.primavera.integration.client.bo.enm.ConstraintType.MANDATORY_START);
        }
        else if (O.einsa.equals("2")) {
            act.setPrimaryConstraintDate(saprim.sap.Sap.parseDate(O.ntanf));
            act.setPrimaryConstraintType(com.primavera.integration.client.bo.enm.ConstraintType.START_ON_OR_AFTER);
        }
        else if (O.einsa.equals("3")) {
            act.setPrimaryConstraintDate(saprim.sap.Sap.parseDate(O.ntanf));
            act.setPrimaryConstraintType(com.primavera.integration.client.bo.enm.ConstraintType.START_ON_OR_BEFORE); 
        }    
        else if (O.einse.equals("1")) {
            act.setPrimaryConstraintDate(saprim.sap.Sap.parseDate(O.ntend));
            act.setPrimaryConstraintType(com.primavera.integration.client.bo.enm.ConstraintType.MANDATORY_FINISH);
        }
        else if (O.einse.equals("2")) {
            act.setPrimaryConstraintDate(saprim.sap.Sap.parseDate(O.ntend));
            act.setPrimaryConstraintType(com.primavera.integration.client.bo.enm.ConstraintType.FINISH_ON_OR_AFTER);
        }
        else if (O.einse.equals("3")) {
            act.setPrimaryConstraintDate(saprim.sap.Sap.parseDate(O.ntend));
            act.setPrimaryConstraintType(com.primavera.integration.client.bo.enm.ConstraintType.FINISH_ON_OR_BEFORE);            
        }  
        else if (O.frsp.equals("1")) {
            act.setPrimaryConstraintDate(new Date());
            act.setPrimaryConstraintType(com.primavera.integration.client.bo.enm.ConstraintType.START_ON_OR_AFTER);
        }
        else if (O.frsp.equals("2")) {
            act.setPrimaryConstraintDate(saprim.sap.Sap.parseDate(O.ntend));
            act.setPrimaryConstraintType(com.primavera.integration.client.bo.enm.ConstraintType.AS_LATE_AS_POSSIBLE);
        }        
        
        return act.create();
    }
    
    public void createResources(com.primavera.integration.client.bo.object.Project P, saprim.integration.Project IP) throws Exception {
        // Her proje için bir ana kaynak grubu yaratıyoruz
        com.primavera.integration.client.bo.object.Resource parent = new com.primavera.integration.client.bo.object.Resource(session);
        parent.setId(P.getId());
        parent.setName(P.getName());
        parent.setResourceType(com.primavera.integration.client.bo.enm.ResourceType.NONLABOR);
        parent.setObjectId(parent.create());     
        
        // Diğer kaynak bilgileri
        for (int n = 0; n < IP.resources.length; n++) {
            com.primavera.integration.client.bo.enm.ResourceType r = com.primavera.integration.client.bo.enm.ResourceType.NULL; 
            if (IP.resources[n].prire.equals("1")) r = com.primavera.integration.client.bo.enm.ResourceType.LABOR;
            if (IP.resources[n].prire.equals("2")) r = com.primavera.integration.client.bo.enm.ResourceType.NONLABOR;
            if (IP.resources[n].prire.equals("3")) r = com.primavera.integration.client.bo.enm.ResourceType.MATERIAL;
            
            com.primavera.integration.client.bo.object.Resource child = new com.primavera.integration.client.bo.object.Resource(session);
            child.setId(IP.resources[n].larnt + ":" + IP.resources[n].arbid);
            child.setName(IP.resources[n].ktext);
            child.setResourceType(r);
            child.setParentObjectId(parent.getObjectId());
            child.setObjectId(child.create());
            
            com.primavera.integration.client.bo.object.ProjectResource pr = new com.primavera.integration.client.bo.object.ProjectResource(session);
            pr.setProjectObjectId(P.getObjectId());
            pr.setResourceObjectId(child.getObjectId());
            pr.create();
        }
        
        // Malzeme kaynakları
        for (int n = 0; n < IP.materialResources.length; n++) {
            com.primavera.integration.client.bo.object.Resource child = new com.primavera.integration.client.bo.object.Resource(session);
            child.setId(IP.materialResources[n].matnr);
            child.setName(IP.materialResources[n].maktx);
            child.setResourceType(com.primavera.integration.client.bo.enm.ResourceType.MATERIAL);
            child.setParentObjectId(parent.getObjectId());
            child.setObjectId(child.create());
            
            com.primavera.integration.client.bo.object.ProjectResource pr = new com.primavera.integration.client.bo.object.ProjectResource(session);
            pr.setProjectObjectId(P.getObjectId());
            pr.setResourceObjectId(child.getObjectId());
            pr.create();
        }        
    }
    
    public saprim.integration.DataValidation validateData(saprim.integration.Project P) throws Exception
    {
        saprim.integration.DataValidation dv = new saprim.integration.DataValidation();
        
        // Proje uzunluğu
        String pid = P.getIdForPrimavera();
        if (pid.length() > MAX_PROJECT_ID_LENGTH)
        {
            dv.isValid = false;
            dv.addSeparator();
            dv.addMessage("Length of project id " + pid + " would be too large; shouldnt exceed " + String.valueOf(MAX_PROJECT_ID_LENGTH) + " characters.");           
        }
        
        // WBS / İşlem kontrolleri
        for (int n = 0; n < P.wbss.length; n++)
        {
            // WBS uzunluk kontrolü
            String wid = P.wbss[n].getIdForPrimavera();
            if (wid.length() > MAX_WBS_ID_LENGTH)
            {
                dv.isValid = false;
                dv.addSeparator();
                dv.addMessage("Length of WBS id " + wid + " would be too large; shouldnt exceed " + String.valueOf(MAX_WBS_ID_LENGTH) + " characters.");
                dv.addMessage("Corresponding WBS: " + P.wbss[n].toString());
            }
            
            // İşlem kontrolleri
            for (int y = 0; y < P.operations.length; y++) {
                if (P.operations[y].pspnr.equals(P.wbss[n].pspnr)) 
                {
                    // İşlem uzunluğu
                    String id = P.operations[y].getIdForPrimavera();
                    if (id.length() > MAX_TASK_DEPENDENT_ACTIVITY_ID_LENGTH)
                    {
                        dv.isValid = false;
                        dv.addSeparator();
                        dv.addMessage("Length of activity " + id + " would be too large; shouldnt exceed " + String.valueOf(MAX_TASK_DEPENDENT_ACTIVITY_ID_LENGTH) + " characters.");
                        dv.addMessage("Corresponding WBS: " + P.wbss[n].toString());
                        dv.addMessage("Corresponding operation: " + P.operations[y].toString());
                    }
                    
                    // İşlem takvimi
                    if (getCalendarId(P.operations[y].prcal) == null)
                    {
                        dv.isValid = false;
                        dv.addSeparator();
                        dv.addMessage("Calendar " + P.operations[y].prcal + " is not defined in Primavera. ");
                        dv.addMessage("Corresponding WBS: " + P.wbss[n].toString());
                        dv.addMessage("Corresponding operation: " + P.operations[y].toString());
                        dv.addMessage("Primavera calendars are matched with SAP calendars in SAP transaction ZSAPRIM08_V.");
                    }
                }
            }    
        }
        
        // Bu kadar
        return dv;
    }
    
    public com.primavera.common.value.ObjectId getCalendarId(String CalendarText) throws Exception
    {
        EnterpriseLoadManager elm = session.getEnterpriseLoadManager();
        BOIterator<com.primavera.integration.client.bo.object.Calendar> boical = elm.loadCalendars(new String[] {"ObjectId", "Name"}, null, null);   
        while (boical.hasNext())
        {
            com.primavera.integration.client.bo.object.Calendar c = boical.next();
            if (c.getName().equals(CalendarText)) return c.getObjectId();
        }           
        
        return null;
    }    
    
    public static Date parseDate(BeginDate D)
    {
        return parseDate(D.toString());
    }    
    
    public static Date parseDate(EndDate D)
    {
        return parseDate(D.toString());
    }
    
    public static BeginDate parseDateToBeginDate(Date D)
    {
        if (D == null) return null;
        return BeginDate.createBeginDate(D);
    }
    
    public static EndDate parseDateToEndDate(Date D)
    {
        if (D == null) return null;
        return EndDate.createEndDate(D);
    }
    
    public static Date parseDate(String PrimaveraDate)
    {
        return parseDate(PrimaveraDate, false);
    }
    
    public static Date parseDate(String PrimaveraDate, boolean SourceIsSap)
    {
        Date ret;
        int year, month, day;
        
        year = Integer.parseInt(PrimaveraDate.substring(0, 4));
        month = Integer.parseInt(PrimaveraDate.substring(5, 7));
        day = Integer.parseInt(PrimaveraDate.substring(8, 10));
        
        ret = new Date(year - 1900, SourceIsSap ? month - 1 : month, day);
        return ret;        
    }
    
    public static com.primavera.integration.client.bo.enm.RelationshipType parseRelationshipType(String SapRelationshipType)
    {   
        if (SapRelationshipType.equals("EF")) return com.primavera.integration.client.bo.enm.RelationshipType.FINISH_TO_FINISH;     // FF
        if (SapRelationshipType.equals("NF")) return com.primavera.integration.client.bo.enm.RelationshipType.FINISH_TO_START;      // FS
        if (SapRelationshipType.equals("SF")) return com.primavera.integration.client.bo.enm.RelationshipType.START_TO_FINISH;      // SF
        if (SapRelationshipType.equals("AF")) return com.primavera.integration.client.bo.enm.RelationshipType.START_TO_START;       // SS
        
        return com.primavera.integration.client.bo.enm.RelationshipType.NULL;
    }
    
}
