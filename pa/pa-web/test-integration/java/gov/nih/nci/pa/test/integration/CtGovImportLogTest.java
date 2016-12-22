package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.test.integration.support.Batch;

import org.junit.Test;

/**
 * @author dkrylov
 * 
 */
@Batch(number = 1)
public class CtGovImportLogTest extends AbstractPaSeleniumTest {

    @Test
    public void testReset() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        logInAndAccessTrialLog(trial);

        selenium.type("id=officialTitle", "Test");
        selenium.type("id=nciIdentifier", "Test");
        selenium.type("id=nctIdentifier", "Test");
        selenium.type("id=userCreated", "Test");
        selenium.type("id=logsOnOrAfter", "Test");
        selenium.type("id=logsOnOrBefore", "Test");
        selenium.select("id=importStatus", "Failure");
        selenium.select("id=action", "Update");
        selenium.check("id=pendingAdminAcknowledgment");
        selenium.check("id=pendingScientificAcknowledgment");
        selenium.check("id=performedAdminAcknowledgment");
        selenium.check("id=performedScientificAcknowledgment");

        selenium.click("link=Reset");
        assertEquals("", selenium.getValue("id=officialTitle"));
        assertEquals("", selenium.getValue("id=nciIdentifier"));
        assertEquals("", selenium.getValue("id=nctIdentifier"));
        assertEquals("", selenium.getValue("id=userCreated"));
        assertEquals("", selenium.getValue("id=logsOnOrAfter"));
        assertEquals("", selenium.getValue("id=logsOnOrBefore"));
        assertEquals("", selenium.getValue("id=importStatus"));
        assertEquals("", selenium.getValue("id=action"));

        assertFalse(selenium.isChecked("id=pendingAdminAcknowledgment"));
        assertFalse(selenium.isChecked("id=pendingScientificAcknowledgment"));
        assertFalse(selenium.isChecked("id=performedAdminAcknowledgment"));
        assertFalse(selenium.isChecked("id=performedScientificAcknowledgment"));

    }

    @Test
    public void testSearchAdminAndScientificPendingAndPerformed()
            throws Exception {
        TrialInfo trial = createAcceptedTrial();
        Number studyInboxID = createStudyInbox(trial);
        Number ctgovLogEntryID = createCtGovImportLogEntry(trial, studyInboxID);
        makeAdminPending(studyInboxID, ctgovLogEntryID);
        makeAdminPerformed(studyInboxID, trial.csmUserID);
        makeScientificPending(studyInboxID, ctgovLogEntryID);
        makeScientificPerformed(studyInboxID, trial.csmUserID);

        studyInboxID = createStudyInbox(trial);
        ctgovLogEntryID = createCtGovImportLogEntry(trial, studyInboxID);
        makeAdminPending(studyInboxID, ctgovLogEntryID);
        makeScientificPending(studyInboxID, ctgovLogEntryID);

        logInAndAccessTrialLog(trial);
        populateSearchCriteriaFromTrial(trial);
        selenium.check("id=pendingAdminAcknowledgment");
        selenium.check("id=pendingScientificAcknowledgment");
        selenium.check("id=performedAdminAcknowledgment");
        selenium.check("id=performedScientificAcknowledgment");
        selenium.click("link=Display Log");
        assertTrue(selenium.isTextPresent("One item found."));
        assertTrue(selenium.isElementPresent("xpath=//table[@id='row']"));
        verifyMainTable(trial, "Admin & Scientific", "Admin & Scientific");

    }

    @Test
    public void testSearchNoAcknowledgments() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        Number studyInboxID = createStudyInbox(trial);
        Number ctgovLogEntryID = createCtGovImportLogEntry(trial, studyInboxID);

        logInAndAccessTrialLog(trial);
        populateSearchCriteriaFromTrial(trial);
        selenium.click("link=Display Log");
        assertTrue(selenium.isTextPresent("One item found."));
        assertTrue(selenium.isElementPresent("xpath=//table[@id='row']"));

        selenium.click("link=Reset");
        populateSearchCriteriaFromTrial(trial);
        selenium.check("id=pendingAdminAcknowledgment");
        selenium.click("link=Display Log");
        assertTrue(selenium.isTextPresent("No log entries found"));

        selenium.click("link=Reset");
        populateSearchCriteriaFromTrial(trial);
        selenium.check("id=pendingScientificAcknowledgment");
        selenium.click("link=Display Log");
        assertTrue(selenium.isTextPresent("No log entries found"));

        selenium.click("link=Reset");
        populateSearchCriteriaFromTrial(trial);
        selenium.check("id=performedAdminAcknowledgment");
        selenium.click("link=Display Log");
        assertTrue(selenium.isTextPresent("No log entries found"));

        selenium.click("link=Reset");
        populateSearchCriteriaFromTrial(trial);
        selenium.check("id=performedScientificAcknowledgment");
        selenium.click("link=Display Log");
        assertTrue(selenium.isTextPresent("No log entries found"));

    }

    /**
     * @param trial
     */
    private void populateSearchCriteriaFromTrial(TrialInfo trial) {
        selenium.type("id=officialTitle", trial.title);
        selenium.type("id=nciIdentifier", trial.nciID);
        selenium.type("id=nctIdentifier", trial.nciID);
        selenium.type("id=userCreated", "ClinicalTrials.gov Import");
        selenium.type("id=logsOnOrAfter", "01/01/2000");
        selenium.type("id=logsOnOrBefore", "01/01/2000");
        selenium.select("id=importStatus", "Success");
        selenium.select("id=action", "Update");
    }

    @Test
    public void testViewLogNoAcknowledgments() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        Number studyInboxID = createStudyInbox(trial);
        Number ctgovLogEntryID = createCtGovImportLogEntry(trial, studyInboxID);

        logInAndAccessTrialLog(trial);
        verifyMainTable(trial, "No", "No");
        verifyPopUpDetailsTable(trial, 1, "01/01/2000 12:00 AM", "Update",
                "No", "", "Success");

    }

    @Test
    public void testViewLogAdminPending() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        Number studyInboxID = createStudyInbox(trial);
        Number ctgovLogEntryID = createCtGovImportLogEntry(trial, studyInboxID);
        makeAdminPending(studyInboxID, ctgovLogEntryID);

        logInAndAccessTrialLog(trial);
        verifyMainTable(trial, "Admin", "No");
        verifyPopUpDetailsTable(trial, 1, "01/01/2000 12:00 AM", "Update",
                "Admin", "", "Success");

    }

    @Test
    public void testViewLogAdminPerformed() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        Number studyInboxID = createStudyInbox(trial);
        Number ctgovLogEntryID = createCtGovImportLogEntry(trial, studyInboxID);
        makeAdminPending(studyInboxID, ctgovLogEntryID);
        makeAdminPerformed(studyInboxID, trial.csmUserID);

        logInAndAccessTrialLog(trial);
        verifyMainTable(trial, "No", "Admin");
        verifyPopUpDetailsTable(trial, 1, "01/02/2000 12:00 AM",
                "Acknowledgment", "", "Admin", "");
        verifyPopUpDetailsTable(trial, 2, "01/01/2000 12:00 AM", "Update",
                "Admin", "", "Success");

    }

    @Test
    public void testViewLogScientificPerformed() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        Number studyInboxID = createStudyInbox(trial);
        Number ctgovLogEntryID = createCtGovImportLogEntry(trial, studyInboxID);
        makeScientificPending(studyInboxID, ctgovLogEntryID);
        makeScientificPerformed(studyInboxID, trial.csmUserID);

        logInAndAccessTrialLog(trial);
        verifyMainTable(trial, "No", "Scientific");
        verifyPopUpDetailsTable(trial, 1, "01/02/2000 12:00 AM",
                "Acknowledgment", "", "Scientific", "");
        verifyPopUpDetailsTable(trial, 2, "01/01/2000 12:00 AM", "Update",
                "Scientific", "", "Success");

    }

    @Test
    public void testViewLogAdminAndScientificPerformed() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        Number studyInboxID = createStudyInbox(trial);
        Number ctgovLogEntryID = createCtGovImportLogEntry(trial, studyInboxID);
        makeAdminPending(studyInboxID, ctgovLogEntryID);
        makeAdminPerformed(studyInboxID, trial.csmUserID);
        makeScientificPending(studyInboxID, ctgovLogEntryID);
        makeScientificPerformed(studyInboxID, trial.csmUserID);

        logInAndAccessTrialLog(trial);
        verifyMainTable(trial, "No", "Admin & Scientific");
        verifyPopUpDetailsTable(trial, 1, "01/02/2000 12:00 AM",
                "Acknowledgment", "", "Admin", "");
        verifyPopUpDetailsTable(trial, 2, "01/02/2000 12:00 AM",
                "Acknowledgment", "", "Scientific", "");
        verifyPopUpDetailsTable(trial, 3, "01/01/2000 12:00 AM", "Update",
                "Admin & Scientific", "", "Success");

    }

    @Test
    public void testViewLogAdminAndScientificPendingAndPerformed()
            throws Exception {
        TrialInfo trial = createAcceptedTrial();
        Number studyInboxID = createStudyInbox(trial);
        Number ctgovLogEntryID = createCtGovImportLogEntry(trial, studyInboxID);
        makeAdminPending(studyInboxID, ctgovLogEntryID);
        makeAdminPerformed(studyInboxID, trial.csmUserID);
        makeScientificPending(studyInboxID, ctgovLogEntryID);
        makeScientificPerformed(studyInboxID, trial.csmUserID);

        studyInboxID = createStudyInbox(trial);
        ctgovLogEntryID = createCtGovImportLogEntry(trial, studyInboxID);
        makeAdminPending(studyInboxID, ctgovLogEntryID);
        makeScientificPending(studyInboxID, ctgovLogEntryID);

        logInAndAccessTrialLog(trial);
        verifyMainTable(trial, "Admin & Scientific", "Admin & Scientific");
        verifyPopUpDetailsTable(trial, 1, "01/02/2000 12:00 AM",
                "Acknowledgment", "", "Admin", "");
        verifyPopUpDetailsTable(trial, 2, "01/02/2000 12:00 AM",
                "Acknowledgment", "", "Scientific", "");
        verifyPopUpDetailsTable(trial, 3, "01/01/2000 12:00 AM", "Update",
                "Admin & Scientific", "", "Success");
        verifyPopUpDetailsTable(trial, 4, "01/01/2000 12:00 AM", "Update",
                "Admin & Scientific", "", "Success");

    }

    @Test
    public void testViewLogScientificPending() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        Number studyInboxID = createStudyInbox(trial);
        Number ctgovLogEntryID = createCtGovImportLogEntry(trial, studyInboxID);
        makeScientificPending(studyInboxID, ctgovLogEntryID);

        logInAndAccessTrialLog(trial);
        verifyMainTable(trial, "Scientific", "No");
        verifyPopUpDetailsTable(trial, 1, "01/01/2000 12:00 AM", "Update",
                "Scientific", "", "Success");

    }

    @Test
    public void testViewLogAdminAndScientificPending() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        Number studyInboxID = createStudyInbox(trial);
        Number ctgovLogEntryID = createCtGovImportLogEntry(trial, studyInboxID);
        makeAdminPending(studyInboxID, ctgovLogEntryID);
        makeScientificPending(studyInboxID, ctgovLogEntryID);

        logInAndAccessTrialLog(trial);
        verifyMainTable(trial, "Admin & Scientific", "No");
        verifyPopUpDetailsTable(trial, 1, "01/01/2000 12:00 AM", "Update",
                "Admin & Scientific", "", "Success");

    }

    /**
     * @param trial
     */
    private void verifyPopUpDetailsTable(TrialInfo trial, int row, String date,
            String action, String ackPending, String ackPerformed, String status) {
        clickAndWaitAjax("xpath=//table[@id='row']//tr[1]//td[4]/a");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        waitForElementById("row", 15);

        assertEquals(
                "NCI Trial Identifier:",
                selenium.getText(
                        "xpath=//form//table[@class='form']//tr[1]//td[1]")
                        .trim());
        assertEquals(
                trial.nciID,
                selenium.getText(
                        "xpath=//form//table[@class='form']//tr[1]//td[2]")
                        .trim());
        assertEquals(
                "ClinicalTrials.gov Identifier:",
                selenium.getText(
                        "xpath=//form//table[@class='form']//tr[2]//td[1]")
                        .trim());
        assertEquals(
                trial.nciID,
                selenium.getText(
                        "xpath=//form//table[@class='form']//tr[2]//td[2]")
                        .trim());
        assertEquals(
                "Trial Title:",
                selenium.getText(
                        "xpath=//form//table[@class='form']//tr[3]//td[1]")
                        .trim());
        assertEquals(
                trial.title,
                selenium.getText(
                        "xpath=//form//table[@class='form']//tr[3]//td[2]")
                        .trim());

        assertEquals(
                action,
                selenium.getText(
                        "xpath=//table[@id='row']//tr[" + row + "]//td[1]")
                        .trim());
        assertEquals(
                "ClinicalTrials.gov Import",
                selenium.getText(
                        "xpath=//table[@id='row']//tr[" + row + "]//td[2]")
                        .trim());
        assertEquals(
                date,
                selenium.getText(
                        "xpath=//table[@id='row']//tr[" + row + "]//td[3]")
                        .trim());
        assertEquals(
                status,
                selenium.getText(
                        "xpath=//table[@id='row']//tr[" + row + "]//td[4]")
                        .trim());
        assertEquals(
                ackPending,
                selenium.getText(
                        "xpath=//table[@id='row']//tr[" + row + "]//td[5]")
                        .trim());
        assertEquals(
                ackPerformed,
                selenium.getText(
                        "xpath=//table[@id='row']//tr[" + row + "]//td[6]")
                        .trim());
        assertTrue(selenium
                .isElementPresent("xpath=//span[@class='export csv']"));
        assertTrue(selenium
                .isElementPresent("xpath=//span[@class='export excel']"));

        driver.switchTo().defaultContent();
        selenium.click("id=popCloseBox");
    }

    /**
     * @param trial
     */
    private void verifyMainTable(TrialInfo trial, String ackPending,
            String ackPerformed) {
        assertTrue(selenium.isTextPresent("One item found."));
        assertEquals(trial.nciID,
                selenium.getText("xpath=//table[@id='row']//tr[1]//td[1]/a")
                        .trim());
        assertEquals(trial.nciID,
                selenium.getText("xpath=//table[@id='row']//tr[1]//td[2]/a")
                        .trim());
        assertEquals(trial.title,
                selenium.getText("xpath=//table[@id='row']//tr[1]//td[3]")
                        .trim());
        assertEquals("Update",
                selenium.getText("xpath=//table[@id='row']//tr[1]//td[4]/a")
                        .trim());
        assertEquals("ClinicalTrials.gov Import",
                selenium.getText("xpath=//table[@id='row']//tr[1]//td[5]")
                        .trim());
        assertEquals("01/01/2000 12:00 AM",
                selenium.getText("xpath=//table[@id='row']//tr[1]//td[6]")
                        .trim());
        assertEquals("Success",
                selenium.getText("xpath=//table[@id='row']//tr[1]//td[7]")
                        .trim());
        assertEquals(ackPending,
                selenium.getText("xpath=//table[@id='row']//tr[1]//td[8]")
                        .trim());
        assertEquals(ackPerformed,
                selenium.getText("xpath=//table[@id='row']//tr[1]//td[9]")
                        .trim());
        assertTrue(selenium
                .isElementPresent("xpath=//span[@class='export csv']"));
        assertTrue(selenium
                .isElementPresent("xpath=//span[@class='export excel']"));
    }

    /**
     * @param trial
     */
    private void logInAndAccessTrialLog(TrialInfo trial) {
        loginAsSuperAbstractor();
        clickAndWait("id=ctGovImportLogMenuOption");
        selenium.type("id=nciIdentifier", trial.nciID);
        selenium.click("link=Display Log");
    }
}
