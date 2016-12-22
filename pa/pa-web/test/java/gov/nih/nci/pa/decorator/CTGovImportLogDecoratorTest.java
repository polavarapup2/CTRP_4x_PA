/**
 *
 */
package gov.nih.nci.pa.decorator;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.action.AbstractPaActionTest;
import gov.nih.nci.pa.domain.CTGovImportLog;
import gov.nih.nci.pa.domain.StudyInbox;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.search.CTGovImportLogSearchCriteria;
import gov.nih.nci.pa.service.util.CTGovSyncServiceLocal;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author dkrylov
 * 
 */
public class CTGovImportLogDecoratorTest extends AbstractPaActionTest {

    CTGovImportLogDecorator decor;
    CTGovImportLog log;

    @Before
    public void setUp() throws PAException {

        log = new CTGovImportLog();
        log.setAction("Update");
        log.setAdmin(true);
        log.setDateCreated(new Date());
        log.setImportStatus("Success");
        log.setNciID("NCI-2014-00000001");
        log.setNctID("NCT134895734897");
        log.setReviewRequired(true);
        log.setScientific(true);
        log.setTitle("The trial title");
        log.setUserCreated("dkrylov");

        CTGovSyncServiceLocal serviceLocal = mock(CTGovSyncServiceLocal.class);
        when(
                serviceLocal
                        .getLogEntries(any(CTGovImportLogSearchCriteria.class)))
                .thenReturn(Arrays.asList(log));

        decor = new CTGovImportLogDecorator(log, serviceLocal);

    }

    @Test
    public void testNoPending() throws PAException {
        StudyInbox si = new StudyInbox();
        si.setAdmin(true);
        si.setAdminCloseDate(new Timestamp(System.currentTimeMillis()));
        si.setScientific(true);
        si.setScientificCloseDate(new Timestamp(System.currentTimeMillis()));
        log.setStudyInbox(si);
        assertEquals(CTGovImportLog.NO_ACKNOWLEDGEMENT, decor.getAckPending());
    }

    @Test
    public void testAdminPending() throws PAException {
        StudyInbox si = new StudyInbox();
        si.setAdmin(true);
        log.setStudyInbox(si);
        assertEquals(CTGovImportLog.ADMIN_ACKNOWLEDGMENT, decor.getAckPending());
    }

    @Test
    public void testSciPending() throws PAException {
        StudyInbox si = new StudyInbox();
        si.setScientific(true);
        log.setStudyInbox(si);
        assertEquals(CTGovImportLog.SCIENTIFIC_ACKNOWLEDGEMENT,
                decor.getAckPending());
    }

    @Test
    public void testAdminAndSciPending() throws PAException {
        StudyInbox si = new StudyInbox();
        si.setScientific(true);
        si.setAdmin(true);
        log.setStudyInbox(si);
        assertEquals(CTGovImportLog.ADMIN_AND_SCIENTIFIC_ACKNOWLEDGEMENT,
                decor.getAckPending());
    }

    @Test
    public void testAdminPerformed() throws PAException {
        StudyInbox si = new StudyInbox();
        si.setAdmin(true);
        si.setAdminCloseDate(new Timestamp(System.currentTimeMillis()));
        log.setStudyInbox(si);
        assertEquals(CTGovImportLog.ADMIN_ACKNOWLEDGMENT,
                decor.getAckPerformed());
    }

    @Test
    public void testSciPerformed() throws PAException {
        StudyInbox si = new StudyInbox();
        si.setScientific(true);
        si.setScientificCloseDate(new Timestamp(System.currentTimeMillis()));
        log.setStudyInbox(si);
        assertEquals(CTGovImportLog.SCIENTIFIC_ACKNOWLEDGEMENT,
                decor.getAckPerformed());
    }

    @Test
    public void testAdminAndSciPerformed() throws PAException {
        StudyInbox si = new StudyInbox();
        si.setAdmin(true);
        si.setAdminCloseDate(new Timestamp(System.currentTimeMillis()));
        si.setScientific(true);
        si.setScientificCloseDate(new Timestamp(System.currentTimeMillis()));
        log.setStudyInbox(si);
        assertEquals(CTGovImportLog.ADMIN_AND_SCIENTIFIC_ACKNOWLEDGEMENT,
                decor.getAckPerformed());
    }

    @Test
    public void testNoPerformed() throws PAException {
        StudyInbox si = new StudyInbox();
        si.setScientific(true);
        si.setAdmin(true);
        log.setStudyInbox(si);
        assertEquals(CTGovImportLog.NO_ACKNOWLEDGEMENT, decor.getAckPerformed());
    }

    @Test
    public void testPassThroughGetters() {
        assertEquals(decor.getAction(), log.getAction());
        assertEquals(decor.getAdmin(), log.getAdmin());
        assertEquals(decor.getDateCreated(), log.getDateCreated());
        assertEquals(decor.getImportStatus(), log.getImportStatus());
        assertEquals(decor.getNciID(), log.getNciID());
        assertEquals(decor.getNctID(), log.getNctID());
        assertEquals(decor.getReviewRequired(), log.getReviewRequired());
        assertEquals(decor.getScientific(), log.getScientific());
        assertEquals(decor.getTitle(), log.getTitle());
        assertEquals(decor.getUserCreated(), log.getUserCreated());
    }

}
