package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.test.integration.support.Batch;

import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

/**
 * Tests for trial status transitions.
 * 
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
@Batch(number = 1)
public class TrialStatusHistoryTest extends AbstractTrialStatusTest {

    @SuppressWarnings("deprecation")
    @Test
    public void testEmptyAuditLogHandledProperly_PO8663() throws SQLException {
        createTrialAndAccessStatusPage();

        // This trial was created via direct DB calls. Trial Status record has
        // no audit log info.
        // Verify never ending spinner fixed.
        selenium.click("xpath=//table[@id='row']/tbody/tr[1]/td[5]/a/img[@alt='Audit trail icon']");
        waitForElementById("audit-trail", 5);
        waitForElementToBecomeAvailable(
                By.xpath("//td[normalize-space(text())='No data available in table']"),
                10);
        pause(5000);
        assertTrue(selenium.isTextPresent("No data available in table"));
        assertFalse(selenium
                .isElementPresent("xpath=//td[@class='dataTables_empty']"));

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testHistory() throws SQLException {
        TrialInfo trial = createTrialAndAccessStatusPage();

        // Validation should have run upon entry
        assertTrue(selenium
                .isElementPresent("xpath=//div[@class='confirm_msg']"));
        assertEquals("Message. All Statuses and Status Transitions are valid.",
                selenium.getText("xpath=//div[@class='confirm_msg']").trim());

        // Verify table column headers.
        assertEquals("Status Date",
                selenium.getText("xpath=//table[@id='row']/thead/tr/th[1]")
                        .trim());
        assertEquals("Status",
                selenium.getText("xpath=//table[@id='row']/thead/tr/th[2]")
                        .trim());
        assertEquals("Comments",
                selenium.getText("xpath=//table[@id='row']/thead/tr/th[3]")
                        .trim());
        assertEquals("Validation Messages",
                selenium.getText("xpath=//table[@id='row']/thead/tr/th[4]")
                        .trim());
        assertEquals("Actions",
                selenium.getText("xpath=//table[@id='row']/thead/tr/th[5]")
                        .trim());

        // Verify initial status is present.
        verifyStatusRow(1, yesterday, "In Review", "", "");
        verifyStatusRow(2, today, "Approved", "", "");

        // Delete In Review
        deleteStatus(1);

        // Edit it back to In Review.
        editStatus(1, "In Review", yesterday, "This must be ignored",
                "Back to In Review");
        confirmSuccess("Message. Trial status record has been updated.");

        // Verify update
        verifyStatusRow(1, yesterday, "In Review", "Back to In Review", "");
        assertEquals(1, getTrialStatusHistory(trial).size());
        verifyCurrentStatusInDB(trial, DateUtils.addDays(new Date(), -1),
                "IN_REVIEW", "Back to In Review", "");

        // Should be unable to set status date in future.
        editStatus(1, "In Review", tomorrow, "",
                "Should be unable to set status date in future.");
        confirmFailure("Message. Validation Exception Current Trial Status Date cannot be in the future.");

        // Editing status history should still enforce validation between trial
        // start/end dates and status
        editStatus(1, "Active", "04/15/2013", "", "");
        confirmFailure("If Current Trial Status is Active, Trial Start Date must be Actual and same as or smaller than Current Trial Status Date.");
        editStatus(1, "Complete", today, "", "");
        confirmFailure("If Current Trial Status is Completed, Primary Completion Date must be Actual.");
        editStatus(1, "Temporarily Closed to Accrual", today, "", "");
        confirmFailure("A reason must be entered when the study status is set to Temporarily Closed to Accrual.");
        verifyCurrentStatusInDB(trial, DateUtils.addDays(new Date(), -1),
                "IN_REVIEW", "Back to In Review", "");

        // Check for HTML injection
        editStatus(1, "In Review", yesterday, "", "<script>alert(1)</script>");
        confirmSuccess("Message. Trial status record has been updated.");
        verifyStatusRow(1, yesterday, "In Review", "script>alert(1)script>", "");
        editStatus(1, "In Review", yesterday, "", "<BR>");
        confirmSuccess("Message. Trial status record has been updated.");
        verifyStatusRow(1, yesterday, "In Review", "<BR>", "");

        // Put In Review back to a known state and insert Active.
        editStatus(1, "In Review", yesterday, "", "");
        insertStatus("Active", today, "", "New status");
        confirmSuccess("Message. Trial status record has been added.");
        verifyStatusRow(2, today, "Active", "New status",
                "WARNING: Interim status [APPROVED] is missing.");
        verifyCurrentStatusInDB(trial, new Date(), "ACTIVE", "New status", "");

        // Should be unable to set status date in future.
        insertStatus("Temporarily Closed to Accrual", tomorrow, "Test", "");
        confirmFailure("Study status date cannot be in future.");

        // Other validations
        insertStatus("Temporarily Closed to Accrual", "", "Test", "");
        confirmFailure("Invalid trial status date format");
        insertStatus("Temporarily Closed to Accrual", today, "", "");
        confirmFailure("A reason must be entered when the study status is set to Temporarily Closed to Accrual");

        // Verify Why Stopped call-out tooltip.
        insertStatus("Temporarily Closed to Accrual", today, "Trial on hold",
                "");
        By by = By
                .xpath("//table[@id='row']/tbody/tr[3]/td[5]/a/img[@alt='Why Study Stopped?']");
        hover(by);
        if (isPhantomJS())
            driver.findElement(by).click();
        pause(2000);
        waitForElementToBecomeVisible(
                By.xpath("//div[@class='ui-tooltip-content' and normalize-space(text())='Why study stopped: Trial on hold']"),
                5);

        // Verify audit log.
        verifyAuditLog();

        // Test Delete
        verifyCurrentStatusInDB(trial, new Date(),
                "TEMPORARILY_CLOSED_TO_ACCRUAL", "", "Trial on hold");
        selenium.click("xpath=//table[@id='row']/tbody/tr[3]/td[5]/a/img[@alt='Delete this trial status record.']");
        waitForElementById("delete-dialog", 5);
        selenium.type("deleteComment", "");
        clickAndWait("xpath=//div/input[@value='Delete Status']");
        confirmFailure("A comment is required when deleting a status.");
        selenium.click("xpath=//table[@id='row']/tbody/tr[3]/td[5]/a/img[@alt='Delete this trial status record.']");
        selenium.type("deleteComment", "Delete Test");
        clickAndWait("xpath=//div/input[@value='Delete Status']");
        confirmSuccess("Record Deleted.");
        verifyCurrentStatusInDB(trial, new Date(), "ACTIVE", "New status", "");
        assertEquals(2, getTrialStatusHistory(trial).size());
        assertEquals(2, getDeletedTrialStatuses(trial).size());
        assertEquals("TEMPORARILY_CLOSED_TO_ACCRUAL",
                getDeletedTrialStatuses(trial).get(1).statusCode);

        // Verify Deleted Statuses Table
        assertTrue(selenium
                .isElementPresent("xpath=//h2[normalize-space(text())='Deleted Trial Status Records']"));
        assertEquals("Status Date",
                selenium.getText("xpath=//table[@id='del']/thead/tr/th[1]")
                        .trim());
        assertEquals("Status",
                selenium.getText("xpath=//table[@id='del']/thead/tr/th[2]")
                        .trim());
        assertEquals("Comments",
                selenium.getText("xpath=//table[@id='del']/thead/tr/th[3]")
                        .trim());
        assertEquals("Deleted By",
                selenium.getText("xpath=//table[@id='del']/thead/tr/th[4]")
                        .trim());
        assertEquals("Deleted On",
                selenium.getText("xpath=//table[@id='del']/thead/tr/th[5]")
                        .trim());
        verifyDeletedStatusRow(2, today, "Temporarily Closed to Accrual",
                "Delete Test", "Trial on hold");

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
        openHistory();

        // Adding In Review next to Approved is an error.
        insertStatus("In Review", today, "", "New status");

        // Verify pop-up (Slide 24).
        assertFalse(selenium
                .isVisible("id=displaySuAbstractorAutoCheckoutMessage"));
        // Close History
        clickAndWait("xpath=//span[normalize-space(text())='Cancel']");
        topWindow();

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
    public void testSuperAbstractorLogic() throws SQLException {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);
        clickAndWait("link=Trial Status");
        openHistory();

        // Adding In Review next to Approved is an error.
        // Check-out the trial for Admin abstraction under the SuperAbstractor's
        // name, and,
        insertStatus("In Review", today, "", "New status");

        // Verify pop-up (Slide 24).
        waitForElementById("displaySuAbstractorAutoCheckoutMessage", 5);
        assertTrue(selenium
                .isVisible("id=displaySuAbstractorAutoCheckoutMessage"));
        assertEquals(
                "The system has checked-out this trial under your name because Trial Status Transition errors were found. Trial record cannot be checked-in until all Status Transition Errors have been resolved. Please use the Trial Status History button to review and make corrections, or Cancel to dismiss this message.",
                selenium.getText("id=displaySuAbstractorAutoCheckoutMessage")
                        .trim());
        assertEquals(
                "Trial Status Validation",
                selenium.getText(
                        "xpath=//div[@aria-describedby='displaySuAbstractorAutoCheckoutMessage']//span[@class='ui-dialog-title']")
                        .trim());
        selenium.click("xpath=//div[@aria-describedby='displaySuAbstractorAutoCheckoutMessage']//button[@title='Close']");

        // Close History
        clickAndWait("xpath=//span[normalize-space(text())='Cancel']");
        topWindow();

        // Verify check out
        openAndWait("/pa/protected/checkOutHistory.action");
        assertEquals("Administrative",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[1]")
                        .trim());
        assertEquals("ctrpsubstractor",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[3]")
                        .trim());
        assertTrue(StringUtils.isBlank(selenium
                .getText("xpath=//table[@id='row']/tbody/tr[1]/td[4]")));

    }

    /**
     * 
     */
    @SuppressWarnings("deprecation")
    private void verifyAuditLog() {
        selenium.click("xpath=//table[@id='row']/tbody/tr[3]/td[5]/a/img[@alt='Audit trail icon']");
        waitForElementById("audit-trail", 5);
        waitForElementById("audit-trail-table_info", 15);
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='audit-trail-table']/tbody/tr[1]/td[3]"),
                15);
        assertTrue(selenium
                .getText(
                        "xpath=//table[@id='audit-trail-table']/tbody/tr[1]/td[1]")
                .trim().startsWith(today));
        assertEquals(
                "admin-ci",
                selenium.getText(
                        "xpath=//table[@id='audit-trail-table']/tbody/tr[1]/td[2]")
                        .trim());
        assertEquals(
                "Insert",
                selenium.getText(
                        "xpath=//table[@id='audit-trail-table']/tbody/tr[1]/td[3]")
                        .trim());
        assertEquals(
                "Attribute",
                selenium.getText(
                        "xpath=//table[@id='audit-trail-table']/tbody/tr[1]/td[4]/table/thead/tr/th[1]")
                        .trim());
        assertEquals(
                "Old Value",
                selenium.getText(
                        "xpath=//table[@id='audit-trail-table']/tbody/tr[1]/td[4]/table/thead/tr/th[2]")
                        .trim());
        assertEquals(
                "New Value",
                selenium.getText(
                        "xpath=//table[@id='audit-trail-table']/tbody/tr[1]/td[4]/table/thead/tr/th[3]")
                        .trim());
        assertEquals(
                "Why Study Stopped",
                selenium.getText(
                        "xpath=//table[@id='audit-trail-table']/tbody/tr[1]/td[4]/table/tbody/tr/td[1]")
                        .trim());
        assertEquals(
                "",
                selenium.getText(
                        "xpath=//table[@id='audit-trail-table']/tbody/tr[1]/td[4]/table/tbody/tr/td[2]")
                        .trim());
        assertEquals(
                "Trial on hold",
                selenium.getText(
                        "xpath=//table[@id='audit-trail-table']/tbody/tr[1]/td[4]/table/tbody/tr/td[3]")
                        .trim());
        assertEquals(
                "Status",
                selenium.getText(
                        "xpath=//table[@id='audit-trail-table']/tbody/tr[1]/td[4]/table/tbody/tr[3]/td[1]")
                        .trim());
        assertEquals(
                "",
                selenium.getText(
                        "xpath=//table[@id='audit-trail-table']/tbody/tr[1]/td[4]/table/tbody/tr[3]/td[2]")
                        .trim());
        assertEquals(
                "Temporarily Closed to Accrual",
                selenium.getText(
                        "xpath=//table[@id='audit-trail-table']/tbody/tr[1]/td[4]/table/tbody/tr[3]/td[3]")
                        .trim());
        assertEquals(
                "Status Date",
                selenium.getText(
                        "xpath=//table[@id='audit-trail-table']/tbody/tr[1]/td[4]/table/tbody/tr[4]/td[1]")
                        .trim());
        assertEquals(
                "",
                selenium.getText(
                        "xpath=//table[@id='audit-trail-table']/tbody/tr[1]/td[4]/table/tbody/tr[4]/td[2]")
                        .trim());
        assertEquals(
                today,
                selenium.getText(
                        "xpath=//table[@id='audit-trail-table']/tbody/tr[1]/td[4]/table/tbody/tr[4]/td[3]")
                        .trim());
        assertEquals(
                "Deleted",
                selenium.getText(
                        "xpath=//table[@id='audit-trail-table']/tbody/tr[1]/td[4]/table/tbody/tr[2]/td[1]")
                        .trim());
        assertEquals(
                "",
                selenium.getText(
                        "xpath=//table[@id='audit-trail-table']/tbody/tr[1]/td[4]/table/tbody/tr[2]/td[2]")
                        .trim());
        assertEquals(
                "false",
                selenium.getText(
                        "xpath=//table[@id='audit-trail-table']/tbody/tr[1]/td[4]/table/tbody/tr[2]/td[3]")
                        .trim());
        assertTrue(selenium
                .isTextPresent("Status: Temporarily Closed to Accrual"));
        driver.findElement(By.className("ui-dialog-titlebar-close")).click();
        assertFalse(selenium.isVisible("id=audit-trail"));
    }

    @SuppressWarnings("deprecation")
    private void editStatus(int row, String newCode, String newDate,
            String reason, String comment) {
        selenium.click("xpath=//table[@id='row']/tbody/tr[" + row
                + "]/td[5]/a[1]");
        waitForElementToBecomeVisible(By.id("edit-dialog"), 2);
        assertEquals("Edit Trial Status",
                selenium.getText("edit-dialog-header"));
        selenium.select("id=statusCode", "label=" + newCode);
        selenium.type("statusDate", newDate);
        selenium.type("reason", reason);
        selenium.type("comment", comment);
        selenium.click("xpath=//div[@id='edit-dialog']//input[@value='Save']");
        waitForPageToLoad();

    }

    @SuppressWarnings("deprecation")
    private void deleteStatus(int row) {
        selenium.click("xpath=//table[@id='row']/tbody/tr[" + row
                + "]/td[5]/a[2]");
        waitForElementToBecomeVisible(By.id("delete-dialog"), 2);
        selenium.type("deleteComment", "Deleting...");
        selenium.click("xpath=//div[@id='delete-dialog']//input[@value='Delete Status']");
        waitForPageToLoad();

    }

    @SuppressWarnings("deprecation")
    private void confirmFailure(String msg) {
        waitForElementToBecomeVisible(By.xpath("//div[@class='error_msg']"), 15);
        assertTrue(selenium.getText("xpath=//div[@class='error_msg']").trim()
                .contains(msg));

    }

    @SuppressWarnings("deprecation")
    private void confirmSuccess(String msg) {
        waitForElementToBecomeVisible(By.xpath("//div[@class='confirm_msg']"),
                15);
        assertTrue(selenium.getText("xpath=//div[@class='confirm_msg']").trim()
                .contains(msg));
    }

    @SuppressWarnings("deprecation")
    private void verifyCurrentStatusInDB(TrialInfo trial, Date date,
            String code, String comments, String whyStopped)
            throws SQLException {
        assertEquals(code, getCurrentTrialStatus(trial).statusCode);
        assertTrue(StringUtils.isBlank(comments) ? StringUtils
                .isBlank(getCurrentTrialStatus(trial).comments) : comments
                .equals(getCurrentTrialStatus(trial).comments));
        assertTrue(DateUtils.isSameDay(date,
                getCurrentTrialStatus(trial).statusDate));
        assertTrue(StringUtils.isBlank(whyStopped) ? StringUtils
                .isBlank(getCurrentTrialStatus(trial).whyStopped) : whyStopped
                .equals(getCurrentTrialStatus(trial).whyStopped));
    }

    @SuppressWarnings("deprecation")
    private void verifyDeletedStatusRow(int row, String date, String code,
            String comments, String whyStopped) {
        assertEquals(
                date,
                selenium.getText(
                        "xpath=//table[@id='del']/tbody/tr[" + row + "]/td[1]")
                        .trim());
        assertEquals(
                code,
                selenium.getText(
                        "xpath=//table[@id='del']/tbody/tr[" + row + "]/td[2]")
                        .trim());
        assertEquals(
                comments,
                selenium.getText(
                        "xpath=//table[@id='del']/tbody/tr[" + row + "]/td[3]")
                        .trim());
        assertEquals(
                "admin-ci",
                selenium.getText(
                        "xpath=//table[@id='del']/tbody/tr[" + row + "]/td[4]")
                        .trim());
        assertTrue(selenium
                .getText("xpath=//table[@id='del']/tbody/tr[" + row + "]/td[5]")
                .trim().startsWith(today));
        if (StringUtils.isNotBlank(whyStopped)) {
            By by = By.xpath("//table[@id='del']/tbody/tr[" + row
                    + "]/td[6]/a/img[@alt='Why Study Stopped?']");
            hover(by);
            if (isPhantomJS())
                driver.findElement(by).click();
            pause(2000);
            waitForElementToBecomeVisible(
                    By.xpath("//div[@class='ui-tooltip-content' and normalize-space(text())='Why study stopped: "
                            + whyStopped + "']"), 5);
        }

        // Audit Log
        selenium.click("xpath=//table[@id='del']/tbody/tr[" + row
                + "]/td[6]/a/img[@alt='Audit trail icon']");
        waitForElementById("audit-trail", 5);
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='audit-trail-table']/tbody/tr[2]/td[4]"),
                15);
        assertEquals(
                "Deleted",
                selenium.getText(
                        "xpath=//table[@id='audit-trail-table']/tbody/tr[2]/td[4]/table/tbody/tr[2]/td[1]")
                        .trim());
        assertEquals(
                "false",
                selenium.getText(
                        "xpath=//table[@id='audit-trail-table']/tbody/tr[2]/td[4]/table/tbody/tr[2]/td[2]")
                        .trim());
        assertEquals(
                "true",
                selenium.getText(
                        "xpath=//table[@id='audit-trail-table']/tbody/tr[2]/td[4]/table/tbody/tr[2]/td[3]")
                        .trim());
        driver.findElement(By.className("ui-dialog-titlebar-close")).click();

    }

    @SuppressWarnings("deprecation")
    private void verifyStatusRow(int row, String date, String code,
            String comments, String valMsg) {
        assertEquals(
                date,
                selenium.getText(
                        "xpath=//table[@id='row']/tbody/tr[" + row + "]/td[1]")
                        .trim());
        assertEquals(
                code,
                selenium.getText(
                        "xpath=//table[@id='row']/tbody/tr[" + row + "]/td[2]")
                        .trim());
        assertEquals(
                comments,
                selenium.getText(
                        "xpath=//table[@id='row']/tbody/tr[" + row + "]/td[3]")
                        .trim());
        assertEquals(
                valMsg,
                selenium.getText(
                        "xpath=//table[@id='row']/tbody/tr[" + row + "]/td[4]")
                        .trim());
    }

}
