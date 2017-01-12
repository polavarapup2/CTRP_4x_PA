package gov.nih.nci.pa.test.integration;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import gov.nih.nci.pa.test.integration.support.Batch;

/**
 * Tests for TrialFunding Page
 * 
 * @author purams
 */
@Batch(number = 2)
public class TrialFundingTest extends AbstractTrialStatusTest {

    @SuppressWarnings("javadoc")
    @Test
    public void testTrialFundingNCIRadioButtonVisible() throws SQLException, InterruptedException, IOException {
        deactivateAllTrials();
        TrialInfo trial = createAcceptedTrial();
        // Insert NIH Grand for TrialFunding
        String sql = "insert into study_resourcing values " + "( (SELECT NEXTVAL('HIBERNATE_SEQUENCE')),"
                + "null, 'FALSE',null," + trial.id + ",'G08','DK'," + "'OSB_SPOREs','1224','TRUE',null,null,null,"
                + "null,null,null)";
        QueryRunner runner = new QueryRunner();
        runner.update(connection, sql);

        // SuAbstractor
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);
        clickAndWait("link=Trial Funding");
        assertTrue(((WebElement) driver.findElement(By.name("nciGrant"))).isDisplayed());
        flipNCIGrantRadioButton(trial);
    }

    @SuppressWarnings("javadoc")
    @Test
    public void testTrialFundingNCIRadioButtonVisible_NoGrants()
            throws SQLException, InterruptedException, IOException {
        deactivateAllTrials();
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);
        clickAndWait("link=Trial Funding");
        assertTrue(((WebElement) driver.findElement(By.name("nciGrant"))).isDisplayed());
        flipNCIGrantRadioButton(trial);
    }

    private void flipNCIGrantRadioButton(TrialInfo trial) throws SQLException {
        assertTrue(getNciGrant(trial) == false);
        ((WebElement) driver.findElement(By.name("nciGrant"))).click();
        waitForElementToBecomeVisible(By.id("ncigrantmessagediv"), 5);
        assertTrue(selenium.isTextPresent("Value updated to Yes"));
        pause(5000);
        assertTrue(getNciGrant(trial) == true);
    }

    private boolean getNciGrant(TrialInfo info) throws SQLException {
        String selectSql = "select nci_grant from study_protocol sp where  sp.status_code='ACTIVE' and sp.identifier = "
                + info.id;
        QueryRunner runner = new QueryRunner();
        return (Boolean) runner.query(connection, selectSql, new ArrayHandler())[0];
    }

}
