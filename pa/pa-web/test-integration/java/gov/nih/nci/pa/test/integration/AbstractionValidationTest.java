package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.test.integration.support.Batch;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;

/**
 * Tests for AbstractionValidation.
 * 
 * @author purams
 */
@Batch(number = 1)
public class AbstractionValidationTest extends AbstractTrialStatusTest {

    @SuppressWarnings("deprecation")
    @Test
    public void testAbstractionValidationWithError_ArmTypeInterventional()
            throws SQLException, InterruptedException, IOException {
        TrialInfo trial = createAcceptedTrial();

        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);

        clickAndWait("link=Design Details");
        s.select("webDTO.studyType", "Non-Interventional");
        String arm = "ARM I";
        addArmNonInterventional(arm);

        clickAndWait("link=Abstraction Validation");
        assertFalse(s.isTextPresent("Arm Type is required: "+arm));

        clickAndWait("link=Design Details");
        s.select("webDTO.studyType", "Interventional");
        clickAndWait("link=Save");
        waitForPageToLoad();

        clickAndWait("link=Abstraction Validation");
        assertTrue(s.isTextPresent("Arm Type is required: "+arm));
    }

    private void addArmNonInterventional(String arm) {
        clickAndWait("link=Groups/Cohorts");
        assertTrue(selenium.isElementPresent("link=Add"));
        clickAndWait("link=Add");
        selenium.type("armName", arm);
        selenium.type("armDescription", "Arm Description");
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Record Created"));
    }

}
