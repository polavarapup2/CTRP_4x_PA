package gov.nih.nci.registry.test.integration;

import static gov.nih.nci.pa.enums.StudyStatusCode.ACTIVE;
import static gov.nih.nci.pa.enums.StudyStatusCode.ADMINISTRATIVELY_COMPLETE;
import static gov.nih.nci.pa.enums.StudyStatusCode.APPROVED;
import static gov.nih.nci.pa.enums.StudyStatusCode.CLOSED_TO_ACCRUAL;
import static gov.nih.nci.pa.enums.StudyStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION;
import static gov.nih.nci.pa.enums.StudyStatusCode.COMPLETE;
import static gov.nih.nci.pa.enums.StudyStatusCode.ENROLLING_BY_INVITATION;
import static gov.nih.nci.pa.enums.StudyStatusCode.IN_REVIEW;
import static gov.nih.nci.pa.enums.StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL;
import static gov.nih.nci.pa.enums.StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION;
import static gov.nih.nci.pa.enums.StudyStatusCode.WITHDRAWN;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudyStatusCode;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import gov.nih.nci.pa.test.integration.support.Batch;
import junit.framework.Assert;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Integration test for Program Code assignment screen.
 */
@Batch(number = 1)
public class ProgramCodeAssignmentTest extends AbstractRegistrySeleniumTest {

    private List<TrialInfo> trials = new ArrayList<TrialInfo>();

    /**
     * Test the program codes menu items
     * 
     * @throws Exception
     *             exception
     */
    @Test
    public void testProgramCodesMenu() throws Exception {
        loginAndAcceptDisclaimer();
        waitForElementToBecomeVisible(By.linkText("Administration"), 2);
        hoverLink("Administration");
        waitForElementToBecomeVisible(By.linkText("Program Codes"), 2);
        assertTrue(selenium.isTextPresent("Program Codes"));
        hoverLink("Program Codes");
        assertTrue(selenium.isTextPresent("Manage Master List"));
        assertTrue(selenium.isTextPresent("Manage Code Assignments"));
        assertFalse(selenium.isTextPresent("Manage Accruals"));
        logoutUser();
    }

    /**
     * Test changing family
     * 
     * @throws Exception
     */
    @Test
    public void testChangeFamily() throws Exception {
        // When I first access search screen
        accessManageCodeAssignmentsScreen();
        // I see empty table
        assertTrue(selenium.isTextPresent("Showing 0 to 0 of 0 entries"));

        // when I change family
        Select dropdown = new Select(driver.findElement(By.id("familyPoId")));
        dropdown.selectByIndex(1);

        // then I should see associated trials.
        TrialInfo trial4 = trials.get(4);
        String trial4Title = trial4.title;
        changePageLength("25");
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody//tr//td/div[text()='"
                        + trial4Title + "']"), 10);

        TrialInfo trial3 = trials.get(3);
        // when I filter trial details.
        selenium.typeKeys(
                "//div[@id='trialsTbl_filter']/descendant::label/descendant::input",
                trial3.nciID);

        // then I should see filtered set of trials
        waitForElementToGoAway(
                By.xpath("//table[@id='trialsTbl']/tbody//tr//td/div[text()='"
                        + trial4Title + "']"), 10);

        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody//tr//td/div[text()='"
                        + trial3.title + "']"), 10);

        logoutUser();

    }

    @Test
    public void testTrialInclusionCriteria() throws Exception {
        accessManageCodeAssignmentsScreen();

        // Set up 1 family.
        final QueryRunner qr = new QueryRunner();
        qr.update(connection, "delete from family");
        qr.update(
                connection,
                String.format(
                        "INSERT INTO family(identifier, po_id, "
                                + "rep_period_end, rep_period_len_months) VALUES (1, 1, '2015-12-31', 12)",
                        date(180)));

        // Use 1 trial.
        deactivateAllTrials();
        TrialInfo trial = createAcceptedTrial();
        Number siteID = addParticipatingSite(trial,
                "National Cancer Institute Division of Cancer Prevention",
                "ACTIVE");

        // Add 1 program code
        qr.update(
                connection,
                "insert into program_code (identifier,family_id, program_code, program_name, status_code) "
                        + "values (1,1,'VA 1', 'VA Program 1', 'ACTIVE')");

        final StudyStatusCode[] ACTIVE_PROTOCOL_STATUSES = new StudyStatusCode[] {
                ACTIVE, APPROVED, IN_REVIEW, ENROLLING_BY_INVITATION,
                TEMPORARILY_CLOSED_TO_ACCRUAL,
                TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION };

        final EnumSet<StudyStatusCode> NON_ACTIVE_STATUSES = EnumSet
                .allOf(StudyStatusCode.class);
        NON_ACTIVE_STATUSES.removeAll(Arrays.asList(ACTIVE_PROTOCOL_STATUSES));

        // Non-Active status within period should NOT bring up the trial.
        for (StudyStatusCode studyStatus : NON_ACTIVE_STATUSES) {
            deleteEntireTrialStatusHistory(trial);
            addSOS(trial, studyStatus.name(), "'2015-06-06 00:00:00'");
            verifyTrialDoesNotShowUp(trial);
        }

        // Active status within period should bring up the trial.
        deleteEntireTrialStatusHistory(trial);
        for (StudyStatusCode studyStatus : ACTIVE_PROTOCOL_STATUSES) {
            for (String statusDate : new String[] { "'2015-01-01 00:00:00'",
                    "'2015-06-06 00:00:00'", "'2015-12-31 00:00:00'" }) {
                addSOS(trial, studyStatus.name(), statusDate);
                verifyTrialShowsUp(trial);

                deleteCurrentTrialStatus(trial);
                verifyTrialDoesNotShowUp(trial);
            }

        }

        // Active status outside period should NOT bring up the trial.
        for (StudyStatusCode studyStatus : ACTIVE_PROTOCOL_STATUSES) {
            for (String statusDate : new String[] { "'2016-01-01 00:00:00'" }) {
                addSOS(trial, studyStatus.name(), statusDate);
                verifyTrialDoesNotShowUp(trial);
            }

        }

        // Test more complicated trial history scenario.
        deleteEntireTrialStatusHistory(trial);
        addSOS(trial, IN_REVIEW.name(), "'2014-12-01'");
        addSOS(trial, APPROVED.name(), "'2014-12-05'");
        addSOS(trial, ACTIVE.name(), "'2014-12-06'");
        addSOS(trial, StudyStatusCode.CLOSED_TO_ACCRUAL.name(), "'2015-06-06'");
        addSOS(trial, StudyStatusCode.ACTIVE.name(), "'2015-07-07'");
        addSOS(trial,
                StudyStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION.name(),
                "'2015-08-08'");
        addSOS(trial, StudyStatusCode.ADMINISTRATIVELY_COMPLETE.name(),
                "'2015-09-09'");
        verifyTrialShowsUp(trial);

        // Test buggy scenarios: PO-9792
        deleteEntireTrialStatusHistory(trial);
        addSOS(trial, IN_REVIEW.name(), "'2014-10-01'");
        addSOS(trial, APPROVED.name(), "'2014-11-05'");
        addSOS(trial, ACTIVE.name(), "'2014-12-06'");
        verifyTrialShowsUp(trial);
        addSOS(trial, CLOSED_TO_ACCRUAL.name(), "'2014-12-20'");
        verifyTrialDoesNotShowUp(trial);

        deleteEntireTrialStatusHistory(trial);
        addSOS(trial, IN_REVIEW.name(), "'2014-05-01'");
        addSOS(trial, APPROVED.name(), "'2014-06-05'");
        addSOS(trial, ACTIVE.name(), "'2014-06-05'");
        addSOS(trial, CLOSED_TO_ACCRUAL.name(), "'2015-01-02'");
        addSOS(trial, CLOSED_TO_ACCRUAL_AND_INTERVENTION.name(), "'2015-01-02'");
        verifyTrialShowsUp(trial);

        deleteEntireTrialStatusHistory(trial);
        addSOS(trial, IN_REVIEW.name(), "'2014-05-01'");
        addSOS(trial, APPROVED.name(), "'2014-06-05'");
        addSOS(trial, ACTIVE.name(), "'2014-06-05'");
        addSOS(trial, CLOSED_TO_ACCRUAL_AND_INTERVENTION.name(), "'2014-12-31'");
        verifyTrialDoesNotShowUp(trial);
        addSOS(trial, IN_REVIEW.name(), "'2015-05-05'");
        addSOS(trial, WITHDRAWN.name(), "'2015-05-06'");
        verifyTrialShowsUp(trial);

        deleteEntireTrialStatusHistory(trial);
        addSOS(trial, IN_REVIEW.name(), "'2014-05-01'");
        addSOS(trial, APPROVED.name(), "'2014-06-05'");
        addSOS(trial, ACTIVE.name(), "'2016-02-02'");
        verifyTrialShowsUp(trial);

        deleteEntireTrialStatusHistory(trial);
        addSOS(trial, IN_REVIEW.name(), "'2015-02-02'");
        addSOS(trial, APPROVED.name(), "'2015-03-03'");
        addSOS(trial, ACTIVE.name(), "'2015-04-04'");
        addSOS(trial, TEMPORARILY_CLOSED_TO_ACCRUAL.name(), "'2015-05-05'");
        addSOS(trial, CLOSED_TO_ACCRUAL_AND_INTERVENTION.name(), "'2015-06-06'");
        addSOS(trial, ADMINISTRATIVELY_COMPLETE.name(), "'2015-07-07'");
        addSOS(trial, COMPLETE.name(), "'2015-07-07'");
        verifyTrialShowsUp(trial);

        deleteEntireTrialStatusHistory(trial);
        addSOS(trial, IN_REVIEW.name(), "'2014-02-02'");
        addSOS(trial, APPROVED.name(), "'2014-03-03'");
        addSOS(trial, ACTIVE.name(), "'2014-04-04'");
        addSOS(trial, TEMPORARILY_CLOSED_TO_ACCRUAL.name(), "'2014-05-05'");
        addSOS(trial, CLOSED_TO_ACCRUAL_AND_INTERVENTION.name(), "'2014-06-06'");
        addSOS(trial, ADMINISTRATIVELY_COMPLETE.name(), "'2014-07-07'");
        addSOS(trial, COMPLETE.name(), "'2014-12-31'");
        verifyTrialDoesNotShowUp(trial);

        deleteEntireTrialStatusHistory(trial);
        addSOS(trial, IN_REVIEW.name(), "'2016-01-01'");
        addSOS(trial, ACTIVE.name(), "'2016-01-15'");
        verifyTrialDoesNotShowUp(trial);

        deleteEntireTrialStatusHistory(trial);
        addSOS(trial, IN_REVIEW.name(), "'2014-02-02'");
        addSOS(trial, APPROVED.name(), "'2014-03-03'");
        addSOS(trial, ACTIVE.name(), "'2014-04-04'");
        addSOS(trial, TEMPORARILY_CLOSED_TO_ACCRUAL.name(), "'2014-12-30'");
        addSOS(trial, CLOSED_TO_ACCRUAL_AND_INTERVENTION.name(), "'2015-01-02'");
        addSOS(trial, COMPLETE.name(), "'2015-07-07'");
        verifyTrialShowsUp(trial);

        deleteEntireTrialStatusHistory(trial);
        addSOS(trial, IN_REVIEW.name(), "'2014-02-02'");
        addSOS(trial, APPROVED.name(), "'2014-03-03'");
        addSOS(trial, ACTIVE.name(), "'2014-04-04'");
        addSOS(trial, TEMPORARILY_CLOSED_TO_ACCRUAL.name(), "'2014-12-30'");
        addSOS(trial, CLOSED_TO_ACCRUAL_AND_INTERVENTION.name(), "'2016-02-02'");
        verifyTrialShowsUp(trial);

        // Trial without a site should not bring up.
        deleteEntireTrialStatusHistory(trial);
        addSOS(trial, StudyStatusCode.ACTIVE.name(), "'2015-07-07'");
        verifyTrialShowsUp(trial);
        qr.update(connection, "delete from study_site where identifier="
                + siteID);
        accessManageAssignmentScreen();
        verifyTrialDoesNotShowUp(trial);

    }

    /**
     * @param trial
     */
    private void verifyTrialDoesNotShowUp(TrialInfo trial) {
        accessManageAssignmentScreen();
        Select dropdown = new Select(driver.findElement(By.id("familyPoId")));
        dropdown.selectByIndex(1);
        waitForElementToBecomeVisible(By.className("dataTables_empty"), 10);
        try {
            waitForElementToBecomeVisible(
                    By.xpath("//div[text()='" + trial.title + "']"), 1);
            Assert.fail();
        } catch (Exception e) {
        }
    }

    /**
     * @param trial
     */
    private void verifyTrialShowsUp(TrialInfo trial) {
        accessManageAssignmentScreen();
        Select dropdown = new Select(driver.findElement(By.id("familyPoId")));
        dropdown.selectByIndex(1);
        waitForElementToBecomeVisible(
                By.xpath("//div[text()='" + trial.title + "']"), 10);
    }

    @Test
    public void testChangeReportingPeriod() throws Exception {
        // Given a bunch of trials created and approved today
        // And family with reporting period ends 6 months from now
        accessManageCodeAssignmentsScreen();

        // pick family 1
        Select dropdown = new Select(driver.findElement(By.id("familyPoId")));
        dropdown.selectByIndex(1);

        changePageLength("25");
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody/tr[4]"), 10);
        assertTrue(selenium.isTextPresent(trials.get(4).title));

        // when I change the reporting period length to 2
        Select rpLengthBox = new Select(driver.findElement(By
                .id("reportingPeriodLength")));
        rpLengthBox.selectByValue("2");

        // when I update the reporting period end date to tomorrow and length to
        // 1 month
        selenium.type("reportingPeriodEndDate", date(1));
        rpLengthBox = new Select(driver.findElement(By
                .id("reportingPeriodLength")));
        rpLengthBox.selectByValue("1");

        // Then I should see all trials
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody/tr[4]"), 10);
        assertTrue(selenium.isTextPresent(trials.get(4).title));

        // when I update the reporting period end date to nex year and length to
        // 6 month
        selenium.type("reportingPeriodEndDate", date(365));
        rpLengthBox = new Select(driver.findElement(By
                .id("reportingPeriodLength")));
        rpLengthBox.selectByValue("6");
        waitForElementToBecomeVisible(By.id("length_flash"), 10);
        waitForElementToBecomeInvisible(By.id("length_flash"), 10);

        

        // Given three trials having status dates moved 2 years back
        moveBackTrialStatusDate(trials.get(1), 2 * 365);
        moveBackTrialStatusDate(trials.get(2), 2 * 365);
        moveBackTrialStatusDate(trials.get(3), 2 * 365);

        // when I update the reporting period end date last year and length to
        // 12 month
        selenium.type("reportingPeriodEndDate", date(-365));
        rpLengthBox = new Select(driver.findElement(By
                .id("reportingPeriodLength")));
        rpLengthBox.selectByValue("12");

        // Then I should see 3 trials
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody/tr[2]"), 10);
        assertTrue(selenium.isTextPresent(trials.get(1).title));
        assertTrue(selenium.isTextPresent(trials.get(2).title));
        assertTrue(selenium.isTextPresent(trials.get(3).title));

        // and not any other trial
        assertFalse(selenium.isTextPresent(trials.get(4).title));
        assertFalse(selenium.isTextPresent(trials.get(6).title));

        logoutUser();

    }

    @Test
    public void testFamilyChangeIsPreservedBetweenListAndManage()
            throws Exception {
        // Go to manage screen
        accessManageCodeAssignmentsScreen();

        // when I change to Family2
        Select dropdown = new Select(driver.findElement(By.id("familyPoId")));
        dropdown.selectByIndex(2);

        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody//tr[4]"), 10);

        // then I see program code of Family2 (VA1)
        assertTrue(selenium.isTextPresent("VA1"));

        // When I go to Manage List screen
        accessManageMasterListScreen();
        waitForPageToLoad();

        // I see program code associated to Family2 (VA1)
        assertTrue(selenium.isTextPresent("VA1"));

        // switch family
        dropdown = new Select(driver.findElement(By.id("selectedDTOId")));
        dropdown.selectByIndex(0);
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='programCodesTable']/tbody//tr[4]"), 10);

        // Then I see program code of Family1- PG1
        assertTrue(selenium.isTextPresent("PG1"));

        // when I go back to Manage assignment screen
        accessManageAssignmentScreen();
        waitForPageToLoad();

        // Then I see program code of Family1- PG1
        assertTrue(selenium.isTextPresent("PG1"));

    }

    private void accessManageMasterListScreen() {
        waitForElementToBecomeVisible(By.linkText("Administration"), 5);
        hoverLink("Administration");
        waitForElementToBecomeVisible(By.linkText("Program Codes"), 5);
        hoverLink("Program Codes");
        clickAndWait("link=Manage Master List");

    }

    private void accessManageAssignmentScreen() {
        waitForElementToBecomeVisible(By.linkText("Administration"), 2);
        hoverLink("Administration");
        waitForElementToBecomeVisible(By.linkText("Program Codes"), 2);
        hoverLink("Program Codes");
        clickAndWait("link=Manage Code Assignments");

    }

    /**
     * Test changing family
     * 
     * @throws Exception
     *             - when error
     */
    @Test
    public void testFamilyListOptions() throws Exception {
        // Given that the user is not "ProgramCodeAdmin"
        unassignUserFromGroup("abstractor-ci", "ProgramCodeAdministrator");

        // Then he should be able to access the families associated with org
        loginAndGoToManageProgramCodeScreen();
        // select box for family is not rendered
        assertFalse(isFamilySelectBoxRendered());
        // family name is rendered as text
        assertTrue(isFamilyNameRendered());

        logoutUser();

        // Given that the user is now granted ProgramCodeAdmin role
        assignUserToGroup("abstractor-ci", "ProgramCodeAdministrator");

        // Then he must see all the families in PO
        accessManageCodeAssignmentsScreen();

        // family name is not rendered as text
        assertFalse(isFamilyNameRendered());

        Select dropdown2 = new Select(driver.findElement(By.id("familyPoId")));
        int size = dropdown2.getOptions().size();

        // which is greater than the families his organization is associated
        // with.
        assertTrue(size > 1);
        logoutUser();

    }

    @Test
    public void testVerifyAcessPrivilege() {
        // login as non site admin
        loginAsSubmitter();

        // and directly access program code assignment
        openAndWait("/registry/siteadmin/managePCAssignmentchangeFamily.action");

        // then I should get error page.

        assertTrue(selenium.isTextPresent("403"));
        logoutUser();
    }

    @Test
    public void testVerifyExport() throws Exception {
        accessManageCodeAssignmentsScreen();
        Select dropdown = new Select(driver.findElement(By.id("familyPoId")));
        dropdown.selectByIndex(1);
        changePageLength("25");

        TrialInfo trial4 = trials.get(4);
        String trial4Title = trial4.title;
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody//tr//td/div[text()='"
                        + trial4Title + "']"), 10);

        verifyCSVExport();
        verifyExcelExport();
        logoutUser();
    }

    @Test
    public void testVerifyProgramCodeFamilyFiltering() throws Exception {

        // Given trials are associated with PG1 of family1 and VA1 of family2
        accessManageCodeAssignmentsScreen();
        TrialInfo trial4 = trials.get(4);
        String trial4Title = trial4.title;

        // Choose family 1
        Select dropdown = new Select(driver.findElement(By.id("familyPoId")));
        dropdown.selectByIndex(1);
        changePageLength("25");

        // Then table to refreshes

        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody//tr//td/div[text()='"
                        + trial4Title + "']"), 10);

        // Filter by trial 4
        selenium.typeKeys(
                "//div[@id='trialsTbl_filter']/descendant::label/descendant::input",
                trial4.nciID);

        // Then I see PG1 and not VA1
        assertTrue(selenium.isTextPresent("PG1"));
        assertFalse(selenium.isTextPresent("VA1"));

        // When I choose family 2
        dropdown = new Select(driver.findElement(By.id("familyPoId")));
        dropdown.selectByIndex(2);
        changePageLength("25");

        // Then table refreshes
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody//tr//td/div[text()='"
                        + trial4Title + "']"), 10);

        // Filter by trial 4
        selenium.typeKeys(
                "//div[@id='trialsTbl_filter']/descendant::label/descendant::input",
                trial4.nciID);

        // Then I see VA1 and not PG1
        assertTrue(selenium.isTextPresent("VA1"));
        assertFalse(selenium.isTextPresent("PG1"));

        logoutUser();
    }

    @Test
    public void testUnassignProgramCode() throws Exception {
        accessManageCodeAssignmentsScreen();

        Select dropdown = new Select(driver.findElement(By.id("familyPoId")));
        dropdown.selectByIndex(1);
        changePageLength("25");
        TrialInfo trial1 = trials.get(1);

        // delete PG1 from trial 1
        waitForElementToBecomeAvailable(By.id(trial1.id + "_PG1_a"), 5);
        s.click(trial1.id + "_PG1_a");

        // make sure the dynamically added elements are removed from DOM
        waitForElementToGoAway(By.id(trial1.id + "_PG1_img"), 10);
        waitForElementToGoAway(By.id(trial1.id + "_PG1_span"), 10);
        waitForElementToGoAway(By.id(trial1.id + "_PG1_a"), 10);

        // refresh the search result and make sure PG1 from trial 4 no longer
        // show up
        dropdown = new Select(driver.findElement(By.id("familyPoId")));
        dropdown.selectByIndex(0);
        dropdown = new Select(driver.findElement(By.id("familyPoId")));
        dropdown.selectByIndex(1);

        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody//tr//td[2]"), 10);

        waitForElementToGoAway(By.id(trial1.id + "_PG1_a"), 5);

        logoutUser();
    }

    @Test
    public void testParticipation() throws Exception {
        accessManageCodeAssignmentsScreen();
        Select dropdown = new Select(driver.findElement(By.id("familyPoId")));
        dropdown.selectByIndex(1);
        changePageLength("25");
        TrialInfo trial4 = trials.get(4);
        String trial4Title = trial4.title;
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody//tr//td/div[text()='"
                        + trial4Title + "']"), 10);

        clickLinkAndWait("Show my participation");
        waitForElementToBecomeVisible(
                By.xpath("//table[@id='participationTbl']"), 10);
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='participationTbl']/tbody//tr//td[text()='National Cancer Institute Division of Cancer Prevention']"),
                10);
        assertTrue(selenium.isTextPresent("Abraham, Sony; Kennedy, James"));
        selenium.click("xpath=//button/span[text()='Close']");
        waitForElementToBecomeInvisible(
                By.xpath("//table[@id='participationTbl']"), 10);

        logoutUser();
    }

    @Test
    public void testAssignMultiple() throws Exception {

        // Go to manage screen
        accessManageCodeAssignmentsScreen();

        // delete all program codes from studies
        removeFromAllStudiesProgramCodes();

        // pick family1
        Select dropdown = new Select(driver.findElement(By.id("familyPoId")));
        dropdown.selectByIndex(1);
        changePageLength("25");

        clickTableRow(1);
        // click the assign button
        clickAndWait("assignPGCBtn");

        // Then the dialog showup
        waitForElementToBecomeVisible(By.id("pgc-madd-dialog"), 5);

        // on the popup select
        useSelect2ToPickAnOption("pgc-madd-sel", "PG2", "PG2 - Cancer Program2");

        // When I click Cancel
        clickAndWait("pgc-madd-dialog-ok");

        // Then the dialog closes
        waitForElementToBecomeInvisible(By.id("pgc-madd-dialog"), 5);

        // Then I see confirmation
        waitForElementToBecomeVisible(By.id("pgcInfo"), 4);
        assertTrue(selenium
                .isTextPresent("Program Codes were successfully assigned"));

        // And I see the confirmation go away after 5 seconds
        waitForElementToBecomeInvisible(By.id("pgcInfo"), 10);

        // Now I see PG2 associated with first trial
        verifyTrialProgramCodeAssociation(1, true, "PG2");

        clickTableRow(1);
        clickTableRow(2);

        // click the assign button
        clickAndWait("assignPGCBtn");

        // Then the dialog showup
        waitForElementToBecomeVisible(By.id("pgc-madd-dialog"), 5);

        // on the popup select
        useSelect2ToPickAnOption("pgc-madd-sel", "PG2", "PG2 - Cancer Program2");

        // When I click Cancel
        clickAndWait("pgc-madd-dialog-ok");

        // Then the dialog closes
        waitForElementToBecomeInvisible(By.id("pgc-madd-dialog"), 5);

        // Then I see confirmation
        waitForElementToBecomeVisible(By.id("pgcInfo"), 4);
        assertTrue(selenium
                .isTextPresent("Program Codes were successfully assigned"));

        // And I see the confirmation go away after 5 seconds
        waitForElementToBecomeInvisible(By.id("pgcInfo"), 10);

        // Now I see PG2 associated with first trial
        verifyTrialProgramCodeAssociation(1, true, "PG2");
        verifyTrialProgramCodeAssociation(2, true, "PG2");

        clickTableRow(1);
        clickTableRow(2);

        // click the assign button
        clickAndWait("assignPGCBtn");

        // Then the dialog showup
        waitForElementToBecomeVisible(By.id("pgc-madd-dialog"), 5);

        // verify that select box has no PG2
        hasNoSelect2Option("pgc-madd-sel", "PG2", "No results found");

        logoutUser();
    }

    @SuppressWarnings("deprecation")
    public void hasNoSelect2Option(String id, String sendKeys, String option) {
        WebElement sitesBox = driver.findElement(By
                .xpath("//span[preceding-sibling::select[@id='" + id
                        + "']]//input[@type='search']"));
        sitesBox.click();
        boolean elementPresent = s.isElementPresent("select2-" + id
                + "-results");
        if (!elementPresent) {
            // odd behavior in FF, click again.
            sitesBox.click();
            elementPresent = s.isElementPresent("select2-" + id + "-results");
        }
        assertTrue(elementPresent);
        sitesBox.sendKeys(sendKeys);

        By xpath = null;
        xpath = By.xpath("//li[@role='treeitem' and text()='" + option + "']");
        waitForElementToBecomeAvailable(xpath, 10);
        assertTrue(driver.findElement(xpath).isDisplayed());
    }

    @Test
    public void testUnAssignMultipleNothing() throws Exception {
        // Given that I am on Manage screen and none of the trials have program
        // codes
        accessManageCodeAssignmentsScreen();
        removeFromAllStudiesProgramCodes();

        // When I pick the first family
        Select dropdown = new Select(driver.findElement(By.id("familyPoId")));
        dropdown.selectByIndex(1);
        changePageLength("25");

        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody//tr[2]"), 10);

        // Select 2 rows
        clickTableRow(1);
        clickTableRow(2);

        // click the Unassign button
        clickAndWait("unassignPGCBtn");

        // Then the dialog showup
        waitForElementToBecomeVisible(By.id("pgc-mrm-dialog-empty"), 5);

        // When I click OK
        clickAndWait("pgc-mrm-dialog-empty-cancel");

        // Then the dialog showup
        waitForElementToBecomeInvisible(By.id("pgc-mrm-dialog-empty"), 5);

    }

    @Test
    public void testUnAssignMultipeAndAssignMultiple() throws Exception {
        accessManageCodeAssignmentsScreen();
        Select dropdown = new Select(driver.findElement(By.id("familyPoId")));
        dropdown.selectByIndex(1);
        changePageLength("25");

        // Initialli I see the buttons disabled
        verifyAssignUnassignButtonState(false);
        // When I click a row
        clickTableRow(1);

        // I see the buttons enabled
        verifyAssignUnassignButtonState(true);

        // When I deselect the row
        clickTableRow(1);
        // I see the buttons disabled
        verifyAssignUnassignButtonState(false);

        // Select 4 rows
        clickTableRow(1);
        clickTableRow(2);
        clickTableRow(3);
        clickTableRow(4);

        // I see the buttons enabled
        verifyAssignUnassignButtonState(true);

        // Also I see PG2 in those rows
        verifyTrialProgramCodeAssociation(1, true, "PG2");
        verifyTrialProgramCodeAssociation(2, true, "PG2");
        verifyTrialProgramCodeAssociation(3, true, "PG2");
        verifyTrialProgramCodeAssociation(4, true, "PG2");

        // click the Unassign button
        clickAndWait("unassignPGCBtn");

        // Then the dialog showup
        waitForElementToBecomeVisible(By.id("pgc-mrm-dialog"), 5);

        // on the popup select
        useSelect2ToPickAnOption("pgc-mrm-sel", "PG2", "PG2 - Cancer Program2");

        // When I click Cancel
        clickAndWait("pgc-mrm-dialog-cancel");

        // Then the dialog closes
        waitForElementToBecomeInvisible(By.id("pgc-mrm-dialog"), 5);

        // the assign button is still enabled
        verifyAssignUnassignButtonState(true);

        // And I see PG2 in those rows
        verifyTrialProgramCodeAssociation(1, true, "PG2");
        verifyTrialProgramCodeAssociation(2, true, "PG2");
        verifyTrialProgramCodeAssociation(3, true, "PG2");
        verifyTrialProgramCodeAssociation(4, true, "PG2");

        // click the Unassign button
        clickAndWait("unassignPGCBtn");

        // Then the dialog showup
        waitForElementToBecomeVisible(By.id("pgc-mrm-dialog"), 5);

        // on the popup select
        useSelect2ToPickAnOption("pgc-mrm-sel", "PG2", "PG2 - Cancer Program2");

        // When I click OK
        clickAndWait("pgc-mrm-dialog-ok");

        // Then the dialog closes
        waitForElementToBecomeInvisible(By.id("pgc-mrm-dialog"), 5);

        // Then I see confirmation
        waitForElementToBecomeVisible(By.id("pgcInfo"), 4);
        assertTrue(selenium
                .isTextPresent("Program Codes were successfully unassigned"));

        // And I see the confirmation go away after 5 seconds
        waitForElementToBecomeVisible(By.id("pgcInfo"), 10);

        // Now I see PG2 no longer associated with those rows
        verifyTrialProgramCodeAssociation(1, false, "PG2");
        verifyTrialProgramCodeAssociation(2, false, "PG2");
        verifyTrialProgramCodeAssociation(3, false, "PG2");
        verifyTrialProgramCodeAssociation(4, false, "PG2");

        // And I see the assign/unassign buttons disabled.
        verifyAssignUnassignButtonState(false);

        // Now select 2 rows
        clickTableRow(3);
        clickTableRow(4);

        // the assign button is still enabled
        verifyAssignUnassignButtonState(true);

        // click the assign button
        clickAndWait("assignPGCBtn");

        // Then the dialog showup
        waitForElementToBecomeVisible(By.id("pgc-madd-dialog"), 5);

        // on the popup select
        useSelect2ToPickAnOption("pgc-madd-sel", "PG2", "PG2 - Cancer Program2");

        // When I click Cancel
        clickAndWait("pgc-madd-dialog-cancel");

        // Then the dialog closes
        waitForElementToBecomeInvisible(By.id("pgc-madd-dialog"), 5);

        // the assign button is still enabled
        verifyAssignUnassignButtonState(true);

        // And still the trials are not associated with PG2
        verifyTrialProgramCodeAssociation(3, false, "PG2");
        verifyTrialProgramCodeAssociation(4, false, "PG2");

        // click the assign button
        clickAndWait("assignPGCBtn");

        // Then the dialog showup
        waitForElementToBecomeVisible(By.id("pgc-madd-dialog"), 5);

        // on the popup select
        useSelect2ToPickAnOption("pgc-madd-sel", "PG2", "PG2 - Cancer Program2");

        // When I click Cancel
        clickAndWait("pgc-madd-dialog-ok");

        // Then the dialog closes
        waitForElementToBecomeInvisible(By.id("pgc-madd-dialog"), 5);

        // Then I see confirmation
        waitForElementToBecomeVisible(By.id("pgcInfo"), 4);
        assertTrue(selenium
                .isTextPresent("Program Codes were successfully assigned"));

        // And I see the confirmation go away after 5 seconds
        waitForElementToBecomeVisible(By.id("pgcInfo"), 10);

        // Now I see PG2 no longer associated with first rows
        verifyTrialProgramCodeAssociation(1, false, "PG2");
        verifyTrialProgramCodeAssociation(2, false, "PG2");

        // And PG2 is associated with the 3rd and 4th row
        verifyTrialProgramCodeAssociation(3, true, "PG2");
        verifyTrialProgramCodeAssociation(4, true, "PG2");

        // the assign button is disabled
        verifyAssignUnassignButtonState(false);

        logoutUser();
    }

    @Test
    public void testReplaceMultipleNothing() throws Exception {
        // Given that I am on Manage screen and none of the trials have program
        // codes
        accessManageCodeAssignmentsScreen();
        removeFromAllStudiesProgramCodes();

        // When I pick the first family
        Select dropdown = new Select(driver.findElement(By.id("familyPoId")));
        dropdown.selectByIndex(1);
        changePageLength("25");

        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody//tr[2]"), 10);

        // Select 2 rows
        clickTableRow(1);
        clickTableRow(2);

        // click the Unassign button
        clickAndWait("replacePGCBtn");

        // Then the dialog showup
        waitForElementToBecomeVisible(By.id("pgc-mrpl-dialog-empty"), 5);

        // When I click OK
        clickAndWait("pgc-mrpl-dialog-empty-cancel");

        // Then the dialog showup
        waitForElementToBecomeInvisible(By.id("pgc-mrpl-dialog-empty"), 5);
    }

    @Test
    public void testReplaceMultipeAndAssignMultiple() throws Exception {

        accessManageCodeAssignmentsScreen();
        Select dropdown = new Select(driver.findElement(By.id("familyPoId")));
        dropdown.selectByIndex(1);
        changePageLength("25");

        // Select 4 rows
        clickTableRow(1);
        clickTableRow(2);
        clickTableRow(3);
        clickTableRow(4);

        // I see the buttons enabled
        verifyAssignUnassignButtonState(true);

        // Also I see PG2 in those rows
        verifyTrialProgramCodeAssociation(1, true, "PG2");
        verifyTrialProgramCodeAssociation(2, true, "PG2");
        verifyTrialProgramCodeAssociation(3, true, "PG2");
        verifyTrialProgramCodeAssociation(4, true, "PG2");

        verifyTrialProgramCodeAssociation(1, false, "PG5");
        verifyTrialProgramCodeAssociation(2, false, "PG5");
        verifyTrialProgramCodeAssociation(3, false, "PG5");
        verifyTrialProgramCodeAssociation(4, false, "PG5");

        // click the Replace button
        clickAndWait("replacePGCBtn");

        // Then the dialog showup
        waitForElementToBecomeVisible(By.id("pgc-mrpl-dialog"), 5);

        // on the popup select
        pickSelect2Item("pgc-mrpl-selone-div", "pgc-mrpl-selone",
                "PG2 - Cancer Program2");

        // makesure that PG2 is disabled in second select box
        verifyDisabledSelect2Item("pgc-mrpl-seltwo-div", "pgc-mrpl-seltwo",
                "PG2 - Cancer Program2");

        // When I click Cancel
        clickAndWait("pgc-mrpl-dialog-cancel");

        // Then the dialog closes
        waitForElementToBecomeInvisible(By.id("pgc-mrpl-dialog"), 5);

        // the assign button is still enabled
        verifyAssignUnassignButtonState(true);

        // And I see PG2 in those rows
        verifyTrialProgramCodeAssociation(1, true, "PG2");
        verifyTrialProgramCodeAssociation(2, true, "PG2");
        verifyTrialProgramCodeAssociation(3, true, "PG2");
        verifyTrialProgramCodeAssociation(4, true, "PG2");

        verifyTrialProgramCodeAssociation(1, false, "PG5");
        verifyTrialProgramCodeAssociation(2, false, "PG5");
        verifyTrialProgramCodeAssociation(3, false, "PG5");
        verifyTrialProgramCodeAssociation(4, false, "PG5");

        // click the Replace button
        clickAndWait("replacePGCBtn");

        // Then the dialog showup
        waitForElementToBecomeVisible(By.id("pgc-mrpl-dialog"), 5);

        // on the popup select
        pickSelect2Item("pgc-mrpl-selone-div", "pgc-mrpl-selone",
                "PG2 - Cancer Program2");
        useSelect2ToPickAnOption("pgc-mrpl-seltwo", "PG5",
                "PG5 - Cancer Program5");

        // When I click OK
        clickAndWait("pgc-mrpl-dialog-ok");

        // Then the dialog closes
        waitForElementToBecomeInvisible(By.id("pgc-mrpl-dialog"), 5);

        // Then I see confirmation
        waitForElementToBecomeVisible(By.id("pgcInfo"), 4);
        assertTrue(selenium
                .isTextPresent("Program Code was successfully replaced"));

        // And I see the confirmation go away after 5 seconds
        waitForElementToBecomeInvisible(By.id("pgcInfo"), 10);

        // Now I see PG2 no longer associated with those rows
        verifyTrialProgramCodeAssociation(1, false, "PG2");
        verifyTrialProgramCodeAssociation(2, false, "PG2");
        verifyTrialProgramCodeAssociation(3, false, "PG2");
        verifyTrialProgramCodeAssociation(4, false, "PG2");

        // And I see PG4 in those rows
        verifyTrialProgramCodeAssociation(1, true, "PG5");
        verifyTrialProgramCodeAssociation(2, true, "PG5");
        verifyTrialProgramCodeAssociation(3, true, "PG5");
        verifyTrialProgramCodeAssociation(4, true, "PG5");

        // And I see the assign/unassign buttons disabled.
        verifyAssignUnassignButtonState(false);

        logoutUser();
    }

    @Test
    public void testFilterByProgramCode() throws Exception {

        accessManageCodeAssignmentsScreen();

        // Also when I change the filter options
        Select dropdown = new Select(driver.findElement(By.id("familyPoId")));
        String value = dropdown.getOptions().get(1).getAttribute("value");

        // When I pass the program code filter parameter
        openAndWait("/registry/siteadmin/managePCAssignmentchangeFamily.action?programCodeId=4&familyPoId="
                + value);

        // Then I should see data filtered
        assertTrue(selenium.isTextPresent("PG4"));

        // I should see that the original filter is preserved
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody/tr[2]"), 10);
        assertTrue(selenium.isTextPresent("Showing 1 to 2 of 2"));

        // When a user changes the filter,
        openAndWait("/registry/siteadmin/managePCAssignmentchangeFamily.action?programCodeId=3&familyPoId="
                + value);

        // then I should see data filtered
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody/tr[3]"), 10);
        assertTrue(selenium.isTextPresent("Showing 1 to 3 of 3"));

        logoutUser();
    }

    @Test
    public void testFunnelFilter() throws Exception {

        accessManageCodeAssignmentsScreen();
        // Then funnel filter is not visible when family is not selected
        waitForElementToGoAway(By.id("fpgc-icon-a"), 0);

        // when I change family
        Select dropdown = new Select(driver.findElement(By.id("familyPoId")));
        dropdown.selectByIndex(1);
        changePageLength("25");
        // then I should see associated trials.
        TrialInfo trial1 = trials.get(1);

        // And I see the funnel filter
        waitForElementToBecomeAvailable(By.id("fpgc-icon-a"), 5);

        // Then I filter by PG5 in funnel
        clickAndWait("fpgc-icon-a");
        pickMultiSelectOptions("fpgc-div", Arrays.asList("5"),
                Arrays.asList("1", "2", "3", "4", "6"));

        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody//td[text()='No data available in table']"),
                5);
        assertTrue(selenium.isTextPresent("Showing 0 to 0 of 0 entries"));

        // Now pick PG1 in the funnel
        clickAndWait("fpgc-icon-a");
        pickMultiSelectOptions("fpgc-div", Arrays.asList("multiselect-all"),
                new ArrayList<String>());

        // filter on trial 1
        waitForElementToBecomeAvailable(By.xpath(String
                .format("//table[@id='trialsTbl']/tbody//tr[@id='trial_%s']",
                        trial1.id)), 5);
        assertFalse(selenium.isTextPresent("Showing 0 to 0 of 0 entries"));

        // click on the program-code dropdown
        final WebElement el = driver.findElement(By.id(trial1.id + "_tra"));
        moveElementIntoView(el);
        el.click();

        // select PG5
        pickSelect2Item(trial1.id + "_trDiv", trial1.id + "_trSel",
                "PG5 - Cancer Program5");

        // indicator disappears
        waitForElementToGoAway(By.id(trial1.id + "_PG5_img"), 10);

        // then we should have PG5 as an option
        waitForElementToBecomeAvailable(By.id(trial1.id + "_PG5_a"), 5);

        // Again pick PG5 in funnel
        moveElementIntoView(By.id("fpgc-icon-a"));
        clickAndWait("fpgc-icon-a");
        pickMultiSelectOptions("fpgc-div", Arrays.asList("5"),
                Arrays.asList("1", "2", "3", "4", "6"));

        // Now I should see only one record in table
        TrialInfo trial2 = trials.get(2);
        waitForElementToGoAway(By.xpath(String
                .format("//table[@id='trialsTbl']/tbody//tr[@id='trial_%s']",
                        trial2.id)), 5);
        assertTrue(selenium.isTextPresent("Showing 1 to 1 of 1"));

        // Now pick PG6 in funnel
        moveElementIntoView(By.id("fpgc-icon-a"));
        clickAndWait("fpgc-icon-a");
        pickMultiSelectOptions("fpgc-div", Arrays.asList("6"),
                Arrays.asList("1", "2", "3", "4", "5"));
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody//td[text()='No data available in table']"),
                5);
        assertTrue(selenium.isTextPresent("Showing 0 to 0 of 0 entries"));

        // Now deselect every thing in funnel
        moveElementIntoView(By.id("fpgc-icon-a"));
        clickAndWait("fpgc-icon-a");
        pickMultiSelectOptions("fpgc-div", Arrays.asList("multiselect-all"),
                new ArrayList<String>()); // select all
        moveElementIntoView(By.id("fpgc-icon-a"));
        clickAndWait("fpgc-icon-a");
        pickMultiSelectOptions("fpgc-div", Arrays.asList("multiselect-all"),
                new ArrayList<String>()); // deselect all
        waitForElementToGoAway(
                By.xpath("//table[@id='trialsTbl']/tbody//td[text()='No data available in table']"),
                5);

        // then table should be refreshed
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody/tr[3]"), 5);
        assertTrue(selenium.isTextPresent("Showing 1 to 11 of 11"));

        // Now pick None in funnel
        clickAndWait("fpgc-icon-a");
        pickMultiSelectOptions("fpgc-div", Arrays.asList("-1"),
                Arrays.asList("1", "2", "3", "4", "5", "6"));
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody//td[text()='No data available in table']"),
                5);
        assertTrue(selenium.isTextPresent("Showing 0 to 0 of 0 entries"));

        // Now via backend disassociate from trial5 and trial6 all program codes
        TrialInfo trial5 = trials.get(5);
        TrialInfo trial6 = trials.get(6);
        removeProgramCodesFromTrial(trial5.id);
        removeProgramCodesFromTrial(trial6.id);

        // Pick None option
        clickAndWait("fpgc-icon-a");
        pickMultiSelectOptions("fpgc-div", Arrays.asList("-1"),
                Arrays.asList("1", "2", "3", "4", "5", "6"));
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody/tr[2]"), 5);
        assertTrue(selenium.isTextPresent("Showing 1 to 2 of 2"));

        logoutUser();
    }

    /**
     * Will pick an option from select2
     * 
     * @param containerId
     *            - the container where the s2 is
     * @param selBoxId
     *            - the id of the s2
     * @param optionLabel
     *            - the option to select
     */
    protected void pickSelect2Item(String containerId, String selBoxId,
            String optionLabel) {
        ((JavascriptExecutor) driver)
                .executeScript("jQuery('div').removeClass( 'table-wrapper' );");
        final By xpath = By
                .xpath(String.format("//div[@id='%s']", containerId));
        waitForElementToBecomeAvailable(xpath, 10);
        moveElementIntoView(xpath);
        waitForElementToBecomeVisible(xpath, 10);

        driver.findElement(By.cssSelector("span.select2-selection__arrow"))
                .click();
        waitForElementToBecomeAvailable(By.xpath(String.format(
                "//ul[@id='select2-%s-results']", selBoxId)), 5);
        driver.findElement(
                By.xpath(String.format(
                        "//ul[@id='select2-%s-results']//li[text()='%s']",
                        selBoxId, optionLabel))).click();

    }

    @SuppressWarnings("deprecation")
    protected void verifyDisabledSelect2Item(String containerId,
            String selBoxId, String optionLabel) {

        waitForElementToBecomeVisible(
                By.xpath(String.format("//div[@id='%s']", containerId)), 10);
        WebElement sitesBox = driver.findElement(By
                .xpath("//span[preceding-sibling::select[@id='" + selBoxId
                        + "']]//input[@type='search']"));
        sitesBox.click();
        boolean elementPresent = s.isElementPresent("select2-" + selBoxId
                + "-results");
        if (!elementPresent) {
            // odd behavior in FF, click again.
            sitesBox.click();
            elementPresent = s.isElementPresent("select2-" + selBoxId
                    + "-results");
        }
        assertTrue(elementPresent);
        driver.findElement(By.xpath(String
                .format("//ul[@id='select2-%s-results']//li[text()='%s' and @aria-disabled='true']",
                        selBoxId, optionLabel)));
    }

    /**
     * Will open the multislect and select few options and dselect others
     * 
     * @param containerId
     *            - the container where select is present
     * @param optsToSelect
     *            - to select
     * @param optsToUnselect
     *            - to unslect
     */
    protected void pickMultiSelectOptions(String containerId,
            List<String> optsToSelect, List<String> optsToUnselect) {
        String buttonXPath = String.format("//div[@id='%s']/div/button",
                containerId);
        driver.findElement(By.xpath(buttonXPath)).click();
        for (String opt : optsToSelect) {
            WebElement el = driver.findElement(By.xpath(String.format(
                    "//div[@id='%s']//input[@value='%s']", containerId, opt)));
            if (!el.isSelected()) {
                el.click();
            }
        }
        for (String opt : optsToUnselect) {
            try {
                WebElement el = driver.findElement(By.xpath(String
                        .format("//div[@id='%s']//input[@value='%s']",
                                containerId, opt)));
                if (el.isSelected()) {
                    el.click();
                }
            } catch (NoSuchElementException ignore) {

            }
        }
        driver.findElement(By.xpath(buttonXPath)).click();

    }

    private void accessManageCodeAssignmentsScreen() throws Exception {
        unassignUserFromGroup("abstractor-ci", "ProgramCodeAdministrator");
        assignUserToGroup("abstractor-ci", "ProgramCodeAdministrator");
        loginAndGoToManageProgramCodeScreen();

    }

    private void loginAndGoToManageProgramCodeScreen() throws Exception {
        loginAndAcceptDisclaimer();
        accessManageAssignmentScreen();
        recreateFamilies();
        recreateTrials();
    }

    private boolean isFamilySelectBoxRendered() {
        try {
            driver.findElement(By.cssSelector(".fn-sb"));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    private boolean isFamilyNameRendered() {
        try {
            driver.findElement(By.cssSelector(".fn-txt"));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    private void recreateFamilies() throws Exception {
        QueryRunner qr = new QueryRunner();
        qr.update(connection, "delete from family");
        qr.update(
                connection,
                String.format(
                        "INSERT INTO family( identifier, po_id, "
                                + "rep_period_end, rep_period_len_months)VALUES (1, 1, '%s', 12)",
                        date(180)));
        qr.update(
                connection,
                String.format(
                        "INSERT INTO family( identifier, po_id, "
                                + "rep_period_end, rep_period_len_months)VALUES (2, 2, '%s', 12)",
                        date(180)));
    }

    private void associateProgramCodes() throws Exception {
        QueryRunner qr = new QueryRunner();
        qr.update(connection, "delete from study_program_code");
        deleteAndReloadProgramCodes(qr);

        assignToAllStudiesProgramCode("PG1");
        assignToAllStudiesProgramCode("PG2");
        assignToAllStudiesProgramCode("VA1");

        // associate PG3 to first two and last trial
        qr.update(
                connection,
                "insert into study_program_code "
                        + "values((select identifier from program_code where program_code='PG3'),"
                        + trials.get(0).id + ")");
        qr.update(
                connection,
                "insert into study_program_code "
                        + "values((select identifier from program_code where program_code='PG3'),"
                        + trials.get(1).id + ")");
        qr.update(
                connection,
                "insert into study_program_code "
                        + "values((select identifier from program_code where program_code='PG3'),"
                        + trials.get(trials.size() - 1).id + ")");

        // associate PG-4 to first two trials.
        qr.update(
                connection,
                "insert into study_program_code "
                        + "values((select identifier from program_code where program_code='PG4'),"
                        + trials.get(0).id + ")");
        qr.update(
                connection,
                "insert into study_program_code "
                        + "values((select identifier from program_code where program_code='PG4'),"
                        + trials.get(1).id + ")");
    }

    /**
     * @param qr
     * @throws SQLException
     */
    protected void deleteAndReloadProgramCodes(QueryRunner qr)
            throws SQLException {
        qr.update(connection, "delete from program_code");
        for (int i = 1; i <= 6; i++) {
            qr.update(
                    connection,
                    String.format(
                            "insert into program_code (identifier, family_id, program_code, program_name, status_code) "
                                    + "values (%1$s,%2$s,'PG%1$s', 'Cancer Program%1$s', 'ACTIVE')",
                            i, 1));
        }

        qr.update(
                connection,
                "insert into program_code (identifier,family_id, program_code, program_name, status_code) "
                        + "values (7,2,'VA1', 'VA Program1', 'ACTIVE')");
        qr.update(
                connection,
                "insert into program_code ( identifier,family_id, program_code, program_name, status_code) "
                        + "values (8,2,'VA2', 'VA Program2', 'ACTIVE')");
    }

    private void removeFromAllStudiesProgramCodes() throws SQLException {
        QueryRunner qr = new QueryRunner();
        qr.update(connection, "delete from study_program_code");
    }

    private void assignToAllStudiesProgramCode(String code) throws SQLException {
        QueryRunner qr = new QueryRunner();
        for (TrialInfo trial : trials) {
            qr.update(
                    connection,
                    "insert into study_program_code "
                            + "values((select identifier from program_code where program_code='"
                            + code + "')," + trial.id + ")");
        }
    }

    private void recreateTrials() throws Exception {
        deactivateAllTrials();
        for (int i = 1; i <= 11; i++) {
            TrialInfo trial = createAcceptedTrial();
            addParticipatingSite(trial,
                    "National Cancer Institute Division of Cancer Prevention",
                    "ACTIVE");
            addSiteInvestigator(trial,
                    "National Cancer Institute Division of Cancer Prevention",
                    "45" + i, "James", "H", "Kennedy",
                    StudySiteContactRoleCode.SUB_INVESTIGATOR.name());
            addSiteInvestigator(trial,
                    "National Cancer Institute Division of Cancer Prevention",
                    "55" + i, "Sony", "K", "Abraham",
                    StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.name());
            trials.add(trial);

        }
        associateProgramCodes();
    }

    private void verifyCSVExport() throws Exception {
        File csv = new File(downloadDir, "program_code_assignments.csv");
        if (csv.exists())
            csv.delete();
        assertFalse(csv.exists());
        clickLinkAndWait("CSV");
        pause(1000);
        assertTrue(csv.exists());
        List<String> lines = FileUtils.readLines(csv);
        assertTrue((lines.size() - 1) == trials.size());
        for (TrialInfo trial : trials) {
            boolean trialPresent = false;
            for (String line : lines) {
                trialPresent |= line.startsWith("\"" + trial.nciID);
            }
            assertTrue(trialPresent);
        }
        if (csv.exists())
            csv.delete();
    }

    private void verifyExcelExport() throws Exception {
        File excel = new File(downloadDir, "program_code_assignments.xlsx");
        if (excel.exists())
            excel.delete();
        assertFalse(excel.exists());
        clickLinkAndWait("Excel");
        pause(1000);

        // assertTrue(excel.exists());
        excel.delete();
    }

    // verify if the buttons are disabled or enabled.
    private void verifyAssignUnassignButtonState(boolean enabled) {
        assertTrue(driver.findElement(By.id("assignPGCBtn")).isEnabled() == enabled);
        assertTrue(driver.findElement(By.id("unassignPGCBtn")).isEnabled() == enabled);
        assertTrue(driver.findElement(By.id("replacePGCBtn")).isEnabled() == enabled);
    }

    private void clickTableRow(int rowNumber) {
        By by = By
                .xpath("//table[@id='trialsTbl']/tbody/tr[" + rowNumber + "]");
        waitForElementToBecomeAvailable(by, 5);
        driver.findElement(by).click();
    }

    private void verifyTrialProgramCodeAssociation(int rowNumber,
            boolean present, String pg) {
        By by = By.xpath("//table[@id='trialsTbl']/tbody/tr[" + rowNumber
                + "]/td[6]");
        waitForElementToBecomeAvailable(by, 5);
        assertTrue(driver.findElement(by).getText().contains(pg) == present);
    }

    private void changePageLength(String number) {
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody/tr[1]"), 10);
        Select s = new Select(driver.findElement(By
                .xpath("//select[@name='trialsTbl_length']")));
        s.selectByVisibleText(number);
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody/tr[11]"), 10);
    }

}
