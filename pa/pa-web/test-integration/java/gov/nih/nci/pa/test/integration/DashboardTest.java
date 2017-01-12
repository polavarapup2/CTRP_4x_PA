package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.OnholdReasonCode;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.test.integration.support.Batch;
import gov.nih.nci.pa.util.PAUtil;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

/**
 * 
 * @author dkrylov
 */
@SuppressWarnings({ "deprecation", "unused", "unchecked" })
@Batch(number = 2)
public class DashboardTest extends AbstractTrialStatusTest {

    private static final String MILESTONES_TO_COUNT = "Submission Received Date,Submission Acceptance Date,Administrative Processing Start Date,Ready for Administrative QC Date,Administrative QC Start Date,Scientific Processing Start Date,Ready for Scientific QC Date,Scientific QC Start Date,Ready for Trial Summary Report Date";
    private static final String HOLDS_TO_COUNT = "Submission Incomplete,Submission Incomplete -- Missing Documents,Invalid Grant,Pending CTRP Review,Pending Disease Curation,Pending Person Curation,Pending Organization Curation,Pending Intervention Curation,Other (CTRP),Other (Submitter)";
    private static final String RANGES = "0-3,4-7,8-10,>10";
    private static final int OP_WAIT_TIME = SystemUtils.IS_OS_LINUX ? 10000
            : 2000;

    /**
     * @throws java.lang.Exception
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        restorePaPropertiesToOriginal();
    }

    @Test
    public void testScenarioOfAddingNewOtherHoldCategory() throws Exception {
        deactivateAllTrials();
        changePaProperty("dashboard.counts.onholds", HOLDS_TO_COUNT
                + ",Other (Bad Weather)");
        changePaProperty("studyonhold.reason_category",
                "Submitter,CTRP,Bad Weather");

        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        findAndSelectTrialInDashboard(trial);

        // Add hold with the new category.
        clickAndWait("link=On-hold Information");
        clickAndWait("addButton");
        s.select("reasonCode", "Other");
        s.select("reasonCategoryList", "Bad Weather");
        clickAndWait("addButton");
        assertTrue(s.isTextPresent("Message. Record Created"));

        // Verify Dashboard is picking up the new category.
        goToDashboardSearch();
        s.click("resetBtn");
        s.select("onHoldReason", "value=Other_Bad Weather");
        clickAndWait("searchBtn");
        verifyResultsTabActive();
        assertTrue(isTrialInResultsTab(trial));

        s.click("searchid");
        s.click("resetBtn");
        new Select(driver.findElement(By.id("onHoldReason")))
                .selectByValue("Other_CTRP");
        new Select(driver.findElement(By.id("onHoldReason")))
                .selectByValue("Other_Submitter");
        clickAndWait("searchBtn");
        verifyResultsTabActive();
        assertFalse(isTrialInResultsTab(trial));

        s.click("searchid");
        s.click("resetBtn");
        new Select(driver.findElement(By.id("onHoldReason")))
                .selectByValue("Other_CTRP");
        new Select(driver.findElement(By.id("onHoldReason")))
                .selectByValue("Other_Submitter");
        new Select(driver.findElement(By.id("onHoldReason")))
                .selectByValue("Other_Bad Weather");
        clickAndWait("searchBtn");
        verifyResultsTabActive();
        assertTrue(isTrialInResultsTab(trial));

        // Now verify On-Hold panel picked it up as well.
        clickAndWait("id=dashboardMenuOption");
        clickAndWait("countsid");
        waitForElementToBecomeVisible(
                By.xpath("//table[@id='on_hold_trials_table']//tr[@id='TotalHold']"),
                20);
        verifyPanelWidget("on_hold_trials", "On-Hold Trials");
        verifyHoldCountRow(11, "Other (Bad Weather)", Arrays.asList(trial));
        verifyHoldCountTotal(1);
    }

    /**
     * Testing backend changes made in PO-9027 to handle Other on-hold criteria
     * searches.
     * 
     * @throws Exception
     */
    @Test
    public void testSearchByOnHoldReasonOther() throws Exception {
        deactivateAllTrials();

        TrialInfo grant = createAcceptedTrial();
        addOnHold(grant, OnholdReasonCode.INVALID_GRANT.name(), new Date(),
                null, "Submitter");
        TrialInfo otherSubmitter = createAcceptedTrial();
        addOnHold(otherSubmitter, OnholdReasonCode.OTHER.name(), new Date(),
                null, "Submitter");

        TrialInfo otherCTRP = createAcceptedTrial();
        addOnHold(otherCTRP, OnholdReasonCode.OTHER.name(), new Date(), null,
                "CTRP");

        loginAsSuperAbstractor();
        clickAndWait("id=dashboardMenuOption");

        s.click("searchid");
        s.click("resetBtn");
        s.select("onHoldReason", "value=Invalid Grant");
        clickAndWait("searchBtn");
        verifyResultsTabActive();
        assertTrue(isTrialInResultsTab(grant));
        assertFalse(isTrialInResultsTab(otherCTRP));
        assertFalse(isTrialInResultsTab(otherSubmitter));

        s.click("searchid");
        s.click("resetBtn");
        s.select("onHoldReason", "value=Other_Submitter");
        clickAndWait("searchBtn");
        verifyResultsTabActive();
        assertFalse(isTrialInResultsTab(grant));
        assertFalse(isTrialInResultsTab(otherCTRP));
        assertTrue(isTrialInResultsTab(otherSubmitter));

        s.click("searchid");
        s.click("resetBtn");
        s.select("onHoldReason", "value=Other_CTRP");
        clickAndWait("searchBtn");
        verifyResultsTabActive();
        assertFalse(isTrialInResultsTab(grant));
        assertTrue(isTrialInResultsTab(otherCTRP));
        assertFalse(isTrialInResultsTab(otherSubmitter));

        s.click("searchid");
        s.click("resetBtn");
        new Select(driver.findElement(By.id("onHoldReason")))
                .selectByValue("Other_CTRP");
        new Select(driver.findElement(By.id("onHoldReason")))
                .selectByValue("Other_Submitter");
        clickAndWait("searchBtn");
        verifyResultsTabActive();
        assertFalse(isTrialInResultsTab(grant));
        assertTrue(isTrialInResultsTab(otherCTRP));
        assertTrue(isTrialInResultsTab(otherSubmitter));

        s.click("searchid");
        s.click("resetBtn");
        new Select(driver.findElement(By.id("onHoldReason")))
                .selectByValue("Invalid Grant");
        new Select(driver.findElement(By.id("onHoldReason")))
                .selectByValue("Other_Submitter");
        clickAndWait("searchBtn");
        verifyResultsTabActive();
        assertTrue(isTrialInResultsTab(grant));
        assertFalse(isTrialInResultsTab(otherCTRP));
        assertTrue(isTrialInResultsTab(otherSubmitter));

        s.click("searchid");
        s.click("resetBtn");
        new Select(driver.findElement(By.id("onHoldReason")))
                .selectByValue("Invalid Grant");
        new Select(driver.findElement(By.id("onHoldReason")))
                .selectByValue("Other_CTRP");
        new Select(driver.findElement(By.id("onHoldReason")))
                .selectByValue("Other_Submitter");
        clickAndWait("searchBtn");
        verifyResultsTabActive();
        assertTrue(isTrialInResultsTab(grant));
        assertTrue(isTrialInResultsTab(otherCTRP));
        assertTrue(isTrialInResultsTab(otherSubmitter));

    }

    @Test
    public void testAbstractorsWorkInProgressPanel() throws Exception {
        deactivateAllTrials();

        TrialInfo admin = createAcceptedTrial();
        TrialInfo scientific = createAcceptedTrial();
        TrialInfo admSci = createAcceptedTrial();

        // Admin CheckOut
        loginAsAdminAbstractor();
        clickAndWait("id=dashboardMenuOption");
        clickAndWait("xpath=//table[@id='wl']//td/a[normalize-space(text())='"
                + admin.nciID.replaceFirst("NCI-", "") + "']");
        clickAndWait("link=Admin Check Out");
        logoutPA();

        // Scientific check out.
        loginAsScientificAbstractor();
        clickAndWait("id=dashboardMenuOption");
        clickAndWait("xpath=//table[@id='wl']//td/a[normalize-space(text())='"
                + scientific.nciID.replaceFirst("NCI-", "") + "']");
        clickAndWait("link=Scientific Check Out");
        logoutPA();

        // Admin & Scientific check out.
        loginAsSuperAbstractor();
        clickAndWait("id=dashboardMenuOption");
        clickAndWait("xpath=//table[@id='wl']//td/a[normalize-space(text())='"
                + admSci.nciID.replaceFirst("NCI-", "") + "']");
        clickAndWait("link=Admin/Scientific Check Out");

        // Now test the panel.
        clickAndWait("id=dashboardMenuOption");
        clickAndWait("countsid");
        assertTrue(s.isElementPresent("count_panels_container"));
        waitForElementToBecomeVisible(
                By.xpath("//table[@id='abstractors_work_table']//tr[3]"), 30);

        // Panel must be collapsible, but initially open.
        verifyPanelWidget("abstractors_work", "Abstractors Work in Progress");

        // Verify table headers.
        assertEquals("Abstractor (Role)",
                s.getText("//table[@id='abstractors_work_table']//th[1]"));
        assertEquals("Admin",
                s.getText("//table[@id='abstractors_work_table']//th[2]"));
        assertEquals("Scientific",
                s.getText("//table[@id='abstractors_work_table']//th[3]"));
        assertEquals("Admin & Scientific",
                s.getText("//table[@id='abstractors_work_table']//th[4]"));

        // Verify counts.
        assertEquals("ctrpsubstractor (SU)",
                s.getText("//table[@id='abstractors_work_table']//tr[1]/td[1]"));
        assertEquals("0",
                s.getText("//table[@id='abstractors_work_table']//tr[1]/td[2]"));
        assertEquals("0",
                s.getText("//table[@id='abstractors_work_table']//tr[1]/td[3]"));
        assertEquals("1",
                s.getText("//table[@id='abstractors_work_table']//tr[1]/td[4]"));

        assertEquals("admin-ci (AS)",
                s.getText("//table[@id='abstractors_work_table']//tr[2]/td[1]"));
        assertEquals("1",
                s.getText("//table[@id='abstractors_work_table']//tr[2]/td[2]"));
        assertEquals("0",
                s.getText("//table[@id='abstractors_work_table']//tr[2]/td[3]"));
        assertEquals("0",
                s.getText("//table[@id='abstractors_work_table']//tr[2]/td[4]"));

        assertEquals("scientific-ci (SC)",
                s.getText("//table[@id='abstractors_work_table']//tr[3]/td[1]"));
        assertEquals("0",
                s.getText("//table[@id='abstractors_work_table']//tr[3]/td[2]"));
        assertEquals("1",
                s.getText("//table[@id='abstractors_work_table']//tr[3]/td[3]"));
        assertEquals("0",
                s.getText("//table[@id='abstractors_work_table']//tr[3]/td[4]"));

        // Ensure clicking on user name brings up trial in results.
        clickAndWait("//table[@id='abstractors_work_table']//tr[1]/td[1]/a");
        verifyResultsTabActive();
        assertTrue(isTrialInResultsTab(admSci));
        clickAndWait("id=dashboardMenuOption");
        clickAndWait("countsid");
        waitForElementToBecomeVisible(
                By.xpath("//table[@id='abstractors_work_table']//tr[3]"), 20);

        clickAndWait("//table[@id='abstractors_work_table']//tr[3]/td[1]/a");
        verifyResultsTabActive();
        assertTrue(isTrialInResultsTab(scientific));
        clickAndWait("id=dashboardMenuOption");
        clickAndWait("countsid");
        waitForElementToBecomeVisible(
                By.xpath("//table[@id='abstractors_work_table']//tr[3]"), 20);

        clickAndWait("//table[@id='abstractors_work_table']//tr[2]/td[1]/a");
        verifyResultsTabActive();
        assertTrue(isTrialInResultsTab(admin));

    }

    @Test
    public void testTrialDistPanel() throws Exception {
        deactivateAllTrials();

        // Prepare trials with holds.
        int total = 0;
        final LinkedHashMap<String, List<TrialInfo>> map = new LinkedHashMap<>();
        final List<TrialInfo> allTrialsCreated = new ArrayList<>();
        for (String range : RANGES.split(",")) {
            List<TrialInfo> trials = registerBunchOfTrialsWithinRange(range);
            map.put(range, trials);
            allTrialsCreated.addAll(trials);
            total += trials.size();
        }

        // Now test the panel.
        loginAsSuperAbstractor();
        clickAndWait("id=dashboardMenuOption");
        clickAndWait("countsid");
        assertTrue(s.isElementPresent("count_panels_container"));
        waitForElementToBecomeVisible(
                By.xpath("//table[@id='trial_dist_table']//tr[4]"), 20);

        // Panel must be collapsible, but initially open.
        verifyPanelWidget("trial_dist", "Trial Submission Distribution");

        // Verify table headers.
        assertEquals("Business Days Since Trial Submission",
                s.getText("//table[@id='trial_dist_table']//th[1]"));
        assertEquals("Trial Count",
                s.getText("//table[@id='trial_dist_table']//th[2]"));

        // Verify counts and their order.
        int row = 1;
        for (String code : RANGES.split(",")) {
            List<TrialInfo> trials = map.get(code);
            verifyRangeCountRow(row, code, trials);
            row++;
        }

        // The panel must only display non-rejected non-terminated
        // trials. Add these conditions to trials
        // and verify zero counts.
        for (TrialInfo trial : allTrialsCreated) {
            int index = allTrialsCreated.indexOf(trial);
            if (index % 2 == 0) {
                addDWS(trial, "SUBMISSION_TERMINATED");
            } else {
                addDWS(trial, "REJECTED");
            }

        }
        refresh();
        clickAndWait("countsid");
        waitForElementToBecomeVisible(
                By.xpath("//table[@id='trial_dist_table']//tr[4]"), 20);
        row = 1;
        for (String code : RANGES.split(",")) {
            verifyRangeCountRow(row, code, ListUtils.EMPTY_LIST);
            row++;
        }

    }

    @Test
    public void testTrialCountsPanel() throws Exception {
        deactivateAllTrials();

        //prepare the trials
        List<TrialInfo> allTrials = registerBunchOfTrialsForCountsByDate();


        // Now test the panel.
        loginAsSuperAbstractor();
        clickAndWait("id=dashboardMenuOption");
        clickAndWait("countsid");
        assertTrue(s.isElementPresent("count_panels_container"));
        waitForElementToBecomeVisible(
                By.xpath("//table[@id='trials_bydate_table']//tr[2]"), 20);

        // Panel must be collapsible, but initially open.
        verifyPanelWidget("trials_bydate", "Trial Counts by Date");
        
        
     


        //Verify that no data comes when filtered out of range.
        s.type("countRangeFrom", "08/10/2015");
        s.type("countRangeTo", "08/11/2015");
        s.click("btnDisplayCounts");
        waitForTextToAppear(By.xpath("//table[@id='trials_bydate_table']//tr[1]/td[1]"), "08/10/2015", 30);
        waitForElementToBecomeVisible(By.xpath("//table[@id='trials_bydate_table']//tr[2]"), 20);
        assertEquals("", s.getText("//tr[@id='TotalByCount']/td[2]"));

        // Verify that data is filtered properly
        Date from = new Date();
        from =  PAUtil.isBusinessDay(from) ? from : PAUtil.addBusinessDays(from , -1);
        String fromDate = DateFormatUtils.format(PAUtil.addBusinessDays(from, -1), PAUtil.DATE_FORMAT);
        s.type("countRangeFrom", fromDate);
        s.type("countRangeTo", DateFormatUtils.format(PAUtil.addBusinessDays(new Date(), 1), PAUtil.DATE_FORMAT));

        //click on display counts and wait for reload
        s.click("btnDisplayCounts");
        waitForTextToAppear(By.xpath("//table[@id='trials_bydate_table']//tr[1]/td[1]"), fromDate, 30);
        waitForElementToBecomeVisible(By.xpath("//table[@id='trials_bydate_table']//tr[2]"), 20);
        assertEquals("6", s.getText("//tr[@id='TotalByCount']/td[2]"));

        //refresh and check data that is displayed
        refresh();
        clickAndWait("countsid");
        waitForElementToBecomeVisible(By.xpath("//table[@id='trials_bydate_table']//tr[2]"), 20);
        assertEquals("10", s.getText("//tr[@id='TotalByCount']/td[2]"));
        assertEquals(DateFormatUtils.format(PAUtil.addBusinessDays(new Date(), -10), PAUtil.DATE_FORMAT), s.getValue("countRangeFrom"));
        assertEquals(DateFormatUtils.format(PAUtil.addBusinessDays(new Date(), 10), PAUtil.DATE_FORMAT), s.getValue("countRangeTo"));

        //click on a link and verify data is properly displayed in results tab
        clickAndWait("//tr[@id='TotalByCount']/td[2]/a");
        verifySearchByDistInResultsTab(allTrials);

        //Clear the filter range, and re-filter on a data range with which we can verify expected range.
        clickAndWait("countsid");
        waitForElementToBecomeVisible(By.xpath("//table[@id='trials_bydate_table']//tr[2]"), 20);
        s.type("countRangeFrom", "08/10/2015");
        s.type("countRangeTo", "08/11/2015");
        s.click("btnDisplayCounts");
        waitForTextToAppear(By.xpath("//table[@id='trials_bydate_table']//tr[1]/td[1]"), "08/10/2015", 30);
        assertEquals("", s.getText("//tr[@id='TotalByCount']/td[4]"));

        s.type("countRangeFrom", DateFormatUtils.format(PAUtil.addBusinessDays(from, -1), PAUtil.DATE_FORMAT));
        s.type("countRangeTo", DateFormatUtils.format(PAUtil.addBusinessDays(new Date(), 10), PAUtil.DATE_FORMAT));
        s.click("btnDisplayCounts");
        waitForTextToAppear(By.xpath("//table[@id='trials_bydate_table']//tr[1]/td[1]"),
                DateFormatUtils.format(PAUtil.addBusinessDays(from, -1), PAUtil.DATE_FORMAT), 30);
        waitForElementToBecomeVisible(By.xpath("//table[@id='trials_bydate_table']//tr[2]"), 20);
        assertEquals("10", s.getText("//tr[@id='TotalByCount']/td[4]"));

    }

    @Test
    public void testOnHoldPanel() throws Exception {
        deactivateAllTrials();

        // Prepare trials with holds.
        int total = 0;
        final LinkedHashMap<String, List<TrialInfo>> map = new LinkedHashMap<>();
        final List<TrialInfo> allTrialsCreated = new ArrayList<>();
        for (String code : HOLDS_TO_COUNT.split(",")) {
            List<TrialInfo> trials = registerBunchOfTrialsWithHold(code);
            map.put(code, trials);
            allTrialsCreated.addAll(trials);
            total += trials.size();
        }

        // Now test the panel.
        loginAsSuperAbstractor();
        clickAndWait("id=dashboardMenuOption");
        clickAndWait("countsid");
        assertTrue(s.isElementPresent("count_panels_container"));
        waitForElementToBecomeVisible(
                By.xpath("//table[@id='on_hold_trials_table']//tr[@id='TotalHold']"),
                20);

        // Panel must be collapsible, but initially open.
        verifyPanelWidget("on_hold_trials", "On-Hold Trials");

        // Verify table headers.
        assertEquals("On-Hold Reason",
                s.getText("//table[@id='on_hold_trials_table']//th[1]"));
        assertEquals("Trial Count",
                s.getText("//table[@id='on_hold_trials_table']//th[2]"));

        // Verify counts and their order.
        int row = 1;
        for (String code : HOLDS_TO_COUNT.split(",")) {
            List<TrialInfo> trials = map.get(code);
            verifyHoldCountRow(row, code, trials);
            row++;
        }

        // Verify Total.
        verifyHoldCountTotal(total);

        // The panel must only display non-rejected non-terminated
        // trials. Add these conditions to trials
        // and verify zero counts.
        for (TrialInfo trial : allTrialsCreated) {
            int index = allTrialsCreated.indexOf(trial);
            if (index % 2 == 0) {
                addDWS(trial, "SUBMISSION_TERMINATED");
            } else {
                addDWS(trial, "REJECTED");
            }

        }
        refresh();
        clickAndWait("countsid");
        waitForElementToBecomeVisible(
                By.xpath("//table[@id='on_hold_trials_table']//tr[@id='TotalHold']"),
                20);
        row = 1;
        for (String code : HOLDS_TO_COUNT.split(",")) {
            verifyHoldCountRow(row, code, ListUtils.EMPTY_LIST);
            row++;
        }
        verifyHoldCountTotal(0);

    }

    private void verifyHoldCountRow(int row, String code, List<TrialInfo> trials) {
        // Code and count are correct.
        assertEquals(
                code,
                s.getText("//table[@id='on_hold_trials_table']/tbody/tr[" + row
                        + "]/td[1]"));
        final String countLinkPath = "//table[@id='on_hold_trials_table']/tbody/tr["
                + row + "]/td[2]/a";
        assertEquals(trials.size() + "", s.getText(countLinkPath));

        // Count link is highlighted
        assertEquals("underline", driver.findElement(By.xpath(countLinkPath))
                .getCssValue("text-decoration"));

        // Clicking on link should bring up Results.
        clickAndWait(countLinkPath);
        verifySearchByDistInResultsTab(trials);
        s.click("countsid");

    }

    private void verifyRangeCountRow(int row, String range,
            List<TrialInfo> trials) {
        // Code and count are correct.
        assertEquals(
                range,
                s.getText("//table[@id='trial_dist_table']/tbody/tr[" + row
                        + "]/td[1]"));
        final String countLinkPath = "//table[@id='trial_dist_table']/tbody/tr["
                + row + "]/td[2]/a";
        assertEquals(trials.size() + "", s.getText(countLinkPath));

        // Count link is highlighted
        assertEquals("underline", driver.findElement(By.xpath(countLinkPath))
                .getCssValue("text-decoration"));

        // Clicking on link should bring up Results.
        clickAndWait(countLinkPath);
        verifySearchByDistInResultsTab(trials);
        refresh();
        verifySearchByDistInResultsTab(trials);
        s.click("countsid");

    }

    /**
     * @param trials
     */
    private void verifySearchByDistInResultsTab(List<TrialInfo> trials) {
        verifyResultsTabActive();
        if (trials.isEmpty()) {
            assertTrue(s.isTextPresent("Nothing found to display."));
        } else if (trials.size() == 1) {
            assertTrue(s.isTextPresent("One trial found."));
        } else {
            assertTrue(s.isTextPresent(trials.size()
                    + " trials found, displaying all trials."));
        }
        for (TrialInfo trialInfo : trials) {
            assertTrue(isTrialInResultsTab(trialInfo));
        }
    }

    @Test
    public void testMilestonesInProgressPanel() throws Exception {
        deactivateAllTrials();

        int total = 0;
        final LinkedHashMap<String, List<TrialInfo>> map = new LinkedHashMap<>();
        final List<TrialInfo> allTrialsCreated = new ArrayList<>();
        for (String code : MILESTONES_TO_COUNT.split(",")) {
            List<TrialInfo> trials = registerBunchOfTrialsWithMilestone(code);
            map.put(code, trials);
            allTrialsCreated.addAll(trials);
            total += trials.size();
        }

        // Now test the panel.
        loginAsSuperAbstractor();
        clickAndWait("id=dashboardMenuOption");
        clickAndWait("countsid");
        assertTrue(s.isElementPresent("count_panels_container"));
        waitForElementToBecomeVisible(
                By.xpath("//table[@id='milestones_in_progress_table']//tr[@id='TotalMilestone']"),
                20);

        // Panel must be collapsible, but initially open.
        verifyPanelWidget("milestones_in_progress", "Milestones in Progress");

        // Verify table headers.
        assertEquals("Milestone (Excluding on-hold)",
                s.getText("//table[@id='milestones_in_progress_table']//th[1]"));
        assertEquals("Trial Count",
                s.getText("//table[@id='milestones_in_progress_table']//th[2]"));

        // Verify counts and their order.
        int row = 1;
        for (String code : MILESTONES_TO_COUNT.split(",")) {
            List<TrialInfo> trials = map.get(code);
            verifyMilestoneCountRow(row, code, trials);
            row++;
        }

        // Verify Total.
        verifyMilestoneCountTotal(total);

        // The panel must only display off-hold non-rejected non-terminated
        // trials. Add these conditions to trials
        // and verify zero counts.
        for (TrialInfo trial : allTrialsCreated) {
            int index = allTrialsCreated.indexOf(trial);
            if (index % 3 == 0) {
                addOnHold(trial, "SUBMISSION_INCOM", date("06/01/2015"), null,
                        "Submitter");
            } else if (index % 3 == 1) {
                addDWS(trial, "REJECTED");
            } else {
                addDWS(trial, "SUBMISSION_TERMINATED");
            }

        }
        refresh();
        clickAndWait("countsid");
        waitForElementToBecomeVisible(
                By.xpath("//table[@id='milestones_in_progress_table']//tr[@id='TotalMilestone']"),
                20);
        row = 1;
        for (String code : MILESTONES_TO_COUNT.split(",")) {
            verifyMilestoneCountRow(row, code, ListUtils.EMPTY_LIST);
            row++;
        }
        verifyMilestoneCountTotal(0);

        // Non-super abstractors should not see the panel.
        logoutPA();
        loginAsAdminAbstractor();
        clickAndWait("id=dashboardMenuOption");
        assertFalse(s.isElementPresent("count_panels_container"));
        logoutPA();
        loginAsScientificAbstractor();
        clickAndWait("id=dashboardMenuOption");
        assertFalse(s.isElementPresent("count_panels_container"));
        logoutPA();

    }

    private void verifyMilestoneCountTotal(int total) {
        assertEquals(
                "Total",
                s.getText("//table[@id='milestones_in_progress_table']//tr[@id='TotalMilestone']/td[1]"));
        assertEquals(
                total + "",
                s.getText("//table[@id='milestones_in_progress_table']//tr[@id='TotalMilestone']/td[2]"));

    }

    private void verifyHoldCountTotal(int total) {
        assertEquals(
                "Total",
                s.getText("//table[@id='on_hold_trials_table']//tr[@id='TotalHold']/td[1]"));
        assertEquals(
                total + "",
                s.getText("//table[@id='on_hold_trials_table']//tr[@id='TotalHold']/td[2]"));

    }

    private void verifyMilestoneCountRow(int row, String code,
            List<TrialInfo> trials) {
        // Code and count are correct.
        assertEquals(
                code,
                s.getText("//table[@id='milestones_in_progress_table']/tbody/tr["
                        + row + "]/td[1]"));
        final String countLinkPath = "//table[@id='milestones_in_progress_table']/tbody/tr["
                + row + "]/td[2]/a";
        assertEquals(trials.size() + "", s.getText(countLinkPath));

        // Count link is highlighted
        assertEquals("underline", driver.findElement(By.xpath(countLinkPath))
                .getCssValue("text-decoration"));

        // Clicking on link should bring up Results.
        clickAndWait(countLinkPath);
        verifySearchByDistInResultsTab(trials);
        s.click("countsid");

    }

    private void verifyPanelWidget(String id, String title) {
        assertTrue(s.isElementPresent(id));
        assertEquals(title, s.getText("//div[@id='" + id + "']/h3").trim());
        assertTrue(s.isVisible("//div[@id='" + id + "']/div[@role='tabpanel']"));

        // Collapse.
        s.click("//div[@id='" + id + "']/h3");
        waitForElementToBecomeInvisible(
                By.xpath("//div[@id='" + id + "']/div[@role='tabpanel']"), 5);

        // Expand
        s.click("//div[@id='" + id + "']/h3");
        waitForElementToBecomeVisible(
                By.xpath("//div[@id='" + id + "']/div[@role='tabpanel']"), 5);
        pause(2000);

    }

    private List<TrialInfo> registerBunchOfTrialsWithMilestone(String code)
            throws SQLException {
        List<TrialInfo> list = new ArrayList<>();
        for (int i = 1; i < new Random().nextFloat() * 6; i++) {
            TrialInfo trial = createAcceptedTrial();
            addMilestone(trial, MilestoneCode.getByCode(code).name());
            list.add(trial);
        }
        return list;
    }

    private List<TrialInfo> registerBunchOfTrialsWithinRange(String range)
            throws SQLException {
        List<TrialInfo> list = new ArrayList<>();
        // find a number that falls into the given range.
        for (int n = 0; n < 1000; n++) {
            if (PAUtil.isInRange(n, range)) {
                for (int i = 1; i < new Random().nextFloat() * 20; i++) {
                    TrialInfo trial = createAcceptedTrial();
                    moveSubmissionDateBackByBizDays(trial, n);
                    list.add(trial);
                }
                break;
            }
        }
        return list;
    }

    private void moveSubmissionDateBackByBizDays(TrialInfo trial, int days)
            throws SQLException {
        Date date = new Date();
        do {
            if (PAUtil.isBusinessDay(date)) {
                days--;
            }
        } while (days >= 0 && (date = DateUtils.addDays(date, -1)) != null);

        new QueryRunner().update(connection,
                "update study_protocol set date_last_created=" + jdbcTs(date)
                        + " where identifier=" + trial.id);
    }

    private List<TrialInfo> registerBunchOfTrialsForCountsByDate() throws SQLException {
        List<TrialInfo> list = new ArrayList<>();
        for(int i = 1; i <= 5 ; i++) {
            TrialInfo trial = createAcceptedTrial();
            moveSubmissionDateBackByBizDays(trial, i);
            list.add(trial);
        }
        for(int i = 1; i <= 5 ; i++) {
            TrialInfo trial = createSubmittedTrial();
            moveSubmissionDateBackByBizDays(trial, 1);
            list.add(trial);
        }
        return list;

    }

    private List<TrialInfo> registerBunchOfTrialsWithHold(String code)
            throws SQLException {
        List<TrialInfo> list = new ArrayList<>();
        String enumCode = code.replaceAll("\\s+\\(.*?\\)$", "");
        String cat = enumCode.equalsIgnoreCase("Other") ? code.replaceFirst(
                "^.*?\\(", "").replaceFirst("\\)", "") : "CTRP";
        for (int i = 1; i < new Random().nextFloat() * 6; i++) {
            TrialInfo trial = createAcceptedTrial();
            addDWS(trial, DocumentWorkflowStatusCode.ON_HOLD.name());
            addOnHold(trial, OnholdReasonCode.getByCode(enumCode).name(),
                    new Date(), null, cat);
            list.add(trial);
        }
        return list;
    }

    @Test
    public void testExpectedAbstractionCompletionDate() throws Exception {
        loginAsSuperAbstractor();
        TrialInfo trial = createAcceptedTrial();
        clickAndWait("id=dashboardMenuOption");
        verifyWorkfloadTabActive();
        assertTrue(isTrialInWorkloadTab(trial));

        // Initial value is 04/30/2014, which is 10 biz days after submission.
        assertEquals("04/30/2014", getOverrideSpan(trial).getText());
        assertEquals("none",
                getOverrideSpan(trial).getCssValue("text-decoration"));

        // Override date & comment.
        populateOverrideInfoAndBringUpConfirmation(trial);

        // 'No' should abort.
        s.click("//div[@aria-describedby='date-override-warning']//button//span[text()='No']");
        assertFalse(s.isVisible("date-override-warning"));
        assertFalse(s.isVisible("abstraction-date-override"));

        // Bring it up again and finally submit the override.
        populateOverrideInfoAndBringUpConfirmation(trial);
        s.click("//div[@aria-describedby='date-override-warning']//button//span[text()='Yes']");

        // AJAX fires in background; upon completion the date should refresh and
        // be underlined with a tooltip.
        verifyOverrideSpanAfterSubmit(
                trial,
                "Moved forward to 05/01/2014 <script>window.location.href='google.com'</script><br/>");

        // Refresh Dashboard and make sure the date is still underlined, etc.
        // However, now tooltip text should have been filtered by CSS filter.
        refresh();
        verifyOverrideSpanAfterSubmit(
                trial,
                "Moved forward to 05/01/2014 script>window.location.href='google.com'script><br/>");

        // Verify backend.
        assertTrue(DateUtils.isSameDay(
                date("05/01/2014"),
                (Date) getTrialField(trial,
                        "expected_abstraction_completion_date")));
        assertEquals(
                "Moved forward to 05/01/2014 script>window.location.href='google.com'script><br/>",
                getTrialField(trial, "expected_abstraction_completion_comments"));

        // Verify this requirement: if the new date value is the same as the
        // calculated value, do not mark the field as being overridden.
        getOverrideIcon(trial).click();
        assertTrue(s.isVisible("abstraction-date-override"));
        WebElement ok = driver
                .findElement(By
                        .xpath("//div[@aria-describedby='abstraction-date-override']//button//span[text()='Save']"));
        assertEquals("05/01/2014", s.getValue("newCompletionDate"));
        assertEquals(
                "Moved forward to 05/01/2014 script>window.location.href='google.com'script><br/>",
                s.getValue("newCompletionDateComments"));
        ((JavascriptExecutor) driver)
                .executeScript("jQuery('#newCompletionDate').val('04/30/2014')");
        s.type("newCompletionDateComments", "Moved back.");
        waitForElementToBecomeAvailable(
                By.xpath("//div[@id='abstraction-date-override']//span[@class='info charcounter' and text()='989 characters left']"),
                3); // ensure char counter working.
        ok.click();
        assertTrue(s.isVisible("date-override-warning"));
        s.click("//div[@aria-describedby='date-override-warning']//button//span[text()='Yes']");
        verifySpanNotOverriddenIfMatchesCalculatedDate(trial);
        refresh();
        verifySpanNotOverriddenIfMatchesCalculatedDate(trial);

        // However, the date/comment are still in backend.
        assertTrue(DateUtils.isSameDay(
                date("04/30/2014"),
                (Date) getTrialField(trial,
                        "expected_abstraction_completion_date")));
        assertEquals(
                "Moved back.",
                getTrialField(trial, "expected_abstraction_completion_comments"));

        // Finally, make sure Admin/Scientific abstractors can't see the edit
        // icon.
        trial = createAcceptedTrial();
        refresh();
        assertTrue(isTrialInWorkloadTab(trial));
        populateOverrideInfoAndBringUpConfirmation(trial);
        s.click("//div[@aria-describedby='date-override-warning']//button//span[text()='Yes']");
        verifyOverrideSpanAfterSubmit(
                trial,
                "Moved forward to 05/01/2014 <script>window.location.href='google.com'</script><br/>");

        logoutPA();
        loginAsAdminAbstractor();
        clickAndWait("id=dashboardMenuOption");
        verifyWorkfloadTabActive();
        assertTrue(isTrialInWorkloadTab(trial));
        verifyOverrideSpanAfterSubmit(
                trial,
                "Moved forward to 05/01/2014 script>window.location.href='google.com'script><br/>");
        // However, no "edit" icon!
        assertNull(getOverrideIcon(trial));

        logoutPA();
        loginAsScientificAbstractor();
        clickAndWait("id=dashboardMenuOption");
        verifyWorkfloadTabActive();
        assertTrue(isTrialInWorkloadTab(trial));
        verifyOverrideSpanAfterSubmit(
                trial,
                "Moved forward to 05/01/2014 script>window.location.href='google.com'script><br/>");
        // However, no "edit" icon!
        assertNull(getOverrideIcon(trial));

    }

    /**
     * @param trial
     */
    private void verifySpanNotOverriddenIfMatchesCalculatedDate(TrialInfo trial) {
        waitForElementToBecomeAvailable(
                By.xpath("//span[@data-overridden='false' and @data-study-protocol-id='"
                        + trial.id + "']"), 15);
        assertEquals("04/30/2014", getOverrideSpan(trial).getText());
        assertEquals("none",
                getOverrideSpan(trial).getCssValue("text-decoration"));
        hover(getOverrideSpan(trial));
        pause(1000);
        waitForElementToGoAway(
                By.xpath("//div[@class='ui-tooltip-content' and text()='Moved back.']"),
                5);
    }

    /**
     * @param trial
     * @param textExpectedInToolTip
     */
    private void verifyOverrideSpanAfterSubmit(TrialInfo trial,
            final String textExpectedInToolTip) {
        waitForElementToBecomeAvailable(
                By.xpath("//span[@data-overridden='true' and @data-study-protocol-id='"
                        + trial.id + "']"), 15);
        assertEquals("05/01/2014", getOverrideSpan(trial).getText());
        assertEquals("underline",
                getOverrideSpan(trial).getCssValue("text-decoration"));
        hover(getOverrideSpan(trial));
        waitForElementToBecomeVisible(
                By.xpath("//div[@class='ui-tooltip-content']"), 5);
        assertEquals(textExpectedInToolTip,
                s.getText("//div[@class='ui-tooltip-content']"));
    }

    /**
     * @param trial
     * @return
     */
    private WebElement populateOverrideInfoAndBringUpConfirmation(
            TrialInfo trial) {
        // Bring up edit dialog and verify
        getOverrideIcon(trial).click();
        assertTrue(s.isVisible("abstraction-date-override"));
        assertEquals("Expected Abstraction Completion Date",
                getDialogTitle("abstraction-date-override"));
        assertEquals("Trial Submission Date: 04/16/2014",
                s.getText("//div[@id='abstraction-date-override']/p[1]"));
        assertEquals("Expected Abstraction Completion Date *",
                s.getText("//div[@id='abstraction-date-override']/div[1]"));
        assertEquals("Comments *",
                s.getText("//div[@id='abstraction-date-override']/div[3]"));

        // Make sure Close icon and Cancel button are working.
        s.click("//div[@aria-describedby='abstraction-date-override']//button[@title='Close']");
        assertFalse(s.isVisible("abstraction-date-override"));
        getOverrideIcon(trial).click();
        assertTrue(s.isVisible("abstraction-date-override"));
        s.click("//div[@aria-describedby='abstraction-date-override']//button//span[text()='Cancel']");
        assertFalse(s.isVisible("abstraction-date-override"));
        getOverrideIcon(trial).click();
        assertTrue(s.isVisible("abstraction-date-override"));

        // Date field should be pre-populated with current date; Comments are
        // empty.
        assertEquals("04/30/2014", s.getValue("newCompletionDate"));
        assertEquals("", s.getValue("newCompletionDateComments"));

        // Check field validation.
        WebElement ok = driver
                .findElement(By
                        .xpath("//div[@aria-describedby='abstraction-date-override']//button//span[text()='Save']"));
        ok.click();
        verifyValidationError("Please provide a comment.");
        ((JavascriptExecutor) driver)
                .executeScript("jQuery('#newCompletionDate').val(null)");
        ok.click();
        verifyValidationError("Please specify a date.");

        // Verify Calendar shows up and defaults to the date selected in box.
        ((JavascriptExecutor) driver)
                .executeScript("jQuery('#newCompletionDate').val('04/30/2014')");
        s.click("//input[@id='newCompletionDate']/following-sibling::img[1]");
        waitForElementToBecomeVisible(By.id("ui-datepicker-div"), 3);
        assertTrue(s
                .isVisible("//div[@id='ui-datepicker-div']//td[@data-year='2014' and @data-month='3']//a[@class='ui-state-default ui-state-active' and text()='30']"));
        s.click("//input[@id='newCompletionDate']/following-sibling::img[1]");
        waitForElementToBecomeInvisible(By.id("ui-datepicker-div"), 3);

        // Now override the date as 05/01/2014 using the calendar.
        s.click("//input[@id='newCompletionDate']/following-sibling::img[1]");
        waitForElementToBecomeVisible(By.id("ui-datepicker-div"), 3);
        s.click("//div[@id='ui-datepicker-div']//a[@title='Next']"); // go
                                                                     // forward
                                                                     // to May
                                                                     // 2014.
        s.click("//div[@id='ui-datepicker-div']//td[@data-year='2014' and @data-month='4']//a[@class='ui-state-default' and text()='1']");
        assertEquals("05/01/2014", s.getValue("newCompletionDate"));
        s.type("newCompletionDateComments",
                "Moved forward to 05/01/2014 <script>window.location.href='google.com'</script><br/>");
        ok.click();

        // Confirmation window should have shown up.
        assertFalse(s.isVisible("abstraction-date-override"));
        assertTrue(s.isVisible("date-override-warning"));
        assertEquals("Confirm Date", getDialogTitle("date-override-warning"));
        waitForElementToBecomeAvailable(
                By.xpath("//span[@id='days1' and text()='10']"), 10);
        waitForElementToBecomeAvailable(
                By.xpath("//span[@id='days2' and text()='11']"), 10);
        assertEquals(
                "Warning: You are about to modify the Abstraction Completion Date as follows: From: 04/30/2014 - 10 business day(s) from trial submission To: 05/01/2014 - 11 business day(s) from trial submission Are you sure?",
                s.getText("//div[@id='date-override-warning']")
                        .replaceAll("\\s+", " ")
                        .replaceAll("[^\\p{ASCII}]", "-"));
        return ok;
    }

    private void verifyValidationError(String msg) {
        assertTrue(s.isVisible("validationError"));
        assertEquals("Error", getDialogTitle("validationError"));
        assertEquals(msg, s.getText("validationErrorText"));
        s.click("//div[@aria-describedby='validationError']//button//span[text()='Close']");
        assertFalse(s.isVisible("validationError"));
    }

    private String getDialogTitle(String id) {
        return s.getText("//div[@aria-describedby='" + id
                + "']//span[@class='ui-dialog-title']");
    }

    private WebElement getOverrideIcon(TrialInfo trial) {
        try {
            return driver.findElement(By
                    .xpath("//span[@data-study-protocol-id='" + trial.id
                            + "']/following-sibling::i[1]"));
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    private WebElement getOverrideSpan(TrialInfo trial) {
        return driver.findElement(By.xpath("//span[@data-study-protocol-id='"
                + trial.id + "']"));
    }

    @Test
    public void testRefreshButton() throws Exception {
        TrialInfo complete = createAcceptedTrial();
        loginAsSuperAbstractor();
        clickAndWait("id=dashboardMenuOption");

        // Refresh Workload tab.
        refresh();
        verifyWorkfloadTabActive();
        isTrialInWorkloadTab(complete);
        assertFalse(s.isTextPresent("Results as of " + today));

        // When on Search Criteria screen, but no search results yet, Refresh
        // still should reload Workload.
        s.click("searchid");
        refresh();
        verifyWorkfloadTabActive();
        isTrialInWorkloadTab(complete);
        assertFalse(s.isTextPresent("Results as of " + today));

        // Results tab.
        findAndSelectTrialInDashboard(complete); // Details is active.
        s.click("resultsid");
        verifyResultsTabActive();
        refresh();
        assertTrue(s.isTextPresent("Results as of " + today));
        verifyResultsTabActive();

        // When both Workload and Results tab are there, but not selected,
        // Results is refreshed.
        findAndSelectTrialInDashboard(complete); // Details is active.
        refresh();
        assertTrue(s.isTextPresent("Results as of " + today));
        verifyResultsTabActive();

        // Even when search results are there, if Workload tab is active, it
        // should be refreshed.
        findAndSelectTrialInDashboard(complete); // Details is active.
        s.click("workloadid");
        verifyWorkfloadTabActive();
        refresh();
        verifyWorkfloadTabActive();
        isTrialInWorkloadTab(complete);
        assertFalse(s.isTextPresent("Results as of " + today));
    }

    @Test
    public void testWorkloadSubmissionTypeFilter() throws Exception {
        deactivateAllTrials();

        TrialInfo abbreviated = createAcceptedTrial();
        TrialInfo complete = createAcceptedTrial();
        TrialInfo amendment = createAcceptedTrial();
        new QueryRunner().update(connection,
                "update study_protocol set proprietary_trial_indicator=true where identifier="
                        + abbreviated.id);
        new QueryRunner()
                .update(connection,
                        "update study_protocol set proprietary_trial_indicator=false, amendment_date=now() where identifier="
                                + amendment.id);

        loginAsSuperAbstractor();
        clickAndWait("id=dashboardMenuOption");
        assertTrue(isTrialInWorkloadTab(abbreviated));
        assertTrue(isTrialInWorkloadTab(complete));
        assertTrue(isTrialInWorkloadTab(amendment));

        applySubmissionTypeFilter(new String[] { "Abbreviated" });
        assertTrue(isTrialInWorkloadTab(abbreviated));
        assertFalse(isTrialInWorkloadTab(complete));
        assertFalse(isTrialInWorkloadTab(amendment));

        applySubmissionTypeFilter(new String[] { "Complete" });
        assertFalse(isTrialInWorkloadTab(abbreviated));
        assertTrue(isTrialInWorkloadTab(complete));
        assertFalse(isTrialInWorkloadTab(amendment));

        applySubmissionTypeFilter(new String[] { "Amendment" });
        assertFalse(isTrialInWorkloadTab(abbreviated));
        assertFalse(isTrialInWorkloadTab(complete));
        assertTrue(isTrialInWorkloadTab(amendment));

        applySubmissionTypeFilter(new String[] { "Complete", "Amendment" });
        assertFalse(isTrialInWorkloadTab(abbreviated));
        assertTrue(isTrialInWorkloadTab(complete));
        assertTrue(isTrialInWorkloadTab(amendment));

        applySubmissionTypeFilter(new String[] { "Abbreviated", "Complete",
                "Amendment" });
        assertTrue(isTrialInWorkloadTab(abbreviated));
        assertTrue(isTrialInWorkloadTab(complete));
        assertTrue(isTrialInWorkloadTab(amendment));

        applySubmissionTypeFilter(new String[] {});
        assertTrue(isTrialInWorkloadTab(abbreviated));
        assertTrue(isTrialInWorkloadTab(complete));
        assertTrue(isTrialInWorkloadTab(amendment));

        // Check Refresh button
        applySubmissionTypeFilter(new String[] { "Abbreviated" });
        assertTrue(isTrialInWorkloadTab(abbreviated));
        assertFalse(isTrialInWorkloadTab(complete));
        assertFalse(isTrialInWorkloadTab(amendment));
        refresh();
        assertTrue(isTrialInWorkloadTab(abbreviated));
        assertTrue(isTrialInWorkloadTab(complete));
        assertTrue(isTrialInWorkloadTab(amendment));

        // Make sure sorts do not reset the filter.
        String filledFunnelPath = "//i[@class='fa fa-filter fa-2x submissionType']";
        applySubmissionTypeFilter(new String[] { "Abbreviated" });
        assertTrue(isTrialInWorkloadTab(abbreviated));
        assertFalse(isTrialInWorkloadTab(complete));
        assertFalse(isTrialInWorkloadTab(amendment));
        sort("Submission Type");
        sort("Submission Type");
        assertTrue(isTrialInWorkloadTab(abbreviated));
        assertFalse(isTrialInWorkloadTab(complete));
        assertFalse(isTrialInWorkloadTab(amendment));
        assertTrue(s.isElementPresent(filledFunnelPath));

        // Selecting the trial in Details tab and performing actions on it must
        // not reset filters either.
        clickAndWait(getXPathForNciIdInWorkloadTab(abbreviated));
        verifyDetailsTabActive();
        clickAndWait("link=Admin/Scientific Check Out");
        assertTrue(s
                .isTextPresent("Trial Check-Out (Admin and Scientific) Successful"));
        s.click("link=Admin/Scientific Check In");
        s.type("comments", "No comments.");
        clickAndWait("//button[text()='Ok']");
        assertTrue(s
                .isTextPresent("Trial Check-In (Admin and Scientific) Successful"));
        s.click("workloadid");
        assertTrue(isTrialInWorkloadTab(abbreviated));
        assertFalse(isTrialInWorkloadTab(complete));
        assertFalse(isTrialInWorkloadTab(amendment));
        assertTrue(s.isElementPresent(filledFunnelPath));

        // Performing searches in Search tab must not reset filters.
        s.click("searchid");
        searchAndFindTrial(abbreviated);
        s.click("workloadid");
        assertTrue(isTrialInWorkloadTab(abbreviated));
        assertFalse(isTrialInWorkloadTab(complete));
        assertFalse(isTrialInWorkloadTab(amendment));
        assertTrue(s.isElementPresent(filledFunnelPath));

    }

    private void applySubmissionTypeFilter(String[] types) {
        refresh();
        verifyWorkfloadTabActive();

        // Find Funnel for this column; it will be unselected.
        String emptyFunnelPath = "//i[@class='fa fa-filter fa-2x fa-inverse submissionType']";
        String filledFunnelPath = "//i[@class='fa fa-filter fa-2x submissionType']";
        assertTrue(s.isElementPresent(emptyFunnelPath));

        // Click on Funnel
        verifySubmissionTypePopup(driver.findElement(By.xpath(emptyFunnelPath)));

        // Check boxes.
        for (WebElement el : driver.findElements(By
                .name("submissionTypeFilter"))) {
            if (ArrayUtils.contains(types, el.getAttribute("value"))) {
                el.click();
            }
        }

        // Submit.
        clickAndWait("//div[@aria-describedby='submission-type-filter']//button//span[text()='OK']");

        // If at least one option selected, funnel must turn black.
        if (types.length > 0) {
            assertTrue(s.isElementPresent(filledFunnelPath));
        } else {
            assertTrue(s.isElementPresent(emptyFunnelPath));
        }
    }

    /**
     * 
     */
    private void refresh() {
        clickAndWait("//input[@value='Refresh']");
    }

    private void verifySubmissionTypePopup(WebElement elementThatInvokesPopup) {
        elementThatInvokesPopup.click();
        assertTrue(s.isVisible("submission-type-filter"));
        assertEquals(
                "Submission Type",
                s.getText("//div[@aria-describedby='submission-type-filter']//span[@class='ui-dialog-title']"));
        assertEquals("Limit the results to the following submission types:",
                s.getText("//div[@id='submission-type-filter']/p"));

        // Check 'x' icon (close)
        clickAndWait("//div[@aria-describedby='submission-type-filter']//button[@title='Close']");
        assertFalse(s.isVisible("submission-type-filter"));

        // Check Cancel button
        elementThatInvokesPopup.click();
        assertTrue(s.isVisible("submission-type-filter"));
        clickAndWait("//div[@aria-describedby='submission-type-filter']//button//span[text()='Cancel']");
        assertFalse(s.isVisible("submission-type-filter"));

        // Verify 3 options are there.
        elementThatInvokesPopup.click();
        assertEquals(3, driver.findElements(By.name("submissionTypeFilter"))
                .size());
        assertTrue(s
                .isVisible("//label[@class='checkboxLabel' and text()='Abbreviated']"));
        assertTrue(s
                .isVisible("//label[@class='checkboxLabel' and text()='Amendment']"));
        assertTrue(s
                .isVisible("//label[@class='checkboxLabel' and text()='Complete']"));

    }

    @Test
    public void testWorkloadDateRangeFilter() throws Exception {
        deactivateAllTrials();
        TrialInfo second = createSubmittedTrial();
        TrialInfo first = createAcceptedTrial();
        loginAsSuperAbstractor();

        // Submitted On.
        new QueryRunner()
                .update(connection,
                        "update study_protocol set date_last_created='2015-06-01 09:15.000' where identifier="
                                + first.id);
        new QueryRunner()
                .update(connection,
                        "update study_protocol set date_last_created='2015-06-02 09:15.000' where identifier="
                                + second.id);
        verifyDateRangeFilter(first, "06/01/2015", second, "06/02/2015",
                "Submitted On");

        // Submission Plus 10 Business Days
        clickAndWait("id=dashboardMenuOption");
        verifyDateRangeFilter(first,
                getColumnValue(1, "Submission Plus 10 Business Days"), second,
                getColumnValue(2, "Submission Plus 10 Business Days"),
                "Submission Plus 10 Business Days");

        // Expected Abstraction Completion Date
        clickAndWait("id=dashboardMenuOption");
        verifyDateRangeFilter(first,
                getColumnValue(1, "Expected Abstraction Completion Date"),
                second,
                getColumnValue(2, "Expected Abstraction Completion Date"),
                "Expected Abstraction Completion Date");

        // Current On-Hold Date
        addOnHold(first, "SUBMISSION_INCOM", date("06/01/2015"), null,
                "Submitter");
        addOnHold(second, "SUBMISSION_INCOM", date("06/02/2015"), null,
                "Submitter");
        verifyDateRangeFilter(first, "06/01/2015", second, "06/02/2015",
                "Current On-Hold Date");

        // Accepted
        addDWS(second, "ACCEPTED");
        addMilestone(second, "SUBMISSION_ACCEPTED",
                jdbcTs(DateUtils.addDays(new Date(), 1)));
        verifyDateRangeFilter(first, today, second, tomorrow, "Accepted");

        // ADMINISTRATIVE_PROCESSING_COMPLETED_DATE
        addMilestone(first, "ADMINISTRATIVE_PROCESSING_COMPLETED_DATE",
                jdbcTs(new Date()));
        addMilestone(second, "ADMINISTRATIVE_PROCESSING_COMPLETED_DATE",
                jdbcTs(DateUtils.addDays(new Date(), 1)));
        verifyDateRangeFilter(first, today, second, tomorrow,
                "Admin Abstraction Completed");

        // ADMINISTRATIVE_QC_COMPLETE
        addMilestone(first, "ADMINISTRATIVE_QC_COMPLETE", jdbcTs(new Date()));
        addMilestone(second, "ADMINISTRATIVE_QC_COMPLETE",
                jdbcTs(DateUtils.addDays(new Date(), 1)));
        verifyDateRangeFilter(first, today, second, tomorrow,
                "Admin QC Completed");

        // SCIENTIFIC_PROCESSING_COMPLETED_DATE
        addMilestone(first, "SCIENTIFIC_PROCESSING_COMPLETED_DATE",
                jdbcTs(new Date()));
        addMilestone(second, "SCIENTIFIC_PROCESSING_COMPLETED_DATE",
                jdbcTs(DateUtils.addDays(new Date(), 1)));
        verifyDateRangeFilter(first, today, second, tomorrow,
                "Scientific Abstraction Completed");

        // SCIENTIFIC_QC_COMPLETE
        addMilestone(first, "SCIENTIFIC_QC_COMPLETE", jdbcTs(new Date()));
        addMilestone(second, "SCIENTIFIC_QC_COMPLETE",
                jdbcTs(DateUtils.addDays(new Date(), 1)));
        verifyDateRangeFilter(first, today, second, tomorrow,
                "Scientific QC Completed");

        // READY_FOR_TSR
        addMilestone(first, "READY_FOR_TSR", jdbcTs(new Date()));
        addMilestone(second, "READY_FOR_TSR",
                jdbcTs(DateUtils.addDays(new Date(), 1)));
        verifyDateRangeFilter(first, today, second, tomorrow, "Ready for TSR");

    }

    @Test
    public void testEnterKeyForSearch() {
        loginAsSuperAbstractor();
        clickAndWait("id=dashboardMenuOption");

        s.click("searchid");

        // press enter on this page
        selenium.keyPressNative("10");

        // check if search is called
        selenium.isTextPresent("At least one criteria is required");

    }

    @Test    
    public void testNullDateFilter() throws Exception {
        
        deactivateAllTrials();
        TrialInfo second = createSubmittedTrial();
        TrialInfo first = createAcceptedTrial();
        loginAsSuperAbstractor();
        
        
        verifyDateNullFilter(first, second, "Submitted On");
        verifyDateNullFilter(first, second, "Submission Plus 10 Business Days");
        verifyDateNullFilter(first, second, "Expected Abstraction Completion Date");
        
        addOnHold(first, "SUBMISSION_INCOM", date("06/01/2015"), null,
                "Submitter");
        verifyDateNullFilter(first, second, "Current On-Hold Date");
        
        addDWS(first, "ACCEPTED");
        addMilestone(first, "SUBMISSION_ACCEPTED",
                jdbcTs(DateUtils.addDays(new Date(), 1)));
        
        verifyDateNullFilter(first, second, "Accepted");
        
        // ADMINISTRATIVE_PROCESSING_COMPLETED_DATE
        addMilestone(first, "ADMINISTRATIVE_PROCESSING_COMPLETED_DATE",
                jdbcTs(new Date()));
        verifyDateNullFilter(first, second, "Admin Abstraction Completed");
        
        addMilestone(first, "ADMINISTRATIVE_QC_COMPLETE", jdbcTs(new Date()));
        verifyDateNullFilter(first, second, "Admin QC Completed");

        // SCIENTIFIC_PROCESSING_COMPLETED_DATE
        addMilestone(first, "SCIENTIFIC_PROCESSING_COMPLETED_DATE",
                jdbcTs(new Date()));
        
        verifyDateNullFilter(first, second, "Scientific Abstraction Completed");

        // SCIENTIFIC_QC_COMPLETE
        addMilestone(first, "SCIENTIFIC_QC_COMPLETE", jdbcTs(new Date()));
        verifyDateNullFilter(first, second, "Scientific QC Completed");

        // READY_FOR_TSR
        addMilestone(first, "READY_FOR_TSR", jdbcTs(new Date()));
        verifyDateNullFilter(first, second, "Ready for TSR");
        
    }
    
    @Test    
    public void testNotNullDateFilter() throws Exception {
        
        deactivateAllTrials();
        TrialInfo second = createSubmittedTrial();
        TrialInfo first = createAcceptedTrial();
        loginAsSuperAbstractor();
        
        
        verifyDateNotNullFilter(first, second, "Submitted On");
        verifyDateNotNullFilter(first, second, "Submission Plus 10 Business Days");
        verifyDateNotNullFilter(first, second, "Expected Abstraction Completion Date");
        
        addOnHold(first, "SUBMISSION_INCOM", date("06/01/2015"), null,
                "Submitter");
        verifyDateNotNullFilter(first, second, "Current On-Hold Date");
        
        addDWS(first, "ACCEPTED");
        addMilestone(first, "SUBMISSION_ACCEPTED",
                jdbcTs(DateUtils.addDays(new Date(), 1)));
        
        verifyDateNotNullFilter(first, second, "Accepted");
        
        // ADMINISTRATIVE_PROCESSING_COMPLETED_DATE
        addMilestone(first, "ADMINISTRATIVE_PROCESSING_COMPLETED_DATE",
                jdbcTs(new Date()));
        verifyDateNotNullFilter(first, second, "Admin Abstraction Completed");
        
        addMilestone(first, "ADMINISTRATIVE_QC_COMPLETE", jdbcTs(new Date()));
        verifyDateNotNullFilter(first, second, "Admin QC Completed");

        // SCIENTIFIC_PROCESSING_COMPLETED_DATE
        addMilestone(first, "SCIENTIFIC_PROCESSING_COMPLETED_DATE",
                jdbcTs(new Date()));
        
        verifyDateNotNullFilter(first, second, "Scientific Abstraction Completed");

        // SCIENTIFIC_QC_COMPLETE
        addMilestone(first, "SCIENTIFIC_QC_COMPLETE", jdbcTs(new Date()));
        verifyDateNotNullFilter(first, second, "Scientific QC Completed");

        // READY_FOR_TSR
        addMilestone(first, "READY_FOR_TSR", jdbcTs(new Date()));
        verifyDateNotNullFilter(first, second, "Ready for TSR");
        
    }
    

    private void verifyDateRangeFilter(TrialInfo first, String date1,
            TrialInfo second, String date2, String columnHeader) {
        clickAndWait("id=dashboardMenuOption");
        verifyWorkfloadTabActive();

        // Find Funnel for this column; it will be unselected.
        String emptyFunnelPath = "//table[@id='wl']//th//a[normalize-space(text())='"
                + columnHeader
                + "']/../..//i[@class='fa fa-filter fa-2x fa-inverse']";
        String filledFunnelPath = "//table[@id='wl']//th//a[normalize-space(text())='"
                + columnHeader + "']/../..//i[@class='fa fa-filter fa-2x']";
        assertTrue(s.isElementPresent(emptyFunnelPath));

        // Click on Funnel
        verifyDateRangePopup(driver.findElement(By.xpath(emptyFunnelPath)));

        // Clear range
        s.type("dateFrom", "");
        s.type("dateTo", "");

        // Date of first trial should return only first trial.
        s.type("dateFrom", date1);
        s.type("dateTo", date1);
        clickAndWait("//div[@aria-describedby='date-range-filter']//button//span[text()='OK']");
        assertTrue(isTrialInWorkloadTab(first));
        assertFalse(isTrialInWorkloadTab(second));

        // Date of second trial should return only 2nd trial. Funnel must change
        // to filled. Pop up dates should remain after submission.
        assertFalse(s.isElementPresent(emptyFunnelPath));
        s.click(filledFunnelPath);
        assertEquals(date1, s.getValue("dateFrom"));
        assertEquals(date1, s.getValue("dateTo"));
        s.type("dateFrom", date2);
        s.type("dateTo", date2);
        clickAndWait("//div[@aria-describedby='date-range-filter']//button//span[text()='OK']");
        assertFalse(isTrialInWorkloadTab(first));
        assertTrue(isTrialInWorkloadTab(second));

        // Clearing both dates must reset filter.
        s.click(filledFunnelPath);
        s.type("dateFrom", "");
        s.type("dateTo", "");
        //click unrestricted radio 
        s.click("//input[@id='choiceunrestricted']");
        
        clickAndWait("//div[@aria-describedby='date-range-filter']//button//span[text()='OK']");
        assertTrue(isTrialInWorkloadTab(first));
        assertTrue(isTrialInWorkloadTab(second));
        assertTrue(s.isElementPresent(emptyFunnelPath));

        // Type dates so that both trials included.
        s.click(emptyFunnelPath);
        //click the radio button first
        clickAndWait("//input[@id='choicelimit']");
        s.type("dateFrom", date1);
        s.type("dateTo", date2);
       
        clickAndWait("//div[@aria-describedby='date-range-filter']//button//span[text()='OK']");
        assertTrue(isTrialInWorkloadTab(first));
        assertTrue(isTrialInWorkloadTab(second));

        // Ensure Refresh resets filters.
        s.click(filledFunnelPath);
        //click the radio button first
        clickAndWait("//input[@id='choicelimit']");
        s.type("dateFrom", date1);
        s.type("dateTo", date1);
        clickAndWait("//div[@aria-describedby='date-range-filter']//button//span[text()='OK']");
        assertTrue(isTrialInWorkloadTab(first));
        assertFalse(isTrialInWorkloadTab(second));
        refresh();
        assertTrue(isTrialInWorkloadTab(first));
        assertTrue(isTrialInWorkloadTab(second));
        assertTrue(s.isElementPresent(emptyFunnelPath));

        // Providing only one of the two dates must produce both trials.
        s.click(emptyFunnelPath);
        clickAndWait("//input[@id='choicelimit']");
        s.type("dateFrom", date1);
        s.type("dateTo", "");
        clickAndWait("//div[@aria-describedby='date-range-filter']//button//span[text()='OK']");
        assertTrue(isTrialInWorkloadTab(first));
        assertTrue(isTrialInWorkloadTab(second));
        s.click(filledFunnelPath);
        clickAndWait("//input[@id='choicelimit']");
        s.type("dateFrom", "");
        s.type("dateTo", date2);
        clickAndWait("//div[@aria-describedby='date-range-filter']//button//span[text()='OK']");
        assertTrue(isTrialInWorkloadTab(first));
        assertTrue(isTrialInWorkloadTab(second));
        refresh();

        // Negative date range should not error out, but must produce no
        // results.
        s.click(emptyFunnelPath);
        clickAndWait("//input[@id='choicelimit']");
        s.type("dateFrom", date2);
        s.type("dateTo", date1);
        clickAndWait("//div[@aria-describedby='date-range-filter']//button//span[text()='OK']");
        assertTrue(s.isTextPresent("Nothing found to display."));
        refresh();

        // Ensure column sorting does not reset filters.
        s.click(emptyFunnelPath);
        clickAndWait("//input[@id='choicelimit']");
        s.type("dateFrom", date1);
        s.type("dateTo", date1);
        clickAndWait("//div[@aria-describedby='date-range-filter']//button//span[text()='OK']");
        assertTrue(isTrialInWorkloadTab(first));
        assertFalse(isTrialInWorkloadTab(second));
        sort(columnHeader);
        sort(columnHeader);
        assertTrue(isTrialInWorkloadTab(first));
        assertFalse(isTrialInWorkloadTab(second));
        assertTrue(s.isElementPresent(filledFunnelPath));

        // Selecting the trial in Details tab and performing actions on it must
        // not reset filters either.
        clickAndWait(getXPathForNciIdInWorkloadTab(first));
        verifyDetailsTabActive();
        clickAndWait("link=Admin/Scientific Check Out");
        assertTrue(s
                .isTextPresent("Trial Check-Out (Admin and Scientific) Successful"));
        s.click("link=Admin/Scientific Check In");
        s.type("comments", "No comments.");
        clickAndWait("//button[text()='Ok']");
        assertTrue(s
                .isTextPresent("Trial Check-In (Admin and Scientific) Successful"));
        s.click("workloadid");
        assertTrue(isTrialInWorkloadTab(first));
        assertFalse(isTrialInWorkloadTab(second));
        assertTrue(s.isElementPresent(filledFunnelPath));

        // Performing searches in Search tab must not reset filters.
        s.click("searchid");
        searchAndFindTrial(second);
        s.click("workloadid");
        assertTrue(isTrialInWorkloadTab(first));
        assertFalse(isTrialInWorkloadTab(second));
        assertTrue(s.isElementPresent(filledFunnelPath));

    }

    private void verifyDateRangePopup(final WebElement elementThatInvokesPopup) {
        elementThatInvokesPopup.click();
        assertTrue(s.isVisible("date-range-filter"));
        assertEquals(
                "Date Filter",
                s.getText("//div[@aria-describedby='date-range-filter']//span[@class='ui-dialog-title']"));
        assertEquals(
                "Limit the results to the following date range (inclusive):",
                s.getText("//div[@id='date-range-filter']/table/tbody/tr[1]/td/label"));

        // Check 'x' icon (close)
        clickAndWait("//div[@aria-describedby='date-range-filter']//button[@title='Close']");
        assertFalse(s.isVisible("date-range-filter"));

        // Check Cancel button
        elementThatInvokesPopup.click();
        assertTrue(s.isVisible("date-range-filter"));
        clickAndWait("//div[@aria-describedby='date-range-filter']//button//span[text()='Cancel']");
        assertFalse(s.isVisible("date-range-filter"));

        // verify textboxes are there
        elementThatInvokesPopup.click();
        assertTrue(s.isVisible("dateFrom"));
        assertTrue(s.isVisible("dateTo"));
        
        //click the radio button first
        clickAndWait("//input[@id='choicelimit']");

        // Verify Calendars come up.
        clickAndWait("//input[@id='dateFrom']/following-sibling::img");
        assertTrue(s.isVisible("ui-datepicker-div"));
        clickAndWait("//input[@id='dateFrom']/following-sibling::img");
        assertFalse(s.isVisible("ui-datepicker-div"));
        clickAndWait("//input[@id='dateTo']/following-sibling::img");
        assertTrue(s.isVisible("ui-datepicker-div"));
        clickAndWait("//input[@id='dateTo']/following-sibling::img");
        assertFalse(s.isVisible("ui-datepicker-div"));

        // Check basic date validation.
        assertFalse(s.isVisible("validationError"));
        s.type("dateFrom", "05/32/2015");
        s.click("//div[@aria-describedby='date-range-filter']//button//span[text()='OK']");
        assertTrue(s.isVisible("validationError"));
        assertTrue(s.isTextPresent("Invalid From Date: 05/32/2015"));
        s.click("//div[@aria-describedby='validationError']//button[@title='Close']");
        assertFalse(s.isVisible("validationError"));

        s.type("dateFrom", "");
        s.type("dateTo", "05/33/2015");
        s.click("//div[@aria-describedby='date-range-filter']//button//span[text()='OK']");
        assertTrue(s.isVisible("validationError"));
        assertTrue(s.isTextPresent("Invalid To Date: 05/33/2015"));
        s.click("//div[@aria-describedby='validationError']//button[@title='Close']");
        assertFalse(s.isVisible("validationError"));

    }
    
    private void verifyDateNullFilter(TrialInfo first, 
            TrialInfo second, String columnHeader) {
        clickAndWait("id=dashboardMenuOption");
        verifyWorkfloadTabActive();

        // Find Funnel for this column; it will be unselected.
        String emptyFunnelPath = "//table[@id='wl']//th//a[normalize-space(text())='"
                + columnHeader
                + "']/../..//i[@class='fa fa-filter fa-2x fa-inverse']";
        String filledFunnelPath = "//table[@id='wl']//th//a[normalize-space(text())='"
                + columnHeader + "']/../..//i[@class='fa fa-filter fa-2x']";
        assertTrue(s.isElementPresent(emptyFunnelPath));

        // Click on Funnel
        driver.findElement(By.xpath(emptyFunnelPath)).click();;
        
        s.click("//input[@id='choicenullDate']");

      
      
        clickAndWait("//div[@aria-describedby='date-range-filter']//button//span[text()='OK']");
        
        List<String> noRecordsFoundColumnsList = new ArrayList<String>();
        List<String> firstRecordFoundColumnsList = new ArrayList<String>();
        noRecordsFoundColumnsList.add("Submission Plus 10 Business Days");
        noRecordsFoundColumnsList.add("Submitted On");
        noRecordsFoundColumnsList.add("Expected Abstraction Completion Date");
      
        firstRecordFoundColumnsList.add("Current On-Hold Date");
        firstRecordFoundColumnsList.add("Accepted");
        firstRecordFoundColumnsList.add("Admin Abstraction Completed");
        firstRecordFoundColumnsList.add("Admin QC Completed");
        firstRecordFoundColumnsList.add("Scientific Abstraction Completed");
        firstRecordFoundColumnsList.add("Scientific QC Completed");
        firstRecordFoundColumnsList.add("Ready for TSR");
        

        
        if (noRecordsFoundColumnsList.contains(columnHeader)) {
            assertFalse(isTrialInWorkloadTab(first));
            assertFalse(isTrialInWorkloadTab(second));
        }
        
        if (firstRecordFoundColumnsList.contains(columnHeader)) {
            assertFalse(isTrialInWorkloadTab(first));
            assertTrue(isTrialInWorkloadTab(second));
            
            //ensure that sorting does not change filter
            sort(columnHeader);
            sort(columnHeader);
            assertFalse(isTrialInWorkloadTab(first));
            assertTrue(isTrialInWorkloadTab(second));
        }
        
        //test refresh reset filter
        refresh();
        assertTrue(isTrialInWorkloadTab(first));
        assertTrue(isTrialInWorkloadTab(second));

      
    }
    
    private void verifyDateNotNullFilter(TrialInfo first, 
            TrialInfo second, String columnHeader) {
        clickAndWait("id=dashboardMenuOption");
        verifyWorkfloadTabActive();

        // Find Funnel for this column; it will be unselected.
        String emptyFunnelPath = "//table[@id='wl']//th//a[normalize-space(text())='"
                + columnHeader
                + "']/../..//i[@class='fa fa-filter fa-2x fa-inverse']";
        String filledFunnelPath = "//table[@id='wl']//th//a[normalize-space(text())='"
                + columnHeader + "']/../..//i[@class='fa fa-filter fa-2x']";
        assertTrue(s.isElementPresent(emptyFunnelPath));

        // Click on Funnel
        driver.findElement(By.xpath(emptyFunnelPath)).click();;
        
        s.click("//input[@id='choicelimit']");

      
      
        clickAndWait("//div[@aria-describedby='date-range-filter']//button//span[text()='OK']");
        
        List<String> noRecordsFoundColumnsList = new ArrayList<String>();
        List<String> firstRecordFoundColumnsList = new ArrayList<String>();
        noRecordsFoundColumnsList.add("Submission Plus 10 Business Days");
        noRecordsFoundColumnsList.add("Submitted On");
        noRecordsFoundColumnsList.add("Expected Abstraction Completion Date");
      
        firstRecordFoundColumnsList.add("Current On-Hold Date");
        firstRecordFoundColumnsList.add("Accepted");
        firstRecordFoundColumnsList.add("Admin Abstraction Completed");
        firstRecordFoundColumnsList.add("Admin QC Completed");
        firstRecordFoundColumnsList.add("Scientific Abstraction Completed");
        firstRecordFoundColumnsList.add("Scientific QC Completed");
        firstRecordFoundColumnsList.add("Ready for TSR");
        

        
        if (noRecordsFoundColumnsList.contains(columnHeader)) {
            assertTrue(isTrialInWorkloadTab(first));
            assertTrue(isTrialInWorkloadTab(second));
        }
        
        if (firstRecordFoundColumnsList.contains(columnHeader)) {
            assertTrue(isTrialInWorkloadTab(first));
            assertFalse(isTrialInWorkloadTab(second));
            
            //ensure that sorting does not change filter
            sort(columnHeader);
            sort(columnHeader);
            assertTrue(isTrialInWorkloadTab(first));
            assertFalse(isTrialInWorkloadTab(second));         
        }
        
        //test refresh reset filter
        refresh();
        assertTrue(isTrialInWorkloadTab(first));
        assertTrue(isTrialInWorkloadTab(second));

      
    }

    @Test
    public void testWorkloadTab_OverlapBusinessDays() throws SQLException,
            ParseException {
        deactivateAllTrials();
        TrialInfo submittedTrial = createSubmittedTrial();
        addOnHold(submittedTrial, "SUBMISSION_INCOM", date("07/03/2015"),
                date("07/03/2015"), "Submitter");
        addOnHold(submittedTrial, "SUBMISSION_INCOM", date("07/04/2015"),
                date("07/06/2015"), "Submitter");
        addOnHold(submittedTrial, "SUBMISSION_INCOM", date("07/01/2015"),
                date("07/10/2015"), "Submitter");

        loginAsSuperAbstractor();
        clickAndWait("id=dashboardMenuOption");
        verifyColumnValue(1, "Business Days on Hold (Submitter)", "7");

        deactivateAllTrials();
        submittedTrial = createSubmittedTrial();
        addOnHold_Timestamp(submittedTrial, "SUBMISSION_INCOM", "{ts '2015-06-30 16:14:09.253'}",
                "{ts '2015-07-06 13:15:47.391'}", "Submitter");
        addOnHold_Timestamp(submittedTrial, "SUBMISSION_INCOM", "{ts '2015-07-13 14:36:36.636'}",
                "{ts '2015-07-21 12:43:36.117'}", "Submitter");
        clickAndWait("id=dashboardMenuOption");
        verifyColumnValue(1, "Business Days on Hold (Submitter)", "11");
    }

    @Test
    public void testWorkloadTab_ExpectedAbstractionAbstractionCompletionDate()
            throws SQLException, ParseException {
        deactivateAllTrials();
        TrialInfo submittedTrial = createSubmittedTrial();

        // Expected Abstraction Completion Date
        new QueryRunner()
                .update(connection,
                        "update study_protocol set date_last_created='2015-10-14 09:15.000' where identifier="
                                + submittedTrial.id);

        addOnHold(submittedTrial, "SUBMISSION_INCOM", date("10/15/2015"),
                date("10/15/2015"), "Submitter");

        loginAsSuperAbstractor();

        clickAndWait("id=dashboardMenuOption");
        verifyColumnValue(1, "Expected Abstraction Completion Date",
                "10/29/2015");
        verifyColumnValue(1, "Business Days on Hold (Submitter)", "1");

        // when calculated Expected Abstraction Completion Date falls on weekend
        // it is moved to next Business day
        new QueryRunner()
                .update(connection,
                        "update study_protocol set date_last_created='2015-10-15 09:15.000' where identifier="
                                + submittedTrial.id);
        addOnHold(submittedTrial, "SUBMISSION_INCOM", date("10/15/2015"),
                date("10/16/2015"), "Submitter");

        clickAndWait("id=dashboardMenuOption");
        verifyColumnValue(1, "Business Days on Hold (Submitter)", "2");
        verifyColumnValue(1, "Expected Abstraction Completion Date",
                "11/02/2015");
        
        logoutPA();
        deactivateAllTrials();
        submittedTrial = createSubmittedTrial();
        new QueryRunner().update(connection,
                "update study_protocol set date_last_created='2015-10-14 09:15.000' where identifier="
                        + submittedTrial.id);
        addOnHold(submittedTrial, "SUBMISSION_INCOM", date("10/15/2015"), null, "Submitter");
        
        Date todayDate = new Date();
        int onHoldDays = PAUtil.getBusinessDaysBetween(date("10/15/2015"),todayDate);
        Date expectedAbsDate = PAUtil.addBusinessDays(PAUtil.addBusinessDays(date("10/14/2015"),10), onHoldDays);
        if (!PAUtil.isBusinessDay(expectedAbsDate)) {
            PAUtil.addBusinessDays(expectedAbsDate, 1);
        }
        
        loginAsSuperAbstractor();
        clickAndWait("id=dashboardMenuOption");
        verifyColumnValue(1, "Business Days on Hold (Submitter)", onHoldDays+"");
        verifyColumnValue(1, "Expected Abstraction Completion Date", PAUtil.convertTsToFormattedDate(TsConverter
                .convertToTs(expectedAbsDate) ));
    }

    @Test 
    public void testWorkloadTab_UpdateCalcSubmissionPlusTenBizDays() throws SQLException, ParseException {
        //(Submission Plus 10 Business Days) = (Submission Date) + (Business Days on-Hold (Submitter)) + 10 biz days
        // The date may not fall on a holiday or a weekend.
        TrialInfo acceptedTrial;
       deactivateAllTrials();
        acceptedTrial = createAcceptedTrial();
        new QueryRunner()
                .update(connection,
                        "update study_protocol set date_last_created='2015-05-22 09:15.000' where identifier="
                                + acceptedTrial.id);
        
        addOnHold(acceptedTrial, "SUBMISSION_INCOM", date("05/15/2015"),
                date("05/15/2015"), "Submitter");

        loginAsSuperAbstractor();
        clickAndWait("id=dashboardMenuOption");
        verifyColumnValue(1, "Submitted On", "05/22/2015");
        verifyColumnValue(1, "Submission Plus 10 Business Days", "06/09/2015");
        
        
        // when calculated "Submission Plus 10 Business Days" Date falls on weekend
        // it is moved to next Business day 
        // As Per PO-9400 Example 
        logoutPA();
        deactivateAllTrials();
        acceptedTrial = createSubmittedTrial();

        new QueryRunner().update(connection,
                "update study_protocol set date_last_created='2015-10-14 09:15.000' where identifier="
                        + acceptedTrial.id);
        addOnHold(acceptedTrial, "SUBMISSION_INCOM", date("10/15/2015"), date("10/19/2015"), "Submitter");
        loginAsSuperAbstractor();
        clickAndWait("id=dashboardMenuOption");
        verifyColumnValue(1, "Submitted On", "10/14/2015");
        verifyColumnValue(1, "Submission Plus 10 Business Days", "11/02/2015");
        
        // Test Case 
        logoutPA();
        deactivateAllTrials();
        acceptedTrial = createSubmittedTrial();

        new QueryRunner().update(connection,
                "update study_protocol set date_last_created='2015-10-14 09:15.000' where identifier="
                        + acceptedTrial.id);
        addOnHold(acceptedTrial, "SUBMISSION_INCOM", date("10/15/2015"), null, "Submitter");
        
        loginAsSuperAbstractor();
        clickAndWait("id=dashboardMenuOption");
        
        Date todayDate = new Date();
        int onHoldDays = PAUtil.getBusinessDaysBetween(date("10/15/2015"),todayDate);
        Date expectedBizDate = PAUtil.addBusinessDays(PAUtil.addBusinessDays(date("10/14/2015"),10), onHoldDays);
        if (!PAUtil.isBusinessDay(expectedBizDate)) {
            PAUtil.addBusinessDays(expectedBizDate, 1);
        }
        
        verifyColumnValue(1, "Submitted On", "10/14/2015");
        verifyColumnValue(1, "Business Days on Hold (Submitter)", onHoldDays+"");
        verifyColumnValue(1, "Submission Plus 10 Business Days", PAUtil.convertTsToFormattedDate(TsConverter
                .convertToTs(expectedBizDate) ));

    }
    
    @Test
    public void testProperSubmissionTypeCalculationAndSearch() throws Exception {
        // Verify submission type.
        deactivateAllTrials();
        TrialInfo acceptedTrial = createAcceptedTrial();
        loginAsSuperAbstractor();
        findAndSelectTrialInDashboard(acceptedTrial);
        assertTrue(s
                .isElementPresent("xpath=//td[text()='Complete']/preceding-sibling::td[text()='Submission Type']"));
        s.click("resultsid");
        assertEquals("Complete",
                s.getText("//table[@id='results']/tbody/tr[1]/td[2]"));

        new QueryRunner().update(connection,
                "update study_protocol set proprietary_trial_indicator=true where identifier="
                        + acceptedTrial.id);
        findAndSelectTrialInDashboard(acceptedTrial);
        assertTrue(s
                .isElementPresent("xpath=//td[text()='Abbreviated']/preceding-sibling::td[text()='Submission Type']"));
        s.click("resultsid");
        assertEquals("Abbreviated",
                s.getText("//table[@id='results']/tbody/tr[1]/td[2]"));

        new QueryRunner()
                .update(connection,
                        "update study_protocol set proprietary_trial_indicator=false, amendment_date=now(), submission_number=2"
                                + " where identifier=" + acceptedTrial.id);
        findAndSelectTrialInDashboard(acceptedTrial);
        assertTrue(s
                .isElementPresent("xpath=//td[text()='Amendment']/preceding-sibling::td[text()='Submission Type']"));
        s.click("resultsid");
        assertEquals("Amendment",
                s.getText("//table[@id='results']/tbody/tr[1]/td[2]"));
    }

    @Test
    public void testWorkloadTab() throws Exception {
        deactivateAllTrials();

        TrialInfo acceptedTrial = createAcceptedTrial();
        TrialInfo submittedTrial = createSubmittedTrial();
        loginAsSuperAbstractor();

        clickAndWait("id=dashboardMenuOption");
        verifyWorkfloadTabActive();
        assertTrue(s.isTextPresent("2 trials found, displaying all trials."));

        // Verify column headers Super Abstractor sees
        verifySuperAbstractorWorkloadHeaders();

        // Admin/Scientific Abstractors see less.
        logoutPA();
        loginAsAdminAbstractor();
        clickAndWait("id=dashboardMenuOption");
        verifyWorkfloadTabActive();
        verifyAdminScientificAbstractorWorkloadHeaders();
        logoutPA();
        loginAsScientificAbstractor();
        clickAndWait("id=dashboardMenuOption");
        verifyWorkfloadTabActive();
        verifyAdminScientificAbstractorWorkloadHeaders();

        // Verify selection criteria (last milestone, excluding Rejected
        // trials).
        logoutPA();
        loginAsSuperAbstractor();
        clickAndWait("id=dashboardMenuOption");
        assertTrue(isTrialInWorkloadTab(submittedTrial));
        assertTrue(isTrialInWorkloadTab(acceptedTrial));
        changePaProperty("dashboard.workload.milestones",
                "Submission Received Date");
        clickAndWait("id=dashboardMenuOption");
        assertTrue(isTrialInWorkloadTab(submittedTrial));
        assertFalse(isTrialInWorkloadTab(acceptedTrial));
        changePaProperty("dashboard.workload.milestones",
                "Submission Acceptance Date");
        clickAndWait("id=dashboardMenuOption");
        assertFalse(isTrialInWorkloadTab(submittedTrial));
        assertTrue(isTrialInWorkloadTab(acceptedTrial));
        changePaProperty("dashboard.workload.milestones",
                "Submission Received Date,Submission Acceptance Date");
        clickAndWait("id=dashboardMenuOption");
        assertTrue(isTrialInWorkloadTab(submittedTrial));
        assertTrue(isTrialInWorkloadTab(acceptedTrial));
        addDWS(submittedTrial, "REJECTED");
        addDWS(acceptedTrial, "REJECTED");
        clickAndWait("id=dashboardMenuOption");
        assertFalse(isTrialInWorkloadTab(submittedTrial));
        assertFalse(isTrialInWorkloadTab(acceptedTrial));
        restorePaPropertiesToOriginal();

        // Verify submission type.
        deactivateAllTrials();
        acceptedTrial = createAcceptedTrial();
        clickAndWait("id=dashboardMenuOption");
        assertTrue(isTrialInWorkloadTab(acceptedTrial));
        verifyColumnValue(1, "Submission Type", "Complete");
        new QueryRunner().update(connection,
                "update study_protocol set proprietary_trial_indicator=true where identifier="
                        + acceptedTrial.id);
        clickAndWait("id=dashboardMenuOption");
        assertTrue(isTrialInWorkloadTab(acceptedTrial));
        verifyColumnValue(1, "Submission Type", "Abbreviated");
        new QueryRunner()
                .update(connection,
                        "update study_protocol set proprietary_trial_indicator=false, amendment_date=now(), submission_number=2 where identifier="
                                + acceptedTrial.id);
        clickAndWait("id=dashboardMenuOption");
        assertTrue(isTrialInWorkloadTab(acceptedTrial));
        verifyColumnValue(1, "Submission Type", "Amendment");

        // Submitted On
        deactivateAllTrials();
        acceptedTrial = createAcceptedTrial();
        new QueryRunner()
                .update(connection,
                        "update study_protocol set date_last_created='2015-05-22 09:15.000' where identifier="
                                + acceptedTrial.id);
        clickAndWait("id=dashboardMenuOption");
        verifyColumnValue(1, "Submitted On", "05/22/2015");
        verifyColumnValue(1, "Submission Plus 10 Business Days", "06/08/2015");
        new QueryRunner()
                .update(connection,
                        "update study_protocol set date_last_created='2015-06-01 09:15.000' where identifier="
                                + acceptedTrial.id);
        clickAndWait("id=dashboardMenuOption");
        verifyColumnValue(1, "Submitted On", "06/01/2015");
        verifyColumnValue(1, "Submission Plus 10 Business Days", "06/15/2015");

        // Expected Abstraction Completion Date & Business Days On Hold.
        new QueryRunner()
                .update(connection,
                        "update study_protocol set date_last_created='2015-05-22 09:15.000' where identifier="
                                + acceptedTrial.id);
        clickAndWait("id=dashboardMenuOption");
        verifyColumnValue(1, "Expected Abstraction Completion Date",
                "06/08/2015");
        verifyColumnValue(1, "Business Days on Hold (CTRP)", "0");
        verifyColumnValue(1, "Business Days on Hold (Submitter)", "0");
        addOnHold(acceptedTrial, "SUBMISSION_INCOM", date("05/26/2015"),
                date("05/27/2015"), "CTRP");
        clickAndWait("id=dashboardMenuOption");
        verifyColumnValue(1, "Expected Abstraction Completion Date",
                "06/08/2015");
        verifyColumnValue(1, "Business Days on Hold (CTRP)", "2");
        verifyColumnValue(1, "Business Days on Hold (Submitter)", "0");
        addOnHold(acceptedTrial, "SUBMISSION_INCOM", date("05/26/2015"),
                date("05/27/2015"), "Submitter");
        clickAndWait("id=dashboardMenuOption");
        verifyColumnValue(1, "Expected Abstraction Completion Date",
                "06/10/2015");
        verifyColumnValue(1, "Business Days on Hold (CTRP)", "2");
        verifyColumnValue(1, "Business Days on Hold (Submitter)", "2");
        addOnHold(acceptedTrial, "SUBMISSION_INCOM", date("05/24/2015"),
                date("05/25/2015"), "Submitter");
        addOnHold(acceptedTrial, "SUBMISSION_INCOM", date("05/23/2015"),
                date("05/25/2015"), "CTRP");
        clickAndWait("id=dashboardMenuOption");
        verifyColumnValue(1, "Expected Abstraction Completion Date",
                "06/10/2015");
        verifyColumnValue(1, "Business Days on Hold (CTRP)", "2");
        verifyColumnValue(1, "Business Days on Hold (Submitter)", "2");

        // Business Days Since Submitted
        final Date date = new Date();
        Date yesterday = DateUtils.addDays(date, -1);
        new QueryRunner().update(connection,
                "update study_protocol set date_last_created=" + jdbcTs(yesterday)
                        + " where identifier=" + acceptedTrial.id);
        clickAndWait("id=dashboardMenuOption");
        verifyColumnValue(1, "Business Days Since Submitted",
                PAUtil.isBusinessDay(date) ? "1" : "0");

        // Current On Hold Date.
        verifyColumnValue(1, "Current On-Hold Date", "");
        addOnHold(acceptedTrial, "SUBMISSION_INCOM", date("05/23/2015"), null,
                "CTRP");
        clickAndWait("id=dashboardMenuOption");
        verifyColumnValue(1, "Current On-Hold Date", "05/23/2015");

        // Accepted
        verifyColumnValue(1, "Accepted", today);

        // Milestones
        verifyColumnValue(1, "Admin Abstraction Completed", "");
        verifyColumnValue(1, "Admin QC Completed", "");
        verifyColumnValue(1, "Scientific Abstraction Completed", "");
        verifyColumnValue(1, "Scientific QC Completed", "");
        verifyColumnValue(1, "Ready for TSR", "");
        addMilestone(acceptedTrial, "ADMINISTRATIVE_PROCESSING_COMPLETED_DATE",
                jdbcTs(DateUtils.addDays(date, 1)));
        addMilestone(acceptedTrial, "ADMINISTRATIVE_QC_COMPLETE",
                jdbcTs(DateUtils.addDays(date, 2)));
        addMilestone(acceptedTrial, "SCIENTIFIC_PROCESSING_COMPLETED_DATE",
                jdbcTs(DateUtils.addDays(date, 3)));
        addMilestone(acceptedTrial, "SCIENTIFIC_QC_COMPLETE",
                jdbcTs(DateUtils.addDays(date, 4)));
        addMilestone(acceptedTrial, "READY_FOR_TSR",
                jdbcTs(DateUtils.addDays(date, 5)));
        clickAndWait("id=dashboardMenuOption");
        verifyColumnValue(1, "Admin Abstraction Completed",
                fmt(DateUtils.addDays(date, 1)));
        verifyColumnValue(1, "Admin QC Completed",
                fmt(DateUtils.addDays(date, 2)));
        verifyColumnValue(1, "Scientific Abstraction Completed",
                fmt(DateUtils.addDays(date, 3)));
        verifyColumnValue(1, "Scientific QC Completed",
                fmt(DateUtils.addDays(date, 4)));
        verifyColumnValue(1, "Ready for TSR", fmt(DateUtils.addDays(date, 5)));

        // Checked out by
        verifyColumnValue(1, "Checked Out By", "");
        findAndSelectTrialInDashboard(acceptedTrial);
        clickAndWait("link=Admin/Scientific Check Out");
        assertTrue(s
                .isTextPresent("Message. Trial Check-Out (Admin and Scientific) Successful"));
        clickAndWait("id=dashboardMenuOption");
        assertTrue(isTrialInWorkloadTab(acceptedTrial));
        verifyColumnValue(1, "Checked Out By", "CI, ctrpsubstractor (AS)");
        deactivateAllTrials();
        acceptedTrial = createAcceptedTrial();
        logoutPA();
        loginAsAdminAbstractor();
        clickAndWait("id=dashboardMenuOption");
        clickAndWait("link=" + acceptedTrial.nciID.replaceFirst("NCI-", ""));
        clickAndWait("link=Admin Check Out");
        refresh();
        verifyColumnValue(1, "Checked Out By", "admin-ci (AD)");
        logoutPA();
        loginAsScientificAbstractor();
        clickAndWait("id=dashboardMenuOption");
        clickAndWait("link=" + acceptedTrial.nciID.replaceFirst("NCI-", ""));
        clickAndWait("link=Scientific Check Out");
        refresh();
        verifyColumnValue(1, "Checked Out By",
                "admin-ci (AD) scientific-ci (SC)");
        // Initial list sort is by Abstraction Expected Completion Date,
        // ascending
        deactivateAllTrials();
        logoutPA();
        TrialInfo second = createAcceptedTrial();
        TrialInfo first = createAcceptedTrial();
        new QueryRunner()
                .update(connection,
                        "update study_protocol set date_last_created='2015-05-05 09:15.000' where identifier="
                                + first.id);
        new QueryRunner()
                .update(connection,
                        "update study_protocol set date_last_created='2015-05-04 09:15.000' where identifier="
                                + second.id);
        loginAsSuperAbstractor();
        clickAndWait("id=dashboardMenuOption");
        verifyColumnValue(2, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(1, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));
        sort("Expected Abstraction Completion Date");
        verifyColumnValue(1, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(2, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));

        // Sort NCI ID.
        sort("NCI Trial Identifier");
        if (first.nciID.compareTo(second.nciID) < 0) {
            verifyColumnValue(1, "NCI Trial Identifier",
                    first.nciID.replaceFirst("NCI-", ""));
            verifyColumnValue(2, "NCI Trial Identifier",
                    second.nciID.replaceFirst("NCI-", ""));
        } else {
            verifyColumnValue(2, "NCI Trial Identifier",
                    first.nciID.replaceFirst("NCI-", ""));
            verifyColumnValue(1, "NCI Trial Identifier",
                    second.nciID.replaceFirst("NCI-", ""));
        }
        sort("NCI Trial Identifier");
        if (first.nciID.compareTo(second.nciID) < 0) {
            verifyColumnValue(2, "NCI Trial Identifier",
                    first.nciID.replaceFirst("NCI-", ""));
            verifyColumnValue(1, "NCI Trial Identifier",
                    second.nciID.replaceFirst("NCI-", ""));
        } else {
            verifyColumnValue(1, "NCI Trial Identifier",
                    first.nciID.replaceFirst("NCI-", ""));
            verifyColumnValue(2, "NCI Trial Identifier",
                    second.nciID.replaceFirst("NCI-", ""));
        }

        // Sort Submission Type.
        new QueryRunner().update(connection,
                "update study_protocol set proprietary_trial_indicator=true where identifier="
                        + first.id);
        refresh();
        sort("Submission Type");
        verifyColumnValue(1, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(2, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));
        sort("Submission Type");
        verifyColumnValue(2, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(1, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));

        // Sort Submitted On.
        sort("Submitted On");
        verifyColumnValue(2, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(1, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));
        sort("Submitted On");
        verifyColumnValue(1, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(2, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));

        // Sort Submission Plus 10 Business Days.
        sort("Submission Plus 10 Business Days");
        verifyColumnValue(2, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(1, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));
        sort("Submission Plus 10 Business Days");
        verifyColumnValue(1, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(2, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));

        // Sort Business Days Since Submitted
        sort("Submission Plus 10 Business Days");
        verifyColumnValue(2, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(1, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));
        sort("Submission Plus 10 Business Days");
        verifyColumnValue(1, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(2, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));

        // Sort by Business Days on Hold.
        addOnHold(first, "SUBMISSION_INCOM", date("05/26/2015"),
                date("05/27/2015"), "CTRP");
        refresh();
        sort("Business Days on Hold (CTRP)");
        verifyColumnValue(2, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(1, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));
        sort("Business Days on Hold (CTRP)");
        verifyColumnValue(1, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(2, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));
        addOnHold(first, "SUBMISSION_INCOM", date("05/26/2015"),
                date("05/27/2015"), "Submitter");
        refresh();
        sort("Business Days on Hold (Submitter)");
        verifyColumnValue(2, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(1, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));
        sort("Business Days on Hold (Submitter)");
        verifyColumnValue(1, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(2, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));

        // Sort by On Hold Date.
        addOnHold(first, "SUBMISSION_INCOM", date("05/27/2015"), null, "CTRP");
        refresh();
        sort("Current On-Hold Date");
        verifyColumnValue(2, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(1, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));
        sort("Current On-Hold Date");
        verifyColumnValue(1, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(2, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));

        // Sort by Submission Accepted
        addMilestone(first, "SUBMISSION_ACCEPTED",
                jdbcTs(DateUtils.addDays(date, 2)));
        refresh();
        sort("Accepted");
        verifyColumnValue(2, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(1, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));
        sort("Accepted");
        verifyColumnValue(1, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(2, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));

        // Sort by all other milestones.
        checkSortByMilestone(first, second, "Admin Abstraction Completed",
                "ADMINISTRATIVE_PROCESSING_COMPLETED_DATE");
        checkSortByMilestone(first, second, "Admin QC Completed",
                "ADMINISTRATIVE_QC_COMPLETE");
        checkSortByMilestone(first, second, "Scientific Abstraction Completed",
                "SCIENTIFIC_PROCESSING_COMPLETED_DATE");
        checkSortByMilestone(first, second, "Scientific QC Completed",
                "SCIENTIFIC_QC_COMPLETE");
        checkSortByMilestone(first, second, "Ready for TSR", "READY_FOR_TSR");

        // Finally, sort by check out.
        logoutPA();
        loginAsAdminAbstractor();
        clickAndWait("id=dashboardMenuOption");
        clickAndWait("link=" + first.nciID.replaceFirst("NCI-", ""));
        clickAndWait("link=Admin Check Out");
        logoutPA();
        loginAsScientificAbstractor();
        clickAndWait("id=dashboardMenuOption");
        clickAndWait("link=" + second.nciID.replaceFirst("NCI-", ""));
        clickAndWait("link=Scientific Check Out");
        clickAndWait("id=dashboardMenuOption");
        sort("Checked Out By");
        verifyColumnValue(1, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(2, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));
        sort("Checked Out By");
        verifyColumnValue(2, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(1, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));

        // Verify CSV Export; firefox only.
        verifyWorkloadCSVExport();

    }
    
    
    @Test
    public void testWorkloadTab_CheckBusinessDaysSinceSubmitted() throws Exception {
        deactivateAllTrials();
        TrialInfo acceptedTrial = createAcceptedTrial();
        loginAsSuperAbstractor();

        // submission date and today's date are the same, the value of this
        // Business Days will be zero.
        final Date date = new Date();
        new QueryRunner().update(connection, "update study_protocol set date_last_created=" + jdbcTs(date)
                + " where identifier=" + acceptedTrial.id);
        clickAndWait("id=dashboardMenuOption");
        // Business Days Since Submitted
        verifyColumnValue(1, "Business Days Since Submitted", "0");

        // submission date one day before today's date
        Date yesterday = DateUtils.addDays(date, -1);
        new QueryRunner().update(connection, "update study_protocol set date_last_created=" + jdbcTs(yesterday)
                + " where identifier=" + acceptedTrial.id);
        clickAndWait("id=dashboardMenuOption");
        // Business Days Since Submitted
        verifyColumnValue(1, "Business Days Since Submitted", PAUtil.isBusinessDay(date) ? "1" : "0");

        // Business Days Since Submitted 20 days before current Date
        Date beforeDate = DateUtils.addDays(date, -20);
        if (PAUtil.isBusinessDay(beforeDate)) {
            beforeDate = PAUtil.addBusinessDays(beforeDate, 1);
        }
        int businessDays = PAUtil.getBusinessDaysBetween(beforeDate, date);

        new QueryRunner().update(connection, "update study_protocol set date_last_created=" + jdbcTs(beforeDate)
                + " where identifier=" + acceptedTrial.id);
        clickAndWait("id=dashboardMenuOption");
        // Business Days Since Submitted
        verifyColumnValue(1, "Business Days Since Submitted",
                (PAUtil.isBusinessDay(beforeDate) ? (businessDays - 1) : businessDays) + "");
    }

    /**
     * @throws SQLException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    private void verifyWorkloadCSVExport() throws SQLException, IOException {
        deactivateAllTrials();
        TrialInfo trial = createAcceptedTrial();
        new QueryRunner().update(connection,
                "update study_protocol set date_last_created="
                        + jdbcTs(new Date()) + " where identifier=" + trial.id);
        logoutPA();
        loginAsSuperAbstractor();
        clickAndWait("id=dashboardMenuOption");
        verifyWorkfloadTabActive();

        // Export banner must be at top and at bottom.
        assertTrue(s
                .isElementPresent("xpath=//div[@id='wl_table_container']/div[@class='exportlinks'][1]"));
        assertTrue(s
                .isElementPresent("xpath=//div[@id='wl_table_container']/div[@class='exportlinks'][2]/preceding-sibling::table[@id='wl']"));

        // Finally, download CSV.
        if (!isPhantomJS()) {
            selenium.click("xpath=//div[@id='workload']//a/span[normalize-space(text())='CSV']");
            pause(OP_WAIT_TIME);
            File csv = new File(downloadDir, "workload.csv");
            assertTrue(csv.exists());
            csv.deleteOnExit();

            List<String> lines = FileUtils.readLines(csv);
            assertEquals(
                    "NCI Trial Identifier,Submission Type,Submitted On,Submission Plus 10 Business Days,Expected Abstraction Completion Date,Business Days Since Submitted,Business Days on Hold (CTRP),Business Days on Hold (Submitter),Current On-Hold Date,Accepted,Admin Abstraction Completed,Admin QC Completed,Scientific Abstraction Completed,Scientific QC Completed,Ready for TSR,Checked Out By,Lead Organization,Lead Org PO ID,ClinicalTrials.gov Identifier,CTEP ID,DCP ID,CDR ID,Amendment #,Data Table 4 Funding,On Hold Date,Off Hold Date,On Hold Reason,On Hold Description,Trial Type,NCI Sponsored,Processing Status,Processing Status Date,Admin Check out Name,Admin Check out Date,Scientific Check out Name,Scientific Check out Date,CTEP/DCP,Submitting Organization,Submission Date,Last Milestone,Last Milestone Date,Submission Source,Processing Priority,Comments,This Trial is,Submission Received Date,Added By,Added On,Submission Acceptance Date,Added By,Added On,Submission Rejection Date,Added By,Added On,Submission Terminated Date,Added By,Added On,Submission Reactivated Date,Added By,Added On,Administrative Processing Completed Date,Added By,Added On,Administrative QC Completed Date,Added By,Added On,Scientific Processing Completed Date,Added By,Added On,Scientific QC Completed Date,Added By,Added On,Trial Summary Report Date,Added By,Added On,Submitter Trial Summary Report Feedback Date,Added By,Added On,Initial Abstraction Verified Date,Added By,Added On,On-going Abstraction Verified Date,Added By,Added On,Late Rejection Date,Added By,Added On",
                    lines.get(0));

            final String normalizedContent = lines.get(1).replaceAll("\\s+",
                    " ");
            final String expected = trial.nciID.replaceFirst("NCI-", "")
                    + ",Complete,"
                    + today
                    + ",\\d{2}/\\d{2}/\\d{4},\\d{2}/\\d{2}/\\d{4},\\d,0,0,,"
                    + today
                    + ",,,,,,,ClinicalTrials.gov,1,,,,,,,,,,,Interventional,No,Accepted,"
                    + today
                    + ",,,,,,ClinicalTrials.gov,"
                    + today
                    + ",Submission Acceptance Date,"
                    + today
                    + ",Other,2,,Ready for Admin ProcessingReady for Scientific Processing,"
                    + today + ",," + today + "," + today + ",," + today
                    + ",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,";

            System.out.println(normalizedContent);
            System.out.println(expected);
            assertTrue(normalizedContent.matches(expected));

            csv.delete();
        }
    }

    private void checkSortByMilestone(TrialInfo first, TrialInfo second,
            String column, String code) throws SQLException {
        refresh();
        sort(column);
        String before = getColumnValue(1, "NCI Trial Identifier");
        sort(column);
        String after = getColumnValue(1, "NCI Trial Identifier");
        assertEquals(before, after);

        addMilestone(first, code, jdbcTs(DateUtils.addDays(new Date(), 1)));
        addMilestone(second, code, jdbcTs(DateUtils.addDays(new Date(), 2)));
        refresh();

        sort(column);
        verifyColumnValue(1, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(2, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));
        sort(column);
        verifyColumnValue(2, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(1, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));

    }

    private void sort(String columnHeader) {
        final String xpath = "//table[@id='wl']//th//a[normalize-space(text())='"
                + columnHeader + "']";
        moveElementIntoView(By.xpath(xpath));
        clickAndWait(xpath);
    }

    private String fmt(Date date) {
        return DateFormatUtils.format(date, "MM/dd/yyyy");
    }

    private Date date(String date) throws ParseException {
        return DateUtils.parseDate(date, new String[] { "MM/dd/yyyy" });
    }

    private void verifyColumnValue(int row, String header, String value) {
        assertEquals(value, getColumnValue(row, header));
    }

    private String getColumnValue(int row, String header) {
        // determine column index.
        int index = 1;
        while (!s.getText("//table[@id='wl']/thead/tr/th[" + index + "]//a")
                .equalsIgnoreCase(header)) {
            index++;
        }
        return s.getText(
                "//table[@id='wl']/tbody/tr[" + row + "]/td[" + index + "]")
                .replaceAll("\\s+", " ");
    }

    /**
     * @throws SQLException
     */
    private void restorePaPropertiesToOriginal() throws SQLException {
        changePaProperty("dashboard.counts.trialdist", RANGES);
        changePaProperty("studyonhold.reason_category", "Submitter,CTRP");
        changePaProperty("dashboard.counts.onholds", HOLDS_TO_COUNT);
        changePaProperty("dashboard.counts.milestones", MILESTONES_TO_COUNT);
        changePaProperty(
                "dashboard.workload.milestones",
                "Submission Received Date,Submission Acceptance Date,Submission Reactivated Date,Administrative Processing Start Date,Administrative Processing Completed Date,Ready for Administrative QC Date,Administrative QC Start Date,Administrative QC Completed Date,Scientific Processing Start Date,Scientific Processing Completed Date,Ready for Scientific QC Date,Scientific QC Start Date,Scientific QC Completed Date,Ready for Trial Summary Report Date");
    }

    /**
     * @throws SQLException
     */
    private void changePaProperty(String name, String value)
            throws SQLException {
        new QueryRunner().update(connection, "update pa_properties set value='"
                + value + "' where name='" + name + "'");

    }

    private boolean isTrialInWorkloadTab(TrialInfo trial) {
        final String xpath = getXPathForNciIdInWorkloadTab(trial);
        return s.isElementPresent(xpath) && s.isVisible(xpath);
    }

    private boolean isTrialInResultsTab(TrialInfo trial) {
        final String xpath = getXPathForNciIdInResultsTab(trial);
        return s.isElementPresent(xpath) && s.isVisible(xpath);
    }

    private String getXPathForNciIdInResultsTab(TrialInfo trial) {
        final String xpath = "xpath=//table[@id='results']//td[1]/a[normalize-space(text())='"
                + trial.nciID.replaceFirst("NCI-", "") + "']";
        return xpath;
    }

    /**
     * @param trial
     * @return
     */
    private String getXPathForNciIdInWorkloadTab(TrialInfo trial) {
        final String xpath = "xpath=//table[@id='wl']//td[1]/a[normalize-space(text())='"
                + trial.nciID.replaceFirst("NCI-", "") + "']";
        return xpath;
    }

    /**
     * 
     */
    private void verifyAdminScientificAbstractorWorkloadHeaders() {
        verifyWorkloadColumnHeader(1, "NCI Trial Identifier");
        verifyWorkloadColumnHeader(2, "Submission Type");
        verifyWorkloadColumnHeader(3, "Submitted On");
        verifyWorkloadColumnHeader(4, "Expected Abstraction Completion Date");
        verifyWorkloadColumnHeader(5, "Current On-Hold Date");
        verifyWorkloadColumnHeader(6, "Accepted");
        verifyWorkloadColumnHeader(7, "Admin Abstraction Completed");
        verifyWorkloadColumnHeader(8, "Admin QC Completed");
        verifyWorkloadColumnHeader(9, "Scientific Abstraction Completed");
        verifyWorkloadColumnHeader(10, "Scientific QC Completed");
        verifyWorkloadColumnHeader(11, "Ready for TSR");
        verifyWorkloadColumnHeader(12, "Checked Out By");
    }

    /**
     * 
     */
    private void verifySuperAbstractorWorkloadHeaders() {
        verifyWorkloadColumnHeader(1, "NCI Trial Identifier");
        verifyWorkloadColumnHeader(2, "Submission Type");
        verifyWorkloadColumnHeader(3, "Submitted On");
        verifyWorkloadColumnHeader(4, "Submission Plus 10 Business Days");
        verifyWorkloadColumnHeader(5, "Expected Abstraction Completion Date");
        verifyWorkloadColumnHeader(6, "Business Days Since Submitted");
        verifyWorkloadColumnHeader(7, "Business Days on Hold (CTRP)");
        verifyWorkloadColumnHeader(8, "Business Days on Hold (Submitter)");
        verifyWorkloadColumnHeader(9, "Current On-Hold Date");
        verifyWorkloadColumnHeader(10, "Accepted");
        verifyWorkloadColumnHeader(11, "Admin Abstraction Completed");
        verifyWorkloadColumnHeader(12, "Admin QC Completed");
        verifyWorkloadColumnHeader(13, "Scientific Abstraction Completed");
        verifyWorkloadColumnHeader(14, "Scientific QC Completed");
        verifyWorkloadColumnHeader(15, "Ready for TSR");
        verifyWorkloadColumnHeader(16, "Checked Out By");
    }

    private void verifyWorkloadColumnHeader(int pos, String header) {
        assertEquals(header,
                s.getText("//table[@id='wl']/thead/tr/th[" + pos + "]//a"));
    }

    @SuppressWarnings("deprecation")
    private void verifyWorkfloadTabActive() {
        assertTrue(s.isVisible("workloadid"));
        assertTrue(s.isVisible("workload"));
        assertTrue(s.isVisible("wl"));
    }

    @SuppressWarnings("deprecation")
    private void verifyResultsTabActive() {
        assertTrue(s.isVisible("resultsid"));
        assertTrue(s.isVisible("results"));
    }

    @SuppressWarnings("deprecation")
    private void verifyDetailsTabActive() {
        assertTrue(s.isVisible("detailsid"));
        assertTrue(s.isVisible("details"));

    }

    @SuppressWarnings({ "deprecation", "unused", "unchecked" })
    @Test
    public void testCsvExport() throws Exception {
        logoutUser();
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        goToDashboardSearch();
        s.type("submittedOnOrAfter", "01/01/1990");
        clickAndWait("xpath=//a//span[text()='Search']");

        // Export banner must be at top and at bottom.
        assertTrue(s
                .isElementPresent("xpath=//div[@id='results']/div[@class='exportlinks'][1]"));
        assertTrue(s
                .isElementPresent("xpath=//div[@id='results']/div[@class='exportlinks'][2]/preceding-sibling::table[@id='results']"));

        // Finally, download CSV.
        if (!isPhantomJS()) {
            selenium.click("xpath=//div[@id='results']//a/span[normalize-space(text())='CSV']");
            pause(OP_WAIT_TIME);
            File csv = new File(downloadDir, "dashboardSearchResults.csv");
            assertTrue(csv.exists());
            csv.deleteOnExit();

            List<String> lines = FileUtils.readLines(csv);
            String content = FileUtils.readFileToString(csv);
            assertEquals(
                    "NCI Trial Identifier,Lead Organization,Lead Org PO ID,ClinicalTrials.gov Identifier,CTEP ID,DCP ID,CDR ID,Amendment #,Data Table 4 Funding,On Hold Date,Off Hold Date,On Hold Reason,On Hold Description,Trial Type,NCI Sponsored,Processing Status,Processing Status Date,Admin Check out Name,Admin Check out Date,Scientific Check out Name,Scientific Check out Date,Submission Type,CTEP/DCP,Submitting Organization,Submission Date,Last Milestone,Last Milestone Date,Submission Source,Processing Priority,Comments,This Trial is,Submission Received Date,Added By,Added On,Submission Acceptance Date,Added By,Added On,Submission Rejection Date,Added By,Added On,Submission Terminated Date,Added By,Added On,Submission Reactivated Date,Added By,Added On,Administrative Processing Completed Date,Added By,Added On,Administrative QC Completed Date,Added By,Added On,Scientific Processing Completed Date,Added By,Added On,Scientific QC Completed Date,Added By,Added On,Trial Summary Report Date,Added By,Added On,Submitter Trial Summary Report Feedback Date,Added By,Added On,Initial Abstraction Verified Date,Added By,Added On,On-going Abstraction Verified Date,Added By,Added On,Late Rejection Date,Added By,Added On",
                    lines.get(0));

            final String normalizedContent = content.replaceAll("\\s+", " ");
            final String expected = trial.nciID.replaceFirst("NCI-", "")
                    + ",ClinicalTrials.gov,1,,,,,,,,,,,Interventional,No,Accepted,"
                    + today
                    + ",,,,,Complete,,ClinicalTrials.gov,04/16/2014,Submission Acceptance Date,"
                    + today
                    + ",Other,2,,\"Ready for Admin Processing Ready for Scientific Processing\","
                    + today + ",," + today + "," + today + ",," + today
                    + ",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,";

            System.out.println(normalizedContent);
            System.out.println(expected);
            assertTrue(normalizedContent.contains(expected));

            csv.delete();
        }

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testAdminCheckOut() throws Exception {
        logoutUser();
        TrialInfo trial = createAcceptedTrial();
        loginAsAdminAbstractor();
        clickAndWait("id=dashboardMenuOption");
        clickAndWait("link=" + trial.nciID.replaceFirst("NCI-", ""));
        clickAndWait("link=Admin Check Out");
        assertTrue(s
                .isTextPresent("Message. Trial Check-Out (Admin) Successful"));
        assertEquals("My Dashboard - Administrative Abstractor", s.getTitle()
                .replaceAll("[^\\p{ASCII}]", "-"));
        clickAndWait("link=Check out History");
        assertEquals("Administrative",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[1]")
                        .trim());
        assertEquals("admin-ci",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[3]")
                        .trim());

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testScientificCheckOut() throws Exception {
        logoutUser();
        TrialInfo trial = createAcceptedTrial();
        loginAsScientificAbstractor();
        clickAndWait("id=dashboardMenuOption");
        clickAndWait("link=" + trial.nciID.replaceFirst("NCI-", ""));
        clickAndWait("link=Scientific Check Out");
        assertTrue(s
                .isTextPresent("Message. Trial Check-Out (Scientific) Successful"));
        assertEquals("My Dashboard - Scientific Abstractor", s.getTitle()
                .replaceAll("[^\\p{ASCII}]", "-"));
        clickAndWait("link=Check out History");
        assertEquals("Scientific",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[1]")
                        .trim());
        assertEquals("scientific-ci",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[3]")
                        .trim());

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSuperAbstractorCheckOut() throws Exception {
        logoutUser();
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        findAndSelectTrialInDashboard(trial);
        clickAndWait("link=Admin/Scientific Check Out");
        assertTrue(s
                .isTextPresent("Message. Trial Check-Out (Admin and Scientific) Successful"));
        assertEquals("My Dashboard - Super Abstractor", s.getTitle()
                .replaceAll("[^\\p{ASCII}]", "-"));
        clickAndWait("link=Check out History");
        assertEquals("Administrative",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[1]")
                        .trim());
        assertEquals("ctrpsubstractor",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[3]")
                        .trim());
        assertEquals("Scientific",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[2]/td[1]")
                        .trim());
        assertEquals("ctrpsubstractor",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[2]/td[3]")
                        .trim());

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSiteInterventionAndDiseaseCombinedSearch() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();

        runCombinedSearch();
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Adding site along is not sufficient to find.
        findAndSelectTrialInDashboard(trial);
        addAnatomicSite("Kidney");
        runCombinedSearch();
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Adding site and intervention still not enough.
        findAndSelectTrialInDashboard(trial);
        addInterevention("tarenflurbil");
        runCombinedSearch();
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Only when all 3 criterions present, trial found.
        findAndSelectTrialInDashboard(trial);
        addDisease("trichothiodystrophy");
        runCombinedSearch();
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

    }

    /**
     * 
     */
    private void runCombinedSearch() {
        goToDashboardSearch();
        useSelect2ToPickAnOption("diseases", "trichothiodystrophy",
                "trichothiodystrophy");
        useSelect2ToPickAnOption("interventions", "tarenflurbil",
                "tarenflurbil");
        useSelect2ToPickAnOption("anatomicSites", "kidney", "Kidney");
        clickAndWait("link=Search");
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testDiseaseSearch() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();

        // trichothiodystrophy not found.
        findAndSelectTrialInDashboard(trial);
        addDisease("xerostomia");
        goToDashboardSearch();
        useSelect2ToPickAnOption("diseases", "trichothiodystrophy",
                "trichothiodystrophy");
        clickAndWait("link=Search");
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Add trichothiodystrophy to disease list, and now trial found.
        findAndSelectTrialInDashboard(trial);
        addDisease("trichothiodystrophy");
        goToDashboardSearch();
        useSelect2ToPickAnOption("diseases", "trichothiodystrophy",
                "trichothiodystrophy");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Trial found using both diseases, too
        goToDashboardSearch();
        useSelect2ToPickAnOption("diseases", "trichothiodystrophy",
                "trichothiodystrophy");
        useSelect2ToPickAnOption("diseases", "xerostomia", "xerostomia");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // verify disease option removal.
        trial = createAcceptedTrial();
        findAndSelectTrialInDashboard(trial);
        addDisease("xerostomia");
        goToDashboardSearch();
        useSelect2ToPickAnOption("diseases", "trichothiodystrophy",
                "trichothiodystrophy");
        useSelect2ToPickAnOption("diseases", "xerostomia", "xerostomia");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        s.click("searchid");
        useSelect2ToUnselectOption("xerostomia");
        clickAndWait("link=Search");
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Verify selected values persist in search criteria and resets
        // properly.
        goToDashboardSearch();
        useSelect2ToPickAnOption("diseases", "trichothiodystrophy",
                "trichothiodystrophy");
        useSelect2ToPickAnOption("diseases", "xerostomia", "xerostomia");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        clickAndWait("//input[@type='button' and @value='Refresh']");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        s.click("searchid");
        assertOptionSelected("trichothiodystrophy");
        assertOptionSelected("xerostomia");
        clickAndWait("link=Reset");
        assertOptionNotSelected("trichothiodystrophy");
        assertOptionNotSelected("xerostomia");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent("At least one criteria is required."));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testDiseaseSearchWithDiseaseWidget() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();

        // trichothiodystrophy not found.
        findAndSelectTrialInDashboard(trial);
        addDisease("xerostomia");
        goToDashboardSearch();
        selectDiseasesUsingWidget("trichothiodystrophy");
        clickAndWait("link=Search");
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Add trichothiodystrophy to disease list, and now trial found.
        findAndSelectTrialInDashboard(trial);
        addDisease("trichothiodystrophy");
        goToDashboardSearch();
        selectDiseasesUsingWidget("trichothiodystrophy");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Trial found using both diseases, too
        goToDashboardSearch();
        selectDiseasesUsingWidget("trichothiodystrophy", "xerostomia");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // verify disease option removal.
        trial = createAcceptedTrial();
        findAndSelectTrialInDashboard(trial);
        addDisease("xerostomia");
        goToDashboardSearch();
        selectDiseasesUsingWidget("trichothiodystrophy");
        selectDiseasesUsingWidget("xerostomia");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        s.click("searchid");
        useSelect2ToUnselectOption("xerostomia");
        clickAndWait("link=Search");
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Verify selected values persist in search criteria and resets
        // properly.
        goToDashboardSearch();
        selectDiseasesUsingWidget("trichothiodystrophy");
        selectDiseasesUsingWidget("xerostomia");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        clickAndWait("//input[@type='button' and @value='Refresh']");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        s.click("searchid");
        assertOptionSelected("trichothiodystrophy");
        assertOptionSelected("xerostomia");
        clickAndWait("link=Reset");
        assertOptionNotSelected("trichothiodystrophy");
        assertOptionNotSelected("xerostomia");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent("At least one criteria is required."));
    }

    @SuppressWarnings("deprecation")
    private void selectDiseasesUsingWidget(String... diseases) {
        for (String disease : diseases) {
            selectDiseaseUsingWidget(disease);
        }
    }

    /**
     * @param disease
     */
    @SuppressWarnings("deprecation")
    private void selectDiseaseUsingWidget(String disease) {
        WebElement sitesBox = driver
                .findElement(By
                        .xpath("//span[preceding-sibling::select[@id='diseases']]//input[@type='search']"));
        sitesBox.click();
        assertTrue(s.isElementPresent("select2-diseases-results"));
        sitesBox.sendKeys(disease);

        By xpath = By.xpath("//li[@role='treeitem']//td/b[text()='" + disease
                + "']/../../td[2]/i[@class='fa fa-sitemap']");
        waitForElementToBecomeAvailable(xpath, 10);

        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(xpath));
        driver.findElement(xpath).click();

        driver.switchTo().frame("popupFrame");
        waitForElementToBecomeAvailable(By.id("pdq_tree_dialog"), 15);
        final By diseaseHighlightedInTree = By
                .xpath("//span[@class='pdq-tree-highlight' and text()='"
                        + disease + "']");
        waitForElementToBecomeAvailable(diseaseHighlightedInTree, 30);
        driver.findElement(diseaseHighlightedInTree).click();
        final By diseaseSelected = By
                .xpath("//span[@class='selectionFeaturedElement' and text()='"
                        + disease + "']");
        waitForElementToBecomeAvailable(diseaseSelected, 5);
        s.click("//span[@class='ui-button-icon-primary ui-icon ui-icon-closethick']");
        clickAndWait("//span[@class='add' and text()='Add']");
        driver.switchTo().defaultContent();
        waitForElementToBecomeAvailable(
                By.xpath(getXPathForSelectedOption(disease)), 10);
        assertOptionSelected(disease);
    }

    @SuppressWarnings("deprecation")
    private void addDisease(String disease) {
        clickAndWait("link=Disease/Condition");
        clickAndWait("link=Add");
        driver.switchTo().frame("popupFrame");
        s.type("disease", disease);
        clickAndWait("css=.search_inner_button");
        final String xpath = "//span[@class='breadcrumbHighlight' and text()='"
                + disease + "']";
        waitForElementToBecomeAvailable(By.xpath(xpath), 10);
        clickAndWait(xpath);
        clickAndWait("//span[@class='add' and text()='Add']");
        topWindow();

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testInterventionSearch() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();

        // tarenflurbil not found.
        findAndSelectTrialInDashboard(trial);
        addInterevention("pyroxamide");
        goToDashboardSearch();
        useSelect2ToPickAnOption("interventions", "tarenflurbil",
                "tarenflurbil");
        clickAndWait("link=Search");
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // now found.
        findAndSelectTrialInDashboard(trial);
        addInterevention("tarenflurbil");
        goToDashboardSearch();
        useSelect2ToPickAnOption("interventions", "tarenflurbil",
                "tarenflurbil");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // both found.
        trial = createAcceptedTrial();
        findAndSelectTrialInDashboard(trial);
        addInterevention("tarenflurbil");
        addInterevention("pyroxamide");
        goToDashboardSearch();
        useSelect2ToPickAnOption("interventions", "tarenflurbil",
                "tarenflurbil");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        goToDashboardSearch();
        useSelect2ToPickAnOption("interventions", "pyroxamide", "pyroxamide");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // add both, remove one, not found.
        trial = createAcceptedTrial();
        findAndSelectTrialInDashboard(trial);
        addInterevention("tarenflurbil");
        goToDashboardSearch();
        useSelect2ToPickAnOption("interventions", "tarenflurbil",
                "tarenflurbil");
        useSelect2ToPickAnOption("interventions", "pyroxamide", "pyroxamide");
        useSelect2ToUnselectOption("tarenflurbil");
        clickAndWait("link=Search");
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Verify selected values persist in search criteria and resets
        // properly.
        goToDashboardSearch();
        useSelect2ToPickAnOption("interventions", "tarenflurbil",
                "tarenflurbil");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        clickAndWait("//input[@type='button' and @value='Refresh']");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        s.click("searchid");
        assertOptionSelected("tarenflurbil");
        clickAndWait("link=Reset");
        assertOptionNotSelected("tarenflurbil");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent("At least one criteria is required."));

    }

    @SuppressWarnings("deprecation")
    private void addInterevention(String in) {
        clickAndWait("link=Interventions");
        clickAndWait("link=Add");
        clickAndWait("link=Look Up");
        driver.switchTo().frame("popupFrame");
        s.type("searchName", in);
        clickAndWait("link=Search");
        clickAndWait("//td[@class='action']//span[text()='Select']");
        pause(1000);
        driver.switchTo().defaultContent();
        clickAndWait("link=Save");
        assertTrue(s.isTextPresent("Message. Record Created."));

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testAnatomicSiteSearch() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();

        // Kidney not found.
        findAndSelectTrialInDashboard(trial);
        addAnatomicSite("Colon");
        goToDashboardSearch();
        useSelect2ToPickAnOption("anatomicSites", "kidney", "Kidney");
        clickAndWait("link=Search");
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Colon now found.
        goToDashboardSearch();
        useSelect2ToPickAnOption("anatomicSites", "colo", "Colon");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Colon & Kidney now found.
        goToDashboardSearch();
        useSelect2ToPickAnOption("anatomicSites", "kidney", "Kidney");
        useSelect2ToPickAnOption("anatomicSites", "colo", "Colon");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Add Colon & Kidney, then remove Colon. Not found.
        goToDashboardSearch();
        useSelect2ToPickAnOption("anatomicSites", "kidney", "Kidney");
        useSelect2ToPickAnOption("anatomicSites", "colo", "Colon");
        useSelect2ToUnselectOption("Colon");
        clickAndWait("link=Search");
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Verify selected values persist in search criteria and resets
        // properly.
        goToDashboardSearch();
        useSelect2ToPickAnOption("anatomicSites", "colo", "Colon");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        clickAndWait("//input[@type='button' and @value='Refresh']");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        s.click("searchid");
        assertOptionSelected("Colon");
        clickAndWait("link=Reset");
        assertOptionNotSelected("Colon");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent("At least one criteria is required."));

    }

    @SuppressWarnings("deprecation")
    public void useSelect2ToUnselectOption(String option) {
        s.click("//li[@class='select2-selection__choice' and @title='" + option
                + "']/span[@class='select2-selection__choice__remove']");
        assertFalse(s.isElementPresent(getXPathForSelectedOption(option)));

    }

    @SuppressWarnings("deprecation")
    public void useSelect2ToPickAnOption(String id, String sendKeys,
            String option) {
        WebElement sitesBox = driver.findElement(By
                .xpath("//span[preceding-sibling::select[@id='" + id
                        + "']]//input[@type='search']"));
        sitesBox.click();
        assertTrue(s.isElementPresent("select2-" + id + "-results"));
        sitesBox.sendKeys(sendKeys);

        By xpath = null;
        try {
            xpath = By.xpath("//li[@role='treeitem' and text()='" + option
                    + "']");
            waitForElementToBecomeAvailable(xpath, 3);
        } catch (TimeoutException e) {
            xpath = By.xpath("//li[@role='treeitem']//b[text()='" + option
                    + "']");
            waitForElementToBecomeAvailable(xpath, 15);
        }

        driver.findElement(xpath).click();
        assertOptionSelected(option);
    }

    /**
     * @param option
     */
    @SuppressWarnings("deprecation")
    public void assertOptionSelected(String option) {
        assertTrue(s.isElementPresent(getXPathForSelectedOption(option)));
    }

    /**
     * @param option
     * @return
     */
    public String getXPathForSelectedOption(String option) {
        return "//li[@class='select2-selection__choice' and @title='" + option
                + "']";
    }

    @SuppressWarnings("deprecation")
    public void assertOptionNotSelected(String option) {
        assertFalse(s.isElementPresent(getXPathForSelectedOption(option)));
    }

    /**
     * @param site
     */
    @SuppressWarnings("deprecation")
    private void addAnatomicSite(String site) {
        clickAndWait("link=Data Table 4 Anatomic Site");
        clickAndWait("link=Add");
        s.select("anatomicSite_code", site);
        clickAndWait("link=Save");
        assertTrue(s.isTextPresent("Message. Record Created."));
    }

    /**
     * @param trial
     */
    @SuppressWarnings("deprecation")
    private void findAndSelectTrialInDashboard(TrialInfo trial) {
        goToDashboardSearch();
        searchAndFindTrial(trial);
    }

    /**
     * @param trial
     */
    private void searchAndFindTrial(TrialInfo trial) {
        s.type("submittedOnOrAfter", "01/01/1990");
        clickAndWait("xpath=//a//span[text()='Search']");
        clickAndWait("xpath=//table[@id='results']//td/a[normalize-space(text())='"
                + trial.nciID.replaceFirst("NCI-", "") + "']");
    }

    /**
     * 
     */
    protected void goToDashboardSearch() {
        clickAndWait("id=dashboardMenuOption");
        clickAndWait("searchid");
    }
}
