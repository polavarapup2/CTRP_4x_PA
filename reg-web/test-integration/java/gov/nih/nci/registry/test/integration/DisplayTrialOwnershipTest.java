package gov.nih.nci.registry.test.integration;

import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;

/**
 * @author Denis G. Krylov
 */
@Ignore
public class DisplayTrialOwnershipTest extends AbstractRegistrySeleniumTest {

    private static final int WAIT_FOR_ELEMENT_TIMEOUT = 30;

    /**
     * 
     */
    private void goToDisplayTrialOwnershipScreen() {
        loginAndAcceptDisclaimer();
        openAndWait("/registry/siteadmin/displayTrialOwnershipsearch.action");
    }

    @Test
    public void testSearch_PO_7492() throws SQLException {
        goToDisplayTrialOwnershipScreen();
        TrialInfo trial = createAcceptedTrial(true);
        assignTrialOwner("abstractor-ci", trial.id);
        changeRegUserAffiliation("abstractor-ci", 1, "ClinicalTrials.gov");

        selenium.type("id=nciIdentifier", trial.nciID);
        driver.findElement(By.className("fa-search")).click();

        assertTrue(selenium.isTextPresent("Showing 1 to 1 of 1"));
        assertTrue(selenium.isTextPresent(trial.leadOrgID));

        selenium.type("id=nciIdentifier", trial.nciID.substring(3));
        driver.findElement(By.className("fa-search")).click();
        assertTrue(selenium.isTextPresent("Showing 1 to 1 of 1"));
        assertTrue(selenium.isTextPresent(trial.leadOrgID));

    }

}
