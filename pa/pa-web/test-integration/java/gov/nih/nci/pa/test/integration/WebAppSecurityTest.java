package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.test.integration.support.Batch;

import java.sql.SQLException;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

/**
 * Verifies resolution of AppScan-found security vulnerabilities.
 * 
 * @author dkrylov
 */
@Batch(number = 1)
public class WebAppSecurityTest extends AbstractPaSeleniumTest {

    @SuppressWarnings("deprecation")
    @Test
    public void testTrialHistoryActiveTabHandling_PO8081() throws SQLException {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);
        clickAndWait("link=Trial History");
        assertTrue(selenium.isTextPresent("Trial History Information"));

        // Verify normal tab operation
        openAndWait("/pa/protected/trialHistory.action?activeTab=updates&nciID="
                + trial.nciID);
        assertFalse(selenium.isVisible("submissions"));
        assertTrue(selenium.isVisible("updates"));
        assertFalse(selenium.isVisible("auditTrail"));

        // verify CSS handling
        openAndWait("/pa/protected/trialHistory.action?activeTab=updates%2F%3E%27%3B%3C%2Fscript%3E%3Ciframe+src%3Djavascript%3Aalert%2819816%29%3E"
                + "&nciID=" + trial.nciID);
        assertFalse(selenium.isVisible("updates"));
        assertFalse(selenium.isElementPresent("xpath=//table//iframe"));

    }

}
