package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.test.integration.support.Batch;

import java.sql.SQLException;
import java.util.Iterator;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.openqa.selenium.By;

import com.dumbster.smtp.SmtpMessage;

/**
 * 
 * @author Reshma Koganti
 */
@Batch(number = 3)
public class TrialCheckInOutTest extends AbstractTrialStatusTest {

    @SuppressWarnings("deprecation")
    @Test
    public void testCheckInPreventedIfStatusErrorsForAdminAbstractor()
            throws Exception {
        final String user = "admin-ci";
        testCheckInPreventedIfStatusErrors(user, false);
        testCheckInPreventedIfStatusErrors(user, true);

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testCheckInPreventedIfStatusErrorsForSuperAbstractor()
            throws Exception {
        final String user = "ctrpsubstractor";
        testCheckInPreventedIfStatusErrors(user, false);
        testCheckInPreventedIfStatusErrors(user, true);

    }

    @SuppressWarnings({ "deprecation", "rawtypes" })
    @Test
    public void testScientificCheckInWhenStatusErrors() throws Exception {
        scientificCheckInWhenStatusErrors(false);
        scientificCheckInWhenStatusErrors(true);

    }

    /**
     * @param useDashboard
     * @throws SQLException
     */
    private void scientificCheckInWhenStatusErrors(boolean useDashboard)
            throws SQLException {
        logoutUser();
        restartEmailServer();
        TrialInfo trial = createAcceptedTrial();
        loginAsScientificAbstractor();
        searchAndSelectTrial(trial.title);
        checkOutTrialAsScientificAbstractor();
        addSOS(trial, "IN_REVIEW");
        selectTrial(useDashboard, trial);

        // Checking In should be prevented
        selenium.click("link=Scientific Check In");
        // Verify pop-up (Slide 52).
        waitForElementById("pickSuperAbstractor", 5);
        assertTrue(selenium.isVisible("pickSuperAbstractor"));
        assertTrue(selenium
                .getText("pickSuperAbstractor")
                .replaceAll("\\s+", " ")
                .trim()
                .contains(
                        "Status Transition Errors were found. Please select a Super Abstractor from the list below, then click Proceed with Check-in. The system will: Check the trial in for Scientific Abstraction, Check-out the trial to the selected Super Abstractor for Admin Abstraction, Send an email to the Super Abstractor to correct the errors found. Super Abstractor:"));

        final String titlePath = "//div[@aria-describedby='pickSuperAbstractor']//span[@class='ui-dialog-title']";
        final String cancelPath = !useDashboard ? "//div[@aria-describedby='pickSuperAbstractor']//div[@class='ui-dialog-buttonset']//button[2]//span[text()='Cancel']"
                : "//div[@aria-describedby='pickSuperAbstractor']//button//span[text()='Cancel']";
        final String proceedPath = !useDashboard ? "//div[@aria-describedby='pickSuperAbstractor']//div[@class='ui-dialog-buttonset']//button[1]//span[text()='Proceed with Check-in']"
                : "//div[@aria-describedby='pickSuperAbstractor']//button//span[text()='Proceed with Check-in']";

        assertEquals("Trial Status Validation", selenium.getText(titlePath)
                .trim());

        // Close the dialog and make sure it's gone.
        selenium.click(cancelPath);
        assertFalse(selenium.isVisible("pickSuperAbstractor"));

        // Bring the dialog back again
        selenium.click("link=Scientific Check In");
        selenium.select("supAbsId", "label=ctrpsubstractor");

        clickAndWait(proceedPath);
        assertFalse(selenium.isVisible("id=pickSuperAbstractor"));
        assertTrue(selenium.isElementPresent("comment-dialog"));
        selenium.type("comments", "Test sci check in comments");

        clickAndWait("xpath=//div[@id='comment-dialog']//button[text()='Ok']");
        pause(5000);
        assertTrue(selenium
                .isTextPresent("Message. Trial checked in for Scientific Abstraction and checked out to the selected"
                        + " Super Abstractor for Admin Abstration. The Super Abstractor has been notified by email as well."));

        // Verify check out
        clickAndWait("link=Check-Out History");
        assertEquals("Scientific",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[1]")
                        .trim());
        assertEquals("scientific-ci",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[5]")
                        .trim());
        assertEquals("Administrative",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[2]/td[1]")
                        .trim());
        assertEquals("ctrpsubstractor",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[2]/td[3]")
                        .trim());
        assertEquals("",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[2]/td[5]")
                        .trim());

        // Verify email went out to Super Abstractor.
        assertEquals(1, server.getReceivedEmailSize());
        Iterator emailIter = server.getReceivedEmail();
        SmtpMessage email = (SmtpMessage) emailIter.next();
        String subject = email.getHeaderValues("Subject")[0];
        String to = email.getHeaderValues("To")[0];
        String body = email.getBody().replaceAll("<br/>", " ")
                .replaceAll("\\s+", " ");
        assertEquals("ctrpsubstractor-ci@example.com", to);
        assertTrue(subject.startsWith("Trial " + trial.nciID
                + " was checked out for Administrative"));
        assertEquals(
                "Dear ctrpsubstractor CI, The system found trial status transition errors for trial "
                        + trial.nciID
                        + " when the Scientific Abstractor scientific-ci checked the trial in for Scientific Abstraction. The system has automatically checked out this trial for Administrative Abstraction under your name. Please log in, correct the status transition error(s), and check the trial in. Thank you. This is an automated message sent by the CTRP system. Please do not reply.",
                body);
    }

    /**
     * @param user
     * @throws SQLException
     */
    private void testCheckInPreventedIfStatusErrors(final String user,
            boolean useDashboard) throws SQLException {
        logoutUser();
        TrialInfo trial = createTrialAndAccessStatusPage(user);

        // Adding In Review next to Approved is an error.
        // Check-out the trial for Admin abstraction under the SuperAbstractor's
        // name, and,
        insertStatus("In Review", today, "", "New status");
        // Close History
        clickAndWait("xpath=//span[normalize-space(text())='Cancel']");
        driver.switchTo().defaultContent();

        selectTrial(useDashboard, trial);

        // Checking In should be prevented
        selenium.click("link=Admin Check In");
        verifyTransitionErrorsPopUp();

        // Bring the dialog back again
        selenium.click("link=Admin Check In");
        clickAndWait("//div[@aria-describedby='transitionErrors']//div[@class='ui-dialog-buttonset']//button[1]//span[text()='Trial Status History']");
        assertEquals("Trial Status", driver.getTitle());
    }

    /**
     * 
     */
    @SuppressWarnings("deprecation")
    private void verifyTransitionErrorsPopUp() {
        // Verify pop-up (Slide 27).
        waitForElementById("transitionErrors", 5);
        assertTrue(selenium.isVisible("id=transitionErrors"));
        assertEquals(
                "Status Transition Errors were found. Trial record cannot be checked-in until all Status Transition Errors have been resolved. Please use the Trial Status History button to review and make corrections.",
                selenium.getText("transitionErrors").trim());

        if (s.isElementPresent("ui-dialog-title-transitionErrors"))
            assertEquals("Trial Status Validation",
                    selenium.getText("ui-dialog-title-transitionErrors").trim());
        else
            assertEquals(
                    "Trial Status Validation",
                    selenium.getText(
                            "//div[@aria-describedby='transitionErrors']//span[@class='ui-dialog-title']")
                            .trim());
        // Close the dialog and make sure it's gone.
        selenium.click(s
                .isElementPresent("//div[@aria-labelledby='ui-dialog-title-transitionErrors']//button//span[text()='Cancel']") ? "//div[@aria-labelledby='ui-dialog-title-transitionErrors']//button//span[text()='Cancel']"
                : "//div[@aria-describedby='transitionErrors']//button//span[text()='Cancel']");
        assertFalse(selenium.isVisible("id=transitionErrors"));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testCheckInWarningFunctionalityForAdminAbstractor()
            throws Exception {
        final String user = "admin-ci";
        testCheckInWarningFunctionalityAsUser(user, false);
        testCheckInWarningFunctionalityAsUser(user, true);

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testCheckInWarningFunctionalityForSuperAbstractor()
            throws Exception {
        final String user = "ctrpsubstractor";
        testCheckInWarningFunctionalityAsUser(user, false);
        testCheckInWarningFunctionalityAsUser(user, true);

    }

    /**
     * @param user
     * @throws SQLException
     */
    private void testCheckInWarningFunctionalityAsUser(final String user,
            boolean useDashboard) throws SQLException {
        logoutUser();
        TrialInfo trial = createTrialAndAccessStatusPage(user);

        // Adding In Review next to Approved is an error.
        // Check-out the trial for Admin abstraction under the SuperAbstractor's
        // name, and,
        insertStatus("Temporarily Closed to Accrual", today,
                "Temporarily Closed to Accrual", "New status");
        // Close History
        clickAndWait("xpath=//span[normalize-space(text())='Cancel']");
        driver.switchTo().defaultContent();

        selectTrial(useDashboard, trial);

        // Checking In should be prevented
        final String checkInOption = "Admin Check In";
        verifyCheckInWarningDialog(checkInOption);
        selectTrial(useDashboard, trial);

        // proceed with check in
        s.click("link=Admin Check In");
        s.click("//button//span[text()='Proceed with Check-in']");
        assertFalse(selenium.isVisible("id=transitionWarnings"));
        assertTrue(selenium.isElementPresent("comment-dialog"));
        selenium.type("comments", "Test admin check in comments");
        clickAndWait("xpath=//div[@id='comment-dialog']//button[text()='Ok']");

        // Verify check out
        clickAndWait("link=Check-Out History");
        assertEquals("Administrative",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[1]")
                        .trim());
        assertEquals(user,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[5]")
                        .trim());
    }

    /**
     * @param checkInOption
     * @param useDashboard
     * @param trial
     */
    @SuppressWarnings("deprecation")
    private void verifyCheckInWarningDialog(final String checkInOption) {
        selenium.click("link=" + checkInOption);
        // Verify pop-up (Slide 29).
        waitForElementToBecomeVisible(By.id("transitionWarnings"), 15);      
        assertEquals(
                "Status Transition Warnings were found. Use the Trial Status History button to review and make corrections, or select Proceed with Check-in.",
                selenium.getText("transitionWarnings").trim());

        if (s.isElementPresent("ui-dialog-title-transitionWarnings"))
            assertEquals("Trial Status Validation",
                    selenium.getText("ui-dialog-title-transitionWarnings")
                            .trim());
        else
            assertEquals(
                    "Trial Status Validation",
                    selenium.getText(
                            "//div[@aria-describedby='transitionWarnings']//span[@class='ui-dialog-title']")
                            .trim());

        // Close the dialog and make sure it's gone.
        selenium.click(s
                .isElementPresent("//div[@aria-labelledby='ui-dialog-title-transitionWarnings']") ? "//div[@aria-labelledby='ui-dialog-title-transitionWarnings']//button//span[text()='Cancel']"
                : "//div[@aria-describedby='transitionWarnings']//button//span[text()='Cancel']");
        assertFalse(selenium.isVisible("id=transitionWarnings"));

        // Bring the dialog back again
        selenium.click("link=" + checkInOption);
        clickAndWait(s
                .isElementPresent("//div[@aria-labelledby='ui-dialog-title-transitionWarnings']") ? "//div[@aria-labelledby='ui-dialog-title-transitionWarnings']//button//span[text()='Trial Status History']"
                : "//div[@aria-describedby='transitionWarnings']//button//span[text()='Trial Status History']");
        assertEquals("Trial Status", driver.getTitle());

    }

    @Test
    public void testSorting() throws Exception {
        TrialInfo trial = createSubmittedTrial();
        createStudyCheckout(trial, DateUtils.parseDate("02/02/2013",
                new String[] { "MM/dd/yyyy" }));
        createStudyCheckout(trial, DateUtils.parseDate("01/01/2014",
                new String[] { "MM/dd/yyyy" }));
        createStudyCheckout(trial, DateUtils.parseDate("03/03/2015",
                new String[] { "MM/dd/yyyy" }));

        loginAsAdminAbstractor();
        searchAndSelectTrial(trial.title);
        clickAndWait("link=Check-Out History");

        clickAndWait("link=Check-In Time");
        assertEquals("02/02/2013 24:00",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[4]"));
        assertEquals("01/01/2014 24:00",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[2]/td[4]"));
        assertEquals("03/03/2015 24:00",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[3]/td[4]"));

        clickAndWait("link=Check-Out Time");
        assertEquals("02/02/2013 24:00",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[2]"));
        assertEquals("01/01/2014 24:00",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[2]/td[2]"));
        assertEquals("03/03/2015 24:00",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[3]/td[2]"));

    }

    @Test
    public void testAdminCheckIn() throws Exception {
        logoutUser();
        TrialInfo trial = createSubmittedTrial();
        loginAsAdminAbstractor();
        searchAndSelectTrial(trial.title);
        assertTrue(selenium.isElementPresent("link=Trial Identification"));
        clickAndWait("link=Trial Identification");
        assertTrue(selenium.isElementPresent("link=Admin Check Out"));
        clickAndWait("link=Admin Check Out");
        assertFalse(selenium.isElementPresent("link=Admin Check Out"));
        assertTrue(selenium.isElementPresent("link=Admin Check In"));
        clickAndWait("link=Admin Check In");
        assertTrue(selenium.isElementPresent("id=comments"));
        assertTrue(selenium.isElementPresent("class=btn btn-icon btn-primary"));
        assertTrue(selenium.isElementPresent("class=btn btn-icon btn-default"));
        assertEquals(selenium.getText("class=btn btn-icon btn-primary"), "Ok");
        assertEquals(selenium.getText("class=btn btn-icon btn-default"),
                "Cancel");
        selenium.type("id=comments", "Test admin check in comments");
        clickAndWait("class=btn btn-icon btn-primary");
        assertTrue(selenium.isElementPresent("link=Admin Check Out"));
    }

    @Test
    public void testScientificCheckIn() throws Exception {
        logoutUser();
        TrialInfo trial = createSubmittedTrial();
        loginAsScientificAbstractor();
        searchAndSelectTrial(trial.title);
        assertTrue(selenium.isElementPresent("link=Trial Identification"));
        clickAndWait("link=Trial Identification");
        assertTrue(selenium.isElementPresent("link=Scientific Check Out"));
        clickAndWait("link=Scientific Check Out");
        assertFalse(selenium.isElementPresent("link=Scientific Check Out"));
        assertTrue(selenium.isElementPresent("link=Scientific Check In"));
        clickAndWait("link=Scientific Check In");
        assertTrue(selenium.isElementPresent("id=comments"));
        assertTrue(selenium.isElementPresent("class=btn btn-icon btn-primary"));
        assertTrue(selenium.isElementPresent("class=btn btn-icon btn-default"));
        assertEquals(selenium.getText("class=btn btn-icon btn-primary"), "Ok");
        assertEquals(selenium.getText("class=btn btn-icon btn-default"),
                "Cancel");
        selenium.type("id=comments", "Test Scientific check in comments");
        clickAndWait("class=btn btn-icon btn-primary");
        assertTrue(selenium.isElementPresent("link=Scientific Check Out"));
    }

    @Test
    public void testAdminScientificCheckIn() throws Exception {
        logoutUser();
        TrialInfo trial = createSubmittedTrial();
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);
        assertTrue(selenium.isElementPresent("link=Trial Identification"));
        clickAndWait("link=Trial Identification");
        assertTrue(selenium.isElementPresent("link=Admin Check Out"));
        assertTrue(selenium.isElementPresent("link=Scientific Check Out"));
        assertTrue(selenium.isElementPresent("link=Admin/Scientific Check Out"));
        clickAndWait("link=Admin/Scientific Check Out");
        assertFalse(selenium.isElementPresent("link=Scientific Check Out"));
        assertTrue(selenium.isElementPresent("link=Scientific Check In"));
        assertFalse(selenium.isElementPresent("link=Admin Check Out"));
        assertTrue(selenium.isElementPresent("link=Admin Check In"));
        assertFalse(selenium
                .isElementPresent("link=Admin/Scientific Check Out"));
        assertTrue(selenium.isElementPresent("link=Admin/Scientific Check In"));
        clickAndWait("link=Admin/Scientific Check In");
        assertTrue(selenium.isElementPresent("id=comments"));
        assertTrue(selenium.isElementPresent("class=btn btn-icon btn-primary"));
        assertTrue(selenium.isElementPresent("class=btn btn-icon btn-default"));
        assertEquals(selenium.getText("class=btn btn-icon btn-primary"), "Ok");
        assertEquals(selenium.getText("class=btn btn-icon btn-default"),
                "Cancel");
        selenium.type("id=comments",
                "Test both Admin and Scientific check in comments");
        clickAndWait("class=btn btn-icon btn-primary");
        assertTrue(selenium.isElementPresent("link=Admin Check Out"));
        assertTrue(selenium.isElementPresent("link=Scientific Check Out"));
        assertTrue(selenium.isElementPresent("link=Admin/Scientific Check Out"));

        clickAndWait("link=Admin/Scientific Check Out");
        assertTrue(selenium.isElementPresent("link=Scientific Check In"));
        assertTrue(selenium.isElementPresent("link=Admin Check In"));
        assertTrue(selenium.isElementPresent("link=Admin/Scientific Check In"));

        clickAndWait("link=Scientific Check In");
        selenium.type("id=comments", "Test Scientific check in comments only");
        clickAndWait("class=btn btn-icon btn-primary");
        assertFalse(selenium.isElementPresent("link=Admin Check Out"));
        assertTrue(selenium.isElementPresent("link=Scientific Check Out"));
        assertFalse(selenium
                .isElementPresent("link=Admin/Scientific Check Out"));

        clickAndWait("link=Admin Check In");
        selenium.type("id=comments", "Test Admin check in comments only");
        clickAndWait("class=btn btn-icon btn-default");
        assertFalse(selenium.isElementPresent("link=Admin Check Out"));
        assertTrue(selenium.isElementPresent("link=Scientific Check Out"));
        assertFalse(selenium
                .isElementPresent("link=Admin/Scientific Check Out"));
    }
}
