package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.test.integration.support.Batch;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Tests for Trial Validation Page
 * 
 * @author josephb2
 */
@Batch(number = 2)
public class TrialValidationTest extends AbstractTrialStatusTest {

    public void setUp() throws Exception {
        super.setUp();
        setupFamilies();
    }

    @SuppressWarnings({ "javadoc", "deprecation" })
    @Test
    public void testProgramCodesFieldDoesNotDisappear() throws Exception {
        // Given that a trial exist in submitted state
        deactivateAllTrials();
        TrialInfo trial = createSubmittedTrial(false, false,
                "National Cancer Institute");

        // When I login as super abstractor
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);

        // I must be able to go to validation page
        clickLinkAndWait("Trial Validation");

        // Then I see program codes
        assertTrue(selenium.isTextPresent("Program Code:"));

        // Produce validation error.
        s.type("officialTitle", "");
        clickAndWait("saveButton");
        assertTrue(s.isElementPresent("xpath=//span[@class='formErrorMsg']"));
        assertTrue(selenium.isTextPresent("Program Code:"));
        s.type("officialTitle", trial.title);

        verifyProgramCodesField(trial);

        logoutUser();

    }

    /**
     * @param trial
     * @throws SQLException
     */
    @SuppressWarnings("deprecation")
    private void verifyProgramCodesField(TrialInfo trial) throws SQLException {
        useSelect2ToPickAnOption("programCodeIds", "PG1", "PG1 - ProgramCode1");
        useSelect2ToPickAnOption("programCodeIds", "PG2", "PG2 - ProgramCode2");

        // click the save button
        clickAndWait("saveButton");
        assertTrue(s.isElementPresent("xpath=//div[@class='confirm_msg']"));

        // Then the program codes must be associated to the trial and persisted
        // in database
        assertEquals(Arrays.asList(new String[] { "PG1", "PG2" }),
                getTrialProgramCodes(trial.id));

        // Now unselect PG2
        useSelect2ToUnselectOption("PG2 - ProgramCode2");

        // click the save button
        clickAndWait("saveButton");
        assertTrue(s.isElementPresent("xpath=//div[@class='confirm_msg']"));

        // Then the program codes must be associated to the trial and persisted
        // in database
        assertEquals(Arrays.asList(new String[] { "PG1" }),
                getTrialProgramCodes(trial.id));

        clickAndWait("acceptButton");
        assertTrue(s.isElementPresent("xpath=//div[@class='confirm_msg']"));
        assertEquals(Arrays.asList(new String[] { "PG1" }),
                getTrialProgramCodes(trial.id));
    }

    @SuppressWarnings("javadoc")
    @Test
    public void testValidationOnCancerTrials() throws Exception {
        // Given that a trial exist in submitted state
        deactivateAllTrials();
        TrialInfo trial = createSubmittedTrial(false, false,
                "National Cancer Institute");

        // When I login as super abstractor
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);

        // I must be able to go to validation page
        clickLinkAndWait("Trial Validation");

        // Then I see program codes
        assertTrue(selenium.isTextPresent("Program Code:"));

        verifyProgramCodesField(trial);

        logoutUser();

    }

    @SuppressWarnings("javadoc")
    @Test
    public void testValidationOnNonCancerTrials() throws Exception {
        // Given that no families are present for National Cancer Institute
        deleteProgramCodesOfFamily(1);
        deleteProgramCodesOfFamily(2);

        // And that a trial exist in submitted state
        deactivateAllTrials();
        TrialInfo trial = createSubmittedTrial(false, false,
                "National Cancer Institute");

        // When I login as super abstractor
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);

        // I must be able to go to validation page
        clickLinkAndWait("Trial Validation");

        // Then I should not see program code
        assertFalse(selenium.isTextPresent("Program Code:"));

        // click the save button
        clickAndWait("saveButton");
        assertTrue(s.isElementPresent("xpath=//div[@class='confirm_msg']"));
        clickAndWait("acceptButton");
        assertTrue(s.isElementPresent("xpath=//div[@class='confirm_msg']"));

    }

    public void setupFamilies() throws Exception {
        QueryRunner runner = new QueryRunner();
        for (int i : new int[] { 1, 2 }) {
            int familyId = (Integer) runner.query(connection,
                    "select identifier from family where po_id = " + i,
                    new ArrayHandler())[0];
            if (familyId <= 0) {
                Calendar c = Calendar.getInstance();
                c.add(Calendar.MONTH, 12);
                String endDate = new SimpleDateFormat("yyyy-MM-dd").format(c
                        .getTime());
                runner.update(connection, String.format(
                        "INSERT INTO family(po_id, rep_period_end, rep_period_len_months) "
                                + "VALUES (%s, '%s', %s)", i, endDate, 12));
                familyId = (Integer) runner.query(connection,
                        "select identifier from family where po_id = " + i,
                        new ArrayHandler())[0];
            }
            runner.update(connection,
                    "DELETE FROM program_code where family_id = " + familyId);
            for (int j : new int[] { 1, 2, 3, 4, 5, 6 }) {
                runner.update(connection, String.format(
                        "INSERT INTO program_code(family_id, program_code, program_name, status_code) "
                                + "VALUES (%s, '%s', '%s', '%s')", familyId,
                        "PG" + j, "ProgramCode" + j, "ACTIVE"));
            }

        }
    }

    private void deleteProgramCodesOfFamily(int familyPoId) throws Exception {
        QueryRunner runner = new QueryRunner();
        runner.update(connection, "DELETE FROM program_code where family_id = "
                + "(select identifier from family where po_id = " + familyPoId
                + ")");
    }

    private long countStudyProgramCodes(long trialId) throws Exception {
        QueryRunner runner = new QueryRunner();
        long cnt = (Long) runner.query(connection,
                "select count(*) from study_program_code where  study_protocol_id = "
                        + trialId, new ArrayHandler())[0];
        return cnt;
    }

}
