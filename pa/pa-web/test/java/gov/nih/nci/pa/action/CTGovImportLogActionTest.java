package gov.nih.nci.pa.action;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import gov.nih.nci.pa.domain.CTGovImportLog;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.search.CTGovImportLogSearchCriteria;
import gov.nih.nci.pa.service.util.CTGovSyncServiceLocal;

/**
 * @author ADas
 */
public class CTGovImportLogActionTest extends AbstractPaActionTest {
    private CTGovImportLogAction ctGovImportLogAction;
    private CTGovSyncServiceLocal ctGovSyncServiceLocal;
    
    /**
     * Initialization method.
     * @throws PAException if an error occurs
     */
    @Before
    public void setUp() throws PAException {
        ctGovImportLogAction = new CTGovImportLogAction();
        ctGovImportLogAction.prepare();        
        ctGovSyncServiceLocal = mock(CTGovSyncServiceLocal.class);
        ctGovImportLogAction.setCtGovSyncService(ctGovSyncServiceLocal);
        CTGovImportLog logEntry = new CTGovImportLog();
        logEntry.setAction("UPDATE");
        logEntry.setAdmin(true);
        logEntry.setScientific(false);
        logEntry.setImportStatus("Success");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date date = calendar.getTime();
        logEntry.setDateCreated(date);
        logEntry.setNciID("NCI-2012-00260");
        logEntry.setNctID("NCT-2012-00260");
        logEntry.setUserCreated("User");
        logEntry.setTitle("abc");
        List<CTGovImportLog> logEntries = new ArrayList<CTGovImportLog>();
        logEntries.add(logEntry);        
        when(ctGovSyncServiceLocal.getLogEntries(any(CTGovImportLogSearchCriteria.class))
                ).thenReturn(logEntries);
    }
    
    @Test
    public void testExecute() {
        assertEquals("success", ctGovImportLogAction.execute());
    }
    
    @Test    
    public void testQuery() throws PAException {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DATE, 3);
        Date endDate = calendar.getTime();
        CTGovImportLogSearchCriteria searchCriteria = new CTGovImportLogSearchCriteria();
        ctGovImportLogAction.setLogsOnOrAfter(dateFormat.format(startDate));
        ctGovImportLogAction.setLogsOnOrBefore(dateFormat.format(endDate));
        searchCriteria.setAction("Update");
        searchCriteria.setImportStatus("Failure");
        searchCriteria.setNciIdentifier("NCI");
        searchCriteria.setNctIdentifier("NCT");
        searchCriteria.setUserCreated("User");
        searchCriteria.setOfficialTitle("Title");
        searchCriteria.setPendingAdminAcknowledgment(false);
        searchCriteria.setPendingScientificAcknowledgment(false);
        searchCriteria.setPerformedAdminAcknowledgment(false);
        searchCriteria.setPerformedScientificAcknowledgment(false);
        ctGovImportLogAction.setSearchCriteria(searchCriteria);
        assertEquals("success", ctGovImportLogAction.query());
        assertNotNull(ctGovImportLogAction.getAllCtGovImportLogs());   
        calendar.add(Calendar.DATE, -5);
        endDate = calendar.getTime();
        ctGovImportLogAction.setLogsOnOrBefore(dateFormat.format(endDate));
        assertEquals("error", ctGovImportLogAction.query());         
        calendar.add(Calendar.DATE, 8);
        endDate = calendar.getTime();
        ctGovImportLogAction.setLogsOnOrBefore(dateFormat.format(endDate));
        when(ctGovSyncServiceLocal.getLogEntries(any(CTGovImportLogSearchCriteria.class))
                ).thenThrow(new PAException());
        assertEquals("error", ctGovImportLogAction.query());
    }    
    
    @Test
    public void testShowDetailsPopUp() throws PAException {
        ctGovImportLogAction.setNctId("NCT");
        assertEquals("details", ctGovImportLogAction.showDetailspopup());
        assertNotNull(ctGovImportLogAction.getNctCtGovImportLogs());
        when(ctGovSyncServiceLocal.getLogEntries(any(CTGovImportLogSearchCriteria.class))
                ).thenThrow(new PAException());
        assertEquals("error", ctGovImportLogAction.showDetailspopup());        
    }
}
