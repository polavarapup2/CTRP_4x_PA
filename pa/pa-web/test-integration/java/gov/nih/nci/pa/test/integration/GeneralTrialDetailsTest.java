package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.test.integration.support.Batch;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.junit.Test;

/**
 * Tests for Trial Validation Page
 * 
 * @author josephb2
 */
@Batch(number = 3)
public class GeneralTrialDetailsTest extends AbstractTrialStatusTest {

    public void setUp() throws Exception {
        super.setUp();
        setupFamilies();
    }

    @SuppressWarnings({ "javadoc", "deprecation" })
    @Test
    public void testSaveDoesNotWipeOutProgramCodes() throws Exception {
        // Given that a trial exist in submitted state
        TrialInfo trial = createAcceptedTrial(false,
                "National Cancer Institute");

        // When I login as super abstractor
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);
        assignProgramCode(trial, 1, "PG1");
        assignProgramCode(trial, 1, "PG7");

        // I must be able to go to validation page
        clickLinkAndWait("General Trial Details");

        // click the save button
        clickAndWait("xpath=//a[@class='btn']//span[@class='save']");
        assertTrue(s.isElementPresent("xpath=//div[@class='confirm_msg']"));
        assertEquals(Arrays.asList(new String[] { "PG1", "PG7" }),
                getTrialProgramCodes(trial));
        clickAndWait("xpath=//a[@class='btn']//span[@class='save']");
        assertTrue(s.isElementPresent("xpath=//div[@class='confirm_msg']"));
        assertEquals(Arrays.asList(new String[] { "PG1", "PG7" }),
                getTrialProgramCodes(trial));

    }

}
