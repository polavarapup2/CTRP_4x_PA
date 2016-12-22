package gov.nih.nci.pa.service.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import gov.nih.nci.pa.domain.CTGovImportLog;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ctgov.ClinicalStudy;
import gov.nih.nci.pa.service.ctgov.DateStruct;
import gov.nih.nci.pa.service.ctgov.IdInfoStruct;
import gov.nih.nci.pa.service.search.CTGovImportLogSearchCriteria;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;


/**
 * Exercises CTGovSyncNightlyServiceBeanLocal.
 * @author ADas
 */
public class CTGovSyncNightlyServiceLocalTest extends AbstractHibernateTestCase {
    
    private ProtocolQueryServiceLocal protocolQueryServiceLocal;
    private CTGovSyncServiceLocal ctGovSyncServiceLocal;
    private LookUpTableServiceRemote lookUpTableService;
    private MailManagerServiceLocal mailManagerSvc;
    private CTGovSyncNightlyServiceBeanLocal ctGovSyncNightlyServiceBeanLocal = new CTGovSyncNightlyServiceBeanLocal();
    private List<StudyProtocolQueryDTO> queryDTOList = new ArrayList<StudyProtocolQueryDTO>();    
    
    /**
     * Mocks up necessary services and populates entries in ctgovimport_log table.
     * @throws Exception Exception
     */
    @Before
    public void setUp() throws Exception {
        populateCTGovImportLogs();
        setupLookupTableServiceMock();        
        setupProtocolQueryServiceMock();
        setupCtGovSyncServiceMock();
        setupMailManagerServiceMock();
        ctGovSyncNightlyServiceBeanLocal.setMailManagerService(mailManagerSvc);
        ctGovSyncNightlyServiceBeanLocal.setQueryServiceLocal(protocolQueryServiceLocal);
        ctGovSyncNightlyServiceBeanLocal.setLookUpTableService(lookUpTableService);
        ctGovSyncNightlyServiceBeanLocal.setCtGovSyncServiceLocal(ctGovSyncServiceLocal);
    }
    
    /**
     * Mocks MailManagerService
     * @throws PAException PAException
     */
    private void setupMailManagerServiceMock() throws PAException {
        mailManagerSvc = mock(MailManagerServiceLocal.class);
        doNothing().when(mailManagerSvc).sendCTGovSyncStatusSummaryMail(any(List.class));
    }
    
    /**
     * Mocks LookUpTableServiceRemote.
     * @throws PAException PAException
     */
    private void setupLookupTableServiceMock() throws PAException {
        lookUpTableService = mock(LookUpTableServiceRemote.class);
        when(lookUpTableService.getPropertyValue("ctgov.sync.enabled")).thenReturn("true");
    }
    
    /**
     * Mocks ProtocolQueryService.
     * @throws PAException PAException
     */
    private void setupProtocolQueryServiceMock() throws PAException {        
        protocolQueryServiceLocal = mock(ProtocolQueryServiceLocal.class);
        //adding three study protocols 
        StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
        dto.setNciIdentifier("NCI-2012-0001");
        dto.setNctIdentifier("NCT-2012-0001");
        dto.setLocalStudyProtocolIdentifier("LEAD_ORG_ID_0001");
        dto.setStudyProtocolId(1L);
        queryDTOList.add(dto);
        
        dto = new StudyProtocolQueryDTO();
        dto.setNciIdentifier("NCI-2012-0002");
        dto.setNctIdentifier("NCT-2012-0002");
        dto.setLocalStudyProtocolIdentifier("LEAD_ORG_ID_0002");
        dto.setStudyProtocolId(2L);
        queryDTOList.add(dto);
        
        dto = new StudyProtocolQueryDTO();
        dto.setNciIdentifier("NCI-2012-0003");
        dto.setNctIdentifier("NCT-2012-0003");
        dto.setLocalStudyProtocolIdentifier("LEAD_ORG_ID_0003");
        dto.setStudyProtocolId(3L);
        queryDTOList.add(dto);
        
        //returning all three study protocols
        when(protocolQueryServiceLocal.getStudyProtocolByCriteria(
                any(StudyProtocolQueryCriteria.class))).thenReturn(queryDTOList);
    }
    
    /**
     * Mocks CTGovSyncServiceLocal
     * @throws PAException PAException
     */
    private void setupCtGovSyncServiceMock()throws PAException {   
               
        ctGovSyncServiceLocal = mock(CTGovSyncServiceLocal.class);
        DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        Calendar calendar = Calendar.getInstance();
        
        //CT.Gov trial which is more recent than 
        //corresponding CT.Gov import log entry.
        ClinicalStudy study1 = new ClinicalStudy();                
        IdInfoStruct idInfo1 = new IdInfoStruct();
        idInfo1.setNctId("NCT-2012-0001");
        study1.setIdInfo(idInfo1);        
        DateStruct date1 = new DateStruct();        
        date1.setContent(dateFormat.format(calendar.getTime()));
        study1.setLastchangedDate(date1);       
        
        //CT.Gov trial which doesn't have a corresponding CT.Gov
        //import log entry.
        ClinicalStudy study2 = new ClinicalStudy();        
        IdInfoStruct idInfo2 = new IdInfoStruct();
        idInfo2.setNctId("NCT-2012-0002");
        study2.setIdInfo(idInfo2);        
        DateStruct date2 = new DateStruct();                
        date2.setContent(dateFormat.format(calendar.getTime()));
        study2.setLastchangedDate(date2);
        
        //CT.Gov trial which is older than the corresponding CT.Gov
        //import log entry.
        ClinicalStudy study3 = new ClinicalStudy();                
        IdInfoStruct idInfo3 = new IdInfoStruct();
        idInfo3.setNctId("NCT-2012-0003");
        study3.setIdInfo(idInfo3);        
        DateStruct date3 = new DateStruct();        
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.MONTH, -1);
        date3.setContent(dateFormat.format(calendar1.getTime()));
        study3.setLastchangedDate(date3);
        
        when(ctGovSyncServiceLocal.getCtGovStudyByNctId("NCT-2012-0001")).thenReturn(study1);
        when(ctGovSyncServiceLocal.getCtGovStudyByNctId("NCT-2012-0002")).thenReturn(study2);
        when(ctGovSyncServiceLocal.getCtGovStudyByNctId("NCT-2012-0003")).thenReturn(study3);
        
        when(ctGovSyncServiceLocal.importTrial("NCT-2012-0001")).thenReturn("NCI-2012-0001");
        when(ctGovSyncServiceLocal.importTrial("NCT-2012-0002")).thenReturn("NCI-2012-0002");
        when(ctGovSyncServiceLocal.importTrial("NCT-2012-0003")).thenReturn("NCI-2012-0003");      
        
        when(ctGovSyncServiceLocal.getLogEntries(any(CTGovImportLogSearchCriteria.class))
                ).thenReturn(new ArrayList<CTGovImportLog>());
    }

    /**
     * Populates entries in ctgovimport_log table for NCT-2012-0001 and NCT-2012-0003.
     */
    private void populateCTGovImportLogs() {
        Calendar calendar = Calendar.getInstance();
        
        Session s = PaHibernateUtil.getCurrentSession();
        SQLQuery query = s.createSQLQuery(
                "insert into ctgovimport_log (identifier, nci_id, nct_id, trial_title, action_performed, " 
        + "import_status, user_created, date_created) " 
        + "values (:id, :nci_id, :nct_id, :title, :action, :status, :user, :date)");
        
        calendar.add(Calendar.MONTH, -2);
        query.setText("id", "1");
        query.setText("nci_id", "NCI-2012-0001");
        query.setText("nct_id", "NCT-2012-0001");
        query.setText("title", "Test");
        query.setText("action", "UPDATE");
        query.setText("status", "Success");
        query.setText("user", "User");
        query.setTimestamp("date", calendar.getTime());
        query.executeUpdate();
        s.flush();
        
        calendar.add(Calendar.MONTH, 2);
        query.setText("id", "2");
        query.setText("nci_id", "NCI-2012-0003");
        query.setText("nct_id", "NCT-2012-0003");
        query.setText("title", "Test");
        query.setText("action", "UPDATE");
        query.setText("status", "Success");
        query.setText("user", "User");
        query.setTimestamp("date", calendar.getTime());
        query.executeUpdate();
        s.flush();
    }
    
    /**
     * Removes the entries in ctgovimport_log table
     */
    @After
    public void tear() {
        Session s = PaHibernateUtil.getCurrentSession();
        SQLQuery query = s.createSQLQuery("delete from ctgovimport_log");
        query.executeUpdate();
    }
 
    /**
     * Exercises updateIndustrialAndConsortiaTrials method in CTGovSyncNightlyServiceLocal
     * @throws PAException PAException
     */ 
    @Test
    public void testUpdateIndustrialAndConsortiaTrials() throws PAException {
        ctGovSyncNightlyServiceBeanLocal.updateIndustrialAndConsortiaTrials();
        //Only NCT-2012-0001 and NCT-2012-0002 should get updated
        //NCT-2012-0003 should be skipped
        verify(ctGovSyncServiceLocal, times(1)).importTrial("NCT-2012-0001");
        verify(ctGovSyncServiceLocal, times(1)).importTrial("NCT-2012-0002");
        verify(ctGovSyncServiceLocal, never()).importTrial("NCT-2012-0003");
    }    
}
