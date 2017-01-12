package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.test.integration.AbstractPaSeleniumTest.TrialInfo;
import gov.nih.nci.pa.test.integration.support.Batch;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

/**
 * 
 * @author Reshma Koganti
 *
 */
@Batch(number = 1)
public class NCISpecificInformationTest extends AbstractPaSeleniumTest {

    public void setUp()  throws  Exception {
        super.setUp();
        setupFamilies();
    }

    @Test
     public void testConsortiaTrialCategoryCodeValues() throws SQLException {
         loginAsSuperAbstractor();
          TrialInfo info = createAcceptedTrial(true);
          logoutUser();
          selectTrialInPA(info);
          assertTrue(selenium.isElementPresent("link=NCI Specific Information"));
          clickAndWait("link=NCI Specific Information");
          assertTrue(selenium.isElementPresent("id=nciSpecificInformationWebDTO.consortiaTrialCategoryCode"));
          selenium.select("id=nciSpecificInformationWebDTO.consortiaTrialCategoryCode","label=No - Institutional");
          clickAndWait("link=Save");
          assertTrue(selenium.isTextPresent("Message. Record Updated."));
          String valueCode = getConsortiaTrialCategory(info);
          assertTrue(StringUtils.equalsIgnoreCase(valueCode, "INSTITUTIONAL"));
     }

    @SuppressWarnings("javadoc")
    @Test
    public void testValidationOnCancerTrials() throws Exception {
        //Given that a trial exist in submitted state
        deactivateAllTrials();
        TrialInfo trial = createAcceptedTrial(true, false, "National Cancer Institute");
        assignProgramCode(trial, 1, "PG1");

        // When I login as super abstractor  & select the trial
        selectTrialInPA(trial);

        //And go to Nci specific information page
        clickAndWait("link=NCI Specific Information");
        //Then I see program codes
        assertTrue(selenium.isTextPresent("Program Code:"));
        assertTrue(selenium.isTextPresent("Only Program Codes of the Lead Organization Family are displayed."));
        Select reportingMethodBox = new Select(driver.findElement(By.id("accrualReportingMethodCode")));
        reportingMethodBox.selectByIndex(0);

        //And save
        clickAndWait("link=Save");


        //I still see program codes
        assertTrue(selenium.isTextPresent("Program Code:"));

        //and PG1 is still selected
        assertOptionSelected("PG1 - ProgramCode1");

        //change reporting method
        reportingMethodBox = new Select(driver.findElement(By.id("accrualReportingMethodCode")));
        reportingMethodBox.selectByIndex(1);

        //Then I select PG1 and PG2
        useSelect2ToPickAnOption("programCodeIds", "PG2", "PG2 - ProgramCode2");

        //And save
        clickAndWait("link=Save");


        //Then the program codes must be associated to the trial and persisted in database
        assertEquals(2L, countStudyProgramCodes(trial.id));

        //Now deselect PG2
        useSelect2ToUnselectOption("PG2 - ProgramCode2");

        //click the save button
        clickAndWait("link=Save");

        //Then the program codes must be associated to the trial and persisted in database
        assertEquals(1L, countStudyProgramCodes(trial.id));

        //Then I logout
        logoutUser();

        //And If I come back in and go to the trial
        selectTrialInPA(trial);

        //And go to Nci specific information page
        clickAndWait("link=NCI Specific Information");

        //And deselect PG1
        useSelect2ToUnselectOption("PG1 - ProgramCode1");


        //click the save button
        clickAndWait("link=Save");

        //Then no program codes should be linked to this trial in database
        assertEquals(0L, countStudyProgramCodes(trial.id));

        //Then I logout
        logoutUser();

    }

    @SuppressWarnings("javadoc")
    @Test
    public void testValidationOnNonCancerTrials() throws Exception {
        //Given that no families are present for National Cancer Institute
        deleteProgramCodesOfFamily(1);
        deleteProgramCodesOfFamily(2);

        //And that a trial exist in submitted state
        deactivateAllTrials();
        TrialInfo trial = createAcceptedTrial(true, false, "National Cancer Institute");

        // When I login as super abstractor  & select the trial
        selectTrialInPA(trial);

        //And go to Nci specific information page
        clickAndWait("link=NCI Specific Information");

        //Then I should not see program code
        assertFalse(selenium.isTextPresent("Program Code:"));

        logoutUser();


    }
     
     private String getConsortiaTrialCategory(TrialInfo trial)
             throws SQLException {
         QueryRunner runner = new QueryRunner();
         return (String) runner
                 .query(connection,
                         "select consortia_trial_category from study_protocol "
                 + "where identifier="
                 + (trial.id != null ? trial.id : getTrialIdByNciId(trial.nciID)),
                         new ArrayHandler())[0];
     }



    public void setupFamilies() throws Exception {
        QueryRunner runner = new QueryRunner();
        for (int i : new int[]{1, 2} ) {
            int familyId = (Integer) runner.query(connection, "select identifier from family where po_id = " + i,
                    new ArrayHandler())[0];
            if (familyId <= 0) {
                Calendar c = Calendar.getInstance();
                c.add(Calendar.MONTH, 12);
                String endDate = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
                runner.update(connection,
                        String.format("INSERT INTO family(po_id, rep_period_end, rep_period_len_months) " +
                                "VALUES (%s, '%s', %s)", i, endDate, 12));
                familyId = (Integer) runner.query(connection, "select identifier from family where po_id = " + i,
                        new ArrayHandler())[0];
            }
            runner.update(connection, "DELETE FROM program_code where family_id = " + familyId);
            for (int j : new int[]{1,2,3,4,5,6}) {
                runner.update(connection,
                        String.format("INSERT INTO program_code(family_id, program_code, program_name, status_code) " +
                                "VALUES (%s, '%s', '%s', '%s')", familyId, "PG" + j, "ProgramCode" + j, "ACTIVE"));
            }

        }
    }

    private void deleteProgramCodesOfFamily(int familyPoId) throws Exception{
        QueryRunner runner = new QueryRunner();
        runner.update(connection, "DELETE FROM program_code where family_id = " +
                "(select identifier from family where po_id = " + familyPoId + ")");
    }

    private long countStudyProgramCodes(long trialId) throws Exception{
        QueryRunner runner = new QueryRunner();
        long cnt = (Long) runner.query(connection, "select count(*) from study_program_code where  study_protocol_id = " + trialId,
                new ArrayHandler())[0];
        return  cnt;
    }
}
