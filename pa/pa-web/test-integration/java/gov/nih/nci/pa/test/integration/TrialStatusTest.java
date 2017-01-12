package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.test.integration.support.Batch;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

/**
 * Tests for trial status transitions.
 * 
 * @author dkrylov
 */
@Batch(number = 1)
public class TrialStatusTest extends AbstractTrialStatusTest {

    @SuppressWarnings("deprecation")
    @Test
    public void testPrimaryCompletionDateOfTypeN_A() throws SQLException,
            InterruptedException, IOException {
        String dcpID = RandomStringUtils.randomAlphabetic(10);
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);
        clickAndWait("link=Trial Status");

        // Non-empty PCD cannot be N/A
        s.click("primaryCompletionDateTypeN/A");
        clickAndWait("link=Save");
        assertTrue(s
                .isTextPresent("When the Primary Completion Date Type is set to 'N/A', the Primary Completion Date must be null."));
        assertTrue(s
                .isTextPresent("Only a DCP trial can have a Primary Completion Date Type equals to 'N/A'."));
        clickAndWait("link=Trial Status");

        // Only DCP trial can have empty PCD with the type of N/A.
        s.click("primaryCompletionDateTypeN/A");
        s.type("primaryCompletionDate", "");
        clickAndWait("link=Save");
        assertFalse(s
                .isTextPresent("When the Primary Completion Date Type is set to 'N/A', the Primary Completion Date must be null."));
        assertTrue(s
                .isTextPresent("Only a DCP trial can have a Primary Completion Date Type equals to 'N/A'."));

        // DCP trial is allowed to have N/A PCD.
        clickAndWait("link=General Trial Details");
        s.select("otherIdentifierType", "DCP Identifier");
        s.type("otherIdentifierOrg", dcpID);
        clickAndWait("id=otherIdbtnid");
        waitForTextToAppear(By.className("confirm_msg"),
                "Identifier added to the trial", 15);
        clickAndWait("link=Trial Status");
        s.click("primaryCompletionDateTypeN/A");
        s.type("primaryCompletionDate", "");
        clickAndWait("link=Save");
        assertTrue(s.isTextPresent("Message. Record Updated."));
        clickAndWait("link=Trial Status");
        assertEquals("", s.getValue("primaryCompletionDate"));
        assertTrue(driver.findElement(By.id("primaryCompletionDateTypeN/A"))
                .isSelected());

        // Attempting to set an empty PCD to Actual or Anticipated is an error.
        ensureEmptyPcdNotAllowedForActualOrAnticipated();

        // N/A PCD must not result in an abstraction validation error for a DCP
        // trial.
        ensureNoAbstractionValidationErrorsRelatedToPCD();

        // Ensure TSR properly reflects the N/A PCD.
        checkTsr();

        // Now remove DCP ID and ensure validation errors start popping up.
        clickAndWait("link=General Trial Details");
        ((JavascriptExecutor) driver)
                .executeScript("deleteOtherIdentifierRow('2');");
        waitForTextToAppear(By.className("confirm_msg"),
                "Identifier deleted from the trial", 15);

        // Abstraction validation now must have an error.
        clickAndWait("link=Abstraction Validation");
        assertTrue(s
                .isTextPresent("Only a DCP trial can have a Primary Completion Date Type equals to 'N/A'."));
        assertTrue(s
                .isTextPresent("Select Trial Status from Administrative Data menu."));

        // Unable to save on Trial Status screen.
        clickAndWait("link=Trial Status");
        clickAndWait("link=Save");
        assertTrue(s
                .isTextPresent("Only a DCP trial can have a Primary Completion Date Type equals to 'N/A'."));
        ensureEmptyPcdNotAllowedForActualOrAnticipated();

        // Now switch to Non-interventional trial with CtGov=false. This is a
        // prerequisite for having empty PCD.
        clickAndWait("link=Design Details");
        s.select("study", "Non-Interventional");
        waitForPageToLoad();
        clickAndWait("link=General Trial Details");
        assertTrue(driver.findElement(By.id("xmlRequiredfalse")).isSelected());

        // Now can save an empty PCD at all.
        clickAndWait("link=Trial Status");
        clickAndWait("link=Save");
        assertTrue(s
                .isTextPresent("Only a DCP trial can have a Primary Completion Date Type equals to 'N/A'."));
        s.click("primaryCompletionDateTypeAnticipated");
        clickAndWait("link=Save");
        assertTrue(s.isTextPresent("Message. Record Updated."));
        ensureNoAbstractionValidationErrorsRelatedToPCD();
    }

    /**
     * 
     */
    @SuppressWarnings("deprecation")
    private void ensureNoAbstractionValidationErrorsRelatedToPCD() {
        clickAndWait("link=Abstraction Validation");
        assertFalse(s.isTextPresent("PrimaryCompletionDate must be Entered"));
        assertFalse(s
                .isTextPresent("PrimaryCompletionDateType must be Entered"));
        assertFalse(s
                .isTextPresent("Only a DCP trial can have a Primary Completion Date Type equals to 'N/A'."));
    }

    /**
     * 
     */
    @SuppressWarnings("deprecation")
    private void ensureEmptyPcdNotAllowedForActualOrAnticipated() {
        s.click("primaryCompletionDateTypeActual");
        clickAndWait("link=Save");
        assertTrue(s
                .isTextPresent("Primary Completion Date is required, unless this is a DCP trial and the date type is set to N/A."));
        s.click("primaryCompletionDateTypeAnticipated");
        clickAndWait("link=Save");
        assertTrue(s
                .isTextPresent("Primary Completion Date is required, unless this is a DCP trial and the date type is set to N/A."));
    }

    /**
     * @throws InterruptedException
     * @throws IOException
     */
    @SuppressWarnings("deprecation")
    private void checkTsr() throws InterruptedException, IOException {
        if (!isPhantomJS() && !SystemUtils.IS_OS_LINUX) {
            s.click("link=View TSR");
            File tsr = waitForTsrDownload();
            assertTrue(tsr.exists());
            final String tsrContent = FileUtils.readFileToString(tsr, "UTF-8");
            tsr.deleteOnExit();

            System.out.println("TSR Path: " + tsr.getAbsolutePath());
            System.out.println("TSR Size: " + tsr.length());
            System.out.println("TSR Content:\r\n" + tsrContent);

            assertTrue(tsrContent
                    .matches("(?s)^.*?Primary Completion Date\\\\cell.*?N/A\\\\cell.+$"));
            tsr.delete();
        }
    }

    private File waitForTsrDownload() throws InterruptedException {
        long stamp = System.currentTimeMillis();
        while (FileUtils.listFiles(downloadDir, new String[] { "rtf" }, false)
                .isEmpty() && System.currentTimeMillis() - stamp < 1000 * 30) {
            Thread.sleep(1000);
        }
        Thread.sleep(10000);

        System.out.println("List of TSR files in the download dir:");
        for (File file : (List<File>) FileUtils.listFiles(downloadDir,
                new String[] { "rtf" }, false)) {
            System.out.println(file.getAbsolutePath() + ", size "
                    + file.length());
        }

        return (File) FileUtils
                .listFiles(downloadDir, new String[] { "rtf" }, false)
                .iterator().next();
    }

    @Test
    public void testAbstractionValidationWithErrors() throws SQLException {
        TrialInfo trial = createTrialAndAccessStatusPage("admin-ci");

        // Adding In Review next to Approved is an error.
        // Check-out the trial for Admin abstraction under the SuperAbstractor's
        // name, and,
        insertStatus("In Review", today, "", "New status");
        // Close History
        clickAndWait("xpath=//span[normalize-space(text())='Cancel']");
        driver.switchTo().defaultContent();

        selectTrial(false, trial);
        clickAndWait("link=Abstraction Validation");
        assertTrue(selenium
                .isElementPresent("xpath=//td[text()='Trial status transition errors were found.']"));
        assertTrue(selenium
                .isElementPresent("xpath=//td[text()='Select Trial Status from Administrative Data menu, then click History.']"));

    }

    @Test
    public void testAbstractionValidationWithWarnings() throws SQLException {
        TrialInfo trial = createTrialAndAccessStatusPage("admin-ci");

        insertStatus("Temporarily Closed to Accrual", today,
                "Temporarily Closed to Accrual", "New status");
        // Close History
        clickAndWait("xpath=//span[normalize-space(text())='Cancel']");
        driver.switchTo().defaultContent();

        selectTrial(false, trial);

        clickAndWait("link=Abstraction Validation");
        assertTrue(selenium
                .isElementPresent("xpath=//td[text()='Trial status transition warnings were found.']"));
        assertTrue(selenium
                .isElementPresent("xpath=//td[text()='Select Trial Status from Administrative Data menu, then click History.']"));

    }

    /**
     * Tests the standard trial transitions from In Review to Complete, with
     * stops at Approved, Active, Closed to Accrual & Closed to Accrual and
     * Intervention.
     * 
     * @throws SQLException
     */
    @Test
    public void testStandardTrialStatusTransitions() throws SQLException {
        TrialInfo trial = createSubmittedTrial();

        loginAsAdminAbstractor();
        searchSelectAndAcceptTrial(trial.title, true, false);

        clickAndWait("link=Trial Status");
        changeStatus("Approved", false, false, null);
        changeStatus("Active", true, false, null);
        changeStatus("Closed to Accrual", false, false, null);
        changeStatus("Closed to Accrual and Intervention", false, false, null);
        changeStatus("Complete", false, true, null);
    }

    /**
     * Tests going from In Review to Active to Complete.
     * 
     * @throws SQLException
     */
    @Test
    public void testSkippedCompleteStatus() throws SQLException {
        TrialInfo trial = createSubmittedTrial();

        loginAsAdminAbstractor();
        searchSelectAndAcceptTrial(trial.title, true, false);

        clickAndWait("link=Trial Status");
        changeStatus("Active", true, false, null);
        changeStatus("Complete", false, true, null);
    }

    /**
     * Tests going from In Review to Active to Administratively Complete.
     * 
     * @throws SQLException
     */
    @Test
    public void testSkippedAdministrativelyCompleteStatus() throws SQLException {
        TrialInfo trial = createSubmittedTrial();

        loginAsAdminAbstractor();
        searchSelectAndAcceptTrial(trial.title, true, false);

        clickAndWait("link=Trial Status");
        changeStatus("Active", true, false, null);
        changeStatus("Administratively Complete", false, true,
                "Administratively Complete Reason.");
    }

    @SuppressWarnings("deprecation")
    private void changeStatus(String statusName, boolean startDateActual,
            boolean completionDateActual, String statusReason) {
        selenium.select("id=currentTrialStatus", "label=" + statusName);

        if (startDateActual) {
            selenium.type("id=startDate", yesterday);
            selenium.click("id=startDateTypeActual");
        }

        if (completionDateActual) {
            selenium.click("id=primaryCompletionDateTypeActual");
            selenium.type("id=primaryCompletionDate", today);
        }

        if (StringUtils.isNotEmpty(statusReason)) {
            selenium.type("id=statusReason", statusReason);
        }
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Record Updated"));
    }

    @Test
    public void testStandardTrialStatusTransitionsForScientificAbs()
            throws SQLException {
        TrialInfo trial = createSubmittedTrial();

        loginAsScientificAbstractor();
        searchSelectAndAcceptTrial(trial.title, false, true);
        clickAndWait("link=Trial Status");
        changeStatus("Approved", false, false, null);
        changeStatus("Active", true, false, null);
        changeStatus("Closed to Accrual", false, false, null);
        changeStatus("Closed to Accrual and Intervention", false, false, null);
        changeStatus("Complete", false, true, null);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testTrialHistoryForScientificAbs() throws SQLException {
        TrialInfo trial = createSubmittedTrial();

        loginAsScientificAbstractor();
        searchSelectAndAcceptTrial(trial.title, false, true);
        clickAndWait("link=Trial Status");
        clickAndWait("link=History");
        assertTrue(selenium.isTextPresent("Status History"));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testTrialStatusHistoryBeforeCheckOut() throws SQLException {
        TrialInfo trial = createSubmittedTrial();
        loginAsAdminAbstractor();
        searchSelectAndAcceptTrial(trial.title, true, false);
        checkInTrialAsAdminAbstractor();
        clickAndWait("link=Trial Status");
        assertFalse(selenium.isTextPresent("Save"));

        clickAndWait("link=History");
        assertTrue(selenium.isTextPresent("Status History"));
        assertFalse(selenium.isTextPresent("Add New Status"));
        assertFalse(selenium.isTextPresent("Validate Status Transactions"));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSuperAbstractorLogic() throws SQLException {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);
        clickAndWait("link=Trial Status");

        // Move to Active: no errors.
        selenium.select("id=currentTrialStatus", "label=Active");
        clickAndWait("link=Save");

        assertTrue(selenium.isTextPresent("Record Updated"));
        assertTrue(driver.findElements(By.className("error_msg")).isEmpty());
        assertEquals("ACTIVE", getCurrentTrialStatus(trial).statusCode);

        // Move to Withdrawn produces an error: bad transition.
        selenium.select("id=currentTrialStatus", "label=Approved");
        clickAndWait("link=Save");

        assertTrue(selenium.isTextPresent("Record Updated"));
        assertTrue(selenium
                .isTextPresent("Status Transition Errors were found. Please use the History button to review and make corrections. Trial record cannot be checked-in until all Status Transition Errors have been resolved."));
        assertEquals("APPROVED", getCurrentTrialStatus(trial).statusCode);

        // Verify pop-up (Slide 24).
        waitForElementById("displaySuAbstractorAutoCheckoutMessage", 5);
        assertTrue(selenium
                .isVisible("id=displaySuAbstractorAutoCheckoutMessage"));
        assertEquals(
                "The system has checked-out this trial under your name because Trial Status Transition errors were found. Trial record cannot be checked-in until all Status Transition Errors have been resolved. Please use the Trial Status History button to review and make corrections, or Cancel to dismiss this message.",
                selenium.getText("id=displaySuAbstractorAutoCheckoutMessage")
                        .trim());
        assertEquals("Trial Status Validation",
                selenium.getText("class=ui-dialog-title").trim());
        // Verify History button
        selenium.click("xpath=//button/span[normalize-space(text())='Trial Status History']");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        waitForElementById("row", 5);
        selenium.click("xpath=//span[normalize-space(text())='Cancel']");
        driver.switchTo().defaultContent();

        clickAndWait("link=Check-Out History");
        assertEquals("Administrative",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[1]")
                        .trim());
        assertEquals("ctrpsubstractor",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[3]")
                        .trim());
        assertTrue(StringUtils.isBlank(selenium
                .getText("xpath=//table[@id='row']/tbody/tr[1]/td[4]")));

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSuperAbstractorLogicNoAutoCheckOutIfAlreadyCheckedOut()
            throws SQLException {
        TrialInfo trial = createAcceptedTrial();
        loginAsAdminAbstractor();
        searchAndSelectTrial(trial.title);
        checkOutTrialAsAdminAbstractor();
        logoutUser();

        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);
        clickAndWait("link=Trial Status");

        // Move to Withdrawn produces an error: bad transition.
        selenium.select("id=currentTrialStatus", "label=Closed to Accrual");
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Record Updated"));
        assertTrue(selenium
                .isTextPresent("Status Transition Errors and Warnings were found. Please use the History button to review and make corrections. Trial record cannot be checked-in until all Status Transition Errors have been resolved."));

        // Verify pop-up (Slide 24).
        assertFalse(selenium
                .isVisible("id=displaySuAbstractorAutoCheckoutMessage"));
        clickAndWait("link=Check-Out History");
        assertEquals("Administrative",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[1]")
                        .trim());
        assertEquals("admin-ci",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[3]")
                        .trim());

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testTransitionValidationService() throws SQLException {
        TrialInfo trial = createAcceptedTrial();
        loginAsAdminAbstractor();
        searchAndSelectTrial(trial.title);
        checkOutTrialAsAdminAbstractor();
        clickAndWait("link=Trial Status");

        // Merely entering a comment should not affect the status history.
        selenium.type("additionalComments", "Just a comment");
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Record Updated"));
        clickAndWait("link=Trial Status");
        assertEquals("Just a comment", selenium.getValue("additionalComments"));
        List<TrialStatus> hist = getTrialStatusHistory(trial);
        assertEquals(2, hist.size());
        assertEquals("APPROVED", hist.get(1).statusCode);
        assertEquals("Just a comment", hist.get(1).comments);

        // Should be unable to move to a status with a future date
        TrialStatus statusBefore = getCurrentTrialStatus(trial);
        selenium.type("statusDate", tomorrow);
        selenium.select("id=currentTrialStatus", "label=Active");
        clickAndWait("link=Save");
        assertTrue(selenium
                .isTextPresent("Current Trial Status Date cannot be in the future."));
        assertEquals(statusBefore, getCurrentTrialStatus(trial));

        // Should be unable to move to a status with a past date
        clickAndWait("link=Trial Status");
        statusBefore = getCurrentTrialStatus(trial);
        selenium.type("statusDate", yesterday);
        selenium.select("id=currentTrialStatus", "label=Active");
        clickAndWait("link=Save");
        assertTrue(selenium
                .isTextPresent("New current status date should be bigger/same as old date."));
        assertEquals(statusBefore, getCurrentTrialStatus(trial));

        // Active status cannot precede trial start
        clickAndWait("link=Trial Status");
        statusBefore = getCurrentTrialStatus(trial);
        selenium.type("statusDate", "04/15/2013");
        selenium.select("id=currentTrialStatus", "label=Active");
        clickAndWait("link=Save");
        assertTrue(selenium
                .isTextPresent("If Current Trial Status is Active, Trial Start Date must be Actual and same as or smaller than Current Trial Status Date."));
        assertEquals(statusBefore, getCurrentTrialStatus(trial));

        // Complete status past trial end date.
        clickAndWait("link=Trial Status");
        statusBefore = getCurrentTrialStatus(trial);
        selenium.type("statusDate", "04/17/2018");
        selenium.select("id=currentTrialStatus", "label=Complete");
        clickAndWait("link=Save");
        assertTrue(selenium
                .isTextPresent("If Current Trial Status is Completed, Primary Completion Date must be Actual."));
        assertTrue(selenium
                .isTextPresent("Current Trial Status Date cannot be in the future."));
        assertEquals(statusBefore, getCurrentTrialStatus(trial));

        // Move to Active: no errors.
        clickAndWait("link=Trial Status");
        selenium.select("id=currentTrialStatus", "label=Active");
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Record Updated"));
        assertTrue(driver.findElements(By.className("error_msg")).isEmpty());
        assertEquals("ACTIVE", getCurrentTrialStatus(trial).statusCode);

        // Move to Closed to Accrual and Intervention: a warning due to missing
        // optional status.
        clickAndWait("link=Trial Status");
        selenium.select("id=currentTrialStatus",
                "label=Closed to Accrual and Intervention");
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Record Updated"));
        assertTrue(selenium
                .isTextPresent("Status Transition Warnings were found. Please use the History button to review and make corrections. Trial record cannot be checked-in until all Status Transition Errors have been resolved"));
        assertEquals("CLOSED_TO_ACCRUAL_AND_INTERVENTION",
                getCurrentTrialStatus(trial).statusCode);

        // Move to Withdrawn produces an error: bad transition.
        clickAndWait("link=Trial Status");
        selenium.select("id=currentTrialStatus", "label=Withdrawn");
        clickAndWait("link=Save");
        assertTrue(selenium
                .isTextPresent("A reason must be entered when the study status is set to Withdrawn."));
        selenium.type("statusReason", "Just testing.");
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Record Updated"));
        assertTrue(selenium
                .isTextPresent("Status Transition Errors and Warnings were found. Please use the History button to review and make corrections. Trial record cannot be checked-in until all Status Transition Errors have been resolved."));
        assertEquals("WITHDRAWN", getCurrentTrialStatus(trial).statusCode);

    }

}
