package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.test.integration.support.Batch;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Tests for ResultsAbstractorRoleActions
 * 
 * @author purams
 */
@Batch(number = 1)
public class ResultsAbstractorRoleActionsTest extends AbstractTrialStatusTest  {
    
    @Test
    public void testDesignDetailsStudyTypeSelectEnabled_Roles() throws SQLException, InterruptedException, IOException {
        TrialInfo trial = createAcceptedTrial();
        
        //SuAbstractor
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);
        clickAndWait("link=Design Details");
        assertTrue(((WebElement)driver.findElement(By.id("study"))).isEnabled());
        
        //ScientificAbstractor
        logoutUser();
        loginAsScientificAbstractor();
        searchAndSelectTrial(trial.title);
        clickAndWait("link=Design Details");
        assertTrue(((WebElement)driver.findElement(By.id("study"))).isEnabled());
        
        //AdminAbstractor
        logoutUser();
        loginAsAdminAbstractor();
        searchAndSelectTrial(trial.title);
        clickAndWait("link=Design Details");
        assertTrue(((WebElement)driver.findElement(By.id("study"))).isEnabled());
        
        // Result Abstractor
        logoutUser();
        loginAsResultsAbstractor();
        searchAndSelectTrial(trial.title);
        clickAndWait("link=Design Details");
        assertFalse(((WebElement)driver.findElement(By.id("study"))).isEnabled());
    }
    
    @SuppressWarnings("deprecation")
    @Test
    public void testTrialFundingNCIRadioButtonEnabled_Roles() throws SQLException, InterruptedException, IOException {
        TrialInfo trial = createAcceptedTrial();
        //Insert NIH Grand for TrialFunding
        String sql = "insert into study_resourcing values "
                + "( (SELECT NEXTVAL('HIBERNATE_SEQUENCE')),"
                + "null, 'FALSE',null," + trial.id
                + ",'G08','DK'," + "'OSB_SPOREs','1224','TRUE',null,null,null,"
                //+ userId + "," + userId + ",null)";
                + "null,null,null)";
        QueryRunner runner = new QueryRunner();
        runner.update(connection, sql);
        
        //SuAbstractor
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);
        clickAndWait("link=Trial Funding");
        assertTrue(((WebElement)driver.findElement(By.name("nciGrant"))).isEnabled());
        
        //ScientificAbstractor
        logoutUser();
        loginAsScientificAbstractor();
        searchAndSelectTrial(trial.title);
        clickAndWait("link=Trial Funding");
        assertTrue(((WebElement)driver.findElement(By.name("nciGrant"))).isEnabled());
        
        //AdminAbstractor
        logoutUser();
        loginAsAdminAbstractor();
        searchAndSelectTrial(trial.title);
        clickAndWait("link=Trial Funding");
        assertTrue(((WebElement)driver.findElement(By.name("nciGrant"))).isEnabled());
        
        // Result Abstractor
        logoutUser();
        loginAsResultsAbstractor();
        searchAndSelectTrial(trial.title);
        clickAndWait("link=Trial Funding");
        assertFalse(((WebElement)driver.findElement(By.name("nciGrant"))).isEnabled());
    }

    
    @SuppressWarnings("deprecation")
    @Test
    public void testAssociatedTrialsAddButon_Roles() throws SQLException, InterruptedException, IOException {
        TrialInfo trial = createAcceptedTrial();
        
        //SuAbstractor
        logoutUser();
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);
        clickAndWait("link=Associated Trials");
        assertTrue(selenium.isElementPresent("link=Add"));
        
        //ScientificAbstractor
        logoutUser();
        loginAsScientificAbstractor();
        searchAndSelectTrial(trial.title);
        clickAndWait("link=Associated Trials");
        assertTrue(selenium.isElementPresent("link=Add"));
        
        //AdminAbstractor
        logoutUser();
        loginAsAdminAbstractor();
        searchAndSelectTrial(trial.title);
        clickAndWait("link=Associated Trials");
        assertTrue(selenium.isElementPresent("link=Add"));
        
        // Result Abstractor
        logoutUser();
        loginAsResultsAbstractor();
        searchAndSelectTrial(trial.title);
        clickAndWait("link=Associated Trials");
        assertFalse(selenium.isElementPresent("link=Add"));
    }
}
