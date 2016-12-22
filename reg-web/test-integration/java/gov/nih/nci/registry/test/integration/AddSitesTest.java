package gov.nih.nci.registry.test.integration;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import gov.nih.nci.pa.test.integration.support.Batch;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.openqa.selenium.By;

/**
 * @author Denis G. Krylov
 */
@SuppressWarnings("deprecation")
@Batch(number = 1)
public class AddSitesTest extends AbstractRegistrySeleniumTest {

    private static final int WAIT_FOR_ELEMENT_TIMEOUT = 60;

    @Test
    public void testEmptyCriteriaError() throws SQLException {
        goToAddSitesScreen();
        clickAndWait("id=runSearchBtn");
        assertTrue(selenium
                .isTextPresent("Please provide a search criteria; "
                        + "otherwise the number of trials returned may be unmanageable"));
    }

    @Test
    public void testTooManyResults() throws SQLException {
        goToAddSitesScreen();
        for (int i = 0; i < 101; i++) {
            createAcceptedTrial(true);
        }
        selenium.type("id=officialTitle", "-");
        clickAndWait("id=runSearchBtn");
        assertTrue(selenium
                .isTextPresent("Error Message: Search criteria you provided has produced"));
        assertTrue(selenium
                .isTextPresent("(!) matching trials, which is too many to manage at once on this page."
                        + " The limit is 100. If you could, please revise the criteria to produce a smaller number of trials"
                        + " that will be more manageable"));
    }

    /**
     * 
     */
    private void goToAddSitesScreen() {
        loginAndAcceptDisclaimer();
        hoverLink("Register Trial");
        waitForElementToBecomeVisible(By.xpath("//a[text()='Add Sites']"),
                WAIT_FOR_ELEMENT_TIMEOUT);
        clickAndWait("link=Add Sites");
    }

    @Test
    public void testResetButton() throws SQLException {
        goToAddSitesScreen();
        selenium.type("id=identifier", "NCI-2014-00001");
        selenium.type("id=officialTitle", "Trial Title");
        clickAndWait("id=resetBtn");
        assertEquals("", selenium.getValue("id=identifier"));
        assertEquals("", selenium.getValue("id=officialTitle"));
    }

    @Test
    public void testDataTableSort() throws SQLException {
        goToAddSitesScreen();

        deactivateAllTrials();
        TrialInfo trial1 = createAcceptedTrial(true);
        TrialInfo trial2 = createAcceptedTrial(true);

        selenium.type("id=identifier", "NCI-"); // should give only two trials.
        clickAndWait("id=runSearchBtn");

        Set<String> ascending = new TreeSet<String>(Arrays.asList(trial1.nciID,
                trial2.nciID));

        Iterator<String> it = ascending.iterator();
        String smaller = it.next();
        String larger = it.next();

        // Ascending
        clickAndWait("link=Trial Identifier");
        assertTrue(selenium.getText(
                "xpath=//table[@id='trialsTable']/tbody/tr[1]//td[2]")
                .contains(smaller));
        assertTrue(selenium.getText(
                "xpath=//table[@id='trialsTable']/tbody/tr[2]//td[2]")
                .contains(larger));
        // Descending
        clickAndWait("link=Trial Identifier");
        assertTrue(selenium.getText(
                "xpath=//table[@id='trialsTable']/tbody/tr[2]//td[2]")
                .contains(smaller));
        assertTrue(selenium.getText(
                "xpath=//table[@id='trialsTable']/tbody/tr[1]//td[2]")
                .contains(larger));

        // now sort by titles
        ascending = new TreeSet<String>(Arrays.asList(trial1.title,
                trial2.title));

        it = ascending.iterator();
        smaller = it.next();
        larger = it.next();

        // Ascending
        clickAndWait("link=Trial Title");
        assertTrue(selenium.getText(
                "xpath=//table[@id='trialsTable']/tbody/tr[1]//td[3]")
                .contains(smaller));
        assertTrue(selenium.getText(
                "xpath=//table[@id='trialsTable']/tbody/tr[2]//td[3]")
                .contains(larger));
        // Descending
        clickAndWait("link=Trial Title");
        assertTrue(selenium.getText(
                "xpath=//table[@id='trialsTable']/tbody/tr[2]//td[3]")
                .contains(smaller));
        assertTrue(selenium.getText(
                "xpath=//table[@id='trialsTable']/tbody/tr[1]//td[3]")
                .contains(larger));

    }

    @Test
    public void testAddSite() throws Exception {
        TrialInfo trial = createTrialAndBeginAddingSites();

        // Select Investigator
        searchAndSelectPerson(
                By.id("trial_" + trial.id + "_site_0_pi_lookupBtn"), "John",
                "Doe");
        assertTrue(selenium.isTextPresent("Doe, John"));
        assertEquals("1",
                selenium.getValue("id=trial_" + trial.id + "_site_0_pi_poid"));

        selenium.type("id=trial_" + trial.id + "_site_0_localID", "XYZ0001");

        populateStatusHistory(trial);

        clickAndWait("id=saveBtn");
        waitForElementById("summaryTable", WAIT_FOR_ELEMENT_TIMEOUT);

        assertTrue(selenium.getText(
                "xpath=//table[@id='summaryTable']/tbody/tr[1]//td[1]")
                .contains(trial.nciID));
        assertTrue(selenium
                .getText("xpath=//table[@id='summaryTable']/tbody/tr[1]//td[2]")
                .contains(
                        "National Cancer Institute Division of Cancer Prevention"));
        assertTrue(selenium.getText(
                "xpath=//table[@id='summaryTable']/tbody/tr[1]//td[3]")
                .contains("XYZ0001"));
        assertTrue(selenium.getText(
                "xpath=//table[@id='summaryTable']/tbody/tr[1]//td[4]")
                .contains("SUCCESS"));

        driver.findElement(By.className("fa-thumbs-up")).click();
        waitForPageToLoad();
        assertTrue(selenium.isTextPresent("Search Trials"));

        final Number siteID = findParticipatingSite(trial,
                "National Cancer Institute Division of Cancer Prevention",
                "XYZ0001");
        assertNotNull(siteID);

        // Ensure status history properly created.
        List<SiteStatus> hist = getSiteStatusHistory(siteID);
        assertEquals(2, hist.size());

        assertTrue(DateUtils.isSameDay(hist.get(0).statusDate, yesterdayDate));
        assertEquals("IN_REVIEW", hist.get(0).statusCode);
        assertTrue(StringUtils.isBlank(hist.get(0).comments));

        assertTrue(DateUtils.isSameDay(hist.get(1).statusDate, new Date()));
        assertEquals("Approved".toUpperCase(), hist.get(1).statusCode);
        assertEquals("Changed to Approved.", hist.get(1).comments);

    }


    @Test
    public void testAddSiteWithProgramCode() throws Exception {
        TrialInfo trial = createTrialAndBeginAddingSites();


        List<String> codes = getProgramCodesByTrial(trial.id);
        assertEquals(2, codes.size());
        System.out.println(codes);
        // Select Investigator
        searchAndSelectPerson(
                By.id("trial_" + trial.id + "_site_0_pi_lookupBtn"), "John",
                "Doe");
        assertTrue(selenium.isTextPresent("Doe, John"));
        assertEquals("1",
                selenium.getValue("id=trial_" + trial.id + "_site_0_pi_poid"));

        selenium.type("id=trial_" + trial.id + "_site_0_localID", "XYZ0001");

        populateStatusHistory(trial);

        //I see that PG3 & PG4 is already selected
        assertOptionSelected("PG3 - Cancer Program3");
        assertOptionSelected("PG4 - Cancer Program4");

        //When I select PG1 and PG2
        useSelect2ToPickAnOption("pgc_" + trial.id ,"PG1","PG1 - Cancer Program1");
        useSelect2ToPickAnOption("pgc_" + trial.id ,"PG2","PG2 - Cancer Program2");

        //And unselect PG4
        useSelect2ToUnselectOption("PG4 - Cancer Program4");

        clickAndWait("id=saveBtn");
        waitForElementById("summaryTable", WAIT_FOR_ELEMENT_TIMEOUT);

        assertTrue(selenium.getText(
                "xpath=//table[@id='summaryTable']/tbody/tr[1]//td[1]")
                .contains(trial.nciID));

        //When I check the program codes associated with the trial
        codes = getProgramCodesByTrial(trial.id);
        assertFalse(codes.isEmpty());
        //And I see PG1, PG2, PG3 associated with trial
        assertTrue(codes.contains("PG1"));
        assertTrue(codes.contains("PG2"));
        assertTrue(codes.contains("PG3"));

        //Also I see that PG4 is still present, as study site will only add new entries.
        assertTrue(codes.contains("PG4"));

    }

    /**
     * @param trial
     */
    private void populateStatusHistory(TrialInfo trial) {
        addStatus(trial, null, "In Review");

        // Add a comment to In Review.
        selenium.click("xpath=//table[@id='trial_"
                + trial.id
                + "_site_0_trialStatusHistoryTable']/tbody/tr[1]/td[5]/i[@class='fa fa-edit']");
        selenium.type("editComment", "This is initial status");
        selenium.click("xpath=//div[@class='ui-dialog-buttonset']//span[text()='Save']");
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trial_"
                        + trial.id
                        + "_site_0_trialStatusHistoryTable']/tbody/tr[1]/td[position()=3 and text()='This is initial status']"),
                10);

        // Add Active, ensure warning, and delete.
        addStatus(trial, null, "Active");
        assertEquals(
                "Interim status [APPROVED] is missing",
                selenium.getText("xpath=//table[@id='trial_"
                        + trial.id
                        + "_site_0_trialStatusHistoryTable']/tbody/tr[2]/td[4]/div[@class='warning']"));
        deleteStatus(trial, 2);

        // Add Temporarily Closed to Accrual and Intervention, ensure errors,
        // ensure unable to submit,
        // and delete.
        addStatus(trial, null, "Temporarily Closed to Accrual and Intervention");
        assertEquals(
                "Statuses [IN REVIEW] and [TEMPORARILY CLOSED TO ACCRUAL AND INTERVENTION] can not have the same date",
                selenium.getText("xpath=//table[@id='trial_"
                        + trial.id
                        + "_site_0_trialStatusHistoryTable']/tbody/tr[2]/td[4]/div[position()=1 and @class='error']"));
        assertEquals(
                "Interim status [ACTIVE] is missing",
                selenium.getText("xpath=//table[@id='trial_"
                        + trial.id
                        + "_site_0_trialStatusHistoryTable']/tbody/tr[2]/td[4]/div[position()=2 and @class='error']"));
        assertEquals(
                "Interim status [APPROVED] is missing",
                selenium.getText("xpath=//table[@id='trial_"
                        + trial.id
                        + "_site_0_trialStatusHistoryTable']/tbody/tr[2]/td[4]/div[position()=3 and @class='warning']"));
        assertEquals(
                "Interim status [TEMPORARILY CLOSED TO ACCRUAL] is missing",
                selenium.getText("xpath=//table[@id='trial_"
                        + trial.id
                        + "_site_0_trialStatusHistoryTable']/tbody/tr[2]/td[4]/div[position()=4 and @class='warning']"));
        s.click("saveBtn");
        waitForElementToBecomeVisible(
                By.xpath("//div[@id='trial_" + trial.id + "_site_0_errorDiv']"),
                10);
        assertEquals(
                "Recruitment status history for this site has errors; please see below.",
                selenium.getText(
                        "//div[@id='trial_" + trial.id + "_site_0_errorDiv']")
                        .replaceAll("\\s+", " ").trim());
        deleteStatus(trial, 2);

        // Change In Review to Approved.
        editStatus(trial, 1, "", "Approved", "Changed to Approved.");

        // Add In Review with yesterday's date.
        addStatus(trial, yesterday, "In Review");
    }

    protected void editStatus(TrialInfo trial, int row, String date,
            String newStatus, String comment) {
        selenium.click("xpath=//table[@id='trial_" + trial.id
                + "_site_0_trialStatusHistoryTable']/tbody/tr[" + row
                + "]/td[5]/i[@class='fa fa-edit']");
        waitForElementToBecomeVisible(By.id("dialog-edit"), 5);
        assertEquals("Edit Site Recruitment Status",
                selenium.getText("ui-dialog-title-dialog-edit"));
        if (StringUtils.isNotBlank(date)) {
            selenium.type("statusDate", date);
        } else {
            selenium.click("xpath=//span[@class='add-on btn-default' and preceding-sibling::input[@id='statusDate']]");
            clickOnFirstVisible(By.xpath("//td[@class='day active']"));
            clickOnFirstVisible(By
                    .xpath("//div[@class='datepicker']/button[@class='close']"));
        }
        selenium.select("statusCode", "label=" + newStatus);
        selenium.type("editComment", comment);
        selenium.click("xpath=//div[@class='ui-dialog-buttonset']//span[text()='Save']");
        waitForElementToBecomeInvisible(By.id("dialog-edit"), 10);
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trial_" + trial.id
                        + "_site_0_trialStatusHistoryTable']/tbody/tr[" + row
                        + "]/td[position()=1 and text()='"
                        + (StringUtils.isNotBlank(date) ? date : today) + "']"),
                10);
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trial_" + trial.id
                        + "_site_0_trialStatusHistoryTable']/tbody/tr[" + row
                        + "]/td[position()=2 and text()='" + newStatus + "']"),
                10);
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trial_" + trial.id
                        + "_site_0_trialStatusHistoryTable']/tbody/tr[" + row
                        + "]/td[position()=3 and text()='" + comment + "']"),
                10);

    }

    protected final void deleteStatus(TrialInfo trial, int row) {
        selenium.click("xpath=//table[@id='trial_" + trial.id
                + "_site_0_trialStatusHistoryTable']/tbody/tr[" + row
                + "]/td[5]/i[@class='fa fa-trash-o']");
        if (row == 1) {
            waitForElementToBecomeAvailable(
                    By.xpath("//table[@id='trial_"
                            + trial.id
                            + "_site_0_trialStatusHistoryTable']//td[@class='dataTables_empty']"),
                    10);
        } else {
            waitForElementToGoAway(
                    By.xpath("//table[@id='trial_" + trial.id
                            + "_site_0_trialStatusHistoryTable']/tbody/tr["
                            + row + "]"), 10);
        }
    }

    @SuppressWarnings("deprecation")
    protected void addStatus(TrialInfo trial, String date, String status) {
        selenium.select("id=trial_" + trial.id + "_site_0_status", "label="
                + status);
        if (StringUtils.isNotBlank(date)) {
            selenium.type("id=trial_" + trial.id + "_site_0_statusDate", date);
        } else {
            selenium.click("xpath=//span[@class='add-on btn-default' and preceding-sibling::input[@id='trial_"
                    + trial.id + "_site_0_statusDate']]");
            clickOnFirstVisible(By.xpath("//td[@class='day active']"));
            clickOnFirstVisible(By
                    .xpath("//div[@class='datepicker']/button[@class='close']"));
        }
        clickAndWaitAjax("id=trial_" + trial.id + "_site_0_addStatusBtn");
        waitForElementToBecomeVisible(
                By.id("trial_" + trial.id + "_site_0_trialStatusHistoryTable"),
                WAIT_FOR_ELEMENT_TIMEOUT);
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trial_"
                        + trial.id
                        + "_site_0_trialStatusHistoryTable']/tbody//tr//td[position()=2 and text()='"
                        + status + "']"), 10);
    }

    /**
     * @return
     * @throws SQLException
     */
    private TrialInfo createTrialAndBeginAddingSites() throws Exception {
        goToAddSitesScreen();

        deactivateAllTrials();
        TrialInfo trial = createAcceptedTrial(true);
        removeProgramCodesFromTrial(trial.id);
        assignProgramCode(trial, 1, "PG3");
        assignProgramCode(trial, 1, "PG4");

        selenium.type("id=identifier", trial.nciID);
        clickAndWait("id=runSearchBtn");
        waitForElementById("trialsTable", WAIT_FOR_ELEMENT_TIMEOUT);

        // Make sure tooltip for "+" button is there.
        hover(By.id("plussign_" + trial.id));
        pause(1000);
        assertTrue(selenium.isTextPresent("Click here to add sites to "
                + trial.nciID));

        // click on "+" to start adding sites
        selenium.click("xpath=//table[@id='trialsTable']/tbody/tr[1]//td[1]//i");
        waitForElementById("saveCancelDiv", WAIT_FOR_ELEMENT_TIMEOUT);
        pause(1000);

        assertNotNull(driver.findElement(By.className("popover-content")));
        assertTrue(selenium
                .isTextPresent("If you change your mind about entering a particular site, simply leave all the fields empty and it will be ignored on save."));
        assertEquals("3",
                selenium.getValue("id=trial_" + trial.id + "_site_0_org_poid"));

        // Make sure tooltip for PI lookup button is there.
        hover(By.id("trial_" + trial.id + "_site_0_pi_lookupBtn"));
        pause(1000);
        assertTrue(selenium.isTextPresent("Select PI for this site"));
        return trial;
    }

    @Test
    public void testAddSiteNothingEntered() throws Exception {
        TrialInfo trial = createTrialAndBeginAddingSites();
        clickAndWait("id=saveBtn");
        waitForPageToLoad();
        assertTrue(selenium.isTextPresent("No participating sites created."));
        driver.findElement(By.className("fa-thumbs-up")).click();
    }

    @Test
    public void testAddSiteValidationMissingFields() throws Exception {
        TrialInfo trial = createTrialAndBeginAddingSites();
        selenium.type("id=trial_" + trial.id + "_site_0_localID", "XYZ0001");
        clickAndWait("id=saveBtn");
        waitForTextToAppear(By.className("alert-danger"),
                "Please choose a Site Principal Investigator using the lookup",
                WAIT_FOR_ELEMENT_TIMEOUT);
        waitForTextToAppear(By.className("alert-danger"),
                "Please enter a value for Recruitment Status",
                WAIT_FOR_ELEMENT_TIMEOUT);
        waitForTextToAppear(By.className("alert-danger"),
                "A valid Recruitment Status Date is required",
                WAIT_FOR_ELEMENT_TIMEOUT);

    }

    private void searchAndSelectPerson(By buttonToClick, String fname,
            String lname) {
        driver.findElement(buttonToClick).click();
        waitForElementById("popupFrame", WAIT_FOR_ELEMENT_TIMEOUT);
        selenium.selectFrame("popupFrame");
        waitForElementById("search_person_btn", WAIT_FOR_ELEMENT_TIMEOUT);
        selenium.type("id=firstName", fname);
        selenium.type("id=lastName", lname);
        clickAndWait("id=search_person_btn");
        waitForElementById("row", WAIT_FOR_ELEMENT_TIMEOUT);
        selenium.click("//table[@id='row']/tbody/tr[1]/td[8]/button");
        waitForPageToLoad();
        driver.switchTo().defaultContent();
    }
}
