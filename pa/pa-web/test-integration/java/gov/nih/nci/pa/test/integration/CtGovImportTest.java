package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.test.integration.support.Batch;

import java.sql.SQLException;
import java.util.Iterator;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.junit.Test;

import com.dumbster.smtp.SmtpMessage;

/**
 * @author Denis G. Krylov
 * 
 */
@Batch(number = 1)
public class CtGovImportTest extends AbstractTrialStatusTest {

    @Test
    public void testImportNCT01721876() throws Exception {
        importAndVerify("NCT01721876");
    }
    
    @Test
    public void testImportNCT01033123() throws Exception {
        importAndVerify("NCT01033123");
        assertEquals(
                "false",
                new QueryRunner()
                        .query(connection,
                                "select expd_access_indidicator from study_protocol where nct_id='NCT01033123'",
                                new ArrayHandler())[0]
                        + "");
    }
    
    @Test
    public void testImportNCT00338442() throws Exception {
        importAndVerify("NCT00338442");
        assertEquals(
                "true",
                new QueryRunner()
                        .query(connection,
                                "select expd_access_indidicator from study_protocol where nct_id='NCT00338442'",
                                new ArrayHandler())[0]
                        + "");
    }

    @Test
    public void testImportNCT01875705() throws Exception {
        importAndVerify("NCT01875705");
    }

    @Test
    public void testImportNCT01806064() throws Exception {
        importAndVerify("NCT01806064");
    }

    @Test
    public void testImportNCT01756729() throws Exception {
        importAndVerify("NCT01756729");
    }

    @Test
    public void testImportNCT01576406() throws Exception {
        importAndVerify("NCT01576406");
    }

    @Test
    public void testImportNCT00738699() throws Exception {
        importAndVerify("NCT00738699");
    }

    @Test
    public void testImportNCT00038610() throws Exception {
        importAndVerify("NCT00038610");
    }

    @Test
    public void testImportNCT02158936() throws Exception {
        importAndVerify("NCT02158936");
    }
  //commented as part of PO-9862
    /*@SuppressWarnings({ "deprecation", "rawtypes" })
    @Test
    public void testClosingSitesEmailToSiteContacts_PO_8780() throws Exception {
        final String nctID = "NCT01326494";
        importAndVerify(nctID);

        // Set up trial.
        TrialInfo info = new TrialInfo();
        info.id = (Long) getTrialIdByNct(nctID);
        info.nciID = getLastNciId();
        info.title = "Reducing the Acute Care Burden of Childhood Asthma on Health Services in British Columbia";
        info.leadOrgID = "H10-00184";
        info.uuid = info.leadOrgID;

        acceptTrialByNciId(info.nciID, info.leadOrgID);
        changeTrialStatus(info, "ACTIVE");

        // Add site.
        logoutPA();
        selectTrialInPA(info);
        addSiteToTrial(info, "DCP", "Approved" , false);

        // Now re-import. This will trigger closing the sites automatically.
        // Intercept email.
        restartEmailServer();
        findInCtGov(nctID);
        clickAndWait("link=Import & Update Trial");
        assertTrue(selenium
                .isTextPresent("An update to trial(s) with identifiers "
                        + nctID + " and " + info.nciID
                        + " has been made successfully."));

        // Verify email.
        waitForEmailsToArrive(1);
        Iterator emailIter = server.getReceivedEmail();
        SmtpMessage email = (SmtpMessage) emailIter.next();
        String subject = email.getHeaderValues("Subject")[0];
        String to = email.getHeaderValues("To")[0];
        String body = email.getBody().replaceAll("\\s+", " ")
                .replaceAll(">\\s+", ">");
        assertEquals("ctrpsubstractor-ci@example.com", to);
        assertEquals(
                "Participating Site Status was changed for National Cancer Institute",
                subject);
        assertEquals(
                String.format(
                        "<hr><table border=\"0\"><tr><td><b>Title:</b></td><td>%1s</td></tr><tr><td><b>NCI Trial ID:</b></td><td>%2s</td></tr><tr><td><b>Lead Organization:</b></td><td>%3s</td></tr><tr><td><b>Participating Site:</b></td><td>%4s</td></tr></table><hr><p>Date: %5s</p><p>Dear %6s %7s,</p><p>The ClinicalTrials.gov record shows that the trial listed above was closed as of %8s. For consistency, the CTRP system has changed the trials status at your participating site:</p><table border=\"0\"><tr><td><b>Participating Site:</b></td><td>%9s</td></tr><tr><td><b>Old Site Status:</b></td><td>%10s</td></tr><tr><td><b>New Site Status:</b></td><td>%11s</td></tr></table><p>If you believe this is an error, or if you have additional questions about this or other CTRP topics, please contact the CTRO at ncictro@mail.nih.gov.</p><p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>",
                        info.title,
                        info.nciID,
                        "University of British Columbia",
                        "National Cancer Institute Division of Cancer Prevention",
                        today,
                        "ctrpsubstractor",
                        "CI",
                        today,
                        "National Cancer Institute Division of Cancer Prevention",
                        "Approved", "Closed to accrual")
                        .replaceAll("\\s+", " ").replaceAll(">\\s+", ">"), body);

    }

   */

    @SuppressWarnings("deprecation")
    @Test
    public void testImportDoesNotResetCtroOverride() throws Exception {
        final String nctID = "NCT01963949";
        importAndVerify(nctID);
        Number id = getTrialIdByNct(nctID);
        enableCtroOverride(id);

        clickAndWait("id=importCtGovMenuOption");
        selenium.type("id=nctID", nctID);
        clickAndWait("link=Search Studies");
        clickAndWait("link=Import & Update Trial");
        assertTrue(selenium
                .isTextPresent("An update to trial(s) with identifiers "
                        + nctID + " and " + getLastNciId()
                        + " has been made successfully"));

        clickAndWait("id=trialSearchMenuOption");
        selenium.type("id=identifier", nctID);
        selenium.select("id=identifierType", "NCT");
        clickAndWait("link=Search");
        waitForElementById("row", 30);
        assertTrue(selenium.isTextPresent("One item found"));
        clickAndWait("xpath=//table[@id='row']//tr[1]//td[1]/a");
        acceptTrial();
        clickAndWait("link=NCI Specific Information");
        // now non interventional and nci sponsored trials are not displayed
        // check if this flag is not displayed
        assertFalse(selenium.isTextPresent("Send XML to ClinicalTrials.gov?:"));

    }

    @SuppressWarnings("deprecation")
    private void importAndVerify(String nctID) throws SQLException {
        deactivateTrialByNctId(nctID);
        loginAsSuperAbstractor();
        findInCtGov(nctID);
        clickAndWait("link=Import Trial");
        assertTrue(selenium
                .isTextPresent("Trial "
                        + nctID
                        + " has been imported and registered in CTRP system successfully."));
        clickAndWait("id=trialSearchMenuOption");
        selenium.type("id=identifier", nctID);
        selenium.select("id=identifierType", "NCT");
        clickAndWait("link=Search");
        assertTrue(selenium.isTextPresent("One item found"));
        clickAndWait("xpath=//table[@id='row']//tr[1]//td[1]/a");

        assertTrue(selenium.getText("id=displaySubmitterLink").contains(
                "ClinicalTrials.gov Import"));
        assertTrue(selenium.getText("id=td_CTGOV_value").contains(nctID));

    }

    /**
     * @param nctID
     */
    @SuppressWarnings("deprecation")
    private void findInCtGov(String nctID) {
        int counter = 0;
        do {
            counter++;
            clickAndWait("id=importCtGovMenuOption");
            selenium.type("id=nctID", nctID);
            clickAndWait("link=Search Studies");
            pause(2000);
        } while (!selenium.isTextPresent("One item found.") && counter <= 3);
        assertTrue(selenium.isTextPresent("One item found."));
    }

}
