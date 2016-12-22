package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.enums.StudySourceCode;
import gov.nih.nci.pa.test.integration.support.Batch;
import gov.nih.nci.pa.util.PAConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 
 * @author dkrylov
 */
@Batch(number = 3)
public class TrialSearchTest extends AbstractTrialStatusTest {

    private static final int OP_WAIT_TIME = SystemUtils.IS_OS_LINUX ? 35000
            : 2000;

    @SuppressWarnings({ "deprecation", "unused", "unchecked" })
    @Test
    public void testCsvExport() throws Exception {
        TrialInfo trial = createSubmittedTrial();
        loginAsSuperAbstractor();
        clickAndWait("trialSearchMenuOption");
        s.type("identifier", trial.nciID);
        clickAndWait("link=Search");

        // Export banner must be at top and at bottom.
        assertTrue(s
                .isElementPresent("xpath=//form[@id='studyProtocolquery']/div[@class='exportlinks'][1]"));
        assertTrue(s
                .isElementPresent("xpath=//form[@id='studyProtocolquery']/div[@class='exportlinks'][2]/preceding-sibling::table[@id='row']"));

        // Finally, download CSV.
        if (!isPhantomJS()) {
            selenium.click("xpath=//a/span[normalize-space(text())='CSV']");
            pause(OP_WAIT_TIME);
            File csv = new File(downloadDir, "SearchTrialResults.csv");
            assertTrue(csv.exists());
            csv.deleteOnExit();

            List<String> lines = FileUtils.readLines(csv);
            String content = FileUtils.readFileToString(csv);
            assertEquals(
                    "NCI Trial Identifier,Lead Organization,Lead Org PO ID,Processing Priority,CTEP ID,DCP ID,Official Title,Milestone,Admin Milestone,Scientific Milestone,Processing Status,Processing Status Date,Trial Type,Trial Sub-type,Record Verification Date,Onhold Reasons,Onhold Dates,Submission Type,Submission Source,Checked Out for Admin. Use by,Checked Out for Scientific Use by",
                    lines.get(0));
            assertTrue(content.replaceAll("\\s+", " ").contains(
                    trial.nciID + ",ClinicalTrials.gov,1,2,,," + trial.title
                            + ",\"Submission Received Date " + today
                            + "\",,,Submitted," + today
                            + ",Interventional,,,,,O,Other,,"));

            csv.delete();
        }

    }

    @SuppressWarnings({ "deprecation", "unused", "unchecked" })
    @Test
    public void testTSRExport() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        clickAndWait("trialSearchMenuOption");
        s.type("identifier", trial.nciID);
        clickAndWait("link=Search");

        // Finally, download CSV.
        if (!isPhantomJS()) {
            String fileName = "TsrReport_"
                    + DateFormatUtils.format(new Date(),
                            PAConstants.TSR_DATE_FORMAT) + ".rtf";
            selenium.click("xpath=//a[normalize-space(text())='View TSR']");
            pause(OP_WAIT_TIME);
            File tsr = new File(downloadDir, fileName);
            assertTrue(tsr.exists());
            tsr.deleteOnExit();
            tsr.delete();
        }
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSearchByStudySource() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();

        for (StudySourceCode code : StudySourceCode.values()) {
            QueryRunner runner = new QueryRunner();
            String sql = "update study_protocol set study_source='"
                    + code.name() + "' where identifier=" + trial.id;
            runner.update(connection, sql);
            runSearch("studySourceType", new String[] { code.getCode() });
            assertTrue(isTrialInSearchResults(trial));

            List<String> list = new ArrayList<>();
            for (StudySourceCode code2 : StudySourceCode.values()) {
                if (code != code2) {
                    list.add(code2.getCode());
                }
            }
            runSearch("studySourceType", list.toArray(new String[0]));
        }

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSearchBySubmissionType() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        runSearch("submissionType", new String[] { "Original" });
        assertTrue(isTrialInSearchResults(trial));
        runSearch("submissionType", new String[] { "Update" });
        assertFalse(isTrialInSearchResults(trial));
        runSearch("submissionType", new String[] { "Amendment" });
        assertFalse(isTrialInSearchResults(trial));

        // now create a study inbox; this will make the trial submission an
        // Update or Original.
        createStudyInbox(trial);
        runSearch("submissionType", new String[] { "Original" });
        assertTrue(isTrialInSearchResults(trial));
        runSearch("submissionType", new String[] { "Amendment" });
        assertFalse(isTrialInSearchResults(trial));
        runSearch("submissionType", new String[] { "Update" });
        assertTrue(isTrialInSearchResults(trial));

        // now pretend this is an amended trial.
        trial = createAcceptedTrial();
        QueryRunner runner = new QueryRunner();
        String sql = "update study_protocol set submission_number=2, amendment_date=now() where identifier="
                + trial.id;
        runner.update(connection, sql);
        runSearch("submissionType", new String[] { "Original", "Update" });
        assertFalse(isTrialInSearchResults(trial));
        runSearch("submissionType", new String[] { "Amendment" });
        assertTrue(isTrialInSearchResults(trial));

        // And by the way, amendment with unack. updates is Amendment and
        // Update.
        createStudyInbox(trial);
        runSearch("submissionType", new String[] { "Original" });
        assertFalse(isTrialInSearchResults(trial));
        runSearch("submissionType", new String[] { "Update" });
        assertTrue(isTrialInSearchResults(trial));
        runSearch("submissionType", new String[] { "Amendment" });
        assertTrue(isTrialInSearchResults(trial));

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSearchMilestones() throws Exception {
        TrialInfo trial = createSubmittedTrial();
        loginAsSuperAbstractor();
        runSearch("studyMilestone",
                new String[] { "Submission Acceptance Date" });
        assertFalse(isTrialInSearchResults(trial));
        runSearch("studyMilestone", new String[] { "Submission Received Date" });
        assertTrue(isTrialInSearchResults(trial));

        trial = createAcceptedTrial();
        runSearch("studyMilestone",
                new String[] { "Submission Acceptance Date" });
        assertTrue(isTrialInSearchResults(trial));
        runSearch("studyMilestone", new String[] { "Submission Received Date" });
        assertFalse(isTrialInSearchResults(trial));

        // Ensure last milestone is determined by latest milestone date, not
        // just biggest ID.
        addMilestone(trial, "SUBMISSION_REJECTED", yday_midnight());
        runSearch("studyMilestone",
                new String[] { "Submission Acceptance Date" });
        assertTrue(isTrialInSearchResults(trial));
        runSearch("studyMilestone",
                new String[] { "Submission Rejection Date" });
        assertFalse(isTrialInSearchResults(trial));

        addMilestone(trial, "SUBMISSION_REJECTED", today());
        runSearch("studyMilestone",
                new String[] { "Submission Rejection Date" });
        assertTrue(isTrialInSearchResults(trial));

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSearchProcessingStatus() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        runSearch("documentWorkflowStatusCode", new String[] { "Submitted" });
        assertFalse(isTrialInSearchResults(trial));
        runSearch("documentWorkflowStatusCode", new String[] { "Accepted" });
        assertTrue(isTrialInSearchResults(trial));

        addDWS(trial, "ABSTRACTED");
        runSearch("documentWorkflowStatusCode", new String[] { "Submitted",
                "Submission Terminated", "Accepted", "Rejected",
                "Verification Pending", "Abstraction Verified Response",
                "Abstraction Verified No Response", "On-Hold" });
        assertFalse(isTrialInSearchResults(trial));
        runSearch("documentWorkflowStatusCode", new String[] { "Submitted",
                "Abstracted" });
        assertTrue(isTrialInSearchResults(trial));

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSearchTrialStatus() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        runSearch("studyStatusCode", new String[] { "Approved" });
        assertTrue(isTrialInSearchResults(trial));

        runSearch("studyStatusCode", new String[] { "In Review", "Active" });
        assertFalse(isTrialInSearchResults(trial));

        // This will verify that between two statuses on the same date, the one
        // with largest ID is the current.
        addSOS(trial, "ACTIVE");
        runSearch("studyStatusCode", new String[] { "Active" });
        assertTrue(isTrialInSearchResults(trial));
        runSearch("studyStatusCode", new String[] { "In Review" });
        assertFalse(isTrialInSearchResults(trial));

        // this will verify latest trial status is NOT determined by the largest
        // ID value, but rather by status date.
        addSOS(trial, "IN_REVIEW", yday_midnight());
        addSOS(trial, "APPROVED", yday_midnight());
        runSearch("studyStatusCode", new String[] { "In Review", "Approved" });
        assertFalse(isTrialInSearchResults(trial));
        runSearch("studyStatusCode", new String[] { "Active" });
        assertTrue(isTrialInSearchResults(trial));

        // verify deleted statuses are handled properly during search.
        runSearch("studyStatusCode", new String[] { "Complete" });
        assertFalse(isTrialInSearchResults(trial));
        addSOS(trial, "COMPLETE");
        runSearch("studyStatusCode", new String[] { "Complete" });
        assertTrue(isTrialInSearchResults(trial));
        deleteCurrentTrialStatus(trial);
        runSearch("studyStatusCode", new String[] { "Complete" });
        assertFalse(isTrialInSearchResults(trial));
        runSearch("studyStatusCode", new String[] { "Active" });
        assertTrue(isTrialInSearchResults(trial));

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSearchByPhase() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        runSearch("phaseCode", new String[] { "II" });
        assertTrue(isTrialInSearchResults(trial));

        runSearch("phaseCode", new String[] { "0", "I", "I/II", "II/III",
                "III", "IV", "NA" });
        assertFalse(isTrialInSearchResults(trial));

        runSearch("phaseCode", new String[] { "0", "I", "I/II", "II/III",
                "III", "IV", "NA", "II" });
        assertTrue(isTrialInSearchResults(trial));

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSearchByPrimaryPurpose() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        runSearch("primaryPurpose", new String[] { "Treatment" });
        assertTrue(isTrialInSearchResults(trial));

        runSearch("primaryPurpose", new String[] { "Supportive Care",
                "Screening", "Diagnostic", "Health Services Research",
                "Basic Science", "Prevention", "Other" });
        assertFalse(isTrialInSearchResults(trial));

        runSearch("primaryPurpose", new String[] { "Supportive Care",
                "Screening", "Diagnostic", "Health Services Research",
                "Basic Science", "Prevention", "Other", "Treatment" });
        assertTrue(isTrialInSearchResults(trial));

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSearchByPI() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        runSearch("principalInvestigatorId", new String[] { "Doe,John" });
        assertTrue(isTrialInSearchResults(trial));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSearchByLeadOrg() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        pause(180 * 1000); // Let PA cache expire.
        runSearch("leadOrganizationId", new String[] { "ClinicalTrials.gov" });
        assertTrue(isTrialInSearchResults(trial));

        replaceLeadOrg(trial, "Cancer Therapy Evaluation Program");
        pause(180 * 1000); // Let PA cache expire.
        runSearch("leadOrganizationId", new String[] { "ClinicalTrials.gov" });
        assertFalse(isTrialInSearchResults(trial));
        runSearch("leadOrganizationId",
                new String[] { "Cancer Therapy Evaluation Program" });
        assertTrue(isTrialInSearchResults(trial));

        runSearch("leadOrganizationId", new String[] { "ClinicalTrials.gov",
                "Cancer Therapy Evaluation Program" });
        assertTrue(isTrialInSearchResults(trial));

    }

    @SuppressWarnings("deprecation")
    private boolean isTrialInSearchResults(TrialInfo trial) {
        int page = 1;
        while (!s.isElementPresent("xpath=//table[@id='row']//tr/td/a[text()='"
                + trial.nciID + "']")
                && s.isElementPresent("xpath=//a[@title='Go to page "
                        + (++page) + "']")) {
            clickAndWait("xpath=//a[@title='Go to page " + (page) + "']");
        }
        return s.isElementPresent("xpath=//table[@id='row']//tr/td/a[text()='"
                + trial.nciID + "']");
    }

    @SuppressWarnings("deprecation")
    private void runSearch(String fieldID, String[] values) {
        clickAndWait("trialSearchMenuOption");
        for (String value : values) {
            useSelect2ToPickAnOption(fieldID, value, value);
        }
        clickAndWait("link=Search");

        // verify option remains selected after search screen comes back.
        for (String value : values) {
            assertOptionSelected(value);
        }

        // Make sure Reset works.
        clickAndWait("link=Reset");
        for (String value : values) {
            assertOptionNotSelected(value);
        }
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent("At least one criteria is required."));

        // Make sure un-selecting is working too.
        for (String value : values) {
            useSelect2ToPickAnOption(fieldID, value, value);
            useSelect2ToUnselectOption(value);
            assertOptionNotSelected(value);
        }
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent("At least one criteria is required."));

        // Finally, do the search.
        for (String value : values) {
            useSelect2ToPickAnOption(fieldID, value, value);
        }
        clickAndWait("link=Search");
        clickAndWait("link=Search");

    }

   
}
